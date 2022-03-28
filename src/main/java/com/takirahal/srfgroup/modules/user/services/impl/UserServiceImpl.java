package com.takirahal.srfgroup.modules.user.services.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.takirahal.srfgroup.constants.AuthoritiesConstants;
import com.takirahal.srfgroup.modules.address.mapper.AddressMapper;
import com.takirahal.srfgroup.modules.notification.entities.Notification;
import com.takirahal.srfgroup.modules.notification.enums.ModuleNotification;
import com.takirahal.srfgroup.modules.notification.repositories.NotificationRepository;
import com.takirahal.srfgroup.modules.user.dto.*;
import com.takirahal.srfgroup.exceptions.BadRequestAlertException;
import com.takirahal.srfgroup.modules.user.entities.UserOneSignal;
import com.takirahal.srfgroup.modules.user.exceptioins.InvalidPasswordException;
import com.takirahal.srfgroup.modules.user.services.UserOneSignalService;
import com.takirahal.srfgroup.security.CustomUserDetailsService;
import com.takirahal.srfgroup.security.JwtTokenProvider;
import com.takirahal.srfgroup.security.UserPrincipal;
import com.takirahal.srfgroup.services.impl.MailService;
import com.takirahal.srfgroup.services.impl.ResizeImage;
import com.takirahal.srfgroup.services.impl.StorageService;
import com.takirahal.srfgroup.modules.user.entities.Authority;
import com.takirahal.srfgroup.modules.user.entities.User;
import com.takirahal.srfgroup.modules.user.exceptioins.AccountResourceException;
import com.takirahal.srfgroup.modules.user.exceptioins.EmailAlreadyUsedException;
import com.takirahal.srfgroup.exceptions.ResouorceNotFoundException;
import com.takirahal.srfgroup.modules.user.mapper.UserMapper;
import com.takirahal.srfgroup.modules.user.repositories.AuthorityRepository;
import com.takirahal.srfgroup.modules.user.repositories.UserRepository;
import com.takirahal.srfgroup.modules.user.services.UserService;
import com.takirahal.srfgroup.utils.RandomUtil;
import com.takirahal.srfgroup.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    MailService mailService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    StorageService storageService;

    @Autowired
    ResizeImage resizeImage;

    @Autowired
    AddressMapper addressMapper;

    @Value("${dynamicsvariables.googleClientId}")
    private String googleClientId;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    UserOneSignalService userOneSignalService;

    @Autowired
    NotificationRepository notificationRepository;

    @Override
    public User registerUser(RegisterDTO registerDTO) {
        log.debug("REST request for register user {}", registerDTO);
        if (isPasswordLengthInvalid(registerDTO.getPassword())) {
            throw new InvalidPasswordException("Incorrect password");
        }
        userRepository
                .findOneByEmailIgnoreCase(registerDTO.getEmail())
                .ifPresent(
                        existingUser -> {
                            throw new EmailAlreadyUsedException("Email is already in use!");
                        }
                );

        User newUser = new User();
        newUser.setEmail(registerDTO.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        newUser.setFirstName("");
        newUser.setLastName("");
        newUser.setUsername(registerDTO.getEmail());
        newUser.setActivated(false);
        newUser.setActivationKey(RandomUtil.generateActivationKey(20));
        newUser.setSourceRegister(registerDTO.getSourceRegister());

        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
        newUser.setAuthorities(authorities);
        User user = userRepository.save(newUser);

        mailService.sendActivationEmail(newUser);

        // Register one signal id
        if(registerDTO.getIdOneSignal()!=null){
            UserOneSignalDTO userOneSignalDTO = new UserOneSignalDTO();
            userOneSignalDTO.setIdOneSignal(registerDTO.getIdOneSignal());
            userOneSignalDTO.setUser(userMapper.toDtoIdEmail(user));
            userOneSignalService.save(userOneSignalDTO);
        }

        // Add Welcome notification
        addWelcomeNotification(user);

        return user;
    }

    @Override
    public Optional<User> activateRegistration(String key) {
        log.debug("REST request to Activating user for activation key {}", key);
        return userRepository
                .findOneByActivationKey(key)
                .map(
                        user -> {
                            // activate given user for the registration key.
                            user.setActivated(true);
                            user.setActivationKey(null);
                            log.debug("Activated user: {}", user);
                            return user;
                        }
                );
    }

    @Override
    public UserDTO findById(Long id) {
        return userRepository.findById(id)
                .map(user -> userMapper.toDtoPublicUser(user))
                .orElseThrow(() -> new ResouorceNotFoundException("Not found user with id "+id));
    }

    @Override
    public UserDTO getCurrentUser() {
        return SecurityUtils.getEmailByCurrentUser()
                .flatMap(userRepository::findOneByEmailIgnoreCase)
                .map(userMapper::toCurrentUser)
                .orElseThrow(() -> new AccountResourceException("Current user login not found"));
    }

    @Override
    public String signinClient(LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getEmail(),
                            loginDTO.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.generateToken(authentication);

            // Register one signal id if not exist already
            saveOneSignal(loginDTO.getIdOneSignal());

            return jwt;
        }
        catch(BadCredentialsException e){
            throw new InvalidPasswordException("Bad Credentials");
        }
    }

    @Override
    public String signInAdmin(LoginDTO loginDTO) {
        log.debug("REST request to signin admin: {} ", loginDTO);
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(loginDTO.getEmail());
        if( !existingUser.isPresent() ){
            throw new AccountResourceException("Not found user with email");
        }

        if( !SecurityUtils.hasUserThisAuthority(existingUser.get().getAuthorities(), AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.hasUserThisAuthority(existingUser.get().getAuthorities(), AuthoritiesConstants.SUPER_ADMIN)){
            throw new BadRequestAlertException("Not admin");
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getEmail(),
                            loginDTO.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return tokenProvider.generateToken(authentication);
        }
        catch(BadCredentialsException e){
            throw new InvalidPasswordException("Bad Credentials");
        }
    }

    @Override
    public UserDTO updateAvatar(MultipartFile file) {
        log.debug("REST request to update avatar: {} ");
        Long currentUserId = SecurityUtils
                .getIdByCurrentUser()
                .orElseThrow(() -> new AccountResourceException("Current user login not found"));


        Optional<User> existingUser = userRepository.findById(currentUserId);
        if(!existingUser.isPresent()){
            throw new AccountResourceException("Current user login not found");
        }

        existingUser.get().setImageUrl(file.getOriginalFilename());
        userRepository.save(existingUser.get());

        String pathAvatarUser = storageService.getBaseStorageUserImages() + currentUserId;

        Path rootLocation = Paths.get(pathAvatarUser);
        if (storageService.existPath(pathAvatarUser)) { // Already exixit path
            storageService.store(file, rootLocation);
        } else { // Create  new path
            storageService.init(pathAvatarUser);
            storageService.store(file, rootLocation);
        }

        // Resize
        resizeImage.resizeAvatar(storageService.getBaseStorageUserImages() + currentUserId + "/" + file.getOriginalFilename());

        return userMapper.toDto(existingUser.get());
    }

    @Override
    public Resource getAvatar(Long id, String filename) {
        Path rootLocation = Paths.get(storageService.getBaseStorageUserImages() + id);
        return storageService.loadFile(filename, rootLocation);
    }

    @Override
    public UserDTO updateCurrentUser(UserDTO user) {
        log.debug("REST request to update infos: {} ", user);
        Long userId = SecurityUtils.getIdByCurrentUser()
                .orElseThrow(() -> new AccountResourceException("Current user login not found"));

        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new AccountResourceException("Current user login not found"));

        currentUser.setFirstName(user.getFirstName());
        currentUser.setLastName(user.getLastName());
        currentUser.setPhone(user.getPhone());
        currentUser.setAddress(addressMapper.toEntity(user.getAddress()));

        User newUser = userRepository.save(currentUser);

        return userMapper.toCurrentUser(newUser);
    }

    @Override
    public Boolean updatePasswordCurrentUser(UpdatePasswordDTO updatePasswordDTO) {
        log.debug("REST request to update password: {} ", updatePasswordDTO);
        if (isPasswordLengthInvalid(updatePasswordDTO.getNewPassword())) {
            throw new InvalidPasswordException("Inavalid length password");
        }

        Long userId = SecurityUtils.getIdByCurrentUser()
                .orElseThrow(() -> new AccountResourceException("Current user login not found"));

        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new AccountResourceException("Current user login not found"));

        String currentEncryptedPassword = currentUser.getPassword();

        if (!passwordEncoder.matches(updatePasswordDTO.getCurrentPassword(), currentEncryptedPassword)) {
            throw new InvalidPasswordException("Invalid old password");
        }

        String encryptedPassword = passwordEncoder.encode(updatePasswordDTO.getNewPassword());
        currentUser.setPassword(encryptedPassword);
        userRepository.save(currentUser);
        return Boolean.TRUE;
    }

    @Override
    public String signinGooglePlus(GooglePlusVM googlePlusVM) throws IOException{
        final NetHttpTransport transport = new NetHttpTransport();
        final JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();
        GoogleIdTokenVerifier.Builder verifier = new GoogleIdTokenVerifier.Builder(transport, jacksonFactory)
                .setAudience(Collections.singletonList(googleClientId));
        final GoogleIdToken googleIdToken = GoogleIdToken.parse(verifier.getJsonFactory(), googlePlusVM.getTokenId());
        final GoogleIdToken.Payload payload = googleIdToken.getPayload();

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(payload.getEmail());
        userDTO.setEmail(payload.getEmail());
        userDTO.setFirstName(googlePlusVM.getProfileObj().getFamilyName());
        userDTO.setLastName(googlePlusVM.getProfileObj().getGivenName());
        userDTO.setImageUrl(googlePlusVM.getProfileObj().getImageUrl());
        userDTO.setSourceRegister(googlePlusVM.getSourceProvider());
        Set<Authority> authorities = new HashSet<>();
        Authority authority = new Authority();
        authority.setName(AuthoritiesConstants.USER);
        authorities.add(authority);
        userDTO.setAuthorities(authorities);

        Optional<User> userExist = userRepository.findOneByEmailIgnoreCase(payload.getEmail());
        if (userExist.isPresent()) {
            // Update user
            userDTO.setId(userExist.get().getId());
        }

        // Save new User
        User user = userMapper.toEntity(userDTO);
        user.setPassword(RandomUtil.generateActivationKey(20));
        user.setActivated(true);
        User newUser = userRepository.save(user);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userMapper.toDto(newUser).getEmail());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                "",
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        String jwt = tokenProvider.createToken(authenticationToken, true);


        // Register one signal id if not exist already
        saveOneSignal(googlePlusVM.getIdOneSignal());


        // Add Welcome notification first time
        if (!userExist.isPresent()) {
            addWelcomeNotification(user);
        }

        return jwt;
    }

    @Override
    public String signinFacebook(FacebookVM facebookVM) {
        Facebook facebook = new FacebookTemplate(facebookVM.getAccessToken());
        final String[] fields = { "email", "picture" };
        FacebookVM userFacebook = facebook.fetchObject("me", FacebookVM.class, fields);

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(userFacebook.getEmail());
        userDTO.setEmail(facebookVM.getEmail());
        userDTO.setFirstName(facebookVM.getName());
        userDTO.setLastName(facebookVM.getName());
        userDTO.setImageUrl(facebookVM.getPicture().getData().getUrl());
        userDTO.setSourceRegister(facebookVM.getSourceProvider());
        Set<Authority> authorities = new HashSet<>();
        Authority authority = new Authority();
        authority.setName(AuthoritiesConstants.USER);
        authorities.add(authority);
        userDTO.setAuthorities(authorities);

        Optional<User> userExist = userRepository.findOneByEmailIgnoreCase(userFacebook.getEmail());
        if (userExist.isPresent()) {
            // Update user
            userDTO.setId(userExist.get().getId());
        }

        // Save new User
        User user = userMapper.toEntity(userDTO);
        user.setPassword(RandomUtil.generateActivationKey(20));
        user.setActivated(true);
        User newUser = userRepository.save(user);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userMapper.toDto(newUser).getEmail());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                "",
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        String jwt = tokenProvider.createToken(authenticationToken, true);

        // Register one signal id if not exist already
        saveOneSignal(facebookVM.getIdOneSignal());

        // Add Welcome notification first time
        if (!userExist.isPresent()) {
            addWelcomeNotification(user);
        }

        return jwt;
    }

    /**
     *
     * @param idOneSignal
     */
    private void saveOneSignal(String idOneSignal){
        if(idOneSignal!=null && !ObjectUtils.isEmpty(idOneSignal)){
            Optional<UserPrincipal> userPrincipalOptional = SecurityUtils.getCurrentUser();
            Optional<UserOneSignal> userOneSignal = userOneSignalService.findByIdOneSignalAndUser(idOneSignal,userMapper.currentUserToEntity(userPrincipalOptional.get()));
            if(!userOneSignal.isPresent()){
                UserOneSignalDTO userOneSignalDTO = new UserOneSignalDTO();
                userOneSignalDTO.setIdOneSignal(idOneSignal);
                userOneSignalDTO.setUser(userMapper.toCurrentUserPrincipal(userPrincipalOptional.get()));
                userOneSignalService.save(userOneSignalDTO);
            }
        }
    }

    /**
     *
     * @param user
     */
    private void addWelcomeNotification(User user){
        Notification notification = new Notification();
        notification.setDateCreated(Instant.now());
        notification.setContent("Welcome in Espace SrfGroup");
        notification.setModule(ModuleNotification.AdminNotification.name());
        notification.setUser(user);
        notificationRepository.save(notification);
    }

    private static boolean isPasswordLengthInvalid(String password) {
        return (
                StringUtils.isEmpty(password) ||
                        password.length() < RegisterDTO.PASSWORD_MIN_LENGTH ||
                        password.length() > RegisterDTO.PASSWORD_MAX_LENGTH
        );
    }
}

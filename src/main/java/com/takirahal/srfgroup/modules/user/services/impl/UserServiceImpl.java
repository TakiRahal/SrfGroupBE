package com.takirahal.srfgroup.modules.user.services.impl;

import com.takirahal.srfgroup.constants.AuthoritiesConstants;
import com.takirahal.srfgroup.modules.user.dto.LoginDTO;
import com.takirahal.srfgroup.modules.user.dto.RegisterDTO;
import com.takirahal.srfgroup.exceptions.BadRequestAlertException;
import com.takirahal.srfgroup.security.JwtTokenProvider;
import com.takirahal.srfgroup.services.impl.MailService;
import com.takirahal.srfgroup.services.impl.ResizeImage;
import com.takirahal.srfgroup.services.impl.StorageService;
import com.takirahal.srfgroup.modules.user.dto.UserDTO;
import com.takirahal.srfgroup.modules.user.entities.Authority;
import com.takirahal.srfgroup.modules.user.entities.User;
import com.takirahal.srfgroup.exceptions.AccountResourceException;
import com.takirahal.srfgroup.exceptions.EmailAlreadyUsedException;
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
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
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

    @Override
    public User registerUser(RegisterDTO registerDTO) {
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
        userRepository.save(newUser);

        mailService.sendActivationEmail(newUser);

        return newUser;
    }

    @Override
    public Optional<User> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
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
    public String signInAdmin(LoginDTO loginDTO) {
        log.debug("REST request to signin admin: {} ", loginDTO);
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(loginDTO.getEmail());
        if( !existingUser.isPresent() ){
            throw new AccountResourceException("Not found user with email");
        }

        if( !SecurityUtils.hasUserThisAuthority(existingUser.get().getAuthorities(), AuthoritiesConstants.ADMIN) ){
            throw new BadRequestAlertException("Not admin");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return tokenProvider.generateToken(authentication);
    }

    @Override
    public UserDTO updateAvatar(MultipartFile file) {
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
}

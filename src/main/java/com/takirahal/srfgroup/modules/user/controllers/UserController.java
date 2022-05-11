package com.takirahal.srfgroup.modules.user.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.takirahal.srfgroup.constants.AuthoritiesConstants;
import com.takirahal.srfgroup.modules.notification.repositories.NotificationRepository;
import com.takirahal.srfgroup.modules.user.dto.*;
import com.takirahal.srfgroup.modules.user.dto.filter.UserFilter;
import com.takirahal.srfgroup.modules.user.entities.User;
import com.takirahal.srfgroup.modules.user.exceptioins.AccountResourceException;
import com.takirahal.srfgroup.modules.user.exceptioins.InvalidPasswordException;
import com.takirahal.srfgroup.modules.user.exceptioins.UserBlockedException;
import com.takirahal.srfgroup.modules.user.mapper.UserMapper;
import com.takirahal.srfgroup.modules.user.repositories.UserRepository;
import com.takirahal.srfgroup.modules.websocket.models.ConnectedUser;
import com.takirahal.srfgroup.security.JwtAuthenticationFilter;
import com.takirahal.srfgroup.security.JwtTokenProvider;
import com.takirahal.srfgroup.modules.user.services.UserService;
import com.takirahal.srfgroup.utils.HeaderUtil;
import com.takirahal.srfgroup.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/user/")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    NotificationRepository notificationRepository;

    /**
     * SignIn from WebFront
     * @param loginDTO
     * @return
     */
    @PostMapping("public/signin")
    public ResponseEntity<JWTToken> signinClient(@RequestBody LoginDTO loginDTO) {
        log.info("REST request to signin with email: {} ", loginDTO.getEmail());
        String jwt = userService.signinClient(loginDTO);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtAuthenticationFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        httpHeaders.add("X-app-alert", "signin.message_welcome");
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }


    /**
     *
     * @param googlePlusVM
     * @return
     */
    @PostMapping("public/signin-google-plus")
    public ResponseEntity<JWTToken> signinGooglePlus(@Valid @RequestBody GooglePlusVM googlePlusVM) {
        try {
            log.info("REST request to signin Google Plus: {} ", googlePlusVM);
            String jwt = userService.signinGooglePlus(googlePlusVM);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(JwtAuthenticationFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
            httpHeaders.add("X-app-alert", "signin.message_welcome");
            return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
        }
        catch(Exception e){
            if(e instanceof UserBlockedException){
                throw new UserBlockedException("signin.blocked_by_admin");
            }
            throw new InvalidPasswordException("Bad Credentials");
        }
    }


    /**
     *
     * @param googlePlusOneTapVM
     * @return
     */
    @PostMapping("public/signin-google-plus-one-tap")
    public ResponseEntity<JWTToken> signinGooglePlusOneTap(@Valid @RequestBody GooglePlusOneTapVM googlePlusOneTapVM) {
        try {
            log.info("REST request to signin Google Plus OneTap: {} ", googlePlusOneTapVM);
            String jwt = userService.signinGooglePlusOneTap(googlePlusOneTapVM);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(JwtAuthenticationFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
            httpHeaders.add("X-app-alert", "signin.message_welcome");
            return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
        }
        catch(Exception e){
            if(e instanceof UserBlockedException){
                throw new UserBlockedException("signin.blocked_by_admin");
            }
            throw new InvalidPasswordException("Bad Credentials");
        }
    }

    /**
     *
     * @param facebookVM
     * @return
     */
    @PostMapping("public/signin-facebook")
    public ResponseEntity<JWTToken> signinFacebook(@Valid @RequestBody FacebookVM facebookVM) {
        try {
            log.info("REST request to signin Google Plus: {} ", facebookVM);
            String jwt = userService.signinFacebook(facebookVM);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(JwtAuthenticationFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
            httpHeaders.add("X-app-alert", "signin.message_welcome");
            return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
        }
        catch(Exception e){
            if(e instanceof UserBlockedException){
                throw new UserBlockedException("signin.blocked_by_admin");
            }
            throw new InvalidPasswordException("Bad Credentials");
        }
    }

    /**
     *
     * @param registerDTO
     */
    @PostMapping("public/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterDTO registerDTO) {
        log.info("REST request to signup : {} ", registerDTO);
        userService.registerUser(registerDTO);
        return new ResponseEntity<>("true", HttpStatus.CREATED);
    }

    /**
     *
     * @param loginDTO
     * @return
     */
    @PostMapping("public/signin-admin")
    public ResponseEntity<JWTToken> signinAdmin(@RequestBody LoginDTO loginDTO) {
        log.debug("REST request to signin : {} ", loginDTO);
        String jwt = userService.signInAdmin(loginDTO);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtAuthenticationFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }


    @GetMapping("public/activate-account")
    public void activateAccount(@RequestParam(value = "key") String key) {
        log.debug("Activating user for activation key {}", key);
        Optional<User> user = userService.activateRegistration(key);
        if (!user.isPresent()) {
            throw new AccountResourceException("No user was found for this activation key");
        }
    }

    /**
     * {@code GET /admin/users/:login} : get the "login" user.
     *
     * @param id the login of the user to find.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the "login" user, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("public/profile/{id}")
    public ResponseEntity<UserDTO> getProfile(@PathVariable Long id) {
        log.debug("REST request to get User : {}", id);
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }


    /**
     * {@code GET  /account} : get the current user.
     *
     * @return the current user.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user couldn't be returned.
     */
    @GetMapping("current-user")
    public ResponseEntity<UserDTO> getAccountUser() {
        log.info("REST request to get Current User");
        return new ResponseEntity<>(userService.getCurrentUser(), HttpStatus.OK);
    }


    /**
     * Update infos for current user
     * @param user
     * @return
     */
    @PutMapping("update-current-user")
    public ResponseEntity<UserDTO> updateCurrentUser(@RequestBody UserDTO user) {
        log.info("REST request to update Current User : {}");
        UserDTO userDTO = userService.updateCurrentUser(user);
        return new ResponseEntity<>(userDTO, HeaderUtil.createAlert("account.message_update_infos_succefully", user.getId().toString()), HttpStatus.OK);
    }

    /**
     * Update password for current user
     * @param updatePasswordDTO
     * @return
     */
    @PutMapping("update-password-current-user")
    public ResponseEntity<Boolean> updatePasswordCurrentUser(@RequestBody UpdatePasswordDTO updatePasswordDTO) {
        log.info("REST request to update Current User : {}");
        Boolean result = userService.updatePasswordCurrentUser(updatePasswordDTO);
        return new ResponseEntity<>(result, HeaderUtil.createAlert("account.message_update_password_succefully", ""), HttpStatus.OK);
    }


    /**
     *
     * @return
     */
    @GetMapping("count-not-see-notifications")
    public ResponseEntity<Long> getNumberNotSeeMessageForUserId() {
        log.info("REST request to get number of notification by user");

        Long userId = SecurityUtils.getIdByCurrentUser()
                .orElseThrow(() -> new AccountResourceException("Current user login not found"));

        Long nbeNotSee = notificationRepository.getNotReadNotifications(userId);
        return new ResponseEntity<>(nbeNotSee, HttpStatus.OK);
    }


    /**
     * {@code POST   /account/reset-password/init} : Send an email to reset the password of the user.
     *
     * @param email the mail of the user.
     */
    @PostMapping(path = "public/forgot-password/init")
    public ResponseEntity<Boolean> requestPasswordReset(@RequestBody String email) {
        log.info("REST request to reset password : {}", email);
        Boolean result = userService.requestPasswordReset(email);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    /**
     * {@code POST   /account/reset-password/finish} : Finish to reset the password of the user.
     *
     * @param keyAndPassword the generated key and the new password.
     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the password is incorrect.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the password could not be reset.
     */
    @PostMapping(path = "public/forgot-password/finish")
    public ResponseEntity<Boolean> finishPasswordReset(@RequestBody KeyAndPasswordVM keyAndPassword) {
        userService.completePasswordReset(keyAndPassword.getPassword(), keyAndPassword.getKey());
        return new ResponseEntity<>(true, HeaderUtil.createAlert("forgot_password_init.reset_finish_messages_success", ""), HttpStatus.OK);
    }


    /**
     * {@code GET  /aboutuses} : get all the aboutuses.
     *
     * @param pageable the pagination information.
     * @param userFilter the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aboutuses in body.
     */
    @GetMapping("/admin/list-users")
    public ResponseEntity<Page<UserDTO>> getAllUsers(UserFilter userFilter, Pageable pageable) {
        log.debug("REST request to get users by criteria: {}", userFilter);
        Page<UserDTO> page = userService.findByCriteria(userFilter, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }


    /**
     *
     * @param file
     * @return current user after update avatar
     */
    @RequestMapping(value = "avatar", method = RequestMethod.POST)
    public ResponseEntity<UserDTO> updateAvatar(@RequestParam("avatar") MultipartFile file) {
        log.debug("REST request to update Avatar : {}", file.getOriginalFilename());
        return new ResponseEntity<>(userService.updateAvatar(file), HttpStatus.OK);
    }


    /**
     *
     * @param id
     * @return
     */
    @PostMapping("admin/blocked-user/{id}")
    public ResponseEntity<String> blockedUserByAdmin(@PathVariable Long id, @RequestBody String blockUnblock) {
        log.info("REST request to blocked user by admin : {} - {}", id, blockUnblock);
        userService.blockedUserByAdmin(id, blockUnblock);
        return new ResponseEntity<>("true", HttpStatus.CREATED);
    }


    /**
     *
     * @param id
     * @return
     */
    @PostMapping("super-admin/add-remove-admin/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.SUPER_ADMIN + "\")")
    public ResponseEntity<String> addRemoveAdmin(@PathVariable Long id, @RequestBody String addRemove) {
        log.info("REST request to add/remove admin : {} - {}", id, addRemove);
        userService.addRemoveAdmin(id, addRemove);
        return new ResponseEntity<>("true", HttpStatus.CREATED);
    }

    /**
     *
     * @param id
     * @param filename
     * @return
     */
    @GetMapping("public/avatar/{id}/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable Long id, @PathVariable String filename) {
        Resource file = userService.getAvatar(id, filename);
        return new ResponseEntity<>(file, HttpStatus.OK);
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }

}

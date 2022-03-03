package com.takirahal.srfgroup.user.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.takirahal.srfgroup.dto.LoginDTO;
import com.takirahal.srfgroup.dto.RegisterDTO;
import com.takirahal.srfgroup.dto.UserDTO;
import com.takirahal.srfgroup.entities.User;
import com.takirahal.srfgroup.exceptions.AccountResourceException;
import com.takirahal.srfgroup.exceptions.InvalidPasswordException;
import com.takirahal.srfgroup.exceptions.ResouorceNotFoundException;
import com.takirahal.srfgroup.mapper.UserMapper;
import com.takirahal.srfgroup.repositories.UserRepository;
import com.takirahal.srfgroup.security.JwtAuthenticationFilter;
import com.takirahal.srfgroup.security.JwtTokenProvider;
import com.takirahal.srfgroup.services.UserService;
import com.takirahal.srfgroup.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

//    @Autowired
//    SimpUserRegistry userRegistry;

    /**
     *
     * @param loginDTO
     * @return
     */
    @PostMapping("public/signin")
    public ResponseEntity<JWTToken> signin(@RequestBody LoginDTO loginDTO) {
        log.debug("REST request to signin : {} ", loginDTO);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtAuthenticationFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }

    /**
     *
     * @param registerDTO
     */
    @PostMapping("public/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterDTO registerDTO) {
        log.debug("REST request to signup : {} ", registerDTO);
        if (isPasswordLengthInvalid(registerDTO.getPassword())) {
            throw new InvalidPasswordException("Incorrect password");
        }

        userService.registerUser(registerDTO);
        return new ResponseEntity<String>("true", HttpStatus.CREATED);
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
    @GetMapping("/current-user")
    public ResponseEntity<UserDTO> getAccountUser() {
        log.debug("REST request to get Current User : {}");
        return new ResponseEntity<>(userService.getCurrentUser(), HttpStatus.OK);
    }

//    @GetMapping("/websocket/users")
//    public ResponseEntity<Boolean> getAllUsers() {
//        log.debug("REST request to getAllUsers: {}");
//
//        this.userRegistry.getUsers();
//        return ResponseEntity.ok().body(true);
//    }

    private static boolean isPasswordLengthInvalid(String password) {
        return (
                StringUtils.isEmpty(password) ||
                        password.length() < RegisterDTO.PASSWORD_MIN_LENGTH ||
                        password.length() > RegisterDTO.PASSWORD_MAX_LENGTH
        );
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

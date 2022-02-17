package com.takirahal.srfgroup.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.takirahal.srfgroup.dto.LoginDTO;
import com.takirahal.srfgroup.dto.RegisterDTO;
import com.takirahal.srfgroup.exceptions.InvalidPasswordException;
import com.takirahal.srfgroup.security.JwtAuthenticationFilter;
import com.takirahal.srfgroup.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider tokenProvider;


    /**
     *
     * @param loginDTO
     * @return
     */
    @PostMapping("/public/signin")
    public ResponseEntity<JWTToken> signin(@Valid @RequestBody LoginDTO loginDTO) {
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
    @PostMapping("/public/signup")
    public void signup(@Valid @RequestBody RegisterDTO registerDTO) {
        if (isPasswordLengthInvalid(registerDTO.getPassword())) {
            throw new InvalidPasswordException("Incorrect password");
        }
    }

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

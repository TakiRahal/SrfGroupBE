package com.takirahal.srfgroup.security;

import com.takirahal.srfgroup.modules.user.entities.User;
import com.takirahal.srfgroup.exceptions.UserNotActivatedException;
import com.takirahal.srfgroup.modules.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail)
            throws UsernameNotFoundException {

        return userRepository
                // .findOneByEmailIgnoreCase(login)
                .findOneWithAuthoritiesByEmailIgnoreCase(usernameOrEmail)
                .map(user -> createSpringSecurityUser(usernameOrEmail, user))
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + usernameOrEmail + " was not found in the database"));

        // Let people login with either username or email
//        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
//                .orElseThrow(() ->
//                        new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail)
//                );
//
//        return UserPrincipal.create(user);
    }

    private UserPrincipal createSpringSecurityUser(String lowercaseLogin, User user) {
        if (!user.isActivated()) {
            throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
        }
        return UserPrincipal.create(user);
    }

    // This method is used by JWTAuthenticationFilter
//    @Transactional
//    public UserDetails loadUserById(Long id) {
//        User user = userRepository.findById(id).orElseThrow(
//                () -> new UsernameNotFoundException("User not found with id : " + id)
//        );
//
//        return UserPrincipal.create(user);
//    }
}

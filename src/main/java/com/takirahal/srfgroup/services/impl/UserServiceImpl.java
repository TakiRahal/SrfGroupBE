package com.takirahal.srfgroup.services.impl;

import com.takirahal.srfgroup.constants.AuthoritiesConstants;
import com.takirahal.srfgroup.dto.RegisterDTO;
import com.takirahal.srfgroup.entities.Authority;
import com.takirahal.srfgroup.entities.User;
import com.takirahal.srfgroup.exceptions.EmailAlreadyUsedException;
import com.takirahal.srfgroup.repositories.AuthorityRepository;
import com.takirahal.srfgroup.repositories.UserRepository;
import com.takirahal.srfgroup.services.UserService;
import com.takirahal.srfgroup.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    MailService mailService;

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
        newUser.setUsername(registerDTO.getEmail());
        newUser.setActivated(false);
        newUser.setActivationKey(RandomUtil.generateActivationKey());

        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
        newUser.setAuthorities(authorities);
        // userRepository.save(newUser);

        mailService.sendActivationEmail(newUser);

        return newUser;
    }

}

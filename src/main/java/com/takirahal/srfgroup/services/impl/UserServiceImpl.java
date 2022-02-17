package com.takirahal.srfgroup.services.impl;

import com.takirahal.srfgroup.dto.RegisterDTO;
import com.takirahal.srfgroup.entities.Role;
import com.takirahal.srfgroup.entities.User;
import com.takirahal.srfgroup.enums.RoleName;
import com.takirahal.srfgroup.exceptions.EmailAlreadyUsedException;
import com.takirahal.srfgroup.repositories.RoleRepository;
import com.takirahal.srfgroup.repositories.UserRepository;
import com.takirahal.srfgroup.services.UserService;
import com.takirahal.srfgroup.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

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

        Set<Role> authorities = new HashSet<>();
        // roleRepository.findById(RoleName.ROLE_USER).ifPresent(authorities::add);
        newUser.setRoles(authorities);
        // userRepository.save(newUser);

        mailService.sendActivationEmail(newUser);

        return newUser;
    }

}

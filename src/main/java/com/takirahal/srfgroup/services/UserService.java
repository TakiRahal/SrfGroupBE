package com.takirahal.srfgroup.services;

import com.takirahal.srfgroup.dto.LoginDTO;
import com.takirahal.srfgroup.dto.RegisterDTO;
import com.takirahal.srfgroup.user.dto.UserDTO;
import com.takirahal.srfgroup.user.entities.User;

import java.util.Optional;

public interface UserService {

    User registerUser(RegisterDTO registerDTO);

    Optional<User> activateRegistration(String key);

    UserDTO findById(Long id);

    UserDTO getCurrentUser();

    String signInAdmin(LoginDTO loginDTO);
}

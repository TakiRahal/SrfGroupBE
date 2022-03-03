package com.takirahal.srfgroup.services;

import com.takirahal.srfgroup.dto.RegisterDTO;
import com.takirahal.srfgroup.dto.UserDTO;
import com.takirahal.srfgroup.entities.User;

import java.util.Optional;

public interface UserService {

    User registerUser(RegisterDTO registerDTO);

    Optional<User> activateRegistration(String key);

    UserDTO findById(Long id);
}

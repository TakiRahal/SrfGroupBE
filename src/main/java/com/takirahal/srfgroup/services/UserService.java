package com.takirahal.srfgroup.services;

import com.takirahal.srfgroup.dto.RegisterDTO;
import com.takirahal.srfgroup.entities.User;

public interface UserService {

    User registerUser(RegisterDTO registerDTO);
}

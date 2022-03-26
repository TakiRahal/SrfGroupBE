package com.takirahal.srfgroup.modules.user.services;

import com.takirahal.srfgroup.modules.user.dto.*;
import com.takirahal.srfgroup.modules.user.entities.User;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface UserService {

    User registerUser(RegisterDTO registerDTO);

    Optional<User> activateRegistration(String key);

    UserDTO findById(Long id);

    UserDTO getCurrentUser();

    String signinClient(LoginDTO loginDTO);

    String signInAdmin(LoginDTO loginDTO);

    UserDTO updateAvatar(MultipartFile file);

    Resource getAvatar(Long id, String filename);

    UserDTO updateCurrentUser(UserDTO user);

    Boolean updatePasswordCurrentUser(UpdatePasswordDTO updatePasswordDTO);

    String signinGooglePlus(GooglePlusVM googlePlusVM) throws IOException;

    String signinFacebook(FacebookVM facebookVM);
}

package com.takirahal.srfgroup.modules.user.services;

import com.takirahal.srfgroup.modules.user.dto.UserOneSignalDTO;
import com.takirahal.srfgroup.modules.user.entities.User;
import com.takirahal.srfgroup.modules.user.entities.UserOneSignal;

import java.util.Optional;

public interface UserOneSignalService {

    /**
     *
     * @param userOneSignalDTO
     * @return
     */
    UserOneSignalDTO save(UserOneSignalDTO userOneSignalDTO);


    /**
     *
     * @param idOneSignal
     * @return
     */
    Optional<UserOneSignal> findByIdOneSignalAndUser(String idOneSignal, User user);
}

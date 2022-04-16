package com.takirahal.srfgroup.modules.user.services.impl;

import com.takirahal.srfgroup.modules.user.dto.UserOneSignalDTO;
import com.takirahal.srfgroup.modules.user.entities.User;
import com.takirahal.srfgroup.modules.user.entities.UserOneSignal;
import com.takirahal.srfgroup.modules.user.mapper.UserOneSignalMapper;
import com.takirahal.srfgroup.modules.user.repositories.UserOneSignalRepository;
import com.takirahal.srfgroup.modules.user.services.UserOneSignalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserOneSignalServiceImpl implements UserOneSignalService {

    private final Logger log = LoggerFactory.getLogger(UserOneSignalServiceImpl.class);

    @Autowired
    UserOneSignalRepository userOneSignalRepository;

    @Autowired
    UserOneSignalMapper userOneSignalMapper;

    @Override
    public UserOneSignalDTO save(UserOneSignalDTO userOneSignalDTO) {
        log.debug("REST request to update avatar: {} ", userOneSignalDTO);
        UserOneSignal userOneSignal = userOneSignalMapper.toEntity(userOneSignalDTO);
        userOneSignal = userOneSignalRepository.save(userOneSignal);
        return userOneSignalMapper.toDto(userOneSignal);
    }

    @Override
    public Optional<UserOneSignal> findByIdOneSignalAndUser(String idOneSignal, User user) {
        return userOneSignalRepository.findByIdOneSignalAndUser(idOneSignal, user);
    }

    @Override
    public List<UserOneSignal> findByUser(User user) {
        return userOneSignalRepository.findByUser(user);
    }
}

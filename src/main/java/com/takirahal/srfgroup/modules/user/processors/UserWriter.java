package com.takirahal.srfgroup.modules.user.processors;

import com.takirahal.srfgroup.modules.user.repositories.UserRepository;
import com.takirahal.srfgroup.modules.user.entities.User;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserWriter implements ItemWriter<User> {

    @Autowired
    UserRepository userRepository;

    @Override
    public void write(List<? extends User> list) throws Exception {
        userRepository.saveAll(list);
    }
}
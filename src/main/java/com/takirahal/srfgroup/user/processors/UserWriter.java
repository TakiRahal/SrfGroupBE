package com.takirahal.srfgroup.user.processors;

import com.takirahal.srfgroup.repositories.AuthorityRepository;
import com.takirahal.srfgroup.repositories.UserRepository;
import com.takirahal.srfgroup.user.entities.User;
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
package com.takirahal.srfgroup.modules.user.processors;

import com.takirahal.srfgroup.constants.AuthoritiesConstants;
import com.takirahal.srfgroup.modules.user.entities.Authority;
import com.takirahal.srfgroup.modules.user.repositories.UserRepository;
import com.takirahal.srfgroup.modules.user.entities.User;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class UserWriter implements ItemWriter<User> {

    @Autowired
    UserRepository userRepository;

    @Override
    public void write(List<? extends User> list){
        for(int i=0; i<list.size(); i++){
            User user = list.get(i);
            Set<Authority> authorities = new HashSet<>();
            Authority authority = new Authority();
            authority.setName(AuthoritiesConstants.SUPER_ADMIN);
            authorities.add(authority);
            user.setAuthorities(authorities);
            userRepository.save(user);
        }
        // userRepository.saveAll(list);
    }
}
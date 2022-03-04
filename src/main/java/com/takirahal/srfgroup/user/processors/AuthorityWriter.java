package com.takirahal.srfgroup.user.processors;

import com.takirahal.srfgroup.repositories.AddressRepository;
import com.takirahal.srfgroup.repositories.AuthorityRepository;
import com.takirahal.srfgroup.user.entities.Authority;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class AuthorityWriter implements ItemWriter<Authority> {

    @Autowired
    AuthorityRepository authorityRepository;

    @Override
    public void write(List<? extends Authority> list) throws Exception {
        authorityRepository.saveAll(list);
    }
}

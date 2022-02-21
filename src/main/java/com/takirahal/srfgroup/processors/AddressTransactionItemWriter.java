package com.takirahal.srfgroup.processors;

import com.takirahal.srfgroup.entities.Address;
import com.takirahal.srfgroup.repositories.AddressRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AddressTransactionItemWriter  implements ItemWriter<Address> {

    @Autowired
    AddressRepository addressRepository;

    @Override
    public void write(List<? extends Address> list) throws Exception {
        addressRepository.saveAll(list);
    }
}

package com.takirahal.srfgroup.modules.training.controllers;

import com.takirahal.srfgroup.modules.training.entities.Customer;
import com.takirahal.srfgroup.modules.training.repositories.AddressTrainingRepository;
import com.takirahal.srfgroup.modules.training.repositories.CustomerRepository;
import com.takirahal.srfgroup.modules.training.repositories.SkillsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/customer")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    SkillsRepository skillsRepository;

    @Autowired
    AddressTrainingRepository addressTrainingRepository;

    @GetMapping("/all")
    public List<Customer> getAllCustomer(){
        return customerRepository.findAll();
    }

    @PostMapping("add")
    public Customer save(@RequestBody Customer customer){
        addressTrainingRepository.save(customer.getAddress());
        skillsRepository.saveAll(customer.getSkills());
        return customerRepository.save(customer);
    }

    @PutMapping("update/{id}")
    public Customer updateById(@PathVariable Long id, @RequestBody Customer customer){
        return customerRepository.save(customer);
    }

    @GetMapping("")
    public Customer getAllCustomer(@RequestParam Long id){
        return customerRepository.findById(id).get();
    }
}

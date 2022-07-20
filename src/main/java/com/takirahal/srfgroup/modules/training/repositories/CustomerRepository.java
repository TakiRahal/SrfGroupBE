package com.takirahal.srfgroup.modules.training.repositories;

import com.takirahal.srfgroup.modules.training.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}

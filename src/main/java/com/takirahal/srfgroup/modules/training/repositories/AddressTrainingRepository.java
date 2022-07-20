package com.takirahal.srfgroup.modules.training.repositories;

import com.takirahal.srfgroup.modules.training.models.AddressTraining;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressTrainingRepository extends JpaRepository<AddressTraining, Long> {
}

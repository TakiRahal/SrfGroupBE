package com.takirahal.srfgroup.modules.training.repositories;

import com.takirahal.srfgroup.modules.training.models.Skills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillsRepository extends JpaRepository<Skills, Long> {
}

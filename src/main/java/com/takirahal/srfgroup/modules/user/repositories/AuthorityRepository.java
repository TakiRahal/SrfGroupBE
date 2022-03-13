package com.takirahal.srfgroup.modules.user.repositories;

import com.takirahal.srfgroup.modules.user.entities.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}

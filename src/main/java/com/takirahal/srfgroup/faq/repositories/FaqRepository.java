package com.takirahal.srfgroup.faq.repositories;

import com.takirahal.srfgroup.faq.entities.Faq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FaqRepository extends JpaRepository<Faq, Long>, JpaSpecificationExecutor<Faq> {
}

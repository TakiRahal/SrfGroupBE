package com.takirahal.srfgroup.faq.services;

import com.takirahal.srfgroup.faq.dto.FaqDTO;
import com.takirahal.srfgroup.faq.dto.filter.FaqFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FaqService {
    FaqDTO save(FaqDTO faqDTO);

    Page<FaqDTO> findByCriteria(FaqFilter criteria, Pageable pageable);
}

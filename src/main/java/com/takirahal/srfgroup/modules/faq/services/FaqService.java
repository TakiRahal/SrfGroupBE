package com.takirahal.srfgroup.modules.faq.services;

import com.takirahal.srfgroup.modules.faq.dto.FaqDTO;
import com.takirahal.srfgroup.modules.faq.dto.filter.FaqFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FaqService {
    FaqDTO save(FaqDTO faqDTO);

    Page<FaqDTO> findByCriteria(FaqFilter criteria, Pageable pageable);
}

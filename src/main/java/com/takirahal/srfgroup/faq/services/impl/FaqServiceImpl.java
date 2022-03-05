package com.takirahal.srfgroup.faq.services.impl;

import com.takirahal.srfgroup.faq.dto.FaqDTO;
import com.takirahal.srfgroup.faq.dto.filter.FaqFilter;
import com.takirahal.srfgroup.faq.entities.Faq;
import com.takirahal.srfgroup.faq.mapper.FaqMapper;
import com.takirahal.srfgroup.faq.repositories.FaqRepository;
import com.takirahal.srfgroup.faq.services.FaqService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class FaqServiceImpl implements FaqService {

    private final Logger log = LoggerFactory.getLogger(FaqServiceImpl.class);

    @Autowired
    FaqRepository faqRepository;

    @Autowired
    FaqMapper faqMapper;

    @Override
    public FaqDTO save(FaqDTO faqDTO) {
        log.debug("Request to save Faq : {}", faqDTO);
        Faq faq = faqMapper.toEntity(faqDTO);
        faq = faqRepository.save(faq);
        return faqMapper.toDto(faq);
    }

    @Override
    public Page<FaqDTO> findByCriteria(FaqFilter criteria, Pageable pageable) {
        return faqRepository.findAll(createSpecification(criteria), pageable).map(faqMapper::toDto);
    }

    protected Specification<Faq> createSpecification(FaqFilter faqFilter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
//            if (offerFilter.getTitle() != null && !offerFilter.getTitle().isEmpty()) {
//                predicates.add(criteriaBuilder.like(root.get("title"), "%" + offerFilter.getTitle() + "%"));
//            }
            query.orderBy(criteriaBuilder.desc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

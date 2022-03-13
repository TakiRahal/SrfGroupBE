package com.takirahal.srfgroup.modules.offer.services.impl;

import com.takirahal.srfgroup.modules.offer.dto.FindOfferDTO;
import com.takirahal.srfgroup.modules.offer.dto.OfferImagesDTO;
import com.takirahal.srfgroup.modules.offer.entities.FindOffer;
import com.takirahal.srfgroup.modules.offer.dto.filter.FindOfferFilter;
import com.takirahal.srfgroup.modules.offer.mapper.FindOfferMapper;
import com.takirahal.srfgroup.modules.offer.repositories.FindOfferRepository;
import com.takirahal.srfgroup.modules.offer.services.FindOfferService;
import com.takirahal.srfgroup.modules.offer.services.OfferImagesService;
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
public class FindOfferServiceImpl implements FindOfferService {

    private final Logger log = LoggerFactory.getLogger(FindOfferServiceImpl.class);

    @Autowired
    FindOfferMapper findOfferMapper;

    @Autowired
    FindOfferRepository findOfferRepository;

    @Autowired
    OfferImagesService offerImagesService;

    @Override
    public FindOfferDTO save(FindOfferDTO findOfferDTO) {
        log.debug("Request to save FindOffer : {}", findOfferDTO);
        FindOffer findOffer = findOfferMapper.toEntity(findOfferDTO);
        findOffer = findOfferRepository.save(findOffer);

        FindOfferDTO result = findOfferMapper.toDto(findOffer);

        for (OfferImagesDTO offerImagesDTO : findOfferDTO.getOfferImages()) {
            offerImagesDTO.setOffer(result);
            offerImagesService.save(offerImagesDTO);
        }

        return result;
    }

    @Override
    public Page<FindOfferDTO> findByCriteria(FindOfferFilter findOfferFilter, Pageable pageable) {
        return findOfferRepository.findAll(createSpecification(findOfferFilter), pageable).map(findOfferMapper::toDto);
    }

    private Specification<FindOffer> createSpecification(FindOfferFilter findOfferFilter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            query.orderBy(criteriaBuilder.desc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

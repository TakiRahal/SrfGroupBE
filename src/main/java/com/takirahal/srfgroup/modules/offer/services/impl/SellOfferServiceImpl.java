package com.takirahal.srfgroup.modules.offer.services.impl;

import com.takirahal.srfgroup.modules.offer.dto.OfferImagesDTO;
import com.takirahal.srfgroup.modules.offer.dto.SellOfferDTO;
import com.takirahal.srfgroup.modules.offer.entities.SellOffer;
import com.takirahal.srfgroup.modules.offer.dto.filter.SellOfferFilter;
import com.takirahal.srfgroup.modules.offer.mapper.SellOfferMapper;
import com.takirahal.srfgroup.modules.offer.repositories.SellOfferRepository;
import com.takirahal.srfgroup.modules.offer.services.OfferImagesService;
import com.takirahal.srfgroup.modules.offer.services.SellOfferService;
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
public class SellOfferServiceImpl implements SellOfferService {

    private final Logger log = LoggerFactory.getLogger(SellOfferServiceImpl.class);

    @Autowired
    SellOfferMapper sellOfferMapper;

    @Autowired
    SellOfferRepository sellOfferRepository;

    @Autowired
    OfferImagesService offerImagesService;

    @Override
    public SellOfferDTO save(SellOfferDTO sellOfferDTO) {
        log.debug("Request to save SellOffer : {}", sellOfferDTO);
        SellOffer sellOffer = sellOfferMapper.toEntity(sellOfferDTO);
        sellOffer = sellOfferRepository.save(sellOffer);

        SellOfferDTO result = sellOfferMapper.toDto(sellOffer);

        for (OfferImagesDTO offerImagesDTO : sellOfferDTO.getOfferImages()) {
            offerImagesDTO.setOffer(result);
            offerImagesService.save(offerImagesDTO);
        }

        return result;
    }

    @Override
    public Page<SellOfferDTO> findByCriteria(SellOfferFilter sellOfferFilter, Pageable pageable) {
        return sellOfferRepository.findAll(createSpecification(sellOfferFilter), pageable).map(sellOfferMapper::toDto);
    }

    private Specification<SellOffer> createSpecification(SellOfferFilter sellOfferFilter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            query.orderBy(criteriaBuilder.desc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

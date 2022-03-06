package com.takirahal.srfgroup.offer.services.impl;

import com.takirahal.srfgroup.offer.dto.OfferImagesDTO;
import com.takirahal.srfgroup.offer.dto.RentOfferDTO;
import com.takirahal.srfgroup.entities.RentOffer;
import com.takirahal.srfgroup.offer.dto.filter.RentOfferFilter;
import com.takirahal.srfgroup.offer.mapper.RentOfferMapper;
import com.takirahal.srfgroup.repositories.RentOfferRepository;
import com.takirahal.srfgroup.offer.services.OfferImagesService;
import com.takirahal.srfgroup.offer.services.RentOfferService;
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
public class RentOfferServiceImpl implements RentOfferService {

    private final Logger log = LoggerFactory.getLogger(RentOfferServiceImpl.class);

    @Autowired
    RentOfferMapper rentOfferMapper;

    @Autowired
    RentOfferRepository rentOfferRepository;

    @Autowired
    OfferImagesService offerImagesService;

    @Override
    public RentOfferDTO save(RentOfferDTO rentOfferDTO) {
        log.debug("Request to save RentOffer : {}", rentOfferDTO);
        RentOffer rentOffer = rentOfferMapper.toEntity(rentOfferDTO);
        rentOffer = rentOfferRepository.save(rentOffer);

        RentOfferDTO result = rentOfferMapper.toDto(rentOffer);

        for (OfferImagesDTO offerImagesDTO : rentOfferDTO.getOfferImages()) {
            offerImagesDTO.setOffer(result);
            offerImagesService.save(offerImagesDTO);
        }

        return result;
    }

    @Override
    public Page<RentOfferDTO> findByCriteria(RentOfferFilter rentOfferFilter, Pageable pageable) {
        return rentOfferRepository.findAll(createSpecification(rentOfferFilter), pageable).map(rentOfferMapper::toDto);
    }

    private Specification<RentOffer> createSpecification(RentOfferFilter rentOfferFilter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            query.orderBy(criteriaBuilder.desc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

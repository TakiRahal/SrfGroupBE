package com.takirahal.srfgroup.modules.offer.services.impl;

import com.takirahal.srfgroup.exceptions.BadRequestAlertException;
import com.takirahal.srfgroup.exceptions.UnauthorizedException;
import com.takirahal.srfgroup.modules.offer.dto.OfferImagesDTO;
import com.takirahal.srfgroup.modules.offer.dto.SellOfferDTO;
import com.takirahal.srfgroup.modules.offer.entities.SellOffer;
import com.takirahal.srfgroup.modules.offer.dto.filter.SellOfferFilter;
import com.takirahal.srfgroup.modules.offer.mapper.SellOfferMapper;
import com.takirahal.srfgroup.modules.offer.repositories.OfferImagesRepository;
import com.takirahal.srfgroup.modules.offer.repositories.SellOfferRepository;
import com.takirahal.srfgroup.modules.offer.services.OfferImagesService;
import com.takirahal.srfgroup.modules.offer.services.SellOfferService;
import com.takirahal.srfgroup.modules.user.exceptioins.AccountResourceException;
import com.takirahal.srfgroup.services.impl.StorageService;
import com.takirahal.srfgroup.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SellOfferServiceImpl implements SellOfferService {

    private final Logger log = LoggerFactory.getLogger(SellOfferServiceImpl.class);

    @Autowired
    SellOfferMapper sellOfferMapper;

    @Autowired
    SellOfferRepository sellOfferRepository;

    @Autowired
    OfferImagesService offerImagesService;

    @Autowired
    OfferImagesRepository offerImagesRepository;

    @Autowired
    StorageService storageService;

    @Override
    public SellOfferDTO save(SellOfferDTO sellOfferDTO) {
        log.debug("Request to save SellOffer : {}", sellOfferDTO);
        sellOfferDTO.setBlockedByReported(Boolean.FALSE);
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

    @Override
    public SellOfferDTO updateSellOffer(SellOfferDTO sellOfferDTO,  Long id) {
        log.debug("Request to update SellOffer : {}", sellOfferDTO);
        if (sellOfferDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id null");
        }
        if (!Objects.equals(id, sellOfferDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID id invalid");
        }

        SellOffer sellOffer = sellOfferRepository.findById(id)
                .orElseThrow(() -> new BadRequestAlertException("Entity not found with id"));

        Long useId = SecurityUtils
                .getIdByCurrentUser()
                .orElseThrow(() -> new AccountResourceException("Current user not found"));
        if (!Objects.equals(useId, sellOffer.getUser().getId())) {
            throw new UnauthorizedException("Unauthorized action");
        }

        sellOffer = sellOfferMapper.toEntity(sellOfferDTO);
        SellOffer sellOfferUpdate = sellOfferRepository.save(sellOffer);
        SellOfferDTO newSellOfferDTO = sellOfferMapper.toDto(sellOfferUpdate);


        // Any modif for images
        if( sellOfferDTO.getOfferImages().size()> 0 ){

            // Delete folder for images
            String pathAddProduct = storageService.getBaseStorageProductImages() + id;
            if (storageService.existPath(pathAddProduct)) {
                Path rootLocation = Paths.get(pathAddProduct);
                storageService.deleteFiles(rootLocation);
            }

            // Delete old images
            offerImagesRepository.deleteImagesByOfferId(newSellOfferDTO.getId());
        }

        // Update images for offer
        for (OfferImagesDTO offerImagesDTO : sellOfferDTO.getOfferImages()) {
            offerImagesDTO.setOffer(newSellOfferDTO);
            offerImagesService.save(offerImagesDTO);
        }

        return newSellOfferDTO;
    }

    private Specification<SellOffer> createSpecification(SellOfferFilter sellOfferFilter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            query.orderBy(criteriaBuilder.desc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

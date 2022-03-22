package com.takirahal.srfgroup.modules.offer.services.impl;

import com.takirahal.srfgroup.exceptions.BadRequestAlertException;
import com.takirahal.srfgroup.exceptions.UnauthorizedException;
import com.takirahal.srfgroup.modules.offer.dto.FindOfferDTO;
import com.takirahal.srfgroup.modules.offer.dto.OfferImagesDTO;
import com.takirahal.srfgroup.modules.offer.dto.RentOfferDTO;
import com.takirahal.srfgroup.modules.offer.entities.FindOffer;
import com.takirahal.srfgroup.modules.offer.dto.filter.FindOfferFilter;
import com.takirahal.srfgroup.modules.offer.entities.RentOffer;
import com.takirahal.srfgroup.modules.offer.mapper.FindOfferMapper;
import com.takirahal.srfgroup.modules.offer.repositories.FindOfferRepository;
import com.takirahal.srfgroup.modules.offer.repositories.OfferImagesRepository;
import com.takirahal.srfgroup.modules.offer.services.FindOfferService;
import com.takirahal.srfgroup.modules.offer.services.OfferImagesService;
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
public class FindOfferServiceImpl implements FindOfferService {

    private final Logger log = LoggerFactory.getLogger(FindOfferServiceImpl.class);

    @Autowired
    FindOfferMapper findOfferMapper;

    @Autowired
    FindOfferRepository findOfferRepository;

    @Autowired
    OfferImagesService offerImagesService;

    @Autowired
    OfferImagesRepository offerImagesRepository;

    @Autowired
    StorageService storageService;

    @Override
    public FindOfferDTO save(FindOfferDTO findOfferDTO) {
        log.debug("Request to save FindOffer : {}", findOfferDTO);
        findOfferDTO.setBlockedByReported(Boolean.FALSE);
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

    @Override
    public FindOfferDTO updateFindOffer(FindOfferDTO findOfferDTO, Long id) {
        log.debug("Request to update RentOffer : {}", findOfferDTO);
        if (findOfferDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id idnull");
        }
        if (!Objects.equals(id, findOfferDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID idinvalid");
        }

        FindOffer findOffer = findOfferRepository.findById(id)
                .orElseThrow(() -> new BadRequestAlertException("Entity not found with id"));

        Long useId = SecurityUtils
                .getIdByCurrentUser()
                .orElseThrow(() -> new AccountResourceException("Current user not found"));
        if (!Objects.equals(useId, findOfferDTO.getUser().getId())) {
            throw new UnauthorizedException("Unauthorized action");
        }

        findOffer = findOfferMapper.toEntity(findOfferDTO);
        FindOffer findOfferUpdate = findOfferRepository.save(findOffer);
        FindOfferDTO newFindSellOfferDTO = findOfferMapper.toDto(findOfferUpdate);


        // Any modif for images
        if( findOfferDTO.getOfferImages().size()> 0 ){

            // Delete folder for images
            String pathAddProduct = storageService.getBaseStorageProductImages() + id;
            if (storageService.existPath(pathAddProduct)) {
                Path rootLocation = Paths.get(pathAddProduct);
                storageService.deleteFiles(rootLocation);
            }

            // Delete old images
            offerImagesRepository.deleteImagesByOfferId(newFindSellOfferDTO.getId());
        }

        // Update images for offer
        for (OfferImagesDTO offerImagesDTO : findOfferDTO.getOfferImages()) {
            offerImagesDTO.setOffer(newFindSellOfferDTO);
            offerImagesService.save(offerImagesDTO);
        }

        return newFindSellOfferDTO;
    }

    private Specification<FindOffer> createSpecification(FindOfferFilter findOfferFilter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            query.orderBy(criteriaBuilder.desc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

package com.takirahal.srfgroup.modules.offer.services.impl;

import com.takirahal.srfgroup.exceptions.BadRequestAlertException;
import com.takirahal.srfgroup.exceptions.UnauthorizedException;
import com.takirahal.srfgroup.modules.offer.dto.OfferImagesDTO;
import com.takirahal.srfgroup.modules.offer.dto.RentOfferDTO;
import com.takirahal.srfgroup.modules.offer.dto.SellOfferDTO;
import com.takirahal.srfgroup.modules.offer.entities.RentOffer;
import com.takirahal.srfgroup.modules.offer.dto.filter.RentOfferFilter;
import com.takirahal.srfgroup.modules.offer.entities.SellOffer;
import com.takirahal.srfgroup.modules.offer.mapper.RentOfferMapper;
import com.takirahal.srfgroup.modules.offer.repositories.OfferImagesRepository;
import com.takirahal.srfgroup.modules.offer.repositories.RentOfferRepository;
import com.takirahal.srfgroup.modules.offer.services.OfferImagesService;
import com.takirahal.srfgroup.modules.offer.services.RentOfferService;
import com.takirahal.srfgroup.modules.user.exceptioins.AccountResourceException;
import com.takirahal.srfgroup.modules.user.mapper.UserMapper;
import com.takirahal.srfgroup.security.UserPrincipal;
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
public class RentOfferServiceImpl implements RentOfferService {

    private final Logger log = LoggerFactory.getLogger(RentOfferServiceImpl.class);

    @Autowired
    RentOfferMapper rentOfferMapper;

    @Autowired
    RentOfferRepository rentOfferRepository;

    @Autowired
    OfferImagesService offerImagesService;

    @Autowired
    OfferImagesRepository offerImagesRepository;

    @Autowired
    StorageService storageService;

    @Autowired
    UserMapper userMapper;

    @Override
    public RentOfferDTO save(RentOfferDTO rentOfferDTO) {
        log.debug("Request to save RentOffer : {}", rentOfferDTO);

        if (rentOfferDTO.getId() != null) {
            throw new BadRequestAlertException("A new rentOffer cannot already have an ID idexists");
        }

        UserPrincipal currentUser = SecurityUtils.getCurrentUser().orElseThrow(() -> new AccountResourceException("Current user login not found"));
        rentOfferDTO.setUser(userMapper.toCurrentUserPrincipal(currentUser));
        rentOfferDTO.setBlockedByReported(Boolean.FALSE);
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

    @Override
    public RentOfferDTO updateRentOffer(RentOfferDTO rentOfferDTO, Long id) {
        log.debug("Request to update RentOffer : {}", rentOfferDTO);
        if (rentOfferDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id idnull");
        }
        if (!Objects.equals(id, rentOfferDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID idinvalid");
        }

        RentOffer rentOffer = rentOfferRepository.findById(id)
                .orElseThrow(() -> new BadRequestAlertException("Entity not found with id"));

        Long useId = SecurityUtils
                .getIdByCurrentUser()
                .orElseThrow(() -> new AccountResourceException("Current user not found"));
        if (!Objects.equals(useId, rentOfferDTO.getUser().getId())) {
            throw new UnauthorizedException("Unauthorized action");
        }

        rentOffer = rentOfferMapper.toEntity(rentOfferDTO);
        RentOffer rentOfferUpdate = rentOfferRepository.save(rentOffer);
        RentOfferDTO newSellOfferDTO = rentOfferMapper.toDto(rentOfferUpdate);


        // Any modif for images
        if( rentOfferDTO.getOfferImages().size()> 0 ){

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
        for (OfferImagesDTO offerImagesDTO : rentOfferDTO.getOfferImages()) {
            offerImagesDTO.setOffer(newSellOfferDTO);
            offerImagesService.save(offerImagesDTO);
        }

        return newSellOfferDTO;
    }

    private Specification<RentOffer> createSpecification(RentOfferFilter rentOfferFilter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            query.orderBy(criteriaBuilder.desc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

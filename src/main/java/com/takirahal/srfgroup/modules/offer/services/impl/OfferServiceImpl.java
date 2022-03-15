package com.takirahal.srfgroup.modules.offer.services.impl;

import com.takirahal.srfgroup.exceptions.ResouorceNotFoundException;
import com.takirahal.srfgroup.exceptions.UnauthorizedException;
import com.takirahal.srfgroup.modules.commentoffer.repositories.CommentOfferRepository;
import com.takirahal.srfgroup.modules.offer.repositories.OfferImagesRepository;
import com.takirahal.srfgroup.modules.user.exceptioins.AccountResourceException;
import com.takirahal.srfgroup.modules.offer.dto.OfferDTO;
import com.takirahal.srfgroup.modules.offer.dto.filter.OfferFilter;
import com.takirahal.srfgroup.modules.offer.entities.Offer;
import com.takirahal.srfgroup.modules.offer.mapper.CustomOfferMapper;
import com.takirahal.srfgroup.modules.offer.repositories.OfferRepository;
import com.takirahal.srfgroup.modules.offer.services.OfferService;
import com.takirahal.srfgroup.services.impl.ResizeImage;
import com.takirahal.srfgroup.services.impl.StorageService;
import com.takirahal.srfgroup.modules.user.dto.filter.UserOfferFilter;
import com.takirahal.srfgroup.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import javax.persistence.criteria.Predicate;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OfferServiceImpl implements OfferService {

    private final Logger log = LoggerFactory.getLogger(OfferServiceImpl.class);

    @Autowired
    OfferRepository offerRepository;

    @Autowired
    CustomOfferMapper customOfferMapper;

    @Autowired
    StorageService storageService;

    @Autowired
    ResizeImage resizeImage;

    @Autowired
    CommentOfferRepository commentOfferRepository;

    @Autowired
    OfferImagesRepository offerImagesRepository;

    @Override
    public Page<OfferDTO> findByCriteria(OfferFilter offerFilter, Pageable page) {
        log.debug("find offers by criteria : {}, page: {}", page);
        return offerRepository.findAll(createSpecification(offerFilter), page).map(customOfferMapper::toDtoSearchOffer);
    }

    @Override
    public void uploadImages(List<MultipartFile> multipartFiles, @RequestParam("offerId") Long offerId) {
        log.debug("uploadImages : {}", offerId);
        String pathAddProduct = storageService.getBaseStorageProductImages() + offerId;
        if (storageService.existPath(pathAddProduct)) { // Upload for Update offer


            storeImages(multipartFiles, pathAddProduct);
        } else { // Upload for new offer
            storageService.init(pathAddProduct);
            storeImages(multipartFiles, pathAddProduct);
        }
    }

    @Override
    public Resource loadFile(Long offerId, String filename) {
        Path rootLocation = Paths.get(storageService.getBaseStorageProductImages() + offerId);
        return  storageService.loadFile(filename, rootLocation);
    }

    @Override
    public Optional<OfferDTO> findOne(Long id) {
        log.debug("Request to get Offer : {}", id);
        return offerRepository.findById(id).map(customOfferMapper::toDtoDetailsOffer);
    }

    @Override
    public Page<OfferDTO> getOffersByCurrentUser(OfferFilter offerFilter, Pageable pageable) {
        Long useId = SecurityUtils
                .getIdByCurrentUser()
                .orElseThrow(() -> new AccountResourceException("Current user not found"));

        UserOfferFilter userOfferFilter = new UserOfferFilter();
        userOfferFilter.setId(useId);
        offerFilter.setUser(userOfferFilter);

        return findByCriteria(offerFilter, pageable);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Offer : {}", id);

        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new ResouorceNotFoundException("Entity not found with id"));

        Long useId = SecurityUtils
                .getIdByCurrentUser()
                .orElseThrow(() -> new AccountResourceException("Current user not found"));
        if (!Objects.equals(useId, offer.getUser().getId())) {
            throw new UnauthorizedException("Unauthorized action");
        }

        // Delete folder for images
        String pathAddProduct = storageService.getBaseStorageProductImages() + id;
        if (storageService.existPath(pathAddProduct)) {
            Path rootLocation = Paths.get(pathAddProduct);
            storageService.deleteFiles(rootLocation);
        }

        // Delete all comments
        commentOfferRepository.deleteCommentsByOfferId(id);

        // Delete all images_offer
        offerImagesRepository.deleteImagesByOfferId(id);

        // Delete entity
        offerRepository.deleteById(id);
    }


    private void storeImages(List<MultipartFile> multipartFiles, String pathAddProduct){
        Path rootLocation = Paths.get(pathAddProduct);
        for(MultipartFile file : multipartFiles) {
            storageService.store(file, rootLocation);
            resizeImage.resizeImageOffer(pathAddProduct + "/" + file.getOriginalFilename());
        }
    }

    protected Specification<Offer> createSpecification(OfferFilter offerFilter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (offerFilter.getTitle() != null && !offerFilter.getTitle().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("title"), "%" + offerFilter.getTitle() + "%"));
            }

            if ( offerFilter.getUser() != null && offerFilter.getUser().getId() != null ) {
                predicates.add(criteriaBuilder.equal(root.get("user").get("id"), offerFilter.getUser().getId()));
            }

//            if (request.getName() != null && !request.getName().isEmpty()) {
//                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")),
//                        "%" + request.getName().toLowerCase() + "%"));
//            }
//            if (request.getGender() != null && !request.getGender().isEmpty()) {
//                predicates.add(criteriaBuilder.equal(root.get("gender"), request.getGender()));
//            }
            query.orderBy(criteriaBuilder.desc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

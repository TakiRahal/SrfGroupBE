package com.takirahal.srfgroup.offer.services.impl;

import com.takirahal.srfgroup.offer.dto.OfferDTO;
import com.takirahal.srfgroup.offer.dto.filter.OfferFilter;
import com.takirahal.srfgroup.offer.entities.Offer;
import com.takirahal.srfgroup.offer.mapper.CustomOfferMapper;
import com.takirahal.srfgroup.offer.repositories.OfferRepository;
import com.takirahal.srfgroup.offer.services.OfferService;
import com.takirahal.srfgroup.services.impl.ResizeImage;
import com.takirahal.srfgroup.services.impl.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import javax.persistence.criteria.Predicate;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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

    @Override
    public Page<OfferDTO> findByCriteria(OfferFilter offerFilter, Pageable page) {
        log.debug("find offers by criteria : {}, page: {}", page);
        // return offerRepository.findAll(page).map(offer -> customOfferMapper.toDto(offer));
        return offerRepository.findAll(createSpecification(offerFilter), page).map(customOfferMapper::toDto);
    }

    @Override
    public void uploadImages(List<MultipartFile> multipartFiles, @RequestParam("offerId") Long offerId) {
        log.debug("uploadImages : {}", offerId);
        String pathAddProduct = storageService.getBaseStorageProductImages() + offerId;
        if (storageService.existPath(pathAddProduct)) { // Already exixit path
            storeImages(multipartFiles, pathAddProduct);
        } else { // Create  new path
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

//        Specification<Offer> specification = Specification.where(null);
//        return specification;
    }
}

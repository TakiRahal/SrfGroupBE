package com.takirahal.srfgroup.services;

import com.takirahal.srfgroup.dto.OfferDTO;
import com.takirahal.srfgroup.dto.filter.OfferFilter;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public interface OfferService {

    Page<OfferDTO> findByCriteria(OfferFilter offerFilter, Pageable pageable);

    void uploadImages(List<MultipartFile> multipartFiles, Long offerId);

    Resource loadFile(Long offerId, String filename);

    Optional<OfferDTO> findOne(Long id);
}

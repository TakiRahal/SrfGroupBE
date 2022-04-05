package com.takirahal.srfgroup.modules.home.services;

import com.takirahal.srfgroup.modules.home.dto.TopHomeSlidesImageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TopHomeSlidesImageService {

    /**
     *
     * @param topHomeSlidesImage
     * @return
     */
    TopHomeSlidesImageDTO save(TopHomeSlidesImageDTO topHomeSlidesImage);

    Page<TopHomeSlidesImageDTO> findByCriteria(Pageable pageable);

    Optional<TopHomeSlidesImageDTO> findOne(Long id);
}

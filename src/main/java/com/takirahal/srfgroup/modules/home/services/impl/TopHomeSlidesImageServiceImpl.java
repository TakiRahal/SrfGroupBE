package com.takirahal.srfgroup.modules.home.services.impl;

import com.takirahal.srfgroup.exceptions.BadRequestAlertException;
import com.takirahal.srfgroup.modules.home.dto.TopHomeSlidesImageDTO;
import com.takirahal.srfgroup.modules.home.entities.TopHomeSlidesImage;
import com.takirahal.srfgroup.modules.home.mapper.TopHomeSlidesImageMapper;
import com.takirahal.srfgroup.modules.home.repositories.TopHomeSlidesImageRepository;
import com.takirahal.srfgroup.modules.home.services.TopHomeSlidesImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopHomeSlidesImageServiceImpl implements TopHomeSlidesImageService {

    @Autowired
    TopHomeSlidesImageRepository topHomeSlidesImageRepository;

    @Autowired
    TopHomeSlidesImageMapper topHomeSlidesImageMapper;

    @Override
    public TopHomeSlidesImageDTO save(TopHomeSlidesImageDTO topHomeSlidesImageDTO) {

        if (topHomeSlidesImageDTO.getId() != null) {
            throw new BadRequestAlertException("A new faq cannot already have an ID idexists");
        }

        TopHomeSlidesImage topHomeSlidesImage = topHomeSlidesImageMapper.toEntity(topHomeSlidesImageDTO);
        topHomeSlidesImage = topHomeSlidesImageRepository.save(topHomeSlidesImage);
        return topHomeSlidesImageMapper.toDto(topHomeSlidesImage);
    }

    @Override
    public Page<TopHomeSlidesImageDTO> findByCriteria(Pageable pageable) {
        return topHomeSlidesImageRepository.findAll(pageable).map(topHomeSlidesImageMapper::toDto);
    }

    @Override
    public Optional<TopHomeSlidesImageDTO> findOne(Long id) {
        return topHomeSlidesImageRepository.findById(id).map(topHomeSlidesImageMapper::toDto);
    }

}

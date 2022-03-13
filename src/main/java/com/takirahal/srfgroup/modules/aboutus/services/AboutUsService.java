package com.takirahal.srfgroup.modules.aboutus.services;

import com.takirahal.srfgroup.modules.aboutus.dto.AboutUsDTO;
import com.takirahal.srfgroup.modules.aboutus.dto.filter.AboutUsFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AboutUsService {
    AboutUsDTO save(AboutUsDTO aboutUsDTO);

    Page<AboutUsDTO> findByCriteria(AboutUsFilter aboutUsFilter, Pageable pageable);

    Optional<AboutUsDTO> findLastOne();
}

package com.takirahal.srfgroup.modules.category.services;

import com.takirahal.srfgroup.modules.category.dto.CategoryDTO;
import com.takirahal.srfgroup.modules.category.dto.filter.CategoryFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Page<CategoryDTO> findByCriteria(CategoryFilter criteria, Pageable pageable);
}

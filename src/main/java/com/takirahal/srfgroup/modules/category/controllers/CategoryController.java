package com.takirahal.srfgroup.modules.category.controllers;

import com.takirahal.srfgroup.modules.category.dto.CategoryDTO;
import com.takirahal.srfgroup.modules.category.dto.filter.CategoryFilter;
import com.takirahal.srfgroup.modules.category.services.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category/")
public class CategoryController {

    private final Logger log = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    CategoryService categoryService;

    /**
     * {@code GET  /categories} : get all the categories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categories in body.
     */
    @GetMapping("public")
    public ResponseEntity<Page<CategoryDTO>> getAllPublicCategories(CategoryFilter criteria, Pageable pageable) {
        log.debug("REST request to get Categories by criteria: {}", criteria);
        Page<CategoryDTO> page = categoryService.findByCriteria(criteria, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }
}

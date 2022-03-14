package com.takirahal.srfgroup.modules.category.services.impl;

import com.takirahal.srfgroup.modules.category.dto.CategoryDTO;
import com.takirahal.srfgroup.modules.category.dto.filter.CategoryFilter;
import com.takirahal.srfgroup.modules.category.entities.Category;
import com.takirahal.srfgroup.modules.category.mapper.CategoryMapper;
import com.takirahal.srfgroup.modules.category.repositories.CategoryRepository;
import com.takirahal.srfgroup.modules.category.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public Page<CategoryDTO> findByCriteria(CategoryFilter criteria, Pageable pageable) {
        return categoryRepository.findAll(createSpecification(criteria), pageable).map(categoryMapper::toDto);
    }

    private Specification<Category> createSpecification(CategoryFilter criteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            query.orderBy(criteriaBuilder.desc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

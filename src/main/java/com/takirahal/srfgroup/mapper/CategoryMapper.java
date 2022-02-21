package com.takirahal.srfgroup.mapper;

import com.takirahal.srfgroup.dto.CategoryDTO;
import com.takirahal.srfgroup.entities.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {
}

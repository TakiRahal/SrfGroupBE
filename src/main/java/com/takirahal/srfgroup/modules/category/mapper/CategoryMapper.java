package com.takirahal.srfgroup.modules.category.mapper;

import com.takirahal.srfgroup.mapper.EntityMapper;
import com.takirahal.srfgroup.modules.category.dto.CategoryDTO;
import com.takirahal.srfgroup.modules.category.entities.Category;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {})
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CategoryDTO toDtoId(Category category);
}

package com.takirahal.srfgroup.modules.category.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubCategoryDTO {
    private Long id;

    private String titleAr;

    private String titleFr;

    private String titleEn;

    private CategoryDTO category;
}

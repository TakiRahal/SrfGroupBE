package com.takirahal.srfgroup.modules.category.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sg_category")
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "title_ar")
    private String titleAr;

    @Column(name = "title_fr")
    private String titleFr;

    @Column(name = "title_en")
    private String titleEn;

    @OneToMany(mappedBy = "category")
    @JsonIgnoreProperties(value = { "category" }, allowSetters = true)
    private Set<SubCategory> subCategories = new HashSet<>();
}

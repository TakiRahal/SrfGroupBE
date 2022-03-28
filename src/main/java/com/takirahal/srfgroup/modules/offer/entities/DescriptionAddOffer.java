package com.takirahal.srfgroup.modules.offer.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A DescriptionAddOffer.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "description_add_offer")
public class DescriptionAddOffer implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "description_ar")
    private String descriptionAr;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "description_fr")
    private String descriptionFr;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "description_en")
    private String descriptionEn;
}

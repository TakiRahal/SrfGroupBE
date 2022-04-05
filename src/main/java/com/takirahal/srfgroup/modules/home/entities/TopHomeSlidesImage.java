package com.takirahal.srfgroup.modules.home.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sg_top_home_slides_image")
public class TopHomeSlidesImage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "descriptionAr")
    private String descriptionAr;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "descriptionFr")
    private String descriptionFr;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "descriptionEn")
    private String descriptionEn;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "image")
    private String image;

}

package com.takirahal.srfgroup.modules.aboutus.entities;

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
@Table(name = "sg_about_us")
public class AboutUs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "sequence_name_about_us", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    private Long id;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "content_ar")
    private String contentAr;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "content_en")
    private String contentEn;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "content_fr")
    private String contentFr;
}

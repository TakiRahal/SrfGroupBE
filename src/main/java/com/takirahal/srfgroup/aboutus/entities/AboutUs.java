package com.takirahal.srfgroup.aboutus.entities;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
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

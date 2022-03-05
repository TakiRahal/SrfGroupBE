package com.takirahal.srfgroup.faq.entities;

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
@Table(name = "sg_faq")
public class Faq implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "question_ar")
    private String questionAr;

    @Column(name = "question_fr")
    private String questionFr;

    @Column(name = "question_en")
    private String questionEn;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "response_ar")
    private String responseAr;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "response_fr")
    private String responseFr;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "response_en")
    private String responseEn;

}

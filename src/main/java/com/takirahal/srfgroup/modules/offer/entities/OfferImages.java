package com.takirahal.srfgroup.modules.offer.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.takirahal.srfgroup.modules.offer.entities.Offer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sg_offer_images")
public class OfferImages implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "path")
    private String path;

    @Column(name = "date_created")
    private Instant dateCreated;

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "offerImages" }, allowSetters = true)
    private Offer offer;

//    @ManyToOne
//    private User user;
}

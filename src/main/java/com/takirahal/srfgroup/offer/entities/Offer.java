package com.takirahal.srfgroup.offer.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.takirahal.srfgroup.entities.Address;
import com.takirahal.srfgroup.entities.Category;
import com.takirahal.srfgroup.entities.OfferImages;
import com.takirahal.srfgroup.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sg_offer")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_offer", length = 255)
public class Offer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "title")
    private String title;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "description")
    private String description;

    @Column(name = "date_created")
    private Instant dateCreated;

    @Column(name = "type_offer", insertable = false, updatable = false)
    private String typeOffer;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "offer", orphanRemoval = true)
    @JsonIgnoreProperties(value = { "offer" }, allowSetters = true)
    private Set<OfferImages> offerImages = new HashSet<>();

    @ManyToOne
    private Address address;

    @ManyToOne
    @JsonIgnoreProperties(value = { "subCategories" }, allowSetters = true)
    private Category category;
}

package com.takirahal.srfgroup.commentoffer.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.takirahal.srfgroup.entities.Offer;
import com.takirahal.srfgroup.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comment_offer")
public class CommentOffer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "created_date")
    private Instant createdDate;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "content")
    private String content;

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "offerImages" }, allowSetters = true)
    private Offer offer;

    @ManyToOne
    private User user;
}

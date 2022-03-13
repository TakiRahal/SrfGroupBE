package com.takirahal.srfgroup.modules.favoriteuser.entities;

import com.takirahal.srfgroup.modules.user.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sg_favorite_user")
public class FavoriteUser {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "favorite_date")
    private Instant favoriteDate;

    @ManyToOne
    private User currentUser;

    @ManyToOne
    private User favoriteUser;
}

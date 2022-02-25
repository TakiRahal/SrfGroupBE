package com.takirahal.srfgroup.offer.repositories;

import com.takirahal.srfgroup.entities.User;
import com.takirahal.srfgroup.offer.entities.FavoriteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteUserRepository extends JpaRepository<FavoriteUser, Long> {
    Optional<FavoriteUser> findByCurrentUserAndFavoriteUser(User currentUserEntity, User entityFavoriteUser);
}

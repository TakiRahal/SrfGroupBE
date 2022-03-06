package com.takirahal.srfgroup.favoriteuser.repositories;

import com.takirahal.srfgroup.user.entities.User;
import com.takirahal.srfgroup.favoriteuser.entities.FavoriteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteUserRepository extends JpaRepository<FavoriteUser, Long> {
    Optional<FavoriteUser> findByCurrentUserAndFavoriteUser(User currentUserEntity, User entityFavoriteUser);
}

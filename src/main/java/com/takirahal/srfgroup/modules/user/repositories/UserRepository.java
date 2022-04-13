package com.takirahal.srfgroup.modules.user.repositories;

import com.takirahal.srfgroup.modules.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//    Optional<User> findByEmail(String email);
//
//    Optional<User> findByUsernameOrEmail(String username, String email);
//
//    List<User> findByIdIn(List<Long> userIds);
//
//    Optional<User> findByUsername(String username);
//
//    Boolean existsByUsername(String username);
//
//    Boolean existsByEmail(String email);

    Optional<User> findOneByEmailIgnoreCase(String email);

    Optional<User> findOneByActivationKey(String activationKey);

    Optional<User> findOneWithAuthoritiesByEmailIgnoreCase(String usernameOrEmail);

    Optional<User> findOneByResetKey(String resetKey);
}

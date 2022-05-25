package com.takirahal.srfgroup.modules.cart.repositories;

import com.takirahal.srfgroup.modules.cart.entities.Cart;
import com.takirahal.srfgroup.modules.offer.entities.SellOffer;
import com.takirahal.srfgroup.modules.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>, JpaSpecificationExecutor<Cart> {

    @Query("SELECT COUNT(ct) from Cart ct where ct.user.id = ?#{principal.id} AND ct.sellOffer.id=:sellOfferId")
    long getCountCartBySellOfferAndUser(@Param("sellOfferId") Long sellOfferId);

    Optional<Cart> findBySellOfferAndUser(SellOffer sellOffer, User currentUserToEntity);
}

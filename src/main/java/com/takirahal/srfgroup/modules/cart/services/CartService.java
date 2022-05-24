package com.takirahal.srfgroup.modules.cart.services;

import com.takirahal.srfgroup.modules.cart.dto.CartDTO;
import com.takirahal.srfgroup.modules.cart.dto.filter.CartFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CartService {

    /**
     *
     * @param cartDTO
     * @return
     */
    CartDTO save(CartDTO cartDTO);

    /**
     *
     * @param cartFilter
     * @param pageable
     * @return
     */
    Page<CartDTO> getOffersByCurrentUser(CartFilter cartFilter, Pageable pageable);
}

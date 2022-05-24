package com.takirahal.srfgroup.modules.cart.controllers;

import com.takirahal.srfgroup.modules.cart.dto.CartDTO;
import com.takirahal.srfgroup.modules.cart.dto.filter.CartFilter;
import com.takirahal.srfgroup.modules.cart.services.CartService;
import com.takirahal.srfgroup.utils.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart/")
public class CartController {

    private final Logger log = LoggerFactory.getLogger(CartController.class);

    @Autowired
    CartService cartService;

    @PostMapping("create")
    public ResponseEntity<CartDTO> createFindOffer(@RequestBody CartDTO cartDTO) {
        log.debug("REST request to save Cart : {}", cartDTO);
        CartDTO result = cartService.save(cartDTO);
        return new ResponseEntity<>(result, HeaderUtil.createAlert("add_offer.message_create_offer_succefull", ""), HttpStatus.CREATED);
    }


    @GetMapping("current-user")
    public ResponseEntity<Page<CartDTO>> getCartsByCurrentUser(CartFilter cartFilter, Pageable pageable) {
        log.debug("REST request to get Carts by criteria: {}", cartFilter);
        Page<CartDTO> page = cartService.getOffersByCurrentUser(cartFilter, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }
}

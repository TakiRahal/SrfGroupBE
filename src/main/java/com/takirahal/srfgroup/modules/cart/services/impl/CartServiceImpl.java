package com.takirahal.srfgroup.modules.cart.services.impl;

import com.takirahal.srfgroup.exceptions.BadRequestAlertException;
import com.takirahal.srfgroup.exceptions.ResouorceNotFoundException;
import com.takirahal.srfgroup.exceptions.UnauthorizedException;
import com.takirahal.srfgroup.modules.cart.dto.CartDTO;
import com.takirahal.srfgroup.modules.cart.dto.filter.CartFilter;
import com.takirahal.srfgroup.modules.cart.entities.Cart;
import com.takirahal.srfgroup.modules.cart.mapper.CartMapper;
import com.takirahal.srfgroup.modules.cart.repositories.CartRepository;
import com.takirahal.srfgroup.modules.cart.services.CartService;
import com.takirahal.srfgroup.modules.commentoffer.entities.CommentOffer;
import com.takirahal.srfgroup.modules.offer.entities.SellOffer;
import com.takirahal.srfgroup.modules.offer.repositories.SellOfferRepository;
import com.takirahal.srfgroup.modules.user.dto.filter.UserOfferFilter;
import com.takirahal.srfgroup.modules.user.exceptioins.AccountResourceException;
import com.takirahal.srfgroup.modules.user.mapper.UserMapper;
import com.takirahal.srfgroup.security.UserPrincipal;
import com.takirahal.srfgroup.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final Logger log = LoggerFactory.getLogger(CartServiceImpl.class);

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartMapper cartMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    SellOfferRepository sellOfferRepository;


    /**
     *
     * @param cartDTO
     * @return
     */
    @Override
    public CartDTO save(CartDTO cartDTO) {
        log.info("Request to save SellOffer : {}", cartDTO);

        if (cartDTO.getId() != null) {
            throw new BadRequestAlertException("A new Cart cannot already have an ID idexists");
        }

        long nbeCart = cartRepository.getCountCartBySellOfferAndUser(cartDTO.getSellOffer().getId());

        UserPrincipal currentUser = SecurityUtils.getCurrentUser().orElseThrow(() -> new AccountResourceException("Current user login not found"));
        cartDTO.setUser(userMapper.toCurrentUserPrincipal(currentUser));

        Cart cart = cartMapper.toEntity(cartDTO);

        Optional<SellOffer> sellOfferOption = sellOfferRepository.findById(cartDTO.getSellOffer().getId());

        // Update
        if( nbeCart > 0 ){
            Optional<Cart> cartExist = cartRepository.findBySellOfferAndUser(cart.getSellOffer(), userMapper.currentUserToEntity(currentUser));
            if(cartExist.isPresent()){
                int quantity = cartExist.get().getQuantity() + cartDTO.getQuantity();
                cart.setId(cartExist.get().getId());
                cart.setQuantity(quantity);

                // Set new total
                if( sellOfferOption.isPresent() && !Objects.isNull(sellOfferOption.get().getAmount())){
                    Double newTotal = cartExist.get().getTotal() + (sellOfferOption.get().getAmount()*cartDTO.getQuantity());
                    cart.setTotal(newTotal);
                }

                cart = cartRepository.save(cart);
            }
        }
        else{
            if( sellOfferOption.isPresent() && !Objects.isNull(sellOfferOption.get().getAmount())){
                cart.setTotal(sellOfferOption.get().getAmount());
            }
            cart = cartRepository.save(cart);
        }

        return cartMapper.toDto(cart);
    }

    @Override
    public Page<CartDTO> getOffersByCurrentUser(CartFilter cartFilter, Pageable pageable) {
        log.info("Request to get all Carts for current user : {}", cartFilter);
        Long useId = SecurityUtils
                .getIdByCurrentUser()
                .orElseThrow(() -> new AccountResourceException("Current user not found"));

        UserOfferFilter userOfferFilter = new UserOfferFilter();
        userOfferFilter.setId(useId);
        cartFilter.setUser(userOfferFilter);
        return findByCriteria(cartFilter, pageable);
    }

    @Override
    public void delete(Long id) {
        log.info("Request to delete Cart : {}", id);

        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResouorceNotFoundException("Entity not found with id"));

        Long useId = SecurityUtils
                .getIdByCurrentUser()
                .orElseThrow(() -> new AccountResourceException("Current user not found"));
        if (!Objects.equals(useId, cart.getUser().getId())) {
            throw new UnauthorizedException("Unauthorized action");
        }

        cartRepository.deleteById(id);
    }

    @Override
    public CartDTO updateQuantityCart(CartDTO cartDTO) {

        log.info("Request to update quantity to Cart : {}", cartDTO.getId());

        Cart cart = cartRepository.findById(cartDTO.getId())
                .orElseThrow(() -> new ResouorceNotFoundException("Entity not found with id"));

        Long useId = SecurityUtils
                .getIdByCurrentUser()
                .orElseThrow(() -> new AccountResourceException("Current user not found"));
        if (!Objects.equals(useId, cart.getUser().getId())) {
            throw new UnauthorizedException("Unauthorized action");
        }

        Cart cartUpdate = cartMapper.toEntity(cartDTO);
        cartUpdate.setQuantity(cartDTO.getQuantity());
        cart = cartRepository.save(cartUpdate);

        return cartMapper.toDto(cart);
    }

    private Page<CartDTO> findByCriteria(CartFilter cartFilter, Pageable page) {
        log.debug("find carts by criteria : {}, page: {}", page);
        return cartRepository.findAll(createSpecification(cartFilter), page).map(cartMapper::toDto);
    }

    protected Specification<Cart> createSpecification(CartFilter cartFilter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if ( cartFilter.getUser() != null && cartFilter.getUser().getId() != null ) {
                predicates.add(criteriaBuilder.equal(root.get("user").get("id"), cartFilter.getUser().getId()));
            }

            query.orderBy(criteriaBuilder.desc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

package com.takirahal.srfgroup.modules.cart.mapper;

import com.takirahal.srfgroup.mapper.EntityMapper;
import com.takirahal.srfgroup.modules.cart.dto.CartDTO;
import com.takirahal.srfgroup.modules.cart.entities.Cart;
import com.takirahal.srfgroup.modules.offer.mapper.SellOfferMapper;
import com.takirahal.srfgroup.modules.user.mapper.UserMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class, SellOfferMapper.class})
public interface CartMapper  extends EntityMapper<CartDTO, Cart> {
}

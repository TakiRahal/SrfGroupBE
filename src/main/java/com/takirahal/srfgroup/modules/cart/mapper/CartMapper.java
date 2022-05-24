package com.takirahal.srfgroup.modules.cart.mapper;

import com.takirahal.srfgroup.mapper.EntityMapper;
import com.takirahal.srfgroup.modules.cart.dto.CartDTO;
import com.takirahal.srfgroup.modules.cart.entities.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CartMapper  extends EntityMapper<CartDTO, Cart> {
}

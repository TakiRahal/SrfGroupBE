package com.takirahal.srfgroup.offer.services;

import com.takirahal.srfgroup.dto.OfferDTO;
import com.takirahal.srfgroup.offer.dto.OfferWithMyFavoriteUserDTO;

import java.util.Optional;

public interface FavoriteUserService {
    OfferWithMyFavoriteUserDTO getOfferWithMyFavoriteUser(Optional<OfferDTO> offerDTO);
}

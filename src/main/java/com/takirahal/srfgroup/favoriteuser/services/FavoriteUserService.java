package com.takirahal.srfgroup.favoriteuser.services;

import com.takirahal.srfgroup.favoriteuser.dto.FavoriteUserDTO;
import com.takirahal.srfgroup.offer.dto.OfferDTO;
import com.takirahal.srfgroup.offer.dto.OfferWithMyFavoriteUserDTO;

import java.util.Optional;

public interface FavoriteUserService {

    /**
     * Save a favorite.
     *
     * @param favoriteDTO the entity to save.
     * @return the persisted entity.
     */
    FavoriteUserDTO save(FavoriteUserDTO favoriteDTO);

    OfferWithMyFavoriteUserDTO getOfferWithMyFavoriteUser(Optional<OfferDTO> offerDTO);
}

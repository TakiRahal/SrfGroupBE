package com.takirahal.srfgroup.offer.services.impl;

import com.takirahal.srfgroup.offer.dto.OfferDTO;
import com.takirahal.srfgroup.dto.UserDTO;
import com.takirahal.srfgroup.entities.User;
import com.takirahal.srfgroup.mapper.UserMapper;
import com.takirahal.srfgroup.offer.dto.OfferWithMyFavoriteUserDTO;
import com.takirahal.srfgroup.offer.entities.FavoriteUser;
import com.takirahal.srfgroup.offer.repositories.FavoriteUserRepository;
import com.takirahal.srfgroup.offer.services.FavoriteUserService;
import com.takirahal.srfgroup.security.UserPrincipal;
import com.takirahal.srfgroup.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class FavoriteUserServiceImpl implements FavoriteUserService {


    @Autowired
    UserMapper userMapper;

    @Autowired
    FavoriteUserRepository favoriteUserRepository;

    @Override
    public OfferWithMyFavoriteUserDTO getOfferWithMyFavoriteUser(Optional<OfferDTO> offerDTO) {
        OfferWithMyFavoriteUserDTO offerWithMyFavoriteUserDTO = new OfferWithMyFavoriteUserDTO();
        offerWithMyFavoriteUserDTO.setOffer(offerDTO.get());

        Optional<UserPrincipal> currentUser = SecurityUtils.getCurrentUser();
        if(!currentUser.isPresent()){
            offerWithMyFavoriteUserDTO.setMyFavoriteUser(false);
        }
        else{
            offerWithMyFavoriteUserDTO.setMyFavoriteUser(isMyFavoriteUser(currentUser.get(), offerDTO.get().getUser()));
        }

        return offerWithMyFavoriteUserDTO;
    }

    @Transactional(readOnly = true)
    public boolean isMyFavoriteUser(UserPrincipal currentUser, UserDTO favoriteUser) {
        User entityFavoriteUser = userMapper.toEntity(favoriteUser);
        User currentUserEntity = userMapper.currentUserToEntity(currentUser);
        Optional<FavoriteUser> favorite = favoriteUserRepository.findByCurrentUserAndFavoriteUser(currentUserEntity, entityFavoriteUser);
        if (favorite.isPresent()) {
            return true;
        }
        return false;
    }
}

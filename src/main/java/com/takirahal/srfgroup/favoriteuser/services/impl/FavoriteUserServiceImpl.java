package com.takirahal.srfgroup.favoriteuser.services.impl;

import com.takirahal.srfgroup.exceptions.AccountResourceException;
import com.takirahal.srfgroup.favoriteuser.dto.FavoriteUserDTO;
import com.takirahal.srfgroup.favoriteuser.mapper.FavoriteUserMapper;
import com.takirahal.srfgroup.offer.dto.OfferDTO;
import com.takirahal.srfgroup.user.dto.UserDTO;
import com.takirahal.srfgroup.user.entities.User;
import com.takirahal.srfgroup.mapper.UserMapper;
import com.takirahal.srfgroup.offer.dto.OfferWithMyFavoriteUserDTO;
import com.takirahal.srfgroup.favoriteuser.entities.FavoriteUser;
import com.takirahal.srfgroup.favoriteuser.repositories.FavoriteUserRepository;
import com.takirahal.srfgroup.favoriteuser.services.FavoriteUserService;
import com.takirahal.srfgroup.security.UserPrincipal;
import com.takirahal.srfgroup.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class FavoriteUserServiceImpl implements FavoriteUserService {

    private final Logger log = LoggerFactory.getLogger(FavoriteUserServiceImpl.class);

    @Autowired
    UserMapper userMapper;

    @Autowired
    FavoriteUserRepository favoriteUserRepository;

    @Autowired
    FavoriteUserMapper favoriteUserMapper;

    @Override
    public FavoriteUserDTO save(FavoriteUserDTO favoriteDTO) {
        log.debug("Request to save Favorite User: {}", favoriteDTO);


        UserDTO currentUser = SecurityUtils.getCurrentUser()
                .map(userMapper::toCurrentUser)
                .orElseThrow(() -> new AccountResourceException("Current user login not found"));

        favoriteDTO.setCurrentUser(currentUser);
        FavoriteUser favorite = favoriteUserMapper.toEntity(favoriteDTO);
        favorite = favoriteUserRepository.save(favorite);
        return favoriteUserMapper.toDto(favorite);
    }

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

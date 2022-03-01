package com.takirahal.srfgroup.commentoffer.dto;

import com.takirahal.srfgroup.dto.OfferDTO;
import com.takirahal.srfgroup.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentOfferDTO {
    private Long id;

    private Instant createdDate;

    @Lob
    private String content;

    private OfferDTO offer;

    private UserDTO user;
}

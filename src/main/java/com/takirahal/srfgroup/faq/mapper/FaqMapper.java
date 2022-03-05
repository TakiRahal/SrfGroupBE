package com.takirahal.srfgroup.faq.mapper;

import com.takirahal.srfgroup.faq.dto.FaqDTO;
import com.takirahal.srfgroup.faq.entities.Faq;
import com.takirahal.srfgroup.mapper.EntityMapper;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Faq} and its DTO {@link FaqDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FaqMapper extends EntityMapper<FaqDTO, Faq> {}

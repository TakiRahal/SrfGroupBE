package com.takirahal.srfgroup.aboutus.mapper;

import com.takirahal.srfgroup.aboutus.dto.AboutUsDTO;
import com.takirahal.srfgroup.aboutus.entities.AboutUs;
import com.takirahal.srfgroup.mapper.EntityMapper;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link AboutUs} and its DTO {@link AboutUsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AboutUsMapper extends EntityMapper<AboutUsDTO, AboutUs> {}
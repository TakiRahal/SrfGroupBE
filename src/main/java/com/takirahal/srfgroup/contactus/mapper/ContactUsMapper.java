package com.takirahal.srfgroup.contactus.mapper;

import com.takirahal.srfgroup.contactus.dto.ContactUsDTO;
import com.takirahal.srfgroup.contactus.entities.ContactUs;
import com.takirahal.srfgroup.mapper.EntityMapper;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link ContactUs} and its DTO {@link ContactUsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ContactUsMapper extends EntityMapper<ContactUsDTO, ContactUs> {}

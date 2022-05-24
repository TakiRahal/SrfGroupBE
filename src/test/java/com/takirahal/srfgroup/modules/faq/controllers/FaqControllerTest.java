package com.takirahal.srfgroup.modules.faq.controllers;

import com.takirahal.srfgroup.modules.faq.dto.FaqDTO;
import com.takirahal.srfgroup.modules.faq.dto.filter.FaqFilter;
import com.takirahal.srfgroup.modules.faq.entities.Faq;
import com.takirahal.srfgroup.modules.faq.mapper.FaqMapper;
import com.takirahal.srfgroup.modules.faq.repositories.FaqRepository;
import com.takirahal.srfgroup.modules.faq.services.FaqService;
import com.takirahal.srfgroup.modules.faq.services.impl.FaqServiceImpl;
import com.takirahal.srfgroup.modules.utils.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest // Exist Autowired
@AutoConfigureMockMvc
class FaqControllerTest {

    @Mock
    FaqRepository faqRepository;

    private static final String ENTITY_API_URL = "/api/faq/";

    @MockBean
    FaqService faqService;

//    @InjectMocks
//    FaqServiceImpl faqServiceImpl;

    @Mock
    FaqRepository faqRepositoryMock;

    @Spy
    private FaqMapper faqMapper = Mappers.getMapper(FaqMapper.class);

    @Autowired
    private MockMvc restFaqMockMvc;

//    Faq faq0 = Faq.builder().id(0L).questionAr("Qar0").responseAr("Rar0").questionFr("Qfr0").responseFr("Rfr0").questionEn("Qen0").responseEn("Ren0").build();
//    Faq faq1 = Faq.builder().id(1L).questionAr("Qar1").responseAr("Rar1").questionFr("Qfr1").responseFr("Rfr1").questionEn("Qen1").responseEn("Ren1").build();
//    Faq faq2 = Faq.builder().id(2L).questionAr("Qar2").responseAr("Rar2").questionFr("Qfr2").responseFr("Rfr2").questionEn("Qen2").responseEn("Ren2").build();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void createFaq() throws Exception {

        // Given
        Faq faq = Faq.builder().questionAr("Qar0").responseAr("Rar0").questionFr("Qfr0").responseFr("Rfr0").questionEn("Qen0").responseEn("Ren0").build();
        FaqDTO faqDTO = faqMapper.toDto(faq);

        // When
        restFaqMockMvc
                .perform(post(ENTITY_API_URL+"admin/create").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(faqDTO)))
                .andExpect(status().isCreated());

        // Then

    }

    @Test
    @DisplayName("Should list all posts when making GET equest to endpoint - /api/faq/public")
    void getAllPublicFaqs() throws Exception {

        // Given
        Faq faq = new Faq();
        faq.setId(1L);
        faq.setQuestionAr("Qar");
        faq.setResponseAr("Rar");
        faq.setQuestionFr("Qfr");
        faq.setResponseFr("Rfr");
        faq.setQuestionEn("Qen");
        faq.setResponseEn("Ren");
        List<Faq> faqs = new ArrayList<>();
        faqs.add(faq);
        Pageable pageable = PageRequest.of(0, 1);
        FaqFilter criteria = new FaqFilter();
        Page<Faq> faqPage = new PageImpl<>(faqs, pageable, faqs.size());

        FaqDTO faqDTO = new FaqDTO();
        faqDTO.setId(1L);
        faqDTO.setQuestionAr("Qar");
        faqDTO.setResponseAr("Rar");
        faqDTO.setQuestionFr("Qfr");
        faqDTO.setResponseFr("Rfr");
        faqDTO.setQuestionEn("Qen");
        faqDTO.setResponseEn("Ren");
        List<FaqDTO> faqDtoList = new ArrayList<>();
        faqDtoList.add(faqDTO);
        Page<FaqDTO> faqDtoPage = new PageImpl<>(faqDtoList, pageable, faqDtoList.size());
        Mockito.when(faqMapper.toDto(faq)).thenReturn(faqDTO);


        Mockito.when(faqRepositoryMock.findAll(any(Specification.class), any(Pageable.class))).thenReturn(faqPage);

        // When
        // Execute the GET request
        restFaqMockMvc.perform(get("/api/faq/public"))
                // Validate the response code and content type
                .andExpect(status().isOk());
                // .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // .andExpect(jsonPath("$.[*].id").value(hasItem(faq.getId().intValue())));
                // .andExpect(jsonPath("$.[*].questionAr").value(hasItem("Qar")));

                // Validate headers
//                .andExpect(header().string(HttpHeaders.LOCATION, "/api/faq/public"))

                // Validate the returned fields
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].id", is(1)))
//                .andExpect(jsonPath("$[0].name", is("Widget Name")))
//                .andExpect(jsonPath("$[0].description", is("Description")))
//                .andExpect(jsonPath("$[0].version", is(1)))
//                .andExpect(jsonPath("$[1].id", is(2)))
//                .andExpect(jsonPath("$[1].name", is("Widget 2 Name")))
//                .andExpect(jsonPath("$[1].description", is("Description 2")))
//                .andExpect(jsonPath("$[1].version", is(4)));

    }
}
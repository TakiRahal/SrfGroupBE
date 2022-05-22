package com.takirahal.srfgroup.modules.faq.controllers;

import com.takirahal.srfgroup.modules.faq.dto.FaqDTO;
import com.takirahal.srfgroup.modules.faq.dto.filter.FaqFilter;
import com.takirahal.srfgroup.modules.faq.entities.Faq;
import com.takirahal.srfgroup.modules.faq.mapper.FaqMapper;
import com.takirahal.srfgroup.modules.faq.repositories.FaqRepository;
import com.takirahal.srfgroup.modules.faq.services.FaqService;
import com.takirahal.srfgroup.modules.faq.services.impl.FaqServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class FaqControllerTest {

    @Mock
    FaqRepository faqRepository;

//    @InjectMocks
//    private FaqController faqController;

    @MockBean
    FaqService faqService;

    @InjectMocks
    FaqServiceImpl faqServiceImpl;

    @Spy
    private FaqMapper faqMapper = Mappers.getMapper(FaqMapper.class);

    @Autowired
    private MockMvc mockMvc;

    Faq faq0 = Faq.builder().id(0L).questionAr("Qar0").responseAr("Rar0").questionFr("Qfr0").responseFr("Rfr0").questionEn("Qen0").responseEn("Ren0").build();
    Faq faq1 = Faq.builder().id(1L).questionAr("Qar1").responseAr("Rar1").questionFr("Qfr1").responseFr("Rfr1").questionEn("Qen1").responseEn("Ren1").build();
    Faq faq2 = Faq.builder().id(2L).questionAr("Qar2").responseAr("Rar2").questionFr("Qfr2").responseFr("Rfr2").questionEn("Qen2").responseEn("Ren2").build();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void createFaq() {
    }

    @Test
    @DisplayName("Should list all posts when making GET equest to endpoint - /api/faq/public")
    void getAllPublicFaqs() throws Exception {

        // Given
        FaqDTO faqDTO = FaqDTO.builder().id(0L).questionAr("Qar0").responseAr("Rar0").questionFr("Qfr0").responseFr("Rfr0").questionEn("Qen0").responseEn("Ren0").build();

        Page<FaqDTO> faqDTOS = new Page<FaqDTO>() {
            @Override
            public int getTotalPages() {
                return 1;
            }

            @Override
            public long getTotalElements() {
                return 1;
            }

            @Override
            public <U> Page<U> map(Function<? super FaqDTO, ? extends U> function) {
                return null;
            }

            @Override
            public int getNumber() {
                return 1;
            }

            @Override
            public int getSize() {
                return 1;
            }

            @Override
            public int getNumberOfElements() {
                return 1;
            }

            @Override
            public List<FaqDTO> getContent() {
                return new ArrayList<FaqDTO>(Arrays.asList(faqDTO));
            }

            @Override
            public boolean hasContent() {
                return true;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public Iterator<FaqDTO> iterator() {
                return null;
            }
        };

        FaqFilter criteria = new FaqFilter();
        Pageable pageable = PageRequest.of(0, 1);
        when(faqServiceImpl.findByCriteria(criteria, pageable)).thenReturn(faqDTOS); // Mockito will return this list as response

        // When
        // Execute the GET request
        mockMvc.perform(get("/api/faq/public"))
                // Validate the response code and content type
                .andExpect(status().isOk());
                // .andExpect(content().contentType(MediaType.APPLICATION_JSON))

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
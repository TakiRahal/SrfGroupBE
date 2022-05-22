package com.takirahal.srfgroup.modules.faq.services.impl;

import com.takirahal.srfgroup.modules.faq.dto.FaqDTO;
import com.takirahal.srfgroup.modules.faq.dto.filter.FaqFilter;
import com.takirahal.srfgroup.modules.faq.entities.Faq;
import com.takirahal.srfgroup.modules.faq.mapper.FaqMapper;
import com.takirahal.srfgroup.modules.faq.repositories.FaqRepository;
import com.takirahal.srfgroup.modules.faq.services.FaqService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FaqServiceImplTest {

    @Mock
    FaqRepository faqRepositoryMock;

    @InjectMocks
    private FaqServiceImpl faqServiceImpl;

    @Spy
    private FaqMapper faqMapper = Mappers.getMapper(FaqMapper.class);

    private AutoCloseable autoCloseable;


    @BeforeEach
    void setUp() {
          autoCloseable = MockitoAnnotations.openMocks(this); // this is needed for inititalizytion of mocks, if you use @Mock
    }

    @AfterEach
    void tearDown() throws Exception {
         autoCloseable.close();
    }

    @Test
    @DisplayName("when save Faq, then FaqDTO are retrieved")
    void save() {
        //Given
        FaqDTO faqDTO = FaqDTO.builder().questionAr("Qar").responseAr("Rar").questionFr("Qfr").responseFr("Rfr").questionEn("Qen").responseEn("Ren").build();
        Faq faq = Faq.builder().questionAr("Qar").responseAr("Rar").questionFr("Qfr").responseFr("Rfr").questionEn("Qen").responseEn("Ren").build();

        //When
        when(faqRepositoryMock.save(any(Faq.class))).thenReturn(faq);
        FaqDTO faqDTO1 = faqServiceImpl.save(faqDTO);

        //Then
        assertNotNull(faqDTO1);
        assertEquals(faqDTO1.getId(), faqDTO.getId());
    }

    protected Specification<Faq> createSpecification(FaqFilter faqFilter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            query.orderBy(criteriaBuilder.desc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    @Test
    @Disabled
    void findByCriteria() {

        // Given
        Faq faq = new Faq();
        faq.setId(1L);
        faq.setQuestionAr("Qar");
        faq.setResponseAr("Rar");
        faq.setQuestionFr("Qfr");
        faq.setResponseFr("Rfr");
        faq.setQuestionEn("Qen");
        faq.setResponseEn("Ren");
        FaqFilter criteria = new FaqFilter();
        Pageable pageable = PageRequest.of(0, 1);
        doReturn(new Page<Faq>() {
            @Override
            public int getTotalPages() {
                return 1;
            }

            @Override
            public long getTotalElements() {
                return 1;
            }

            @Override
            public <U> Page<U> map(Function<? super Faq, ? extends U> function) {
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
            public List<Faq> getContent() {
                return Arrays.asList(faq);
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
            public Iterator<Faq> iterator() {
                return null;
            }
        }).when(faqRepositoryMock).findAll(createSpecification(criteria), pageable);

        // When
        Page<Faq> faqDTOS = faqServiceImpl.findByCriteriaEntity(criteria, pageable);

        // Then
        assertNotNull(faqDTOS);
    }

    @Test
    @Disabled
    void createSpecification() {
    }

    @Test
    @DisplayName("when list Faq, then Faqs are retrieved")
    void findAll() {

        // Given
        Faq faq = new Faq();
        faq.setId(1L);
        faq.setQuestionAr("Qar");
        faq.setResponseAr("Rar");
        faq.setQuestionFr("Qfr");
        faq.setResponseFr("Rfr");
        faq.setQuestionEn("Qen");
        faq.setResponseEn("Ren");
        when(faqRepositoryMock.findAll()).thenReturn(Arrays.asList(faq));

        // When
        List<Faq> faqList = faqServiceImpl.findAll();

        // Then
        assertNotNull(faqList);
        assertFalse(faqList.isEmpty());
        assertEquals(faq.getId(), faqList.get(0).getId());
    }
}
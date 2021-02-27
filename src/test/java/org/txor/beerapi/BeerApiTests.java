package org.txor.beerapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.txor.beerapi.domain.BeerService;
import org.txor.beerapi.domain.converters.BeerConverter;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.txor.beerapi.TestMother.someBeers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class BeerApiTests {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private BeerService service;

    private MockMvc mockMvc;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public BeerConverter beerConverter() {
            return new BeerConverter();
        }
    }

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation)).build();
    }

    @Test
    void list_all_beers() throws Exception {
        when(service.getAllBeers()).thenReturn(someBeers());

        this.mockMvc.perform(get("/api/beers"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].name").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].graduation").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].type").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].description").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].manufacturer").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].manufacturer.name").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].manufacturer.nationality").isNotEmpty())
                .andDo(document("beer-list"));
    }
}

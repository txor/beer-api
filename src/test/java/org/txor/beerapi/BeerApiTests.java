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
import org.txor.beerapi.domain.converters.Beer2BeerDtoConverter;
import org.txor.beerapi.domain.converters.Manufacturer2ManufacturerDtoConverter;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.txor.beerapi.TestMother.BEER1_DESCRIPTION;
import static org.txor.beerapi.TestMother.BEER1_GRADUATION;
import static org.txor.beerapi.TestMother.BEER1_NAME;
import static org.txor.beerapi.TestMother.BEER1_TYPE;
import static org.txor.beerapi.TestMother.BEER2_DESCRIPTION;
import static org.txor.beerapi.TestMother.BEER2_GRADUATION;
import static org.txor.beerapi.TestMother.BEER2_NAME;
import static org.txor.beerapi.TestMother.BEER2_TYPE;
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
        public Manufacturer2ManufacturerDtoConverter manufacturerConverter() {
            return new Manufacturer2ManufacturerDtoConverter();
        }

        @Bean
        public Beer2BeerDtoConverter beerConverter(Manufacturer2ManufacturerDtoConverter manufacturerConverter) {
            return new Beer2BeerDtoConverter(manufacturerConverter);
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
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(BEER1_NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].graduation").value(BEER1_GRADUATION))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].type").value(BEER1_TYPE))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value(BEER1_DESCRIPTION))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].manufacturer").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].manufacturer.name").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].manufacturer.nationality").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(BEER2_NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].graduation").value(BEER2_GRADUATION))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].type").value(BEER2_TYPE))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].description").value(BEER2_DESCRIPTION))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].manufacturer").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].manufacturer.name").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].manufacturer.nationality").isNotEmpty())
                .andDo(document("beer-list"));
    }
}

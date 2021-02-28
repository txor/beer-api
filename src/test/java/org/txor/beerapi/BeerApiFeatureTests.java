package org.txor.beerapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.txor.beerapi.domain.BeerService;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseBody;
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
import static org.txor.beerapi.TestMother.beer1;
import static org.txor.beerapi.TestMother.someBeers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class BeerApiFeatureTests {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private BeerService service;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation)).build();
    }

    @Test
    public void list_all_beers() throws Exception {
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
                .andDo(document("beer-list-example", responseBody()));
    }

    @Test
    public void obtain_beer_information() throws Exception {
        when(service.getBeer(anyString())).thenReturn(beer1());

        this.mockMvc.perform(get("/api/beer/{name}/", BEER1_NAME))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(BEER1_NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$.graduation").value(BEER1_GRADUATION))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value(BEER1_TYPE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(BEER1_DESCRIPTION))
                .andExpect(MockMvcResultMatchers.jsonPath("$.manufacturer").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.manufacturer.name").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.manufacturer.nationality").isNotEmpty())
                .andDo(document("beer-example", responseBody()));
    }
}

package org.txor.beerapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class BeerApiTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation)).build();
    }

    @Test
    void list_all_beers() throws Exception {
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

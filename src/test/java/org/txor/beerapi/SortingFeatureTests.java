package org.txor.beerapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseBody;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.txor.beerapi.testutils.TestMother.BEER1_NAME;
import static org.txor.beerapi.testutils.TestMother.BEER2_NAME;
import static org.txor.beerapi.testutils.TestMother.BEER3_NAME;
import static org.txor.beerapi.testutils.TestMother.BEER4_NAME;
import static org.txor.beerapi.testutils.TestMother.BEER5_NAME;
import static org.txor.beerapi.testutils.TestMother.MANUFACTURER1_NAME;
import static org.txor.beerapi.testutils.TestMother.MANUFACTURER2_NAME;
import static org.txor.beerapi.testutils.TestMother.MANUFACTURER3_NAME;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class SortingFeatureTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation)).build();
    }

    @Test
    @Sql({"/delete_beer_data.sql", "/delete_manufacturer_data.sql", "/insert_manufacturer_data.sql", "/insert_beer_data.sql"})
    public void list_all_manufacturer_default_sort() throws Exception {
        this.mockMvc.perform(get("/api/manufacturers"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value(MANUFACTURER1_NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1]").value(MANUFACTURER2_NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2]").value(MANUFACTURER3_NAME))
                .andDo(document("manufacturer-list-example", responseBody()));
    }

    @Test
    @Sql({"/delete_beer_data.sql", "/delete_manufacturer_data.sql", "/insert_manufacturer_data.sql", "/insert_beer_data.sql"})
    public void list_all_beers_default_sort() throws Exception {
        this.mockMvc.perform(get("/api/beers"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value(BEER1_NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1]").value(BEER2_NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2]").value(BEER3_NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3]").value(BEER4_NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$[4]").value(BEER5_NAME))
                .andDo(document("beer-list-example", responseBody()));
    }
}

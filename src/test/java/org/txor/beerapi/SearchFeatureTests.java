package org.txor.beerapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.txor.beerapi.testutils.TestMother.BEER1_NAME;
import static org.txor.beerapi.testutils.TestMother.BEER2_NAME;
import static org.txor.beerapi.testutils.TestMother.BEER3_NAME;
import static org.txor.beerapi.testutils.TestMother.BEER4_NAME;
import static org.txor.beerapi.testutils.TestMother.BEER5_NAME;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
class SearchFeatureTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Sql({"/delete_beer_data.sql", "/delete_manufacturer_data.sql", "/insert_manufacturer_data.sql", "/insert_beer_data.sql"})
    public void search_beers_by_name() throws Exception {
        this.mockMvc.perform(
                get("/api/beers")
                        .param("name", "Coronita"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value(BEER2_NAME));
    }

    @Test
    @Sql({"/delete_beer_data.sql", "/delete_manufacturer_data.sql", "/insert_manufacturer_data.sql", "/insert_beer_data.sql"})
    public void search_beers_by_type() throws Exception {
        this.mockMvc.perform(
                get("/api/beers")
                        .param("type", "Lager Beer"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value(BEER3_NAME));
    }

    @Test
    @Sql({"/delete_beer_data.sql", "/delete_manufacturer_data.sql", "/insert_manufacturer_data.sql", "/insert_beer_data.sql"})
    public void search_beers_by_manufacturer() throws Exception {
        this.mockMvc.perform(
                get("/api/beers")
                        .param("manufacturer", "Spaten-Franziskaner-Bräu GmbH"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value(BEER4_NAME));
    }

    @Test
    @Sql({"/delete_beer_data.sql", "/delete_manufacturer_data.sql", "/insert_manufacturer_data.sql", "/insert_beer_data.sql"})
    public void search_beers_by_nationality() throws Exception {
        this.mockMvc.perform(
                get("/api/beers")
                        .param("nationality", "Spanish"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value(BEER1_NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1]").value(BEER3_NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2]").value(BEER5_NAME));
    }

    @Test
    @Sql({"/delete_beer_data.sql", "/delete_manufacturer_data.sql", "/insert_manufacturer_data.sql", "/insert_beer_data.sql"})
    public void search_beers_with_all_filters() throws Exception {
        this.mockMvc.perform(
                get("/api/beers")
                        .param("name", "Franziskaner")
                        .param("type", "Wheat Beer")
                        .param("manufacturer", "Spaten-Franziskaner-Bräu GmbH")
                        .param("nationality", "German"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value(BEER4_NAME));
    }
}

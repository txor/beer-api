package org.txor.beerapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.txor.beerapi.repository.entity.BeerEntity;
import org.txor.beerapi.testutils.BeerDatabaseTestClient;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseBody;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.txor.beerapi.testutils.TestMother.BEER1_DESCRIPTION;
import static org.txor.beerapi.testutils.TestMother.BEER1_GRADUATION;
import static org.txor.beerapi.testutils.TestMother.BEER1_NAME;
import static org.txor.beerapi.testutils.TestMother.BEER1_TYPE;
import static org.txor.beerapi.testutils.TestMother.BEER2_NAME;
import static org.txor.beerapi.testutils.TestMother.MANUFACTURER1_NAME;
import static org.txor.beerapi.testutils.TestMother.beer1JsonString;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class BeerFeatureTests {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private BeerDatabaseTestClient databaseTestClient;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation)).build();
    }

    @Test
    @Sql({"/delete_beer_data.sql", "/insert_beer_data.sql"})
    public void list_all_beers() throws Exception {
        this.mockMvc.perform(get("/api/beers"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value(BEER1_NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1]").value(BEER2_NAME))
                .andDo(document("beer-list-example", responseBody()));
    }

    @Test
    @Sql({"/delete_beer_data.sql", "/delete_manufacturer_data.sql", "/insert_manufacturer_data.sql"})
    public void create_beer() throws Exception {
        this.mockMvc.perform(post("/api/beer")
                .content(beer1JsonString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(document("beer-create-example",
                        requestFields(
                                fieldWithPath("name").description("The beer name"),
                                fieldWithPath("graduation").description("The beer alcoholic graduation"),
                                fieldWithPath("type").description("The beer type"),
                                fieldWithPath("description").description("The beer description"),
                                fieldWithPath("manufacturer").description("The beer manufacturer name"))));
        assertTrue(databaseTestClient.existsById(BEER1_NAME));
    }

    @Test
    public void not_create_beer_with_bad_data() throws Exception {
        this.mockMvc.perform(post("/api/beer")
                .content("{\"bad\": \"beer data\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql({"/delete_beer_data.sql", "/delete_manufacturer_data.sql", "/insert_manufacturer_data.sql", "/insert_beer_data.sql"})
    public void retrieve_beer_information() throws Exception {
        this.mockMvc.perform(get("/api/beer/{name}", BEER1_NAME))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(BEER1_NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$.graduation").value(BEER1_GRADUATION))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value(BEER1_TYPE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(BEER1_DESCRIPTION))
                .andExpect(MockMvcResultMatchers.jsonPath("$.manufacturer").value(MANUFACTURER1_NAME))
                .andDo(document("beer-get-example", responseBody()));
    }

    @Test
    public void not_retrieve_non_existing_beer_information() throws Exception {
        String nonExistingBeer = "non existing beer";
        this.mockMvc.perform(get("/api/beer/{name}/", nonExistingBeer))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertEquals(nonExistingBeer + " not found", result.getResponse().getContentAsString()));
    }

    @Test
    @Sql({"/delete_beer_data.sql", "/delete_manufacturer_data.sql", "/insert_manufacturer_data.sql", "/insert_minimal_beer_data.sql"})
    public void update_beer_information() throws Exception {
        this.mockMvc.perform(put("/api/beer/{name}", BEER1_NAME)
                .content(beer1JsonString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(document("beer-update-example",
                        requestFields(
                                fieldWithPath("name").description("The beer name"),
                                fieldWithPath("graduation").description("The beer alcoholic graduation"),
                                fieldWithPath("type").description("The beer type"),
                                fieldWithPath("description").description("The beer description"),
                                fieldWithPath("manufacturer").description("The beer manufacturer name"))));
        Optional<BeerEntity> persistedBeer = databaseTestClient.findById(BEER1_NAME);
        assertThat(persistedBeer).map(BeerEntity::getGraduation).hasValue(BEER1_GRADUATION);
        assertThat(persistedBeer).map(BeerEntity::getType).hasValue(BEER1_TYPE);
        assertThat(persistedBeer).map(BeerEntity::getDescription).hasValue(BEER1_DESCRIPTION);
        assertThat(persistedBeer).map(beer -> beer.getManufacturer().getName()).hasValue(MANUFACTURER1_NAME);
    }

    @Test
    public void not_update_beer_information_with_bad_data() throws Exception {
        this.mockMvc.perform(put("/api/beer/{name}", "some beer")
                .content("{\"bad\": \"beer data\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql("/delete_beer_data.sql")
    public void not_update_non_existing_beer() throws Exception {
        this.mockMvc.perform(put("/api/beer/{name}", BEER1_NAME)
                .content(beer1JsonString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertEquals(BEER1_NAME + " not found", result.getResponse().getContentAsString()));
    }

    @Test
    public void not_update_non_matching_beer_data() throws Exception {
        String randomBeerName = "random beer name";

        this.mockMvc.perform(put("/api/beer/{name}/", randomBeerName)
                .content(beer1JsonString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals(randomBeerName + " does not match", result.getResponse().getContentAsString()));
    }

    @Test
    @Sql({"/delete_beer_data.sql", "/delete_manufacturer_data.sql", "/insert_manufacturer_data.sql", "/insert_beer_data.sql"})
    public void delete_beer() throws Exception {
        this.mockMvc.perform(delete("/api/beer/{name}", BEER1_NAME))
                .andExpect(status().isOk())
                .andDo(document("beer-delete-example"));
        assertFalse(databaseTestClient.existsById(BEER1_NAME));
    }

    @Test
    @Sql("/delete_beer_data.sql")
    public void not_delete_non_existing_beer() throws Exception {
        this.mockMvc.perform(delete("/api/beer/{name}", BEER1_NAME))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertEquals(BEER1_NAME + " not found", result.getResponse().getContentAsString()));
    }
}

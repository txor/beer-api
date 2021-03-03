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
import org.txor.beerapi.repository.entity.ManufacturerEntity;

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
import static org.txor.beerapi.TestMother.MANUFACTURER1_NAME;
import static org.txor.beerapi.TestMother.MANUFACTURER1_NATIONALITY;
import static org.txor.beerapi.TestMother.MANUFACTURER2_NAME;
import static org.txor.beerapi.TestMother.manufacturer1JsonString;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class ManufacturerFeatureTests {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ManufacturerDatabaseTestClient databaseTestClient;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation)).build();
    }

    @Test
    @Sql({"/wipe_manufacturer_data.sql", "/manufacturer_data.sql"})
    public void list_all_manufacturer_names() throws Exception {
        this.mockMvc.perform(get("/api/manufacturers"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value(MANUFACTURER1_NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1]").value(MANUFACTURER2_NAME))
                .andDo(document("manufacturer-list-example", responseBody()));
    }

    @Test
    @Sql("/wipe_manufacturer_data.sql")
    public void create_manufacturer() throws Exception {
        this.mockMvc.perform(post("/api/manufacturer")
                .content(manufacturer1JsonString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(document("manufacturer-create-example",
                        requestFields(
                                fieldWithPath("name").description("The name of the manufacturer"),
                                fieldWithPath("nationality").description("The nationality of the manufacturer"))));
        assertTrue(databaseTestClient.findById(MANUFACTURER1_NAME).isPresent());
    }

    @Test
    public void not_create_manufacturer_with_bad_data() throws Exception {
        this.mockMvc.perform(post("/api/manufacturer")
                .content("{\"bad\": \"manufacturer data\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql({"/wipe_manufacturer_data.sql", "/manufacturer_data.sql"})
    public void retrieve_manufacturer_information() throws Exception {
        this.mockMvc.perform(get("/api/manufacturer/{name}", MANUFACTURER1_NAME))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(MANUFACTURER1_NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nationality").value(MANUFACTURER1_NATIONALITY))
                .andDo(document("manufacturer-get-example", responseBody()));
    }

    @Test
    public void not_retrieve_non_existing_manufacturer_information() throws Exception {
        String nonExistingManufacturer = "non existing manufacturer";

        this.mockMvc.perform(get("/api/manufacturer/{name}/", nonExistingManufacturer))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertEquals(nonExistingManufacturer + " not found", result.getResponse().getContentAsString()));
    }

    @Test
    @Sql({"/wipe_manufacturer_data.sql", "/minimal_manufacturer_data.sql"})
    public void update_manufacturer_information() throws Exception {
        this.mockMvc.perform(put("/api/manufacturer/{name}", MANUFACTURER1_NAME)
                .content(manufacturer1JsonString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(document("manufacturer-update-example",
                        requestFields(
                                fieldWithPath("name").description("The name of the manufacturer"),
                                fieldWithPath("nationality").description("The nationality of the manufacturer"))));
        assertThat(databaseTestClient.findById(MANUFACTURER1_NAME))
                .map(ManufacturerEntity::getNationality)
                .hasValue(MANUFACTURER1_NATIONALITY);
    }

    @Test
    public void not_update_manufacturer_information_with_bad_data() throws Exception {
        this.mockMvc.perform(put("/api/manufacturer/{name}", "some manufacturer")
                .content("{\"bad\": \"manufacturer data\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql("/wipe_manufacturer_data.sql")
    public void not_update_non_existing_manufacturer() throws Exception {
        this.mockMvc.perform(put("/api/manufacturer/{name}/", MANUFACTURER1_NAME)
                .content(manufacturer1JsonString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertEquals(MANUFACTURER1_NAME + " not found", result.getResponse().getContentAsString()));
    }

    @Test
    public void not_update_non_matching_manufacturer_data() throws Exception {
        String randomManufacturerName = "random manufacturer name";

        this.mockMvc.perform(put("/api/manufacturer/{name}/", randomManufacturerName)
                .content(manufacturer1JsonString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals(randomManufacturerName + " does not match", result.getResponse().getContentAsString()));
    }

    @Test
    @Sql({"/wipe_manufacturer_data.sql", "/manufacturer_data.sql"})
    public void delete_manufacturer() throws Exception {
        this.mockMvc.perform(delete("/api/manufacturer/{name}", MANUFACTURER1_NAME))
                .andExpect(status().isOk())
                .andDo(document("manufacturer-delete-example"));
        assertFalse(databaseTestClient.findById(MANUFACTURER1_NAME).isPresent());
    }

    @Test
    @Sql("/wipe_manufacturer_data.sql")
    public void not_delete_non_existing_manufacturer() throws Exception {
        this.mockMvc.perform(delete("/api/manufacturer/{name}", MANUFACTURER1_NAME))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertEquals(MANUFACTURER1_NAME + " not found", result.getResponse().getContentAsString()));
    }
}

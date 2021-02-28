package org.txor.beerapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.txor.beerapi.domain.BeerService;
import org.txor.beerapi.domain.ManufacturerService;
import org.txor.beerapi.domain.exceptions.BeerNotFoundException;
import org.txor.beerapi.domain.exceptions.ManufacturerNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
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
import static org.txor.beerapi.TestMother.BEER1_DESCRIPTION;
import static org.txor.beerapi.TestMother.BEER1_GRADUATION;
import static org.txor.beerapi.TestMother.BEER1_NAME;
import static org.txor.beerapi.TestMother.BEER1_TYPE;
import static org.txor.beerapi.TestMother.BEER2_DESCRIPTION;
import static org.txor.beerapi.TestMother.BEER2_GRADUATION;
import static org.txor.beerapi.TestMother.BEER2_NAME;
import static org.txor.beerapi.TestMother.BEER2_TYPE;
import static org.txor.beerapi.TestMother.MANUFACTURER1_NAME;
import static org.txor.beerapi.TestMother.MANUFACTURER1_NATIONALITY;
import static org.txor.beerapi.TestMother.MANUFACTURER2_NAME;
import static org.txor.beerapi.TestMother.allManufacturerNames;
import static org.txor.beerapi.TestMother.beer1;
import static org.txor.beerapi.TestMother.manufacturer1;
import static org.txor.beerapi.TestMother.someBeers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class BeerApiFeatureTests {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private BeerService beerService;

    @MockBean
    private ManufacturerService manufacturerService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation)).build();
    }

    @Test
    public void list_all_beers() throws Exception {
        when(beerService.getAllBeers()).thenReturn(someBeers());

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
        when(beerService.getBeer(anyString())).thenReturn(beer1());

        this.mockMvc.perform(get("/api/beer/{name}", BEER1_NAME))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(BEER1_NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$.graduation").value(BEER1_GRADUATION))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value(BEER1_TYPE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(BEER1_DESCRIPTION))
                .andExpect(MockMvcResultMatchers.jsonPath("$.manufacturer").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.manufacturer.name").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.manufacturer.nationality").isNotEmpty())
                .andDo(document("beer-get-example", responseBody()));
    }

    @Test
    public void obtain_unexisting_beer_information() throws Exception {
        String unexistingBeer = "unexisting beer";
        when(beerService.getBeer(unexistingBeer)).thenThrow(new BeerNotFoundException(unexistingBeer));

        this.mockMvc.perform(get("/api/beer/{name}/", unexistingBeer))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertEquals(unexistingBeer + " not found", result.getResponse().getContentAsString()));
    }

    @Test
    public void list_all_manufacturers() throws Exception {
        when(manufacturerService.getAllManufacturerNames()).thenReturn(allManufacturerNames());

        this.mockMvc.perform(get("/api/manufacturers"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value(MANUFACTURER1_NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1]").value(MANUFACTURER2_NAME))
                .andDo(document("manufacturer-list-example", responseBody()));
    }

    @Test
    public void obtain_manufacturer_information() throws Exception {
        when(manufacturerService.getManufacturer(anyString())).thenReturn(manufacturer1());

        this.mockMvc.perform(get("/api/manufacturer/{name}", MANUFACTURER1_NAME))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(MANUFACTURER1_NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nationality").value(MANUFACTURER1_NATIONALITY))
                .andDo(document("manufacturer-get-example", responseBody()));
    }

    @Test
    public void obtain_unexisting_manufacturer_information() throws Exception {
        String unexistingManufacturer = "unexisting manufacturer";
        when(manufacturerService.getManufacturer(unexistingManufacturer)).thenThrow(new ManufacturerNotFoundException(unexistingManufacturer));

        this.mockMvc.perform(get("/api/manufacturer/{name}/", unexistingManufacturer))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertEquals(unexistingManufacturer + " not found", result.getResponse().getContentAsString()));
    }

    @Test
    public void create_manufacturer() throws Exception {
        this.mockMvc.perform(post("/api/manufacturer")
                .content("{\"name\": \"" + MANUFACTURER1_NAME + "\", \"nationality\":\"" + MANUFACTURER1_NATIONALITY + "\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(document("manufacturer-create-example",
                        requestFields(
                                fieldWithPath("name").description("The name of the manufacturer"),
                                fieldWithPath("nationality").description("The nationality of the manufacturer"))));
    }

    @Test
    public void update_manufacturer_info() throws Exception {
        this.mockMvc.perform(put("/api/manufacturer/{name}", "some manufacturer")
                .content("{\"name\": \"" + MANUFACTURER1_NAME + "\", \"nationality\":\"" + MANUFACTURER1_NATIONALITY + "\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(document("manufacturer-update-example",
                        requestFields(
                                fieldWithPath("name").description("The name of the manufacturer"),
                                fieldWithPath("nationality").description("The nationality of the manufacturer"))));
    }

    @Test
    public void update_unexisting_manufacturer_information() throws Exception {
        String unexistingManufacturer = "unexisting manufacturer";
        doThrow(new ManufacturerNotFoundException(unexistingManufacturer)).when(manufacturerService).updateManufacturer(anyString(), any());

        this.mockMvc.perform(put("/api/manufacturer/{name}/", unexistingManufacturer)
                .content("{\"name\": \"" + MANUFACTURER1_NAME + "\", \"nationality\":\"" + MANUFACTURER1_NATIONALITY + "\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertEquals(unexistingManufacturer + " not found", result.getResponse().getContentAsString()));
    }

    @Test
    public void delete_manufacturer() throws Exception {
        this.mockMvc.perform(delete("/api/manufacturer/{name}", MANUFACTURER1_NAME))
                .andExpect(status().isOk())
                .andDo(document("manufacturer-delete-example"));
    }

    @Test
    public void delete_unexisting_manufacturer_information() throws Exception {
        String unexistingManufacturer = "unexisting manufacturer";
        doThrow(new ManufacturerNotFoundException(unexistingManufacturer)).when(manufacturerService).deleteManufacturer(anyString());

        this.mockMvc.perform(delete("/api/manufacturer/{name}", MANUFACTURER1_NAME))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertEquals(unexistingManufacturer + " not found", result.getResponse().getContentAsString()));
    }
}

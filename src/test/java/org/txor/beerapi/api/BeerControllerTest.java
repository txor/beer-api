package org.txor.beerapi.api;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.txor.beerapi.api.converters.BeerConverter;
import org.txor.beerapi.api.dto.BeerDTO;
import org.txor.beerapi.domain.BeerService;
import org.txor.beerapi.domain.model.Beer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.txor.beerapi.TestMother.BEER1_DESCRIPTION;
import static org.txor.beerapi.TestMother.BEER1_GRADUATION;
import static org.txor.beerapi.TestMother.BEER1_NAME;
import static org.txor.beerapi.TestMother.BEER1_TYPE;
import static org.txor.beerapi.TestMother.BEER2_NAME;
import static org.txor.beerapi.TestMother.MANUFACTURER1_NAME;
import static org.txor.beerapi.TestMother.allBeerNames;
import static org.txor.beerapi.TestMother.beer1;
import static org.txor.beerapi.TestMother.beer1Dto;
import static org.txor.beerapi.TestMother.beer1JsonString;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BeerService beerService;

    @MockBean
    private BeerConverter beerConverter;

    @Captor
    private ArgumentCaptor<BeerDTO> beerDtoCaptor;

    @Captor
    private ArgumentCaptor<Beer> beerCaptor;

    @Captor
    private ArgumentCaptor<String> beerNameCaptor;

    @Test
    public void getAllBeerNames_should_call_the_domain_collaborator() throws Exception {
        when(beerService.getAllBeerNames()).thenReturn(allBeerNames());

        this.mockMvc.perform(get("/api/beers"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value(BEER1_NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1]").value(BEER2_NAME));

        verify(beerService).getAllBeerNames();
    }

    @Test
    public void createBeer_should_convert_the_data_and_call_the_domain_collaborator() throws Exception {
        when(beerConverter.convert(any(BeerDTO.class))).thenReturn(beer1());

        this.mockMvc.perform(post("/api/beer")
                .content(beer1JsonString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(beerConverter).convert(beerDtoCaptor.capture());
        assertThat(beerDtoCaptor.getValue().getName()).isEqualTo(BEER1_NAME);
        assertThat(beerDtoCaptor.getValue().getGraduation()).isEqualTo(BEER1_GRADUATION);
        assertThat(beerDtoCaptor.getValue().getType()).isEqualTo(BEER1_TYPE);
        assertThat(beerDtoCaptor.getValue().getDescription()).isEqualTo(BEER1_DESCRIPTION);
        assertThat(beerDtoCaptor.getValue().getManufacturer()).isEqualTo(MANUFACTURER1_NAME);
        verify(beerService).createBeer(beerCaptor.capture());
        assertThat(beerCaptor.getValue().getName()).isEqualTo(BEER1_NAME);
        assertThat(beerCaptor.getValue().getGraduation()).isEqualTo(BEER1_GRADUATION);
        assertThat(beerCaptor.getValue().getType()).isEqualTo(BEER1_TYPE);
        assertThat(beerCaptor.getValue().getDescription()).isEqualTo(BEER1_DESCRIPTION);
        assertThat(beerCaptor.getValue().getManufacturer()).isEqualTo(MANUFACTURER1_NAME);
    }

    @Test
    public void getBeer_should_rely_on_service_and_converters_to_return_the_matching_beers() throws Exception {
        when(beerService.getBeer(anyString())).thenReturn(beer1());
        when(beerConverter.convert(any(Beer.class))).thenReturn(beer1Dto());

        this.mockMvc.perform(get("/api/beer/{name}", BEER1_NAME))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(BEER1_NAME));
        verify(beerService).getBeer(BEER1_NAME);
        verify(beerConverter).convert(any(Beer.class));
    }

    @Test
    public void updateBeer_should_convert_the_data_and_call_the_domain_collaborator() throws Exception {
        when(beerConverter.convert(any(BeerDTO.class))).thenReturn(beer1());

        this.mockMvc.perform(put("/api/beer/{name}", "some beer")
                .content(beer1JsonString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(beerConverter).convert(beerDtoCaptor.capture());
        assertThat(beerDtoCaptor.getValue().getName()).isEqualTo(BEER1_NAME);
        assertThat(beerDtoCaptor.getValue().getGraduation()).isEqualTo(BEER1_GRADUATION);
        assertThat(beerDtoCaptor.getValue().getType()).isEqualTo(BEER1_TYPE);
        assertThat(beerDtoCaptor.getValue().getDescription()).isEqualTo(BEER1_DESCRIPTION);
        assertThat(beerDtoCaptor.getValue().getManufacturer()).isEqualTo(MANUFACTURER1_NAME);
        verify(beerService).updateBeer(beerNameCaptor.capture(), beerCaptor.capture());
        assertThat(beerNameCaptor.getValue()).isEqualTo("some beer");
        assertThat(beerCaptor.getValue().getName()).isEqualTo(BEER1_NAME);
        assertThat(beerCaptor.getValue().getGraduation()).isEqualTo(BEER1_GRADUATION);
        assertThat(beerCaptor.getValue().getType()).isEqualTo(BEER1_TYPE);
        assertThat(beerCaptor.getValue().getDescription()).isEqualTo(BEER1_DESCRIPTION);
        assertThat(beerCaptor.getValue().getManufacturer()).isEqualTo(MANUFACTURER1_NAME);
    }
}

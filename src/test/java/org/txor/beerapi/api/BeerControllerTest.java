package org.txor.beerapi.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.txor.beerapi.api.converters.BeerConverter;
import org.txor.beerapi.domain.BeerService;
import org.txor.beerapi.domain.model.Beer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.txor.beerapi.TestMother.BEER1_NAME;
import static org.txor.beerapi.TestMother.BEER2_NAME;
import static org.txor.beerapi.TestMother.allBeerNames;
import static org.txor.beerapi.TestMother.beer1;
import static org.txor.beerapi.TestMother.beer1Dto;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BeerService beerService;

    @MockBean
    private BeerConverter beerConverter;

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
    public void getBeer_should_rely_on_service_and_converters_to_return_the_matching_beers() throws Exception {
        when(beerService.getBeer(anyString())).thenReturn(beer1());
        when(beerConverter.convert(any(Beer.class))).thenReturn(beer1Dto());

        this.mockMvc.perform(get("/api/beer/{name}", BEER1_NAME))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(BEER1_NAME));
        verify(beerService).getBeer(BEER1_NAME);
        verify(beerConverter).convert(any());
    }
}

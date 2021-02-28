package org.txor.beerapi.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.txor.beerapi.domain.BeerService;
import org.txor.beerapi.domain.converters.Beer2BeerDtoConverter;
import org.txor.beerapi.domain.model.Beer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.txor.beerapi.TestMother.BEER1_NAME;
import static org.txor.beerapi.TestMother.BEER2_NAME;
import static org.txor.beerapi.TestMother.beer1;
import static org.txor.beerapi.TestMother.beer1Dto;
import static org.txor.beerapi.TestMother.beer2Dto;
import static org.txor.beerapi.TestMother.someBeers;

@WebMvcTest(BeersController.class)
class BeersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BeerService beerService;

    @MockBean
    private Beer2BeerDtoConverter beerConverter;

    @Test
    public void getAllBeers_should_rely_on_service_and_converters_to_return_all_the_beers() throws Exception {
        when(beerService.getAllBeers()).thenReturn(someBeers());
        when(beerConverter.convert(any(Beer.class))).thenReturn(beer1Dto()).thenReturn(beer2Dto());

        this.mockMvc.perform(get("/api/beers"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(BEER1_NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(BEER2_NAME));
        verify(beerService).getAllBeers();
        verify(beerConverter, times(2)).convert(any());
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

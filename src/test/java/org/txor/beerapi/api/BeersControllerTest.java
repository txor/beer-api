package org.txor.beerapi.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.txor.beerapi.domain.BeerService;
import org.txor.beerapi.domain.converters.BeerConverter;
import org.txor.beerapi.domain.model.Beer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.txor.beerapi.TestMother.beer1Name;
import static org.txor.beerapi.TestMother.beer2Name;
import static org.txor.beerapi.TestMother.beerDto1;
import static org.txor.beerapi.TestMother.beerDto2;
import static org.txor.beerapi.TestMother.someBeers;

@WebMvcTest(BeersController.class)
class BeersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BeerService beerService;

    @MockBean
    private BeerConverter beerConverter;

    @Test
    public void getAllBeers_should_rely_on_service_and_converters_to_return_all_the_beers() throws Exception {
        when(beerService.getAllBeers()).thenReturn(someBeers());
        when(beerConverter.convert(any(Beer.class))).thenReturn(beerDto1()).thenReturn(beerDto2());

        this.mockMvc.perform(get("/api/beers"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(beer1Name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(beer2Name()));
        verify(beerService).getAllBeers();
        verify(beerConverter, times(2)).convert(any());
    }
}

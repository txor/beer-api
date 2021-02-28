package org.txor.beerapi.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.txor.beerapi.domain.ManufacturerService;
import org.txor.beerapi.domain.converters.Manufacturer2ManufacturerDtoConverter;
import org.txor.beerapi.domain.model.Manufacturer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.txor.beerapi.TestMother.MANUFACTURER1_NAME;
import static org.txor.beerapi.TestMother.MANUFACTURER1_NATIONALITY;
import static org.txor.beerapi.TestMother.MANUFACTURER2_NAME;
import static org.txor.beerapi.TestMother.allManufacturerNames;
import static org.txor.beerapi.TestMother.manufacturer1;
import static org.txor.beerapi.TestMother.manufacturer1Dto;

@WebMvcTest(ManufacturerController.class)
class ManufacturerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ManufacturerService manufacturerService;

    @MockBean
    private Manufacturer2ManufacturerDtoConverter manufacturerConverter;

    @Test
    public void getAllManufacturerNames_should_rely_on_service_to_return_all_the_manufacturer_names() throws Exception {
        when(manufacturerService.getAllManufacturerNames()).thenReturn(allManufacturerNames());

        this.mockMvc.perform(get("/api/manufacturers"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value(MANUFACTURER1_NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1]").value(MANUFACTURER2_NAME));
        verify(manufacturerService).getAllManufacturerNames();
    }

    @Test
    public void getManufacturer_should_rely_on_service_and_converters_to_return_the_matching_anufacturer() throws Exception {
        when(manufacturerService.getManufacturer(anyString())).thenReturn(manufacturer1());
        when(manufacturerConverter.convert(any(Manufacturer.class))).thenReturn(manufacturer1Dto());

        this.mockMvc.perform(get("/api/manufacturer/{name}", MANUFACTURER1_NAME))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(MANUFACTURER1_NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nationality").value(MANUFACTURER1_NATIONALITY));
        verify(manufacturerService).getManufacturer(MANUFACTURER1_NAME);
        verify(manufacturerConverter).convert(any(Manufacturer.class));
    }
}

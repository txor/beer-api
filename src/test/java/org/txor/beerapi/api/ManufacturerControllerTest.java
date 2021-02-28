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
import org.txor.beerapi.api.converters.ManufacturerConverter;
import org.txor.beerapi.api.dto.ManufacturerDTO;
import org.txor.beerapi.domain.ManufacturerService;
import org.txor.beerapi.domain.model.Manufacturer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
    private ManufacturerConverter manufacturerConverter;

    @Captor
    ArgumentCaptor<Manufacturer> manufacturerCaptor;

    @Captor
    ArgumentCaptor<ManufacturerDTO> manufacturerDtoCaptor;

    @Captor
    ArgumentCaptor<String> manufacturerNameCaptor;

    @Test
    public void getAllManufacturerNames_should_call_the_domain_collaborator() throws Exception {
        when(manufacturerService.getAllManufacturerNames()).thenReturn(allManufacturerNames());

        this.mockMvc.perform(get("/api/manufacturers"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value(MANUFACTURER1_NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1]").value(MANUFACTURER2_NAME));

        verify(manufacturerService).getAllManufacturerNames();
    }

    @Test
    public void createManufacturer_should_convert_the_data_and_call_the_domain_collaborator() throws Exception {
        when(manufacturerConverter.convert(any(ManufacturerDTO.class))).thenReturn(manufacturer1());

        this.mockMvc.perform(post("/api/manufacturer")
                .content("{\"name\": \"" + MANUFACTURER1_NAME + "\", \"nationality\":\"" + MANUFACTURER1_NATIONALITY + "\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(manufacturerConverter).convert(manufacturerDtoCaptor.capture());
        assertThat(manufacturerDtoCaptor.getValue().getName()).isEqualTo(MANUFACTURER1_NAME);
        assertThat(manufacturerDtoCaptor.getValue().getNationality()).isEqualTo(MANUFACTURER1_NATIONALITY);
        verify(manufacturerService).createManufacturer(manufacturerCaptor.capture());
        assertThat(manufacturerCaptor.getValue().getName()).isEqualTo(MANUFACTURER1_NAME);
        assertThat(manufacturerCaptor.getValue().getNationality()).isEqualTo(MANUFACTURER1_NATIONALITY);
    }

    @Test
    public void getManufacturer_should_call_the_domain_collaborator_and_convert_back_the_data() throws Exception {
        when(manufacturerService.getManufacturer(anyString())).thenReturn(manufacturer1());
        when(manufacturerConverter.convert(any(Manufacturer.class))).thenReturn(manufacturer1Dto());

        this.mockMvc.perform(get("/api/manufacturer/{name}", MANUFACTURER1_NAME))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(MANUFACTURER1_NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nationality").value(MANUFACTURER1_NATIONALITY));

        verify(manufacturerService).getManufacturer(manufacturerNameCaptor.capture());
        assertThat(manufacturerNameCaptor.getValue()).isEqualTo(MANUFACTURER1_NAME);
        verify(manufacturerConverter).convert(any(Manufacturer.class));
    }

    @Test
    public void updateManufacturer_should_convert_the_data_and_call_the_domain_collaborator() throws Exception {
        when(manufacturerConverter.convert(any(ManufacturerDTO.class))).thenReturn(manufacturer1());

        this.mockMvc.perform(put("/api/manufacturer/{name}", "some manufacturer")
                .content("{\"name\": \"" + MANUFACTURER1_NAME + "\", \"nationality\":\"" + MANUFACTURER1_NATIONALITY + "\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(manufacturerConverter).convert(manufacturerDtoCaptor.capture());
        assertThat(manufacturerDtoCaptor.getValue().getName()).isEqualTo(MANUFACTURER1_NAME);
        assertThat(manufacturerDtoCaptor.getValue().getNationality()).isEqualTo(MANUFACTURER1_NATIONALITY);
        verify(manufacturerService).updateManufacturer(manufacturerNameCaptor.capture(), manufacturerCaptor.capture());
        assertThat(manufacturerNameCaptor.getValue()).isEqualTo("some manufacturer");
        assertThat(manufacturerCaptor.getValue().getName()).isEqualTo(MANUFACTURER1_NAME);
        assertThat(manufacturerCaptor.getValue().getNationality()).isEqualTo(MANUFACTURER1_NATIONALITY);
    }

    @Test
    public void deleteManufacturer_should_call_the_domain_collaborator() throws Exception {
        this.mockMvc.perform(delete("/api/manufacturer/{name}", MANUFACTURER1_NAME))
                .andExpect(status().isOk());
        verify(manufacturerService).deleteManufacturer(MANUFACTURER1_NAME);
    }
}

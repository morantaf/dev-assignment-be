package com.transferz.controller;

import com.transferz.dao.Airport;
import com.transferz.dto.AirportDTO;
import com.transferz.service.AirportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
public class AirportControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AirportService airportService;

    @InjectMocks
    private AirportController airportController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(airportController).build();
    }


    //TODO: Need to fix error: java.lang.IllegalStateException: No primary or single unique constructor found for interface org.springframework.data.domain.Pageable
    @Disabled
    @Test
    public void testGetAirports() throws Exception {
        String name = "Test Airport";
        String code = "TA";
        String country = "NL";
        Page<Airport> airportPage = new PageImpl<>(Collections.singletonList(new Airport(name, code, country)));
        Pageable pageable = mock(Pageable.class);

        when(airportService.getAirports(name, code, pageable)).thenReturn(airportPage);

        mockMvc.perform(get("/airports")
                        .param("name", name)
                        .param("code", code))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value(name))
                .andExpect(jsonPath("$.content[0].code").value(code));
    }

    @Test
    public void testAddAirport() throws Exception {
        Airport airport = new Airport("New Airport", "NA", "NL");

        when(airportService.addAirport(any(AirportDTO.class))).thenReturn(airport);

        mockMvc.perform(post("/airports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New Airport\", \"code\":\"NA\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Airport"))
                .andExpect(jsonPath("$.code").value("NA"));
    }

}

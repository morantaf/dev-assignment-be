package com.transferz.controller;

import com.transferz.dto.PassengerDTO;
import com.transferz.dto.TimeBucketDTO;
import com.transferz.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class FlightControllerTest {

    @Mock
    private FlightService flightService;

    @InjectMocks
    private FlightController flightController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(flightController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void addPassenger_Success() throws Exception {
        //given
        PassengerDTO passengerDTO = new PassengerDTO("John Doe", "TXT");

        //when
        mockMvc.perform(post("/flights/add-passenger")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passengerDTO)))
                .andExpect(status().isOk());

        //then
        verify(flightService).addPassengerToFlight(any(PassengerDTO.class));
    }

    @Test
    public void addPassenger_FailsWithoutName() throws Exception {
        //given
        PassengerDTO passengerDTO = new PassengerDTO();
        passengerDTO.setFlightCode("TXT");

        //then
        mockMvc.perform(post("/flights/add-passenger")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passengerDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addPassenger_FailsWithoutCode() throws Exception {
        //given
        PassengerDTO passengerDTO = new PassengerDTO();
        passengerDTO.setName("John Doe");

        //then
        mockMvc.perform(post("/flights/add-passenger")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passengerDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getFlightStats_Success() throws Exception {
        LocalDateTime startDateTime = LocalDateTime.now();
        LocalDateTime endDateTime = startDateTime.plusHours(1);
        int bucketLength = 20;
        List<TimeBucketDTO> timeBuckets = List.of(new TimeBucketDTO(startDateTime, 5)); // Mock data

        when(flightService.countFlightsInTimeBuckets(startDateTime, endDateTime, bucketLength)).thenReturn(timeBuckets);

        mockMvc.perform(get("/flights/stats")
                        .param("startDateTime", startDateTime.toString())
                        .param("endDateTime", endDateTime.toString())
                        .param("bucketLength", String.valueOf(bucketLength)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].flightCount").value(5));

        verify(flightService).countFlightsInTimeBuckets(startDateTime, endDateTime, bucketLength);
    }

}

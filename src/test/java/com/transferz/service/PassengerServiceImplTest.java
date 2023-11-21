package com.transferz.service;

import com.transferz.dao.Flight;
import com.transferz.dao.Passenger;
import com.transferz.dto.PassengerDTO;
import com.transferz.repository.FlightRepository;
import com.transferz.serviceImpl.PassengerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class PassengerServiceImplTest {

    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private PassengerServiceImpl passengerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void convertDTO_Success() {
        //given
        String flightCode = "FL123";
        String passengerName = "John Doe";
        PassengerDTO passengerDTO = new PassengerDTO(passengerName, flightCode);

        Flight flight = new Flight();
        flight.setCode(flightCode);

        //when
        when(flightRepository.findByCode(flightCode)).thenReturn(flight);

        Passenger passenger = passengerService.convertDTO(passengerDTO);

        //then
        assertNotNull(passenger);
        assertEquals(passengerName, passenger.getName());
        assertEquals(flight, passenger.getFlight());
        verify(flightRepository).findByCode(flightCode);
    }

}

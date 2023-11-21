package com.transferz.service;

import com.transferz.dao.Flight;
import com.transferz.dao.Passenger;
import com.transferz.dto.PassengerDTO;
import com.transferz.dto.TimeBucketDTO;
import com.transferz.repository.FlightRepository;
import com.transferz.repository.PassengerRepository;
import com.transferz.serviceImpl.FlightServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FlightServiceImplTest {

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private PassengerRepository passengerRepository;

    @Mock
    private PassengerService passengerService;

    @InjectMocks
    private FlightServiceImpl flightService;

    private int passengerLimit = 100;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        Field field = flightService.getClass().getDeclaredField("passengerLimit");
        field.setAccessible(true);
        field.set(flightService, passengerLimit);
    }

    @Test
    void addPassengerToFlight_WithCapacity() {
        //given
        String flightCode = "FTX";
        PassengerDTO passengerDTO = new PassengerDTO("John Doe",flightCode);
        Flight flight = new Flight();
        flight.setCode(flightCode);
        flight.setPassengerCount(50);

        //when
        when(flightRepository.findByCode(flightCode)).thenReturn(flight);
        when(passengerService.convertDTO(any(PassengerDTO.class))).thenReturn(new Passenger());

        flightService.addPassengerToFlight(passengerDTO);

        //then
        verify(passengerRepository).save(any(Passenger.class));
        verify(flightRepository).save(flight);
        assertEquals(51, flight.getPassengerCount());
    }

    @Test
    void addPassengerToFlight_WithoutCapacity() {
        //given
        String flightCode = "FTX";
        PassengerDTO passengerDTO = new PassengerDTO("John Doe",flightCode);
        Flight flight = new Flight();
        flight.setCode(flightCode);
        flight.setPassengerCount(100);
        Pageable pageable = PageRequest.of(0,1);

        Flight flight2 = new Flight(); // available flight
        flight2.setCode(flightCode);
        flight2.setPassengerCount(50);

        Page<Flight> expectedPage = mock(Page.class);
        List<Flight> flightList = Collections.singletonList(flight2);

        //when
        when(expectedPage.stream()).thenReturn(flightList.stream());
        when(flightRepository.findByCode(flightCode)).thenReturn(flight);
        when(passengerService.convertDTO(any(PassengerDTO.class))).thenReturn(new Passenger());
        when(flightRepository.findAvailableFlight(passengerLimit, pageable)).thenReturn(expectedPage);

        flightService.addPassengerToFlight(passengerDTO);

        //then
        verify(passengerRepository).save(any(Passenger.class));
        verify(flightRepository).save(flight2);
        assertEquals(51, flight2.getPassengerCount());
    }

    @Test
    void countFlightsInTimeBuckets_ValidInput() {
        //given
        LocalDateTime startDateTime = LocalDateTime.now();
        LocalDateTime endDateTime = startDateTime.plusHours(1);
        int bucketLength = 10;

        // Mock SQL result
        Object[] sqlRow = { Timestamp.valueOf(startDateTime), BigInteger.valueOf(5) };
        List<Object[]> sqlResult = new ArrayList<>();
        sqlResult.add(sqlRow);

        //when
        when(flightRepository.countFlightsInTimeBuckets(any(LocalDateTime.class), any(LocalDateTime.class), anyInt()))
                .thenReturn(sqlResult);

        List<TimeBucketDTO> result = flightService.countFlightsInTimeBuckets(startDateTime, endDateTime, bucketLength);

        //then
        assertFalse(result.isEmpty());
        assertEquals(5, result.get(0).getFlightCount());
    }

    @Test
    void countFlightsInTimeBuckets_InvalidBucketLength() {
        //given
        LocalDateTime startDateTime = LocalDateTime.now();
        LocalDateTime endDateTime = startDateTime.plusHours(1);
        int bucketLength = 4;

        //then
        assertThrows(IllegalArgumentException.class, () -> {
            flightService.countFlightsInTimeBuckets(startDateTime, endDateTime, bucketLength);
        });
    }

}
package com.transferz.service;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.transferz.dao.Airport;
import com.transferz.dto.AirportDTO;
import com.transferz.exception.DuplicateEntityException;
import com.transferz.repository.AirportRepository;
import com.transferz.serviceImpl.AirportServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class AirportServiceImplTest {

    @Mock
    private AirportRepository airportRepository;

    @InjectMocks
    private AirportServiceImpl airportService;

    @Test
    public void testGetAirportsWithAllParams() {
        //given
        String name = "TestName";
        String code = "TestCode";
        Pageable pageable = mock(Pageable.class);
        Page<Airport> expectedPage = mock(Page.class);

        when(airportRepository.findByNameAndCode(name, code, pageable)).thenReturn(expectedPage);

        //when
        Page<Airport> result = airportService.getAirports(name, code, pageable);

        //then
        assertEquals(expectedPage, result);
        verify(airportRepository).findByNameAndCode(name, code, pageable);
    }

    @Test
    public void testGetAirportsWithNullParams() {
        //given
        Pageable pageable = mock(Pageable.class);
        Page<Airport> expectedPage = mock(Page.class);

        when(airportRepository.findByNameAndCode(null, null, pageable)).thenReturn(expectedPage);

        //when
        Page<Airport> result = airportService.getAirports(null, null, pageable);

        //then
        assertEquals(expectedPage, result);
        verify(airportRepository).findByNameAndCode(null, null, pageable);
    }

    @Test
    void addAirport_WhenNotExists() throws DuplicateEntityException {
        //given
        AirportDTO airportDTO = new AirportDTO();
        airportDTO.setCode("ABC123");
        airportDTO.setName("Test Airport");
        airportDTO.setCountry("Test Country");
        when(airportRepository.existsByCode(airportDTO.getCode())).thenReturn(false);
        when(airportRepository.existsByName(airportDTO.getName())).thenReturn(false);

        Airport airport = new Airport();
        airport.setCode(airportDTO.getCode());
        airport.setName(airportDTO.getName());
        airport.setCountry(airportDTO.getCountry());

        when(airportRepository.save(any(Airport.class))).thenReturn(airport);

        //when
        Airport savedAirport = airportService.addAirport(airportDTO);

        //then
        assertNotNull(savedAirport);
        assertEquals(airportDTO.getCode(), savedAirport.getCode());
        assertEquals(airportDTO.getName(), savedAirport.getName());
        assertEquals(airportDTO.getCountry(), savedAirport.getCountry());

        verify(airportRepository).save(any(Airport.class));
    }

    @Test
    void addAirport_WhenAlreadyExists() {
        //given
        AirportDTO airportDTO = new AirportDTO();
        airportDTO.setCode("ABC123");
        airportDTO.setName("Test Airport");
        airportDTO.setCountry("Test Country");

        //when
        when(airportRepository.existsByCode(airportDTO.getCode())).thenReturn(true);


        //then
        Exception exception = assertThrows(DuplicateEntityException.class, () -> {
            airportService.addAirport(airportDTO);
        });

        String expectedMessage = "Airport code or name already exists";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

        verify(airportRepository, never()).save(any(Airport.class));
    }
}

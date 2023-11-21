package com.transferz.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.transferz.dao.Airport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DataJpaTest
public class AirportRepositoryTest {

    @Autowired
    private AirportRepository airportRepository;

    @Test
    public void testFindByNameAndCodeWithBothParameters() {
        //given
        String name = "Test Airport";
        String code = "TAT";
        Pageable pageable = PageRequest.of(0, 10);

        Airport airport = new Airport(name,code, "NL");
        Airport filteredAirport = new Airport("Other Airport", "OAT", "FR");
        airportRepository.save(airport);
        airportRepository.save(filteredAirport);

        //when
        Page<Airport> result = airportRepository.findByNameAndCode(name, code, pageable);

        //then
        assertThat(result).isNotNull();
        assertEquals(result.getContent().size(),1);
        assertEquals(result.getContent().get(0),airport);
    }

    @Test
    public void testFindByNameAndCodeWithNullName() {
        //given
        String name = "Test Airport";
        String code = "TAT";
        Pageable pageable = PageRequest.of(0, 10);
        Airport airport = new Airport(name,code, "NL");
        airportRepository.save(airport);

        //when
        Page<Airport> result = airportRepository.findByNameAndCode(null, code, pageable);

        //then
        assertThat(result).isNotNull();
        assertEquals(result.getContent().size(),1);
        assertEquals(result.getContent().get(0),airport);
    }

    @Test
    public void testFindByNameAndCodeWithNullCode() {
        //given
        String name = "Test Airport";
        String code = "TAT";
        Pageable pageable = PageRequest.of(0, 10);
        Airport airport = new Airport(name,code, "NL");
        airportRepository.save(airport);

        //when
        Page<Airport> result = airportRepository.findByNameAndCode(name, null, pageable);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isNotEmpty();
    }

    @Test
    public void testFindByNameAndCodeWithNullParameters() {
        //given
        String name = "Test Airport";
        String code = "TAT";
        Pageable pageable = PageRequest.of(0, 10);

        Airport airport = new Airport(name,code, "NL");
        Airport filteredAirport = new Airport("Other Airport", "OAT", "FR");
        airportRepository.save(airport);
        airportRepository.save(filteredAirport);

        //when
        Page<Airport> result = airportRepository.findByNameAndCode(null, null, pageable);

        //then
        assertThat(result).isNotNull();
        assertEquals(result.getContent().size(), 2);
    }

    @Test
    public void testFindByNameAndCodeWithIncompleteName() {
        //given
        String name = "Test";
        Pageable pageable = PageRequest.of(0, 10);

        Airport airport = new Airport("Test Airport","TAT", "NL");
        Airport airport2 = new Airport("Super Test Airport","TAT2", "NL");
        airportRepository.save(airport);
        airportRepository.save(airport2);

        //when
        Page<Airport> result = airportRepository.findByNameAndCode(name, null, pageable);

        //then
        assertThat(result).isNotNull();
        assertEquals(result.getContent().size(), 2);
    }

    @Test
    public void testFindByNameAndCodeWithIncompleteCode() {
        //given
        String code = "TA";
        Pageable pageable = PageRequest.of(0, 10);

        Airport airport = new Airport("Test Airport","TAT", "NL");
        Airport airport2 = new Airport("Super Test Airport","TAT2", "NL");
        airportRepository.save(airport);
        airportRepository.save(airport2);

        //when
        Page<Airport> result = airportRepository.findByNameAndCode(null, code, pageable);

        //then
        assertThat(result).isNotNull();
        assertEquals(result.getContent().size(), 2);
    }

    @Test
    public void testFindByNameAndCodeWithNoMatches() {
        //given
        String name = "NonExistingName";
        String code = "NonExistingCode";
        Pageable pageable = PageRequest.of(0, 10);

        Airport airport = new Airport("Test Airport","TAT", "NL");
        Airport filteredAirport = new Airport("Other Airport", "OAT", "FR");
        airportRepository.save(airport);
        airportRepository.save(filteredAirport);

        //when
        Page<Airport> result = airportRepository.findByNameAndCode(name, code, pageable);

        //then
        assertThat(result).isNotNull();
        assertTrue(result.getContent().isEmpty());
    }
}

package com.transferz.serviceImpl;

import com.transferz.dao.Flight;
import com.transferz.dao.Passenger;
import com.transferz.dto.PassengerDTO;
import com.transferz.dto.TimeBucketDTO;
import com.transferz.repository.FlightRepository;
import com.transferz.repository.PassengerRepository;
import com.transferz.service.FlightService;
import com.transferz.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FlightServiceImpl implements FlightService {

    @Value("${flight.passenger.limit}")
    private int passengerLimit;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private PassengerService passengerService;

    @Transactional
    public void addPassengerToFlight(PassengerDTO passengerDTO) {
        Flight flight = flightRepository.findByCode(passengerDTO.getFlightCode());
        if (flight.getPassengerCount() >= passengerLimit) {
            flight = flightRepository.findAvailableFlight(passengerLimit, PageRequest.of(0, 1)).stream().findFirst().orElse(null);
        }

        assert flight != null;

        Passenger passenger = passengerService.convertDTO(passengerDTO);
        passengerRepository.save(passenger);
        int passengerCount = flight.getPassengerCount() +1;
        flight.setPassengerCount(passengerCount);
        flightRepository.save(flight);
    }

    public List<TimeBucketDTO> countFlightsInTimeBuckets(LocalDateTime startDateTime, LocalDateTime endDateTime, int bucketLength) {

        if (bucketLength < 5) {
            throw new IllegalArgumentException("Bucket length must be at least 5 minutes");
        }

        List<Object[]> sqlResult = flightRepository.countFlightsInTimeBuckets(startDateTime,endDateTime,bucketLength);
        return sqlResult.stream().map(this::convertToTimeBucket).toList();
    }

    private TimeBucketDTO convertToTimeBucket(Object[] sqlRow) {
        Timestamp bucket = (Timestamp) sqlRow[0];
        BigInteger count = (BigInteger) sqlRow[1];
        return new TimeBucketDTO(bucket.toLocalDateTime(),count.longValue());
    }
}

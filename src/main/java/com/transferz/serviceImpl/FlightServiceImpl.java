package com.transferz.serviceImpl;

import com.transferz.dao.Flight;
import com.transferz.dao.Passenger;
import com.transferz.dto.PassengerDTO;
import com.transferz.repository.FlightRepository;
import com.transferz.repository.PassengerRepository;
import com.transferz.service.FlightService;
import com.transferz.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}

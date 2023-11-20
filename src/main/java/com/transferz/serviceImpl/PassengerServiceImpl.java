package com.transferz.serviceImpl;

import com.transferz.dao.Flight;
import com.transferz.dao.Passenger;
import com.transferz.dto.PassengerDTO;
import com.transferz.repository.FlightRepository;
import com.transferz.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PassengerServiceImpl implements PassengerService {


    @Autowired
    FlightRepository flightRepository;

    public Passenger convertDTO(PassengerDTO passengerDTO) {
        Flight flight = flightRepository.findByCode(passengerDTO.getFlightCode());
        return new Passenger(passengerDTO.getName(), flight);
    }
}

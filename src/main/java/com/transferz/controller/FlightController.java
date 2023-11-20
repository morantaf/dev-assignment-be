package com.transferz.controller;

import com.transferz.dto.PassengerDTO;
import com.transferz.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @PostMapping("add-passenger")
    public ResponseEntity<?> addPassenger(@Valid @RequestBody PassengerDTO passengerDTO) {
        flightService.addPassengerToFlight(passengerDTO);
        return ResponseEntity.ok().build();
    }
}

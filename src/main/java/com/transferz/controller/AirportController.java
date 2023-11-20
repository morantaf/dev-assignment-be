package com.transferz.controller;


import com.transferz.dao.Airport;
import com.transferz.dto.AirportDTO;
import com.transferz.exception.DuplicateEntityException;
import com.transferz.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/airports")
public class AirportController {

    @Autowired
    private AirportService airportService;

    @GetMapping
    public ResponseEntity<Page<Airport>> getAirports(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String code,
            Pageable pageable) {

        Page<Airport> airports = airportService.getAirports(name, code, pageable);
        return ResponseEntity.ok(airports);
    }

    @PostMapping
    public ResponseEntity<Airport> addAirport(@Valid @RequestBody AirportDTO airportDTO) throws DuplicateEntityException {
        Airport newAirport = airportService.addAirport(airportDTO);
        return ResponseEntity.ok(newAirport);
    }

}

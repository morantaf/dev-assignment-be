package com.transferz.controller;


import com.transferz.dao.Airport;
import com.transferz.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}

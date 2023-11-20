package com.transferz.controller;

import com.transferz.dto.PassengerDTO;
import com.transferz.dto.TimeBucketDTO;
import com.transferz.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

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

    @GetMapping("/stats")
    public List<TimeBucketDTO> getFlightStats(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime,
            @RequestParam(defaultValue = "20") int bucketLength) {

        return flightService.countFlightsInTimeBuckets(startDateTime, endDateTime, bucketLength);
    }
}

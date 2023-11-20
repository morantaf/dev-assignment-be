package com.transferz.service;

import com.transferz.dto.PassengerDTO;
import com.transferz.dto.TimeBucketDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightService {

    void addPassengerToFlight(PassengerDTO passengerDTO);

    List<TimeBucketDTO> countFlightsInTimeBuckets(LocalDateTime startDateTime, LocalDateTime endDateTime, int bucketLength);

}
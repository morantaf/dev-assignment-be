package com.transferz.repository;

import com.transferz.dao.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FlightRepository extends JpaRepository<Flight,Long> {
    Flight findByCode(String flightCode);

    @Query("SELECT f from Flight f where f.passengerCount < :passengerLimit")
    Page<Flight> findAvailableFlight(int passengerLimit, Pageable pageable);
}

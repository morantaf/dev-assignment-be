package com.transferz.repository;

import com.transferz.dao.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight,Long> {
    Flight findByCode(String flightCode);

    @Query("SELECT f from Flight f where f.passengerCount < :passengerLimit")
    Page<Flight> findAvailableFlight(int passengerLimit, Pageable pageable);

    @Query(value = "SELECT date_trunc('minute', f.departure_time) - " +
            "CAST(EXTRACT(MINUTE FROM f.departure_time) AS integer) % :bucketLength * interval '1 minute' AS bucket, " +
            "COUNT(*) AS flight_count " +
            "FROM Flight f " +
            "WHERE f.departure_time >= :startTime AND f.departure_time < :endTime " +
            "GROUP BY bucket " +
            "ORDER BY bucket", nativeQuery = true)
    List<Object[]> countFlightsInTimeBuckets(LocalDateTime startTime, LocalDateTime endTime, int bucketLength);
}

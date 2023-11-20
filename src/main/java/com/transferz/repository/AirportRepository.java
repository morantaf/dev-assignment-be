package com.transferz.repository;

import com.transferz.dao.Airport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {

    @Query("SELECT a FROM Airport a WHERE " +
            "(:name IS NULL OR a.name LIKE %:name%) AND " +
            "(:code IS NULL OR a.code LIKE %:code%)")
    Page<Airport> findByNameAndCode(String name, String code, Pageable pageable);

    boolean existsByName(String name);

    boolean existsByCode(String name);
}
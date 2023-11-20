package com.transferz.service;

import com.transferz.dao.Airport;
import com.transferz.dto.AirportDTO;
import com.transferz.exception.DuplicateEntityException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AirportService {

    Page<Airport> getAirports(String name, String code, Pageable pageable);

    Airport addAirport(AirportDTO airportDTO) throws DuplicateEntityException;
}

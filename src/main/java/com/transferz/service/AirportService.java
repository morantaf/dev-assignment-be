package com.transferz.service;

import com.transferz.dao.Airport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AirportService {

    public Page<Airport> getAirports(String name, String code, Pageable pageable);
}

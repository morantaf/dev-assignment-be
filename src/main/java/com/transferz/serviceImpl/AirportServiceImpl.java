package com.transferz.serviceImpl;

import com.transferz.dao.Airport;
import com.transferz.repository.AirportRepository;
import com.transferz.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AirportServiceImpl implements AirportService {

    @Autowired
    private AirportRepository airportRepository;

    public Page<Airport> getAirports(String name, String code, Pageable pageable) {
        return airportRepository.findByNameAndCode(name, code, pageable);
    }
}

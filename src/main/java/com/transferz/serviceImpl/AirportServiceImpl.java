package com.transferz.serviceImpl;

import com.transferz.dao.Airport;
import com.transferz.dto.AirportDTO;
import com.transferz.exception.DuplicateEntityException;
import com.transferz.repository.AirportRepository;
import com.transferz.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AirportServiceImpl implements AirportService {

    @Autowired
    private AirportRepository airportRepository;

    public Page<Airport> getAirports(String name, String code, Pageable pageable) {
        return airportRepository.findByNameAndCode(name, code, pageable);
    }

    @Transactional
    public Airport addAirport(AirportDTO airportDTO) throws DuplicateEntityException {
        if (airportRepository.existsByCode(airportDTO.getCode()) ||
                airportRepository.existsByName(airportDTO.getName())) {
            throw new DuplicateEntityException("Airport code or name already exists");
        }

        Airport airport = new Airport();
        airport.setCode(airportDTO.getCode());
        airport.setName(airportDTO.getName());
        airport.setCountry(airportDTO.getCountry());

        return airportRepository.save(airport);
    }
}

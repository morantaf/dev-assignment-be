package com.transferz.config;

import com.transferz.dao.Airport;
import com.transferz.dao.Flight;
import com.transferz.repository.AirportRepository;
import com.transferz.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class BootstrapConfig {

    @Bean
    CommandLineRunner commandLineRunner(AirportRepository airportRepository, FlightRepository flightRepository) {
        return args -> {
            Airport schiphol = new Airport("Schiphol", "SCH", "Netherlands");
            Airport cdg = new Airport("Paris Charles de Gaulle", "CDG", "France");
            Airport mumbai = new Airport("International Mumbai Airport", "MUM", "India");
            airportRepository.saveAll(List.of(schiphol,cdg, mumbai));
            Flight toCDG1 = new Flight("CDG1", schiphol.getCode(),
                    "CDG",
                    LocalDateTime.of(2023,12,12,10,25),
                    LocalDateTime.of(2023,12,12,11,30));
            Flight toCDG2 = new Flight("CDG2", schiphol.getCode(),
                    "CDG",
                    LocalDateTime.of(2023,12,12,10,35),
                    LocalDateTime.of(2023,12,12,12,30));
            Flight toCDG3 = new Flight("CDG3", schiphol.getCode(),
                    "CDG",
                    LocalDateTime.of(2023,12,12,10,35),
                    LocalDateTime.of(2023,12,12,12,30));
            Flight toCDG4 = new Flight("CDG4", schiphol.getCode(),
                    "CDG",
                    LocalDateTime.of(2023,12,12,10,45),
                    LocalDateTime.of(2023,12,12,12,30));
            Flight toMumbai1 = new Flight("MUM1", schiphol.getCode(),
                    "MUM",
                    LocalDateTime.of(2023,12,12,10,45),
                    LocalDateTime.of(2023,12,12,18,30), 150);
            Flight toMumbai2 = new Flight("MUM2", schiphol.getCode(),
                    "MUM",
                    LocalDateTime.of(2023,12,12,10,40),
                    LocalDateTime.of(2023,12,12,18,30), 150);
            Flight toMumbai3 = new Flight("MUM3", schiphol.getCode(),
                    "MUM",
                    LocalDateTime.of(2023,12,12,10,50),
                    LocalDateTime.of(2023,12,12,18,30), 150);
            flightRepository.saveAll(List.of(toCDG1,toCDG2,toMumbai1, toCDG4, toCDG3, toMumbai2, toMumbai3));
        };
    }
}

package com.transferz.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class Flight
{

	@Id
	@SequenceGenerator(name = "flight_sequence", allocationSize = 1, sequenceName = "flight_sequence")
	@GeneratedValue(generator = "flight_sequence", strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(nullable = false, length = 20)
	private String code;

	@Column(nullable = false, length = 20)
	private String originAirportId;

	@Column(nullable = false)
	private Long destinationAirportId;

	@Column(nullable = false)
	private LocalDateTime departureTime;

	@Column(nullable = false)
	private LocalDateTime arrivalTime;
	
}

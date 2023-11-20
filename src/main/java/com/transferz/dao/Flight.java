package com.transferz.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table
@NoArgsConstructor
public class Flight
{

	@Id
	@SequenceGenerator(name = "flight_sequence", allocationSize = 1, sequenceName = "flight_sequence")
	@GeneratedValue(generator = "flight_sequence", strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(nullable = false, length = 20)
	private String code;

	@Column(nullable = false, length = 20)
	private String originAirportCode;

	@Column(nullable = false)
	private String destinationAirportCode;

	@Column(nullable = false)
	private LocalDateTime departureTime;

	@Column(nullable = false)
	private LocalDateTime arrivalTime;

	@OneToMany
	private List<Passenger> passengers;

	@Column
	private Integer passengerCount;

	public Flight(String code, String originAirportCode, String destinationAirportCode, LocalDateTime departureTime, LocalDateTime arrivalTime) {
		this.code = code;
		this.originAirportCode = originAirportCode;
		this.destinationAirportCode = destinationAirportCode;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.passengerCount = 0;
	}

	public Flight(String code, String originAirportCode, String destinationAirportCode, LocalDateTime departureTime, LocalDateTime arrivalTime, int passengerCount) {
		this.code = code;
		this.originAirportCode = originAirportCode;
		this.destinationAirportCode = destinationAirportCode;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.passengerCount = passengerCount;
	}
	
}

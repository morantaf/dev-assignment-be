package com.transferz.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "flight_id"}))
@NoArgsConstructor
public class Passenger
{

	@Id
	@SequenceGenerator(name = "passenger_sequence", allocationSize = 1, sequenceName = "passenger_sequence")
	@GeneratedValue(generator = "passenger_sequence", strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(nullable = false, length = 1024)
	private String name;

	@ManyToOne
	private Flight flight;

	public Passenger(String name, Flight flight) {
		this.name = name;
		this.flight = flight;
	}

}

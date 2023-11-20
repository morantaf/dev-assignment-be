package com.transferz.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class Passenger
{

	@Id
	@SequenceGenerator(name = "passenger_sequence", allocationSize = 1, sequenceName = "passenger_sequence")
	@GeneratedValue(generator = "passenger_sequence", strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(nullable = false, length = 1024)
	private String name;

	@Column(nullable = false, length = 20)
	private String flightCode;

}

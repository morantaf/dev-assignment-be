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
public class Airport
{

	@Id
	@SequenceGenerator(name = "airport_sequence", allocationSize = 1, sequenceName = "airport_sequence")
	@GeneratedValue(generator = "airport_sequence", strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, length = 20)
	private String code;

	@Column(nullable = false, length = 60)
	private String country;

	public Airport(String name, String code, String country) {
		this.name = name;
		this.code = code;
		this.country = country;
	}
	
}

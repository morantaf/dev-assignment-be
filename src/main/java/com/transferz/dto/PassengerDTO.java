package com.transferz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDTO {

    @NotBlank(message = "name is mandatory")
    @Size(max = 20, message = "name must be less than 1024 characters")
    private String name;

    @NotBlank(message = "flight code is mandatory")
    @Size(max = 20, message = "Code must be less than 20 characters")
    private String flightCode;
}

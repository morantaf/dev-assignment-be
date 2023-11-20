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
public class AirportDTO {

    @NotBlank(message = "Code is mandatory")
    @Size(max = 20, message = "Code must be less than 20 characters")
    private String code;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 255, message = "Name must be less than 255 characters")
    private String name;

    @Size(max = 60, message = "Country must be less than 60 characters")
    private String country;

}

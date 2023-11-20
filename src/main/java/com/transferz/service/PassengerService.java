package com.transferz.service;

import com.transferz.dao.Passenger;
import com.transferz.dto.PassengerDTO;

public interface PassengerService {

    Passenger convertDTO(PassengerDTO passengerDTO);
}

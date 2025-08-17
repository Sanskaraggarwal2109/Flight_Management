package com.coforge.training.flightmanagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.coforge.training.flightmanagement.model.Flight;
import com.coforge.training.flightmanagement.repository.FlightRepository;

@Service
public class FlightService {

	private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }
    
    public Flight addFlight(Flight flight) {
        return flightRepository.save(flight);
}
}

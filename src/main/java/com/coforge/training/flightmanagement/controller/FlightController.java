package com.coforge.training.flightmanagement.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coforge.training.flightmanagement.model.Flight;
import com.coforge.training.flightmanagement.service.FlightService;

@RestController
@RequestMapping("/flights")
public class FlightController {

	private final FlightService flightService;

	public FlightController(FlightService flightService) {
		this.flightService = flightService;
	}

	// View all flights
	@GetMapping
	public List<Flight> getFlights() {
		return flightService.getAllFlights();
	}

	// Delete flight by ID
	@DeleteMapping("/{id}")
	public String deleteFlight(@PathVariable Long id) {
		flightService.deleteFlight(id);
		return "Flight with ID " + id + " deleted successfully";
	}
	
	@PostMapping
	public Flight createFlight(@RequestBody Flight flight) {
	    return flightService.addFlight(flight);
	}
}

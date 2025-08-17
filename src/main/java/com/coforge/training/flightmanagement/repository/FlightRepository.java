package com.coforge.training.flightmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coforge.training.flightmanagement.model.Flight;

public interface FlightRepository extends JpaRepository<Flight ,Long>{
	
	

}

package com.coforge.training.flightmanagement.test;
import com.coforge.training.flightmanagement.controller.FlightController;
import com.coforge.training.flightmanagement.model.Flight;
import com.coforge.training.flightmanagement.service.FlightService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FlightController.class)
class FlightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FlightService flightService;

    @Autowired
    private ObjectMapper objectMapper;

    private Flight flight1;
    private Flight flight2;

    @BeforeEach
    void setUp() {
        flight1 = new Flight(1L, "Air India", "Delhi", "Mumbai");  // adjust fields as per your model
        flight2 = new Flight(2L, "IndiGo", "Bangalore", "Chennai");
    }

    @Test
    void testGetFlights() throws Exception {
        Flight flight1 = new Flight();
        flight1.setId(1L);
        flight1.setFlightNo("AI101");
        flight1.setFromLocation("Delhi");
        flight1.setToLocation("Mumbai");
        flight1.setDepart(LocalDateTime.of(2025, 8, 18, 10, 0));
        flight1.setArrival(LocalDateTime.of(2025, 8, 18, 12, 0));
        flight1.setCabin("Economy");
        flight1.setTotalSeats(150);

        Flight flight2 = new Flight();
        flight2.setId(2L);
        flight2.setFlightNo("6E202");
        flight2.setFromLocation("Bangalore");
        flight2.setToLocation("Chennai");
        flight2.setDepart(LocalDateTime.of(2025, 8, 18, 14, 0));
        flight2.setArrival(LocalDateTime.of(2025, 8, 18, 15, 0));
        flight2.setCabin("Economy");
        flight2.setTotalSeats(150);

        Mockito.when(flightService.getAllFlights()).thenReturn(Arrays.asList(flight1, flight2));

        mockMvc.perform(get("/flights"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].flightNo").value("AI101"))
                .andExpect(jsonPath("$[0].fromLocation").value("Delhi"))
                .andExpect(jsonPath("$[1].flightNo").value("6E202"))
                .andExpect(jsonPath("$[1].toLocation").value("Chennai"));
    }
    @Test
    void testCreateFlight() throws Exception {
        Flight flight = new Flight();
        flight.setId(1L);
        flight.setFlightNo("AI101");
        flight.setFromLocation("Delhi");
        flight.setToLocation("Mumbai");
        flight.setDepart(LocalDateTime.of(2025, 8, 18, 10, 0));
        flight.setArrival(LocalDateTime.of(2025, 8, 18, 12, 0));
        flight.setCabin("Economy");
        flight.setTotalSeats(150);

        Mockito.when(flightService.addFlight(any(Flight.class))).thenReturn(flight);

        mockMvc.perform(post("/flights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(flight)))
                .andExpect(status().isOk()) // change to isCreated() if you update controller to return 201
                .andExpect(jsonPath("$.flightNo").value("AI101"))
                .andExpect(jsonPath("$.fromLocation").value("Delhi"))
                .andExpect(jsonPath("$.toLocation").value("Mumbai"))
                .andExpect(jsonPath("$.cabin").value("Economy"))
                .andExpect(jsonPath("$.totalSeats").value(150));
    }
    @Test
    void testDeleteFlight() throws Exception {
        Mockito.doNothing().when(flightService).deleteFlight(eq(1L));

        mockMvc.perform(delete("/flights/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Flight with ID 1 deleted successfully"));
    }
}
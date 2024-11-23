package com.ozyegin.carRental;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.ozyegin.carRental.repository.CarRepository;
import com.ozyegin.carRental.repository.ReservationRepository;
import com.ozyegin.carRental.service.ReservationService;

public class ReservationServiceTest {

    private ReservationService reservationService;
    private CarRepository carRepository;
    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        carRepository = Mockito.mock(CarRepository.class);
        reservationRepository = Mockito.mock(ReservationRepository.class);
        reservationService = new ReservationService(carRepository, reservationRepository);
    }

    @Test
    void testMakeReservation() {
        // Test reservation logic, mock responses and assert results
        // This is not a full implementation, just a structure.
        // Ensure car is available and reservation is successfully created
    }
}


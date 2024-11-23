package com.ozyegin.carRental.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ozyegin.carRental.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    Optional<Reservation> findByReservationNumber(String reservationNumber);

}

package com.ozyegin.carRental.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ozyegin.carRental.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, String> {

    Optional<Reservation> findByReservationNumber(String reservationNumber);
    
    List<Reservation> findByCar_BarcodeAndStatusIn(String carBarcode, List<String> statuses);

}

package com.ozyegin.carRental.repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ozyegin.carRental.model.Reservation;
import com.ozyegin.carRental.model.Service;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservationRepository extends JpaRepository<Reservation, String> {

    Optional<Reservation> findByReservationNumber(String reservationNumber);
    List<Reservation> findByCar_BarcodeAndStatusIn(String carBarcode, List<String> statuses);
    //@Query("SELECT r.services FROM Reservation r WHERE r.reservationNumber = :reservationNumber")
    //List<Service> findServicesByReservationNumber(@Param("reservationNumber") String reservationNumber);
}
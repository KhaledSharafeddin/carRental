package com.ozyegin.carRental.controller;
import java.util.Date;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ozyegin.carRental.model.Reservation;
import com.ozyegin.carRental.service.ReservationService;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    private final ReservationService reservationService;
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }
 @PostMapping
public ResponseEntity<?> makeReservation(
        @RequestParam String carBarcode,
        @RequestParam Integer dayCount,
        @RequestParam Integer memberId,
        @RequestParam String pickupLocationCode,
        @RequestParam String dropoffLocationCode,
        @RequestParam(required = false) List<Integer> equipmentIds,
        @RequestParam(required = false) List<Integer> serviceIds,
        @RequestParam Date reservationDate,
        @RequestParam Date pickUpDate,
        @RequestParam Date dropOffDate
 ) {
    try {
        Reservation reservation = reservationService.makeReservation(
                carBarcode, dayCount, memberId, pickupLocationCode, dropoffLocationCode, equipmentIds, serviceIds, reservationDate, pickUpDate, dropOffDate);
        return ResponseEntity.ok(reservation);
    } catch (IllegalStateException e) {
        return ResponseEntity.status(206).body("Car not available");
    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    }
    @PostMapping("/{reservationNumber}/services")
    public ResponseEntity<String> addAdditionalService(
            @PathVariable String reservationNumber,
            @RequestParam Integer serviceId) {
        try {
            boolean isServiceAdded = reservationService.addServiceToReservation(reservationNumber, serviceId);
            if (isServiceAdded) {
                return ResponseEntity.ok("Service added successfully");
            } else {
                return ResponseEntity.status(400).body("Service already added to the reservation");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while adding the service");
        }
    }
    @PostMapping("/{reservationNumber}/equipment")
    public ResponseEntity<String> addAdditionalEquipment(
            @PathVariable String reservationNumber,
            @RequestParam Integer equipmentId) {

        try {
            boolean isEquipmentAdded = reservationService.addEquipmentToReservation(reservationNumber, equipmentId);
            if (isEquipmentAdded) {
                return ResponseEntity.ok("Equipment added successfully");
            } else {
                return ResponseEntity.status(400).body("Equipment already added to the reservation");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while adding the equipment");
        }
    }
    @PostMapping("/{reservationNumber}/return")
    public ResponseEntity<String> returnCar(
            @PathVariable String reservationNumber,
            @RequestParam int mileage) {
        try {
            String message = reservationService.returnCar(reservationNumber, mileage);
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while returning the car");
        }
    }
    @PostMapping("/{reservationNumber}/cancel")
    public ResponseEntity<String> cancelReservation(@PathVariable String reservationNumber) {
        try {
            String message = reservationService.cancelReservation(reservationNumber);
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while cancelling the reservation");
        }
    }
    @DeleteMapping("/{reservationNumber}")
    public ResponseEntity<String> deleteReservation(@PathVariable String reservationNumber) {
        try {
            String message = reservationService.deleteReservation(reservationNumber);
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while deleting the reservation");
        }
    }
}

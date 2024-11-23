package com.ozyegin.carRental.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
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
            @RequestParam Long memberId,
            @RequestParam String pickupLocationCode,
            @RequestParam String dropoffLocationCode,
            @RequestParam(required = false) List<Long> equipmentIds,
            @RequestParam(required = false) List<Long> serviceIds
    ) {
        try {
            Reservation reservation = reservationService.makeReservation(
                    carBarcode, dayCount, memberId, pickupLocationCode, dropoffLocationCode, equipmentIds, serviceIds);
            return ResponseEntity.ok(reservation); // 200 OK
        } catch (IllegalStateException e) {
            return ResponseEntity.status(206).body("Car not available"); // 206 Not Acceptable
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // 400 Bad Request
        }
    }

    @PostMapping("/{reservationNumber}/services")
    public ResponseEntity<String> addAdditionalService(
            @PathVariable String reservationNumber,
            @RequestParam Long serviceId) {

        try {
            boolean isServiceAdded = reservationService.addServiceToReservation(reservationNumber, serviceId);
            if (isServiceAdded) {
                return ResponseEntity.ok("Service added successfully");
            } else {
                return ResponseEntity.status(400).body("Service already added to the reservation");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage()); // Reservation or service not found
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while adding the service");
        }
    }

    // method to add equipment to a reservation
    @PostMapping("/{reservationNumber}/equipment")
    public ResponseEntity<String> addAdditionalEquipment(
            @PathVariable String reservationNumber,
            @RequestParam Long equipmentId) {

        try {
            boolean isEquipmentAdded = reservationService.addEquipmentToReservation(reservationNumber, equipmentId);
            if (isEquipmentAdded) {
                return ResponseEntity.ok("Equipment added successfully");
            } else {
                return ResponseEntity.status(400).body("Equipment already added to the reservation");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage()); // Reservation or equipment not found
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while adding the equipment");
        }
    }
}

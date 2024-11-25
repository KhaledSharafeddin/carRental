package com.ozyegin.carRental.service;

import com.ozyegin.carRental.model.Car;
import com.ozyegin.carRental.model.Equipment;
import com.ozyegin.carRental.model.Location;
import com.ozyegin.carRental.model.Member;
import com.ozyegin.carRental.model.Reservation;
import com.ozyegin.carRental.repository.CarRepository;
import com.ozyegin.carRental.repository.EquipmentRepository;
import com.ozyegin.carRental.repository.LocationRepository;
import com.ozyegin.carRental.repository.MemberRepository;
import com.ozyegin.carRental.repository.ReservationRepository;
import com.ozyegin.carRental.repository.ServiceRepository;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {

    private final CarRepository carRepository;
    private final MemberRepository memberRepository;
    private final LocationRepository locationRepository;
    private final EquipmentRepository equipmentRepository;
    private final ServiceRepository serviceRepository;
    private final ReservationRepository reservationRepository;

    public ReservationService(
            CarRepository carRepository,
            MemberRepository memberRepository,
            LocationRepository locationRepository,
            EquipmentRepository equipmentRepository,
            ServiceRepository serviceRepository,
            ReservationRepository reservationRepository) {
        this.carRepository = carRepository;
        this.memberRepository = memberRepository;
        this.locationRepository = locationRepository;
        this.equipmentRepository = equipmentRepository;
        this.serviceRepository = serviceRepository;
        this.reservationRepository = reservationRepository;
    }


    public Reservation makeReservation(
            String carBarcode,
            Integer dayCount,
            Long memberId,
            String pickupLocationCode,
            String dropoffLocationCode,
            List<Long> equipmentIds,
            List<Long> serviceIds) {

        // 1. Check if the car is available
        Car car = carRepository.findByBarcodeAndStatus(carBarcode, "AVAILABLE")
                .orElseThrow(() -> new IllegalStateException("Car not available"));

        // 2. Retrieve member, locations, services, and equipment
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));

        Location pickupLocation = locationRepository.findByCode(pickupLocationCode)
                .orElseThrow(() -> new IllegalArgumentException("Invalid pickup location"));

        Location dropoffLocation = locationRepository.findByCode(dropoffLocationCode)
                .orElseThrow(() -> new IllegalArgumentException("Invalid dropoff location"));

        List<Equipment> equipment = equipmentIds != null ? equipmentRepository.findAllById(equipmentIds) : List.of();
        List<Service> services = serviceIds != null ? serviceRepository.findAllById(serviceIds) : List.of();

        // 3. Calculate the total amount
        double equipmentCost = equipment.stream().mapToDouble(Equipment::getPrice).sum();
        double serviceCost = services.stream().mapToDouble(Service::getPrice).sum();
        double totalAmount = dayCount * car.getDailyPrice() + equipmentCost + serviceCost;

        // 4. Create reservation
        Reservation reservation = new Reservation();
        reservation.setReservationNumber(generateReservationNumber()); // Unique 8-digit number
        reservation.setCreationDate(LocalDateTime.now());
        reservation.setPickupDateTime(LocalDateTime.now().plusDays(1));
        reservation.setDropoffDateTime(LocalDateTime.now().plusDays(1 + dayCount));
        reservation.setPickupLocation(pickupLocation);
        reservation.setDropoffLocation(dropoffLocation);
        reservation.setStatus("ACTIVE");
        reservation.setCar(car);
        reservation.setMember(member);
        reservation.setEquipment(equipment);
        reservation.setServices(services);

        // 5. Update the car's status to 'LOANED'
        car.setStatus("LOANED");

        // 6. Save reservation and car
        reservationRepository.save(reservation);
        carRepository.save(car);

        return reservation;
    }

    // method to add additional service to a reservation
    public boolean addServiceToReservation(String reservationNumber, Long serviceId) {
        // Find the reservation by its number
        Reservation reservation = reservationRepository.findByReservationNumber(reservationNumber)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        // Find the service by its ID
        Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new IllegalArgumentException("Service not found"));

        // Check if the service is already added to the reservation
        if (reservation.getServices().contains(service)) {
            return false;  // Service already added
        }

        // Add the service to the reservation
        reservation.getServices().add(service);

        // Save the updated reservation
        reservationRepository.save(reservation);

        return true;  // Successfully added the service
    }

    // Add Equipment To Reservation
    public boolean addEquipmentToReservation(String reservationNumber, Long equipmentId) {
        // 1. Find the reservation by its number
        Reservation reservation = reservationRepository.findByReservationNumber(reservationNumber)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        // 2. Find the equipment by its ID
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new IllegalArgumentException("Equipment not found"));

        // 3. Check if the equipment is already added to the reservation
        if (reservation.getEquipment().contains(equipment)) {
            return false; // Equipment is already added
        }

        // 4. Add the equipment to the reservation
        reservation.getEquipment().add(equipment);

        // 5. Save the updated reservation
        reservationRepository.save(reservation);

        return true; // Successfully added the equipment
    }

    // Return car
    public String returnCar(String reservationNumber, int mileage) {
        // 1. Find the reservation by its number
        Reservation reservation = reservationRepository.findByReservationNumber(reservationNumber)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        // 2. Find the associated car
        Car car = reservation.getCar();

        // 3. Update car mileage and status
        car.setMileage(car.getMileage() + mileage);
        car.setStatus("AVAILABLE");

        // 4. Mark the reservation as completed
        reservation.setStatus("COMPLETED");

        // 5. Save updates
        carRepository.save(car);
        reservationRepository.save(reservation);

        return "Car returned successfully";
    }

    // Cancel Reservation
    public String cancelReservation(String reservationNumber) {
        // 1. Find the reservation by its number
        Reservation reservation = reservationRepository.findByReservationNumber(reservationNumber)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        // 2. Update reservation status to 'CANCELLED'
        reservation.setStatus("CANCELLED");

        // 3. Update car status to 'AVAILABLE' if it was reserved
        Car car = reservation.getCar();
        if ("RESERVED".equals(car.getStatus())) {
            car.setStatus("AVAILABLE");
            carRepository.save(car);
        }

        // 4. Save updated reservation
        reservationRepository.save(reservation);

        return "Reservation cancelled successfully";
    }
    // Delete Reservation
    public String deleteReservation(String reservationNumber) {
        // 1. Find the reservation by its number
        Reservation reservation = reservationRepository.findByReservationNumber(reservationNumber)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        // 2. Check if the reservation's status is 'CANCELLED'
        if (!"CANCELLED".equals(reservation.getStatus())) {
            throw new IllegalStateException("Reservation cannot be deleted unless its status is 'CANCELLED'");
        }

        // 3. Delete the reservation
        reservationRepository.delete(reservation);

        return "Reservation deleted successfully";
    }

    private String generateReservationNumber() {
        return String.format("%08d", (int) (Math.random() * 100000000));
    }
    }
        return null;
    }
}
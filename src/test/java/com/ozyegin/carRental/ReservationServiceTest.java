package com.ozyegin.carRental;

import com.ozyegin.carRental.model.*;
import com.ozyegin.carRental.repository.*;
import com.ozyegin.carRental.service.ReservationService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        reservationRepository.deleteAll();
        carRepository.deleteAll();
        memberRepository.deleteAll();
        locationRepository.deleteAll();
        equipmentRepository.deleteAll();
        serviceRepository.deleteAll();
    }



    // TEST MAKE RESERVATION
    @Test
    void testMakeReservation() {
        // Arrange
        String carBarcode = "123ABC";
        Integer dayCount = 2;
        String pickupLocationCode = "LOC1";
        String dropoffLocationCode = "LOC2";
        List<Integer> equipmentIds = Arrays.asList(1, 2);
        List<Integer> serviceIds = Arrays.asList(1, 2);
        Date reservationDate = new Date();
        Date pickUpDate = new Date();
        Date dropOffDate = new Date();

        Car car = new Car();
        car.setBarcode(carBarcode);
        car.setStatus("AVAILABLE");
        car.setDailyPrice(100.0);
        carRepository.save(car);

        Member member = new Member();
        memberRepository.save(member);

        Location pickupLocation = new Location();
        pickupLocation.setCode(pickupLocationCode);
        locationRepository.save(pickupLocation);

        Location dropoffLocation = new Location();
        dropoffLocation.setCode(dropoffLocationCode);
        locationRepository.save(dropoffLocation);

        Equipment equipment1 = new Equipment();
        equipment1.setPrice(10.0);
        equipmentRepository.save(equipment1);

        Equipment equipment2 = new Equipment();
        equipment2.setPrice(20.0);
        equipmentRepository.save(equipment2);

        Service service1 = new Service();
        service1.setPrice(30.0);
        serviceRepository.save(service1);

        Service service2 = new Service();
        service2.setPrice(40.0);
        serviceRepository.save(service2);

        Reservation reservation = reservationService.makeReservation(carBarcode, dayCount, member.getId(), pickupLocationCode, dropoffLocationCode, equipmentIds, serviceIds, reservationDate, pickUpDate, dropOffDate);

        assertNotNull(reservation);
        assertEquals("LOANED", car.getStatus());
        assertEquals(car.getId(), reservation.getCar().getId());
        assertEquals(member.getId(), reservation.getMember().getId());
        assertEquals(2, reservation.getEquipment().size());
        assertEquals(2, reservation.getServices().size());
    }

    // TEST ADD SERVICE TO RESERVATION
    @Test
    @Transactional
    void testAddServiceToReservation() {
        // Arrange
        String reservationNumber = "12345678";

        Reservation reservation = new Reservation();
        reservation.setReservationNumber(reservationNumber);
        reservationRepository.save(reservation);

        com.ozyegin.carRental.model.Service service = new com.ozyegin.carRental.model.Service();
        service.setPrice(30.0);
        serviceRepository.save(service);

        boolean result = reservationService.addServiceToReservation(reservationNumber, service.getId());

        assertTrue(result);
        reservation = reservationRepository.findByReservationNumber(reservationNumber).orElse(null);
        assertNotNull(reservation);
        assertTrue(reservation.getServices().contains(service));
    }

    // TEST ADD EQUIPMENT TO RESERVATION
    @Test
    @Transactional
    void testAddEquipmentToReservation() {
        String reservationNumber = "12345678";

        Reservation reservation = new Reservation();
        reservation.setReservationNumber(reservationNumber);
        reservationRepository.save(reservation);

        Equipment equipment = new Equipment();
        equipment.setPrice(10.0);
        equipmentRepository.save(equipment);

        boolean result = reservationService.addEquipmentToReservation(reservationNumber, equipment.getId());

        assertTrue(result);
        reservation = reservationRepository.findByReservationNumber(reservationNumber).orElse(null);
        assertNotNull(reservation);
        assertTrue(reservation.getEquipment().contains(equipment));
    }

    // TEST RETURN CAR
    @Test
    void testReturnCar() {
        String reservationNumber = "12345678";
        int mileage = 100;

        Car car = new Car();
        car.setMileage(1000);
        carRepository.save(car);

        Reservation reservation = new Reservation();
        reservation.setReservationNumber(reservationNumber);
        reservation.setCar(car);
        reservationRepository.save(reservation);

        String result = reservationService.returnCar(reservationNumber, mileage);

        assertEquals("Car returned successfully", result);
        car = carRepository.findById(car.getId()).orElse(null); // Fetch the car again to get the updated mileage
        assertNotNull(car);
        assertEquals(1100, car.getMileage());
        assertEquals("AVAILABLE", car.getStatus());
        assertEquals("COMPLETED", reservation.getStatus());
    }

    // TEST CANCEL RESERVATION
    // TEST CANCEL RESERVATION
    @Test
    void testCancelReservation() {
        String reservationNumber = "12345678";

        Car car = new Car();
        car.setStatus("RESERVED");
        carRepository.save(car);

        Reservation reservation = new Reservation();
        reservation.setReservationNumber(reservationNumber);
        reservation.setCar(car);
        reservationRepository.save(reservation);

        String result = reservationService.cancelReservation(reservationNumber);

        assertEquals("Reservation cancelled successfully", result);
        reservation = reservationRepository.findByReservationNumber(reservationNumber).orElse(null); // Fetch the reservation again to get the updated status
        assertNotNull(reservation);
        assertEquals("CANCELLED", reservation.getStatus());
        assertEquals("AVAILABLE", car.getStatus());
    }

    // TEST DELETE RESERVATION
    @Test
    void testDeleteReservation() {
        String reservationNumber = "12345678";

        Reservation reservation = new Reservation();
        reservation.setReservationNumber(reservationNumber);
        reservation.setStatus("CANCELLED");
        reservationRepository.save(reservation);

        String result = reservationService.deleteReservation(reservationNumber);

        assertEquals("Reservation deleted successfully", result);
        assertFalse(reservationRepository.findByReservationNumber(reservationNumber).isPresent());
    }
}
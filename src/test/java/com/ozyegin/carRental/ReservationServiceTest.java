package com.ozyegin.carRental;

import com.ozyegin.carRental.model.*;
import com.ozyegin.carRental.repository.*;
import com.ozyegin.carRental.service.ReservationService;
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
        Integer memberId = 1;
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
        member.setId(memberId);
        memberRepository.save(member);

        Location pickupLocation = new Location();
        pickupLocation.setCode(pickupLocationCode);
        locationRepository.save(pickupLocation);

        Location dropoffLocation = new Location();
        dropoffLocation.setCode(dropoffLocationCode);
        locationRepository.save(dropoffLocation);

        Equipment equipment1 = new Equipment();
        equipment1.setId(1);
        equipment1.setPrice(10.0);
        equipmentRepository.save(equipment1);

        Equipment equipment2 = new Equipment();
        equipment2.setId(2);
        equipment2.setPrice(20.0);
        equipmentRepository.save(equipment2);

        Service service1 = new Service();
        service1.setId(1);
        service1.setPrice(30.0);
        serviceRepository.save(service1);

        Service service2 = new Service();
        service2.setId(2);
        service2.setPrice(40.0);
        serviceRepository.save(service2);

        Reservation reservation = reservationService.makeReservation(carBarcode, dayCount, memberId, pickupLocationCode, dropoffLocationCode, equipmentIds, serviceIds, reservationDate, pickUpDate, dropOffDate);

        assertNotNull(reservation);
        assertEquals("LOANED", car.getStatus());
        assertEquals(1, reservation.getCar().getId());
        assertEquals(1, reservation.getMember().getId());
        assertEquals(2, reservation.getEquipment().size());
        assertEquals(2, reservation.getServices().size());
    }

    // TEST ADD SERVICE TO RESERVATION
    @Test
    void testAddServiceToReservation() {
        // Arrange
        String reservationNumber = "12345678";
        Integer serviceId = 1;

        Reservation reservation = new Reservation();
        reservation.setReservationNumber(reservationNumber);
        reservationRepository.save(reservation);

        Service service = new Service();
        service.setId(serviceId);
        serviceRepository.save(service);

        boolean result = reservationService.addServiceToReservation(reservationNumber, serviceId);

        assertTrue(result);
        assertTrue(reservation.getServices().contains(service));
    }

    // TEST ADD EQIPMENT TO RESERVATION
    @Test
    void testAddEquipmentToReservation() {
        String reservationNumber = "12345678";
        Integer equipmentId = 1;

        Reservation reservation = new Reservation();
        reservation.setReservationNumber(reservationNumber);
        reservationRepository.save(reservation);

        Equipment equipment = new Equipment();
        equipment.setId(equipmentId);
        equipmentRepository.save(equipment);

        boolean result = reservationService.addEquipmentToReservation(reservationNumber, equipmentId);

        assertTrue(result);
        assertTrue(reservation.getEquipment().contains(equipment));
    }

    // TEST RETURN CAR
    @Test
    void testReturnCar() {
        String reservationNumber = "12345678";
        int mileage = 100;

        Reservation reservation = new Reservation();
        reservation.setReservationNumber(reservationNumber);

        Car car = new Car();
        car.setMileage(1000);
        reservation.setCar(car);
        reservationRepository.save(reservation);
        carRepository.save(car);

        String result = reservationService.returnCar(reservationNumber, mileage);

        assertEquals("Car returned successfully", result);
        assertEquals(1100, car.getMileage());
        assertEquals("AVAILABLE", car.getStatus());
        assertEquals("COMPLETED", reservation.getStatus());
    }

    // TEST CANCEL RESERVATION
    @Test
    void testCancelReservation() {
        String reservationNumber = "12345678";

        Reservation reservation = new Reservation();
        reservation.setReservationNumber(reservationNumber);

        Car car = new Car();
        car.setStatus("RESERVED");
        reservation.setCar(car);
        reservationRepository.save(reservation);
        carRepository.save(car);

        String result = reservationService.cancelReservation(reservationNumber);

        assertEquals("Reservation cancelled successfully", result);
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
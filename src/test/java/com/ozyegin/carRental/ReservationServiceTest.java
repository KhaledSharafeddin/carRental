package com.ozyegin.carRental;

import com.ozyegin.carRental.model.*;
import com.ozyegin.carRental.repository.*;
import com.ozyegin.carRental.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;


public class ReservationServiceTest {

    private ReservationService reservationService;
    private CarRepository carRepository;
    private MemberRepository memberRepository;
    private LocationRepository locationRepository;
    private EquipmentRepository equipmentRepository;
    private ServiceRepository serviceRepository;
    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        carRepository = Mockito.mock(CarRepository.class);
        memberRepository = Mockito.mock(MemberRepository.class);
        locationRepository = Mockito.mock(LocationRepository.class);
        equipmentRepository = Mockito.mock(EquipmentRepository.class);
        serviceRepository = Mockito.mock(ServiceRepository.class);
        reservationRepository = Mockito.mock(ReservationRepository.class);
        reservationService = new ReservationService(carRepository, memberRepository, locationRepository, equipmentRepository, serviceRepository, reservationRepository);
    }

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

        Member member = new Member();
        member.setId(memberId);

        Location pickupLocation = new Location();
        pickupLocation.setCode(pickupLocationCode);

        Location dropoffLocation = new Location();
        dropoffLocation.setCode(dropoffLocationCode);

        Equipment equipment1 = new Equipment();
        equipment1.setId(1);
        equipment1.setPrice(10.0);

        Equipment equipment2 = new Equipment();
        equipment2.setId(2);
        equipment2.setPrice(20.0);

        Service service1 = new Service();
        service1.setId(1);
        service1.setPrice(30.0);

        Service service2 = new Service();
        service2.setId(2);
        service2.setPrice(40.0);

        Mockito.when(carRepository.findByBarcodeAndStatus(carBarcode, "AVAILABLE")).thenReturn(Optional.of(car));
        Mockito.when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        Mockito.when(locationRepository.findByCode(pickupLocationCode)).thenReturn(Optional.of(pickupLocation));
        Mockito.when(locationRepository.findByCode(dropoffLocationCode)).thenReturn(Optional.of(dropoffLocation));
        Mockito.when(equipmentRepository.findAllById(equipmentIds)).thenReturn(Arrays.asList(equipment1, equipment2));
        Mockito.when(serviceRepository.findAllById(serviceIds)).thenReturn(Arrays.asList(service1, service2));
        Mockito.when(reservationRepository.save(any(Reservation.class))).thenReturn(new Reservation());

        Reservation reservation = reservationService.makeReservation(carBarcode, dayCount, memberId, pickupLocationCode, dropoffLocationCode, equipmentIds, serviceIds, reservationDate, pickUpDate, dropOffDate);

        assertNotNull(reservation);
        assertEquals("LOANED", car.getStatus());
        Mockito.verify(carRepository).save(car);
        Mockito.verify(reservationRepository).save(any(Reservation.class));
    }

    @Test
    void testAddServiceToReservation() {
        // Arrange
        String reservationNumber = "12345678";
        Integer serviceId = 1;

        Reservation reservation = new Reservation();
        reservation.setReservationNumber(reservationNumber);

        Service service = new Service();
        service.setId(serviceId);

        Mockito.when(reservationRepository.findByReservationNumber(reservationNumber)).thenReturn(Optional.of(reservation));
        Mockito.when(serviceRepository.findById(serviceId)).thenReturn(Optional.of(service));
        Mockito.when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        boolean result = reservationService.addServiceToReservation(reservationNumber, serviceId);

        assertTrue(result);
        assertTrue(reservation.getService().contains(service));
        Mockito.verify(reservationRepository).save(reservation);
    }

    @Test
    void testAddEquipmentToReservation() {
        String reservationNumber = "12345678";
        Integer equipmentId = 1;

        Reservation reservation = new Reservation();
        reservation.setReservationNumber(reservationNumber);

        Equipment equipment = new Equipment();
        equipment.setId(equipmentId);

        Mockito.when(reservationRepository.findByReservationNumber(reservationNumber)).thenReturn(Optional.of(reservation));
        Mockito.when(equipmentRepository.findById(equipmentId)).thenReturn(Optional.of(equipment));
        Mockito.when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        boolean result = reservationService.addEquipmentToReservation(reservationNumber, equipmentId);

        assertTrue(result);
        assertTrue(reservation.getEquipment().contains(equipment));
        Mockito.verify(reservationRepository).save(reservation);
    }

    @Test
    void testReturnCar() {

        String reservationNumber = "12345678";
        int mileage = 100;

        Reservation reservation = new Reservation();
        reservation.setReservationNumber(reservationNumber);

        Car car = new Car();
        car.setMileage(1000);
        reservation.setCar(car);

        Mockito.when(reservationRepository.findByReservationNumber(reservationNumber)).thenReturn(Optional.of(reservation));
        Mockito.when(carRepository.save(any(Car.class))).thenReturn(car);
        Mockito.when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        String result = reservationService.returnCar(reservationNumber, mileage);

        assertEquals("Car returned successfully", result);
        assertEquals(1100, car.getMileage());
        assertEquals("AVAILABLE", car.getStatus());
        assertEquals("COMPLETED", reservation.getStatus());
        Mockito.verify(carRepository).save(car);
        Mockito.verify(reservationRepository).save(reservation);
    }

    @Test
    void testCancelReservation() {
        String reservationNumber = "12345678";

        Reservation reservation = new Reservation();
        reservation.setReservationNumber(reservationNumber);

        Car car = new Car();
        car.setStatus("RESERVED");
        reservation.setCar(car);

        Mockito.when(reservationRepository.findByReservationNumber(reservationNumber)).thenReturn(Optional.of(reservation));
        Mockito.when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);
        Mockito.when(carRepository.save(any(Car.class))).thenReturn(car);

        String result = reservationService.cancelReservation(reservationNumber);

        assertEquals("Reservation cancelled successfully", result);
        assertEquals("CANCELLED", reservation.getStatus());
        assertEquals("AVAILABLE", car.getStatus());
        Mockito.verify(reservationRepository).save(reservation);
        Mockito.verify(carRepository).save(car);
    }

    @Test
    void testDeleteReservation() {
        String reservationNumber = "12345678";

        Reservation reservation = new Reservation();
        reservation.setReservationNumber(reservationNumber);
        reservation.setStatus("CANCELLED");

        Mockito.when(reservationRepository.findByReservationNumber(reservationNumber)).thenReturn(Optional.of(reservation));
        String result = reservationService.deleteReservation(reservationNumber);

        assertEquals("Reservation deleted successfully", result);
        Mockito.verify(reservationRepository).delete(reservation);
    }
}
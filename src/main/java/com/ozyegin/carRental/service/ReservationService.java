package com.ozyegin.carRental.service;

import com.ozyegin.carRental.dto.CarOutputDTO;
import com.ozyegin.carRental.dto.ReservationInputDTO;
import com.ozyegin.carRental.dto.ReservationOutputDTO;
import com.ozyegin.carRental.dto.MemberOutputDTO;
import com.ozyegin.carRental.dto.EquipmentOutputDTO;
import com.ozyegin.carRental.dto.ServiceOutputDTO;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final CarRepository carRepository;
    private final MemberRepository memberRepository;
    private final LocationRepository locationRepository;
    private final EquipmentRepository equipmentRepository;
    private final ServiceRepository serviceRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(CarRepository carRepository, MemberRepository memberRepository,
                              LocationRepository locationRepository, EquipmentRepository equipmentRepository,
                              ServiceRepository serviceRepository, ReservationRepository reservationRepository) {
        this.carRepository = carRepository;
        this.memberRepository = memberRepository;
        this.locationRepository = locationRepository;
        this.equipmentRepository = equipmentRepository;
        this.serviceRepository = serviceRepository;
        this.reservationRepository = reservationRepository;
    }

    public ReservationOutputDTO makeReservation(ReservationInputDTO reservationInputDTO) {
        Car car = carRepository.findByBarcodeAndStatus(reservationInputDTO.getCarBarcode(), "AVAILABLE")
                .orElseThrow(() -> new IllegalArgumentException("Car not available"));

        Member member = memberRepository.findById(reservationInputDTO.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        Location pickupLocation = locationRepository.findByCode(reservationInputDTO.getPickupLocationCode())
                .orElseThrow(() -> new IllegalArgumentException("Pickup location not found"));

        Location dropoffLocation = locationRepository.findByCode(reservationInputDTO.getDropoffLocationCode())
                .orElseThrow(() -> new IllegalArgumentException("Dropoff location not found"));

        List<Equipment> equipment = reservationInputDTO.getEquipmentIds() != null ? equipmentRepository.findAllById(reservationInputDTO.getEquipmentIds()) : List.of();
        List<com.ozyegin.carRental.model.Service> services = reservationInputDTO.getServiceIds() != null ? serviceRepository.findAllById(reservationInputDTO.getServiceIds()) : List.of();

        double equipmentCost = equipment.stream().mapToDouble(Equipment::getPrice).sum();
        double serviceCost = services.stream().mapToDouble(com.ozyegin.carRental.model.Service::getPrice).sum();
        double totalAmount = (reservationInputDTO.getDayCount() * car.getDailyPrice()) + equipmentCost + serviceCost;

        Reservation reservation = new Reservation();
        reservation.setReservationNumber(generateReservationNumber());
        reservation.setCreation(reservationInputDTO.getReservationDate());
        reservation.setPickupDate(reservationInputDTO.getPickUpDate());
        reservation.setDropOffDate(reservationInputDTO.getDropOffDate());
        reservation.setPickupLocation(pickupLocation);
        reservation.setDropOffLocation(dropoffLocation);
        reservation.setStatus("ACTIVE");
        reservation.setCar(car);
        reservation.setMember(member);
        reservation.setEquipment(equipment);
        reservation.setServices(services);

        car.setStatus("LOANED");

        reservationRepository.save(reservation);
        carRepository.save(car);

        return mapToOutputDTO(reservation);
    }

    private ReservationOutputDTO mapToOutputDTO(Reservation reservation) {
        ReservationOutputDTO reservationOutputDTO = new ReservationOutputDTO();
        reservationOutputDTO.setId(reservation.getId());
        reservationOutputDTO.setReservationNumber(reservation.getReservationNumber());
        reservationOutputDTO.setStatus(reservation.getStatus());
        reservationOutputDTO.setCar(mapToCarOutputDTO(reservation.getCar()));
        reservationOutputDTO.setMember(mapToMemberOutputDTO(reservation.getMember()));
        reservationOutputDTO.setEquipment(reservation.getEquipment().stream().map(this::mapToEquipmentOutputDTO).collect(Collectors.toList()));
        reservationOutputDTO.setServices(reservation.getServices().stream().map(this::mapToServiceOutputDTO).collect(Collectors.toList()));
        reservationOutputDTO.setPickUpDate(reservation.getPickupDate());
        reservationOutputDTO.setDropOffDate(reservation.getDropOffDate());
        return reservationOutputDTO;
    }

    private CarOutputDTO mapToCarOutputDTO(Car car) {
        CarOutputDTO carOutputDTO = new CarOutputDTO();
        carOutputDTO.setId(car.getId());
        carOutputDTO.setBarcode(car.getBarcode());
        carOutputDTO.setStatus(car.getStatus());
        carOutputDTO.setDailyPrice(car.getDailyPrice());
        carOutputDTO.setMileage(car.getMileage());
        carOutputDTO.setCarType(car.getCarType());
        carOutputDTO.setTransmissionType(car.getTransmissionType());
        return carOutputDTO;
    }

    private MemberOutputDTO mapToMemberOutputDTO(Member member) {
        MemberOutputDTO memberOutputDTO = new MemberOutputDTO();
        memberOutputDTO.setId(member.getId());
        memberOutputDTO.setName(member.getName());
        memberOutputDTO.setAddress(member.getAddress());
        memberOutputDTO.setEmail(member.getEmail());
        memberOutputDTO.setPhone(member.getPhone());
        memberOutputDTO.setDrivingLicense(member.getDrivingLicense());
        return memberOutputDTO;
    }

    private EquipmentOutputDTO mapToEquipmentOutputDTO(Equipment equipment) {
        EquipmentOutputDTO equipmentOutputDTO = new EquipmentOutputDTO();
        equipmentOutputDTO.setId(equipment.getId());
        equipmentOutputDTO.setName(equipment.getName());
        equipmentOutputDTO.setPrice(equipment.getPrice());
        return equipmentOutputDTO;
    }

    private ServiceOutputDTO mapToServiceOutputDTO(com.ozyegin.carRental.model.Service service) {
        ServiceOutputDTO serviceOutputDTO = new ServiceOutputDTO();
        serviceOutputDTO.setId(service.getId());
        serviceOutputDTO.setName(service.getName());
        serviceOutputDTO.setPrice(service.getPrice());
        return serviceOutputDTO;
    }

    private String generateReservationNumber() {
        return String.format("%08d", (int) (Math.random() * 100000000));
    }
}
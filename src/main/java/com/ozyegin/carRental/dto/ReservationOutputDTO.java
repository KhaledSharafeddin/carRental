package com.ozyegin.carRental.dto;

import java.util.Date;
import java.util.List;

public class ReservationOutputDTO {
    private int id;
    private String reservationNumber;
    private String status;
    private CarOutputDTO car;
    private MemberOutputDTO member;
    private List<EquipmentOutputDTO> equipment;
    private List<ServiceOutputDTO> services;
    private Date pickUpDate;
    private Date dropOffDate;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(String reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CarOutputDTO getCar() {
        return car;
    }

    public void setCar(CarOutputDTO car) {
        this.car = car;
    }

    public MemberOutputDTO getMember() {
        return member;
    }

    public void setMember(MemberOutputDTO member) {
        this.member = member;
    }

    public List<EquipmentOutputDTO> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<EquipmentOutputDTO> equipment) {
        this.equipment = equipment;
    }

    public List<ServiceOutputDTO> getServices() {
        return services;
    }

    public void setServices(List<ServiceOutputDTO> services) {
        this.services = services;
    }

    public Date getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(Date pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public Date getDropOffDate() {
        return dropOffDate;
    }

    public void setDropOffDate(Date dropOffDate) {
        this.dropOffDate = dropOffDate;
    }
}
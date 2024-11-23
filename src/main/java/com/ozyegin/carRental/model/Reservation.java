package com.ozyegin.carRental.model;

import java.util.Date;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class Reservation {
    private String reservationNumber;
    
    private String status;
    
    private Date creation, date, pickupDate, dropOffDate;
    
    @ManyToOne
    @JoinColumn(name = "pickup_location_id")
    private Location pickupLocation;

    @ManyToOne
    @JoinColumn(name = "dropoff_location_id")
    private Location dropOffLocation;
    
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    private Equipment[] equipment;

    private Service[] service;

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

    public Date getCreation() {
        return creation;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(Date pickupDate) {
        this.pickupDate = pickupDate;
    }

    public Date getDropOffDate() {
        return dropOffDate;
    }

    public void setDropOffDate(Date dropOffDate) {
        this.dropOffDate = dropOffDate;
    }

    public Location getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(Location pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public Location getDropOffLocation() {
        return dropOffLocation;
    }

    public void setDropOffLocation(Location dropOffLocation) {
        this.dropOffLocation = dropOffLocation;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Equipment[] getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment[] equipment) {
        this.equipment = equipment;
    }

    public Service[] getService() {
        return service;
    }

    public void setService(Service[] service) {
        this.service = service;
    }
}

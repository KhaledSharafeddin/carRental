package com.ozyegin.carRental.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.sql.ast.tree.from.MappedByTableGroup;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TBL_RESERVATION")//ADD TO OTHERS
public class Reservation {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private String reservationNumber;
    private String status;
    private Date creation, date, pickupDate, dropOffDate;
    @OneToOne
    @JoinColumn(name = "pickup_location_id")
    private Location pickupLocation;
    @OneToOne
    @JoinColumn(name = "dropoff_location_id")
    private Location dropOffLocation;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @OneToOne
    @JoinColumn(name = "car_id")
    private Car car;
    @OneToMany(mappedBy="reservations")
    private List<Equipment> equipment = new ArrayList<>();
    @OneToMany(mappedBy="reservations")
    private List<Service> service = new ArrayList<>();

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
    public List<Equipment> getEquipment() {
        return equipment;
    }
    public void setEquipment(List<Equipment> equipment) {
        this.equipment = equipment;
    }
    public List<Service> getService() {
        return service;
    }
    public void setService(List<Service> service) {
        this.service = service;
    }
}

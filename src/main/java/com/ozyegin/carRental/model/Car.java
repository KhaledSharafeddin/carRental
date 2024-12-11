package com.ozyegin.carRental.model;

import jakarta.persistence.*;

@Entity
@Table
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String barcode, licensePlate, brand, model, transmissionType, carType, status;
    private int passengerCapacity, mileage;
    private  double dailyPrice;

//    @ManyToOne(mappedBy = "Car", cascade = CascadeType.PERSIST)
//    private Reservation reservation;

    public String getBarcode() {
        return barcode;
    }
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    public String getLicensePlate() {
        return licensePlate;
    }
    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public String getTransmissionType() {
        return transmissionType;
    }
    public void setTransmissionType(String transmissionType) {
        this.transmissionType = transmissionType;
    }
    public String getCarType() {
        return carType;
    }
    public void setCarType(String type) {
        this.carType = type;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public int getPassengerCapacity() {
        return passengerCapacity;
    }
    public void setPassengerCapacity(int passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }
    public int getMileage() {
        return mileage;
    }
    public void setMileage(int mileage) {
        this.mileage = mileage;
    }
    public double getDailyPrice() {
        return dailyPrice;
    }
    public void setDailyPrice(double dailyPrice) {
        this.dailyPrice = dailyPrice;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}

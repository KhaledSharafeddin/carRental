package com.ozyegin.carRental.model;

import java.util.Date;

public class Reservation {
    private String reservationNumber;
    private String status;
    private Date creation, date, pickupDate, dropOffDate;
    private Location pickupLocation, dropOffLocation;
    private Member member;
    private Car car;
    private Equipment[] equipment;
    private Service[] service;
}

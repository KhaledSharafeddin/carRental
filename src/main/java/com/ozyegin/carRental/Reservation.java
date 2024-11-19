package com.ozyegin.carRental;

import java.sql.Timestamp;
import java.util.Date;

public class Reservation {
    private String reservationNumber;
    private String status;
    private Date creation, date;
    private Timestamp pickupDate, dropOffDate;
    private Location pickupLocation, dropOffLocation;
    private Member member;


}

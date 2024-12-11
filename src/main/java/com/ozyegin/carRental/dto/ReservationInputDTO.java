package com.ozyegin.carRental.dto;

import java.util.Date;
import java.util.List;

public class ReservationInputDTO {
    private String carBarcode;
    private Integer dayCount;
    private Integer memberId;
    private String pickupLocationCode;
    private String dropoffLocationCode;
    private List<Integer> equipmentIds;
    private List<Integer> serviceIds;
    private Date reservationDate;
    private Date pickUpDate;
    private Date dropOffDate;

    public String getCarBarcode() {
        return carBarcode;
    }

    public void setCarBarcode(String carBarcode) {
        this.carBarcode = carBarcode;
    }

    public Integer getDayCount() {
        return dayCount;
    }

    public void setDayCount(Integer dayCount) {
        this.dayCount = dayCount;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getPickupLocationCode() {
        return pickupLocationCode;
    }

    public void setPickupLocationCode(String pickupLocationCode) {
        this.pickupLocationCode = pickupLocationCode;
    }

    public String getDropoffLocationCode() {
        return dropoffLocationCode;
    }

    public void setDropoffLocationCode(String dropoffLocationCode) {
        this.dropoffLocationCode = dropoffLocationCode;
    }

    public List<Integer> getEquipmentIds() {
        return equipmentIds;
    }

    public void setEquipmentIds(List<Integer> equipmentIds) {
        this.equipmentIds = equipmentIds;
    }

    public List<Integer> getServiceIds() {
        return serviceIds;
    }

    public void setServiceIds(List<Integer> serviceIds) {
        this.serviceIds = serviceIds;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
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

    // Getters and Setters


}
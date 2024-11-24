package com.ozyegin.carRental.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ozyegin.carRental.model.Location;

public interface LocationRepository extends JpaRepository<Location, Integer> {

    // Define custom query method to find Location by code
    Location findByCode(String code);  // This is assuming 'code' is a field in the Location model
}

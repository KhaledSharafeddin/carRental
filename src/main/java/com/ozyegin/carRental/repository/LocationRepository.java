package com.ozyegin.carRental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.ozyegin.carRental.model.Location;

public interface LocationRepository extends JpaRepository<Location, Integer> {

    // Define custom query method to find Location by code
    Optional<Location> findByCode(String code);
}
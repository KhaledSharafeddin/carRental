package com.ozyegin.carRental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.ozyegin.carRental.model.Location;

public interface LocationRepository extends JpaRepository<Location, Integer> {
    Optional<Location> findByCode(Integer code);
}
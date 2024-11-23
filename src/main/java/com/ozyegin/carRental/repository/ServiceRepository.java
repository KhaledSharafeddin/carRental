package com.ozyegin.carRental.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ozyegin.carRental.model.Service;

public interface ServiceRepository extends JpaRepository<Service, Integer> {
    // Method to find a service by its ID
    Optional<Service> findById(Integer id);
}

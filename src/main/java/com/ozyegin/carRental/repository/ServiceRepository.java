package com.ozyegin.carRental.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ozyegin.carRental.model.Service;

public interface ServiceRepository extends JpaRepository<Service, Integer> {
    @Override
    Optional<Service> findById(Integer id);
}

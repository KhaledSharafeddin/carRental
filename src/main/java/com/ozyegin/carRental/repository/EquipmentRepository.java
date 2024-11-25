package com.ozyegin.carRental.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ozyegin.carRental.model.Equipment;

public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {
    @Override
    Optional<Equipment> findById(Integer id);
}

package com.ozyegin.carRental.service;
import com.ozyegin.carRental.model.Equipment;
import com.ozyegin.carRental.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class EquipmentService {
    private final EquipmentRepository equipmentRepository;
    @Autowired
    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }
    public Equipment addEquipment(String name) {
        Equipment equipment = new Equipment();
        equipment.setName(name);
        return equipmentRepository.save(equipment);
    }
}
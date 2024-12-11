package com.ozyegin.carRental.service;

import com.ozyegin.carRental.dto.EquipmentInputDTO;
import com.ozyegin.carRental.dto.EquipmentOutputDTO;
import com.ozyegin.carRental.model.Equipment;
import com.ozyegin.carRental.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipmentService {
    private final EquipmentRepository equipmentRepository;

    @Autowired
    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    public EquipmentOutputDTO addEquipment(EquipmentInputDTO equipmentInputDTO) {
        Equipment equipment = new Equipment();
        equipment.setName(equipmentInputDTO.getName());
        equipment.setPrice(equipmentInputDTO.getPrice());
        equipment = equipmentRepository.save(equipment);
        return mapToOutputDTO(equipment);
    }

    public EquipmentOutputDTO updateEquipment(Integer id, EquipmentInputDTO equipmentInputDTO) {
        Equipment equipment = equipmentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Equipment not found"));
        equipment.setName(equipmentInputDTO.getName());
        equipment.setPrice(equipmentInputDTO.getPrice());
        equipment = equipmentRepository.save(equipment);
        return mapToOutputDTO(equipment);
    }

    public EquipmentOutputDTO getEquipmentById(Integer id) {
        Equipment equipment = equipmentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Equipment not found"));
        return mapToOutputDTO(equipment);
    }

    public List<EquipmentOutputDTO> getAllEquipment() {
        return equipmentRepository.findAll().stream().map(this::mapToOutputDTO).collect(Collectors.toList());
    }

    public void deleteEquipment(Integer id) {
        equipmentRepository.deleteById(id);
    }

    private EquipmentOutputDTO mapToOutputDTO(Equipment equipment) {
        EquipmentOutputDTO equipmentOutputDTO = new EquipmentOutputDTO();
        equipmentOutputDTO.setId(equipment.getId());
        equipmentOutputDTO.setName(equipment.getName());
        equipmentOutputDTO.setPrice(equipment.getPrice());
        return equipmentOutputDTO;
    }
}
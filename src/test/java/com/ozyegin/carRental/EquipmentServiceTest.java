package com.ozyegin.carRental;
import com.ozyegin.carRental.model.Equipment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.ozyegin.carRental.repository.EquipmentRepository;
import com.ozyegin.carRental.service.EquipmentService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

public class EquipmentServiceTest {
    private EquipmentService equipmentService;
    private EquipmentRepository equipmentRepository;
    @BeforeEach
    void setUp() {
        equipmentRepository = Mockito.mock(EquipmentRepository.class);
        equipmentService = new EquipmentService(equipmentRepository);
    }
    @Test
    void testAddEquipment() {
        int equipmentId = 1;
        String equipmentName = "GPS";
        Equipment equipment = new Equipment();
        equipment.setId(equipmentId);
        equipment.setName(equipmentName);

        Mockito.when(equipmentRepository.save(any(Equipment.class))).thenReturn(equipment);
        Equipment result = equipmentService.addEquipment(equipmentName);
        Mockito.verify(equipmentRepository).save(any(Equipment.class));
        assertNotNull(result);
        assertEquals(equipmentId, result.getId());
        assertEquals(equipmentName, result.getName());
    }
}
package com.ozyegin.carRental;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.ozyegin.carRental.model.Equipment;
import com.ozyegin.carRental.repository.EquipmentRepository;
import com.ozyegin.carRental.service.EquipmentService;

@SpringBootTest
@ActiveProfiles("test")
public class EquipmentServiceTest {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private EquipmentService equipmentService;

    @BeforeEach
    void setUp() {
        equipmentRepository.deleteAll();
    }

    // TEST ADD EQUIPMENT
    @Test
    void testAddEquipment() {
        String equipmentName = "GPS";
        Equipment result = equipmentService.addEquipment(equipmentName);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(equipmentName, result.getName());
    }
}
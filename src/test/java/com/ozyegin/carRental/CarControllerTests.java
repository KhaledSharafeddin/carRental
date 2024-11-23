package com.ozyegin.carRental;

import com.ozyegin.carRental.controller.CarController;
import com.ozyegin.carRental.model.Car;
import com.ozyegin.carRental.service.CarService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarController.class)
public class CarControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @Test
    void testSearchAvailableCars() throws Exception {
        Car car = new Car();
        car.setBrand("Mercedes");
        car.setModel("Maybach");
        car.setType("Standard");
        car.setTransmissionType("Automatic");
        car.setStatus("AVAILABLE");

        Mockito.when(carService.searchAvailableCars("Standard", "Automatic"))
                .thenReturn(Collections.singletonList(car));

        mockMvc.perform(get("/api/cars/available")
                .param("carType", "Standard")
                .param("transmissionType", "Automatic"))
                .andExpect(status().isOk());
    }
}

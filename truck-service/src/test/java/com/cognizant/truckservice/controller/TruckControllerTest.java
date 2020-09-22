/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.cognizant.truckservice.controller;

import com.cognizant.truckservice.exception.ResourceNotFoundException;
import com.cognizant.truckservice.model.Truck;
import com.cognizant.truckservice.repository.TruckRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class TruckControllerTest {

    @Autowired
    TruckRepository truckRepository;

    @Autowired
    TruckController truckController;

    @BeforeEach
    void setUp() {
        System.out.println("This is beginning of Test");
    }

    @AfterEach
    void tearDown() {
        System.out.println("This is End of Test");
    }

    @Test
    void getAllTrucks() {

        System.out.println(truckController.getAllTrucks());
    }

    @Test
    void getTruckById( ) {
        long index = -3;
        truckController.getTruckById(index);
        System.out.println(truckController.getTruckById(index));
    }

    @Test
    void createTruck() {
        Truck truck = new Truck();
        truck.setId((long) 4);
        truck.setLicense("AAAAAAA");
        truck.setStatus("Awailable");
        truckController.createTruck(truck);
        System.out.println(truckController.getAllTrucks());
        System.out.println(truck);
    }

    @Test
    void updateTruck() throws ResourceNotFoundException {
        long id = 1;
        Truck truck = new Truck();

        truck.setLicense("ddrrerre");
        truck.setStatus("Awailable");
        truckController.updateTruck(truck, id);
        System.out.println(truckController.getAllTrucks());
        System.out.println(id);
    }

    @Test
    void updateTruckStatus() throws ResourceNotFoundException {
        long id = 1;
        Truck truck = new Truck();

        truck.setStatus("Awailableeeeeeeeeeeeeeeeee");
        truckController.updateTruckStatus(truck, id);
        System.out.println(truckController.getAllTrucks());
        System.out.println(id);
    }

    @Test
    void deleteTruck() {
        Truck truck =  new Truck();
        Long index = Long.valueOf(3);
        truck.setId(index);
        truckController.deleteTruck(truck);
        System.out.println(index);
        System.out.println(truckController.getAllTrucks());
    }

}
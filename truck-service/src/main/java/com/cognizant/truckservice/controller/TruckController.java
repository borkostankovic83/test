package com.cognizant.truckservice.controller;

import com.cognizant.truckservice.exception.ResourceNotFoundException;
import com.cognizant.truckservice.model.Truck;
import com.cognizant.truckservice.repository.TruckRepository;
import com.cognizant.truckservice.sevice.TruckService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class TruckController {

    private TruckService truckService;

    public TruckController(TruckService truckService) {
        this.truckService = truckService;
    }

    @GetMapping("trucks")
    public List<Truck> getAllTrucks(){
        return truckService.findAll();
    }

    @GetMapping("trucks/{id}")
    public Optional<Truck> getTruckById(@PathVariable Long id) {
        return truckService.findById(id);
    }

    @PostMapping("trucks")
    public Truck createTruck(@Valid @RequestBody Truck truck) {
        return truckService.save(truck);
    }

    @PutMapping("trucks/{id}")
    public ResponseEntity<Truck> updateTruck(@RequestBody Truck truck, @PathVariable Long id) throws ResourceNotFoundException  {
        return truckService.updateTruck(truck, id);
    }

    @PatchMapping("trucks/{id}")
    public ResponseEntity<Truck> updateTruckStatus(@RequestBody Truck truck, @PathVariable Long id) throws ResourceNotFoundException  {
        return truckService.updateTruckStatus(truck, id);
    }

    @DeleteMapping("trucks/{id}")
    public void deleteTruck(@PathVariable Truck id) {
        truckService.delete(id);
    }
}

package com.cognizant.truckservice.sevice;

import com.cognizant.truckservice.exception.ResourceNotFoundException;
import com.cognizant.truckservice.model.Truck;
import com.cognizant.truckservice.repository.TruckRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TruckService {

    private TruckRepository truckRepository;

    public TruckService(TruckRepository truckRepository) {
        this.truckRepository = truckRepository;
    }

    public List<Truck> findAll() {
        return truckRepository.findAll();
    }

    public Optional<Truck> findById(Long id) {
        return truckRepository.findById(id);
    }

    public Truck save(Truck truck) {
        return truckRepository.save(truck);
    }

    public ResponseEntity<Truck> updateTruck(Truck truck, Long id) throws ResourceNotFoundException {
        Truck currentTruck = truckRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Truck not found with :: "+ id));
        currentTruck.setLicense(truck.getLicense());
        currentTruck.setStatus(truck.getStatus());

        final Truck updatedTruck = truckRepository.save(currentTruck);
        return ResponseEntity.ok(updatedTruck);
    }

    public ResponseEntity<Truck> updateTruckStatus(Truck truck, Long id) throws ResourceNotFoundException {
        Truck currentTruck = truckRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Truck not found with :: "+ id));
        currentTruck.setStatus(truck.getStatus());

        final Truck updatedTruck = truckRepository.save(currentTruck);
        return ResponseEntity.ok(updatedTruck);
    }

    public void delete(Truck id) {
        truckRepository.delete(id);
    }
}

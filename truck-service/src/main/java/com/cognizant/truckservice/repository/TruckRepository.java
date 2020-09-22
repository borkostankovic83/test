package com.cognizant.truckservice.repository;

import com.cognizant.truckservice.model.Truck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TruckRepository extends JpaRepository<Truck, Long> {
}

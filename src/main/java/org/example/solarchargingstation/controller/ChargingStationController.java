package org.example.solarchargingstation.controller;

import org.example.solarchargingstation.model.ChargingStation;
import org.example.solarchargingstation.service.ChargingStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/charging-stations")
public class ChargingStationController {
    @Autowired
    private ChargingStationService service;

    @GetMapping
    public List<ChargingStation> getAllStations() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ChargingStation getStationById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ChargingStation createStation(@RequestBody ChargingStation station) {
        return service.save(station);
    }

    @PutMapping("/{id}")
    public ChargingStation updateStation(@PathVariable Long id, @RequestBody ChargingStation station) {
        station.setId(id);
        return service.save(station);
    }

    @DeleteMapping("/{id}")
    public void deleteStation(@PathVariable Long id) {
        service.deleteById(id);
    }

    @GetMapping("/{id}/remaining-capacity")
    public double getRemainingCapacity(@PathVariable Long id) {
        return service.calculateRemainingCapacity(id);
    }
}

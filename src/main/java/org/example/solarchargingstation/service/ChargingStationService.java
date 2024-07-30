package org.example.solarchargingstation.service;

import org.example.solarchargingstation.exception.ChargingStationNotFoundException;
import org.example.solarchargingstation.model.ChargingStation;
import org.example.solarchargingstation.repository.ChargingStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChargingStationService {
    @Autowired
    private ChargingStationRepository repository;

    public List<ChargingStation> findAll() {
        return repository.findAll();
    }

    public ChargingStation findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ChargingStationNotFoundException("Charging station not found with id: " + id));
    }

    public ChargingStation save(ChargingStation station) {
        return repository.save(station);
    }

    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ChargingStationNotFoundException("Charging station not found with id: " + id);
        }
        repository.deleteById(id);
    }

    public double calculateRemainingCapacity(Long id) {
        ChargingStation station = findById(id);
        return station.getCapacity() - station.getChargedAmount();
    }
}

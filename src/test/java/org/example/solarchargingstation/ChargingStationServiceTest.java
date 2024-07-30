package org.example.solarchargingstation;

import org.example.solarchargingstation.exception.ChargingStationNotFoundException;
import org.example.solarchargingstation.model.ChargingStation;
import org.example.solarchargingstation.repository.ChargingStationRepository;
import org.example.solarchargingstation.service.ChargingStationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ChargingStationServiceTest {
    @Mock
    private ChargingStationRepository repository;

    @InjectMocks
    private ChargingStationService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByIdThrowsException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ChargingStationNotFoundException.class, () -> service.findById(1L));
    }

    @Test
    void testFindById() {
        ChargingStation station = new ChargingStation();
        station.setId(1L);
        when(repository.findById(anyLong())).thenReturn(Optional.of(station));

        ChargingStation found = service.findById(1L);
        assertEquals(found.getId(), station.getId());
    }

    @Test
    void testSave() {
        ChargingStation station = new ChargingStation();
        when(repository.save(any(ChargingStation.class))).thenReturn(station);

        ChargingStation saved = service.save(station);
        assertNotNull(saved);
        verify(repository, times(1)).save(station);
    }

    @Test
    void testDeleteByIdThrowsException() {
        when(repository.existsById(anyLong())).thenReturn(false);
        assertThrows(ChargingStationNotFoundException.class, () -> service.deleteById(1L));
    }

    @Test
    void testDeleteById() {
        when(repository.existsById(anyLong())).thenReturn(true);
        doNothing().when(repository).deleteById(anyLong());

        service.deleteById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testCalculateRemainingCapacity() {
        ChargingStation station = new ChargingStation();
        station.setId(1L);
        station.setCapacity(1000);
        station.setChargedAmount(250);
        when(repository.findById(anyLong())).thenReturn(Optional.of(station));

        double remainingCapacity = service.calculateRemainingCapacity(1L);
        assertEquals(750, remainingCapacity);
    }
}

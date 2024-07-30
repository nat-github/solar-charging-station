package org.example.solarchargingstation;

import org.example.solarchargingstation.model.ChargingStation;
import org.example.solarchargingstation.service.ChargingStationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChargingStationControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ChargingStationService service;

    @Test
    public void getAllStations() {
        List<ChargingStation> stations = this.restTemplate.getForObject("http://localhost:" + port + "/api/charging-stations", List.class);
        assertThat(stations).isNotEmpty();
    }

    @Test
    public void getStationById() {
        ResponseEntity<ChargingStation> response = this.restTemplate.getForEntity("http://localhost:" + port + "/api/charging-stations/1", ChargingStation.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void createStation() {
        ChargingStation newStation = new ChargingStation();
        newStation.setName("New Station");
        newStation.setCapacity(1500);
        newStation.setChargedAmount(300);

        ResponseEntity<ChargingStation> response = this.restTemplate.postForEntity("http://localhost:" + port + "/api/charging-stations", newStation, ChargingStation.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void updateStation() {
        ChargingStation station = service.findById(1L);
        station.setChargedAmount(400);

        this.restTemplate.put("http://localhost:" + port + "/api/charging-stations/1", station);

        ChargingStation updatedStation = service.findById(1L);
        assertThat(updatedStation.getChargedAmount()).isEqualTo(400);
    }

    @Test
    public void deleteStation() {
        this.restTemplate.delete("http://localhost:" + port + "/api/charging-stations/1");
        assertThat(service.findById(1L)).isNull();
    }

    @Test
    public void calculateRemainingCapacity() {
        Double remainingCapacity = this.restTemplate.getForObject("http://localhost:" + port + "/api/charging-stations/1/remaining-capacity", Double.class);
        assertThat(remainingCapacity).isEqualTo(750);
    }
}

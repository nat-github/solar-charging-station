package org.example.solarchargingstation.exception;

public class ChargingStationNotFoundException extends RuntimeException {
    public ChargingStationNotFoundException(String message) {
        super(message);
    }
}

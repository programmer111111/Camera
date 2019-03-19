package com.develogical.camera;

public class Camera {

    private final Sensor sensor;
    private final MemoryCard card;
    
    private boolean isOn;
    private boolean isCurrentlyWriting;

    public Camera(Sensor sensorIn, MemoryCard cardIn) {
        sensor = sensorIn;
        card = cardIn;
        isOn = false;
    }

    public void pressShutter() {
        if (isOn) {
            byte[] data = sensor.readData();
            isCurrentlyWriting = true;
            card.write(data, () -> {
                isCurrentlyWriting = false;
                if (!isOn) {
                    sensor.powerDown();
                }
            });
        }
    }

    public void powerOn() {
        isOn = true;
        sensor.powerUp();
    }

    public void powerOff() {
        isOn = false;
        if (!isCurrentlyWriting) {
            sensor.powerDown();
        }
    }

}


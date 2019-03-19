package com.develogical.camera;

public class Camera implements WriteCompleteListener {

    Sensor sensor;
    MemoryCard card;
    boolean isOn;
    boolean isCurrentlyWriting;

    public Camera(Sensor sensorIn, MemoryCard cardIn) {
        sensor = sensorIn;
        card = cardIn;
        isOn = false;
    }

    public void pressShutter() {
        if (isOn) {
            byte[] data = sensor.readData();
            isCurrentlyWriting = true;
            card.write(data, this);
        }
    }

    public void powerOn() {
        isOn = true;
        sensor.powerUp();
    }

    public void powerOff() {
        if (!isCurrentlyWriting){
            isOn = false;
            sensor.powerDown();
        }

    }

    @Override
    public void writeComplete() {
        isCurrentlyWriting = false;
    }
}


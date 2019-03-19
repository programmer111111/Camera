package com.develogical.camera;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class CameraTest {
    @Test
    public void switchingTheCameraOnPowersUpTheSensor() {
        MemoryCard card = mock(MemoryCard.class);
        Sensor sensor = mock(Sensor.class);

        Camera camera = new Camera(sensor, card);

        camera.powerOn();

        verify(sensor).powerUp();
        verifyNoMoreInteractions(sensor);
        verifyZeroInteractions(card);
    }

    @Test
    public void switchingTheCameraOffPowersDownTheSensor() {
        MemoryCard card = mock(MemoryCard.class);
        Sensor sensor = mock(Sensor.class);

        Camera camera = new Camera(sensor, card);

        camera.powerOff();

        verify(sensor).powerDown();
    }

    @Test
    public void pressShutterWithPowerOnCopyDataToCard() {
        MemoryCard card = mock(MemoryCard.class);
        Sensor sensor = mock(Sensor.class);

        Camera camera = new Camera(sensor, card);

        //  Turn camera on
        camera.powerOn();
        verify(sensor).powerUp();

        //  Take photo
        camera.pressShutter();
        verify(card).write(sensor.readData(), camera);
    }

    @Test
    public void pressShutterWithPowerOffDoesNothing() {
        MemoryCard card = mock(MemoryCard.class);
        Sensor sensor = mock(Sensor.class);

        Camera camera = new Camera(sensor, card);

        //  Take photo
        camera.pressShutter();

        verifyZeroInteractions(card);
        verifyZeroInteractions(sensor);
    }

    @Test
    public void ifDataIsWrittenDoNotPowerDownSensor(){
        MemoryCard card = mock(MemoryCard.class);
        Sensor sensor = mock(Sensor.class);

        Camera camera = new Camera(sensor, card);

        camera.powerOn();
        camera.pressShutter();

        camera.powerOff();
        verify(sensor, times(0)).powerDown();

        camera.writeComplete();
        camera.powerOff();
        verify(sensor).powerDown();
    }

}

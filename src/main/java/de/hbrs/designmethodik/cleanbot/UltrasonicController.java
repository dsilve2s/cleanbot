package de.hbrs.designmethodik.cleanbot;

import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.objectdetection.FeatureListener;
import lejos.robotics.objectdetection.RangeFeatureDetector;

public class UltrasonicController {

    public static final float MAX_DISTANCE = 30.0f;
    private static final SensorPort SENSOR_PORT = SensorPort.S2;
    private static final int DELAY = 100;

    private final RangeFeatureDetector fd;

    public UltrasonicController() {
        UltrasonicSensor us = new UltrasonicSensor(SENSOR_PORT);
        fd = new RangeFeatureDetector(us, MAX_DISTANCE, DELAY);
    }

    public void run(final FeatureListener featureListener) {
        fd.addListener(featureListener);
    }
}

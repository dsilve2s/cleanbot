package de.hbrs.designmethodik.cleanbot;

import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.objectdetection.FeatureListener;
import lejos.robotics.objectdetection.RangeFeatureDetector;

public class UltrasonicController {

    public static final float MAX_DISTANCE = 60;
    private static final SensorPort SENSOR_PORT = SensorPort.S2;
    public static final int DELAY = 500;
    public static final UltrasonicSensor ULTRASONIC_SENSOR = new UltrasonicSensor(SENSOR_PORT);

    private final RangeFeatureDetector fd;

    public UltrasonicController() {
        fd = new RangeFeatureDetector(ULTRASONIC_SENSOR, MAX_DISTANCE, DELAY);
    }

    public void run(final FeatureListener featureListener) {
        fd.addListener(featureListener);
    }
}

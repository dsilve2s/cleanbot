package de.hbrs.designmethodik.cleanbot;

import lejos.nxt.TouchSensor;
import lejos.robotics.objectdetection.RangeFeatureDetector;

import static de.hbrs.designmethodik.cleanbot.Utils.sleep;

public final class InitialSensorCheck {

    private InitialSensorCheck() {}

    public static void checkSensors() {
        final TouchSensor bumper = BumperController.BUMPER;
        final RangeFeatureDetector fd = new RangeFeatureDetector(
                UltrasonicController.ULTRASONIC_SENSOR,
                UltrasonicController.MAX_DISTANCE,
                UltrasonicController.DELAY
        );

        System.out.println("Checking BS...");
        if (!bumper.isPressed()) {
            System.out.println("failed.");
            sleep(2000);
            System.exit(0);
        }
        System.out.println("done.");

        System.out.println("Checking US...");
        if (fd.scan() != null) {
            System.out.println("failed.");
            sleep(2000);
            System.exit(0);
        }
        System.out.println("done.");

        fd.enableDetection(false);
    }
}

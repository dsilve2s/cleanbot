package de.hbrs.designmethodik.cleanbot;

import lejos.nxt.TouchSensor;
import lejos.robotics.objectdetection.RangeFeatureDetector;

public class ContinuousSensorCheck extends Thread {

    private static final long US_TIMEOUT = 10000;
    private static final long BS_TIMEOUT = 10000;

    private long bdFreeSince;
    private long udFreeSince;

    @Override
    public void run() {
        bdFreeSince = System.currentTimeMillis();
        udFreeSince = System.currentTimeMillis();
        final TouchSensor bumper = BumperController.BUMPER;
        final RangeFeatureDetector fd = new RangeFeatureDetector(
                UltrasonicController.ULTRASONIC_SENSOR,
                UltrasonicController.MAX_DISTANCE,
                UltrasonicController.DELAY
        );
        while (!isInterrupted()) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                break;
            }

            final long bdBlockedSince = System.currentTimeMillis() - bdFreeSince;
            final long udBlockedSince = System.currentTimeMillis() - udFreeSince;

            if (bdBlockedSince > BS_TIMEOUT) {
                System.out.println("BD blocked,\nexiting.");
            }
            if (udBlockedSince > US_TIMEOUT) {
                System.out.println("UD blocked,\nexiting.");
            }
            if (bdBlockedSince > BS_TIMEOUT ||
                udBlockedSince > US_TIMEOUT) {
                Utils.sleep(2000);
                System.exit(0);
            }

            if (bumper.isPressed()) {
                bdFreeSince = System.currentTimeMillis();
            }
            if (fd.scan() == null) {
                udFreeSince = System.currentTimeMillis();
            }
        }
    }
}

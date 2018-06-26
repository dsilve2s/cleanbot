package de.hbrs.designmethodik.cleanbot;

import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;

public class BumperController extends Thread {

    private static final TouchSensor BUMPER = new TouchSensor(SensorPort.S3);

    private BumperCollisionListener listener;

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                break;
            }
            if (!BUMPER.isPressed() && listener != null) {
                listener.handleBumperCollision();
            }
        }
        System.out.println("BC exiting...");
    }

    public void setListener(final BumperCollisionListener listener) {
        this.listener = listener;
    }
}

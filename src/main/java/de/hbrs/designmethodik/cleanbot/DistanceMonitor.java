package de.hbrs.designmethodik.cleanbot;

import lejos.nxt.LCD;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Move;

public class DistanceMonitor extends Thread {

    private final DifferentialPilot differentialPilot;
    private float totalDistance;
    private float tmpDistance;

    public DistanceMonitor(final DifferentialPilot differentialPilot) {
        this.differentialPilot = differentialPilot;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            Utils.sleep(500);
            Move movement = differentialPilot.getMovement();
            final float measuredDistance = Math.abs(movement.getDistanceTraveled());
            if (measuredDistance < tmpDistance) {
                totalDistance += tmpDistance;
            }
            tmpDistance = measuredDistance;
            LCD.clear();
            LCD.drawString((totalDistance + tmpDistance) + " cm", 3, 0);
        }
    }
}

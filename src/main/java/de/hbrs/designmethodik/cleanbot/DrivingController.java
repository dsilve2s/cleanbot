package de.hbrs.designmethodik.cleanbot;

import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.objectdetection.Feature;
import lejos.robotics.objectdetection.FeatureDetector;
import lejos.robotics.objectdetection.FeatureListener;

import static de.hbrs.designmethodik.cleanbot.Utils.requireNonNull;
import static de.hbrs.designmethodik.cleanbot.Utils.sleep;

public class DrivingController implements FeatureListener, BumperCollisionListener {

    public enum TurnDirection {
        LEFT,
        RIGHT
    }

    public enum DriveDirection {
        FORWARD,
        BACKWARD
    }

    private final DifferentialPilot differentialPilot;
    private final DistanceMonitor distanceMonitor;

    public DrivingController(DifferentialPilot differentialPilot) {
        this.differentialPilot = differentialPilot;
        distanceMonitor = new DistanceMonitor(differentialPilot);
    }

    public void start() {
        distanceMonitor.start();
    }

    public void driveStraight(final DriveDirection driveDirection) {

    }

    public void driveStraight(final DriveDirection driveDirection, float distance) {

        if (driveDirection != DriveDirection.FORWARD) distance *= -1;
        differentialPilot.travel(distance);
    }

    public void driveCurve(final TurnDirection turnDirection, final DriveDirection driveDirection, final float angle) {

        requireNonNull(turnDirection);
        requireNonNull(driveDirection);

        final float radius = 30;
        float turnRate = 0;
        float correctedAngle = 0;

        switch (turnDirection) {
            case LEFT:
                turnRate = radius;
                switch (driveDirection) {
                    case FORWARD:
                        correctedAngle = angle;
                        break;
                    case BACKWARD:
                        correctedAngle = -angle;
                        break;
                }
                break;
            case RIGHT:
                turnRate = -radius;
                switch (driveDirection) {
                    case FORWARD:
                        correctedAngle = -angle;
                        break;
                    case BACKWARD:
                        correctedAngle = angle;
                        break;
                }
                break;
        }

        differentialPilot.arc(turnRate, correctedAngle);
    }

    public void handleBumperCollision() {
        System.out.println("Kollision hinten!");
        differentialPilot.stop();
        Sound.playTone(1200, 500);
        driveStraight(DriveDirection.FORWARD, 5);
    }

    public void featureDetected(Feature feature, FeatureDetector detector) {
        int range = (int)feature.getRangeReading().getRange();
        System.out.println("Hindernis in " + range + "cm!");
        differentialPilot.stop();
        Sound.playTone(1200, 500);
        float driveBackwardDistance = (UltrasonicController.MAX_DISTANCE - range) + 10;
        System.out.println("Fahre " + driveBackwardDistance + "cm zurück.");
        driveStraight(DriveDirection.BACKWARD, driveBackwardDistance);
    }

    public void stop() {
        differentialPilot.stop();
        distanceMonitor.interrupt();
    }
}

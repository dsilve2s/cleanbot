package de.hbrs.designmethodik.cleanbot;

import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.objectdetection.Feature;
import lejos.robotics.objectdetection.FeatureDetector;
import lejos.robotics.objectdetection.FeatureListener;

import static de.hbrs.designmethodik.cleanbot.Utils.randomEnum;
import static de.hbrs.designmethodik.cleanbot.Utils.requireNonNull;

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

    public DrivingController(DifferentialPilot differentialPilot) {
        this.differentialPilot = differentialPilot;
        differentialPilot.setTravelSpeed(differentialPilot.getMaxTravelSpeed());
    }

    public void driveStraight(final DriveDirection driveDirection) {
        driveStraight(
                driveDirection,
                driveDirection == DriveDirection.FORWARD ? Float.POSITIVE_INFINITY : Float.NEGATIVE_INFINITY
        );
    }

    public void driveStraight(final DriveDirection driveDirection, float distance) {

        differentialPilot.setTravelSpeed(differentialPilot.getMaxTravelSpeed());

        if (driveDirection != DriveDirection.FORWARD) distance *= -1;
        differentialPilot.travel(distance);

        waitToComplete();
    }

    public void driveCurve(final TurnDirection turnDirection, final DriveDirection driveDirection, final float angle, final float radius) {

        requireNonNull(turnDirection);
        requireNonNull(driveDirection);

        differentialPilot.setTravelSpeed(differentialPilot.getMaxTravelSpeed() * 0.8);

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

        waitToComplete();
    }

    private void waitToComplete() {
        while (differentialPilot.isMoving()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public void handleBumperCollision() {
        driveStraight(DriveDirection.FORWARD, 5);
    }

    public void featureDetected(Feature feature, FeatureDetector detector) {
        int range = (int)feature.getRangeReading().getRange();
        float driveBackwardDistance = (UltrasonicController.MAX_DISTANCE - range) + 10;
        Sound.playTone(1200, 500);
        driveStraight(DriveDirection.BACKWARD, driveBackwardDistance);
        driveCurve(
                randomEnum(TurnDirection.values()),
                DriveDirection.FORWARD,
                90,
                10
        );
    }

    public void stop() {
        differentialPilot.stop();
    }
}

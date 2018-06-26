package de.hbrs.designmethodik.cleanbot;

import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;

import static de.hbrs.designmethodik.cleanbot.DrivingController.DriveDirection.BACKWARD;
import static de.hbrs.designmethodik.cleanbot.DrivingController.DriveDirection.FORWARD;

public class Application {

    public static void main(String[] args) {
        Application application = new Application();
        application.run();
    }

    private final DifferentialPilot differentialPilot = new DifferentialPilot(
            5.5,
            5.5,
            11,
            Motor.A,
            Motor.B,
            true
    );

    private final DrivingController drivingController = new DrivingController(differentialPilot);

    public Application() {

    }

    private void run() {

        UltrasonicController ultrasonicController = new UltrasonicController();
        BumperController tBumperController = new BumperController();
        tBumperController.setListener(drivingController);
        tBumperController.start();
        ultrasonicController.run(drivingController);

        drivingController.driveStraight(FORWARD, 2000);
        drivingController.driveStraight(BACKWARD, 2000);
        Button.waitForAnyPress();
        System.out.println("terminated.");
        tBumperController.interrupt();
        drivingController.stop();
    }
}

package de.hbrs.designmethodik.cleanbot;

import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;

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

    private final UltrasonicController ultrasonicController;
    private final BumperController bumperController;
    private final DrivingAI drivingAI;


    public Application() {
        ultrasonicController = new UltrasonicController();
        bumperController = new BumperController();
        bumperController.setListener(drivingController);
        drivingAI = new DrivingAI(drivingController);
    }

    private void run() {
        ultrasonicController.run(drivingController);
        bumperController.start();
        drivingAI.start();

        Button.waitForAnyPress();
        stop();
    }

    private void stop() {
        System.out.println("terminated.");
        bumperController.interrupt();
        drivingAI.interrupt();
        differentialPilot.stop();
    }
}

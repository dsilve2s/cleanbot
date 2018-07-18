package de.hbrs.designmethodik.cleanbot;

import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;

import static de.hbrs.designmethodik.cleanbot.Utils.sleep;

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
    private final ContinuousSensorCheck continuousSensorCheck;


    public Application() {
        ultrasonicController = new UltrasonicController();
        bumperController = new BumperController();
        bumperController.setListener(drivingController);
        drivingAI = new DrivingAI(drivingController);
        continuousSensorCheck = new ContinuousSensorCheck();
    }

    private void run() {
        InitialSensorCheck.checkSensors();

        ultrasonicController.run(drivingController);
        bumperController.start();
        drivingAI.start();
        continuousSensorCheck.start();

        Button.waitForAnyPress();
        stop();

        sleep(2000);
    }

    public void stop() {
        System.out.println("terminated.");
        bumperController.interrupt();
        drivingAI.interrupt();
        continuousSensorCheck.interrupt();
        differentialPilot.stop();
        drivingController.stop();
    }
}

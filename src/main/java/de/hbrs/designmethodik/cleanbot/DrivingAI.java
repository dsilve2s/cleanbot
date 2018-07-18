package de.hbrs.designmethodik.cleanbot;

import java.util.Random;

import static de.hbrs.designmethodik.cleanbot.Utils.randomEnum;

public class DrivingAI extends Thread {

    private final DrivingController drivingController;
    private boolean running;

    private static final Random RANDOM = new Random();

    public DrivingAI(DrivingController drivingController) {
        this.drivingController = drivingController;
    }

    @Override
    public void run() {
        running = true;
        while (!isInterrupted() && running) {
            switch ((int)(RANDOM.nextFloat() * 2)) {
                case 0:
                    System.out.println("FORWARD");
                    driveForwardRandom();
                    break;
                case 1:
                    System.out.println("CURVE");
                    driveCurve();
                    break;
            }
        }
        System.out.println("DAI exiting...");
    }

    private void driveForwardRandom() {
        drivingController.driveStraight(DrivingController.DriveDirection.FORWARD, RANDOM.nextFloat() * 50 + 25);
    }

    private void driveCurve() {
        drivingController.driveCurve(
                randomEnum(DrivingController.TurnDirection.values()),
                DrivingController.DriveDirection.FORWARD,
                RANDOM.nextFloat() * 45 + 45,
                RANDOM.nextFloat() * 25 + 25
        );
    }

    @Override
    public void interrupt() {
        super.interrupt();
        running = false;
    }
}

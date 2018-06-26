package de.hbrs.designmethodik.cleanbot;

public class DrivingAI extends Thread {

    private final DrivingController drivingController;
    private boolean running;

    public DrivingAI(DrivingController drivingController) {
        this.drivingController = drivingController;
    }

    @Override
    public void run() {
        running = true;
        while (!isInterrupted() && running) {
            drivingController.driveStraight(DrivingController.DriveDirection.FORWARD);
        }
        System.out.println("DAI exiting...");
    }

    @Override
    public void interrupt() {
        super.interrupt();
        running = false;
    }
}

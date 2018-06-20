import lejos.nxt.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Program 3");
        Motor.A.rotate(360*4);
        System.out.println(Motor.A.getTachoCount());
        Motor.A.rotateTo(0);
        System.out.println(Motor.A.getTachoCount());
        Button.waitForAnyPress();
    }
}

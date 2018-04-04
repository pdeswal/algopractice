import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;


public class OneMain {
    public static void main(String[] args) throws AWTException, InterruptedException {
	Robot robot = new Robot();
	while(true){
	    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
	    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	    Thread.sleep(30000);
	}
    }
}

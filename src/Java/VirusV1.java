package Java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Scanner;

public class VirusV1 {
    Scanner input;
    private final Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
    private final int height =(int)screenSize.getHeight();
    private final int width =(int)screenSize.getWidth();
    private final Random Rands=new Random();
    Robot robot;

    public void BlockAll() throws AWTException {
        robot=new Robot();
        robot.keyPress(KeyEvent.VK_0);
        robot.mouseMove(Rands.nextInt(width),Rands.nextInt(height));
    }
    public void popup(){
        JFrame window=new JFrame();
        JLabel label=new JLabel("HHHHHHHH This is virus",JLabel.CENTER);
        window.setBackground(Color.black);
        label.setBackground(Color.green);
        window.add(label);
        window.setSize(200,100);
        window.setLocation(Rands.nextInt(width),Rands.nextInt(height));
        window.setVisible(true);

    }
    public void stopPopup() throws AWTException {
        //nothing in this time
            System.exit(0);

    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by John on 23/09/2016.
 */
public class SwingDemo {
    public static void main(String[] args) {

        // "frame" will be the window containing entities
        JFrame frame = new JFrame();

        // When the "X" button is pressed on the window, actually end the program
        // By default, when you close, it sits there doing nothing while still running. This fixes that
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the size using "new Dimension"
        frame.setSize(new Dimension(500, 500));

        // "button" will be a button entity
        JButton button = new JButton("Okay");

        // Set the font and size of the button
        button.setFont(new Font("Arial", Font.BOLD, 100));

        // Put the button inside of the frame
        frame.add(button);

        // Action occurs when button is pressed
        button.addActionListener(new ActionListener() {
            @Override
            // This method is executed when the button is pressed
            public void actionPerformed(ActionEvent e) {
                System.out.println("Hit that button again, I'll hit you");

                // Change the button text
                button.setText("Ouch...");

                // Change the window title
                frame.setTitle("ALERT: Window is being pressed");
            }
        });

        // .pack() automatically sizes the frame to it's contents
        //frame.pack();
        frame.setVisible(true);

        // Timer just does things when the time delay (in this case 100 milliseconds) passes
        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Get the location of the window
                int x = frame.getLocation().x;
                int y = frame.getLocation().y;

                x += 1;
                y += 1;

                // Change the location of the window
                frame.setLocation(new Point(x, y));
            }
        });
        timer.start();
    }
}

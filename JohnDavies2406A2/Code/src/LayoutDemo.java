import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Created by John on 23/09/2016.
 */
public class LayoutDemo {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Hello window");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("0");
        label.setBackground(Color.red);
        label.setFont(new Font("Arial", Font.BOLD, 100));
        label.setBorder(new LineBorder(Color.red, 10));

        JButton button1 = new JButton("BUTTON");
        JButton button2 = new JButton("BUTTON1");
        JButton button3 = new JButton("BUTTON2");
        JButton button4 = new JButton("BUTTON3");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        panel.setBackground(Color.CYAN);
        panel.add(button1);
        panel.add(button2);
        panel.add(button3);
        panel.add(button4);

        frame.add(label, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

import javax.swing.*;
import java.awt.*;

/**
 * Created by John on 10/17/2016.
 */
public class Frame extends JFrame {
    Font font = new Font("Arial", Font.PLAIN, 20);
    public Frame(String title) {
        super(title);
        setSize(new Dimension(900,900));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
}

import javax.swing.*;
import java.awt.*;

/**
 * Created by John on 10/17/2016.
 */
public class Frame extends JFrame {
    public Frame(String title, int gridRows, int gridCols) {
        super(title);
        setSize(new Dimension(900,900));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(gridRows, gridCols));
    }
}

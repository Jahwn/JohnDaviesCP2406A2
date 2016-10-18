import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by John on 10/10/2016.
 */
public class SwingTest {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelTop = new JPanel();

        JLabel cardsLabel = new JLabel("Cards", SwingConstants.CENTER);
        cardsLabel.setBorder(new LineBorder(Color.red, 1));
        cardsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        JLabel roundLabel = new JLabel("Round", SwingConstants.CENTER);
        roundLabel.setBorder(new LineBorder(Color.red, 1));
        roundLabel.setFont(new Font("Arial", Font.BOLD, 20));
        JLabel playersLabel = new JLabel("Players", SwingConstants.CENTER);
        playersLabel.setBorder(new LineBorder(Color.red, 1));
        playersLabel.setFont(new Font("Arial", Font.BOLD, 20));

        panelTop.setLayout(new GridLayout(1, 3));
        panelTop.setPreferredSize(new Dimension(1000, 70));
        panelTop.add(cardsLabel);
        panelTop.add(roundLabel);
        panelTop.add(playersLabel);

        JPanel panelBottom = new JPanel();
        panelBottom.setLayout(new GridLayout(1, 3));
        panelBottom.setPreferredSize(new Dimension(1000, 500));
        JPanel panelBottomCenter = new JPanel();

        JLabel log = new JLabel ("<html>" + "Something's happen<br>More things happen" + "</html>", SwingConstants.CENTER);
        log.setBorder(new LineBorder(Color.blue, 1));
        log.setFont(new Font("Arial", Font.PLAIN, 15));
        JTextField input = new JTextField(SwingConstants.CENTER);
        input.setFont(new Font("Arial", Font.PLAIN, 100));
        input.setBorder(new LineBorder(Color.blue, 1));
        JButton inputButton = new JButton("SUBMIT");
        inputButton.setFont(new Font("Arial", Font.BOLD, 20));
        JLabel  cards = new JLabel ("<html>" + "Card 1<br>Card 2<br>Card 3" + "</html>", SwingConstants.CENTER);
        cards.setFont(new Font("Arial", Font.PLAIN, 15));
        cards.setBorder(new LineBorder(Color.blue, 1));
        JLabel  players = new JLabel ("<html>" + "Player 1<br>Player 2<br>Player 3" + "</html>", SwingConstants.CENTER);
        players.setBorder(new LineBorder(Color.blue, 1));
        players.setFont(new Font("Arial", Font.PLAIN, 15));

        inputButton.addActionListener(new ActionListener() {
            @Override
            // This method is executed when the button is pressed
            public void actionPerformed(ActionEvent e) {
                System.out.println("Hit that button again, I'll hit you");

                // Change the button text
                inputButton.setText("Ouch...");
                log.setText(input.getText());

                // Change the window title
                frame.setTitle("ALERT: Window is being pressed");
            }
        });

        JPanel inputSection = new JPanel();
        inputSection.setLayout(new GridLayout(1, 2));
        inputSection.add(input);
        inputSection.add(inputButton);

        panelBottomCenter.setLayout(new GridLayout(2, 1));
        panelBottomCenter.add(log, BorderLayout.NORTH);
        panelBottomCenter.add(inputSection, BorderLayout.SOUTH);

        panelBottom.add(cards);
        panelBottom.add(panelBottomCenter);
        panelBottom.add(players);

        frame.add(panelTop, BorderLayout.NORTH);
        frame.add(panelBottom, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);



    }
}

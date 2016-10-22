import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 * Created by John on 9/2/2016.
 */
public class Player {
    public int playerNo;
    public ArrayList<Card> pCards;
    boolean playerPassed = false;
    boolean isPlaying = true;

    Player(int playerNo, ArrayList<Card> pCards) {
        this.playerNo = playerNo;
        this.pCards = pCards;
    }

    public Card getPlayerCard(int index) {
        return pCards.get(index);
    }

    Scanner reader = new Scanner(System.in);

    public static Card cardChoice;
    public static Boolean choiceMade = false;

    public Card playerTurn() {
        cardChoice = null;
        choiceMade = false;
        if (pCards.size() != 0) {
            Frame frame = new Frame("Select a card", 2, 1);
            JPanel cardDisplay = new JPanel();
            JLabel label = new JLabel("EXAMPLE", SwingConstants.CENTER);
            //JPanel inputOptions = new JPanel();

            cardDisplay.setLayout(new FlowLayout());
            label.setPreferredSize(new Dimension(900,200));
            int n = 0;
            System.out.println("Your card(s):");

            boolean containsTheGeophysicist = false;
            boolean containsMagnetite = false;
            for (Card c: pCards) {
                JButton cImage = new JButton(c.image);
                //cImage.setSize(new Dimension(150,210));
                JPanel cPanel = new JPanel();
                cPanel.add(cImage);
                cPanel.setSize(cImage.getWidth(), cImage.getHeight());
                cardDisplay.add(cImage);
                if (c.getName().equals("The Geophysicist")) {
                    containsTheGeophysicist = true;
                }
                if (c.getName().equals("Magnetite")) {
                    containsMagnetite = true;
                }
                System.out.println(n + " " + c.getName());
                n++;

                cImage.addActionListener(new ActionListener() {
                    @Override
                    // This method is executed when the button is pressed
                    public void actionPerformed(ActionEvent e) {
                        // Change the button text
                        cardChoice = c;
                        choiceMade = true;
                        frame.dispose();
                    }
                });
            }

            frame.add(cardDisplay);
            frame.add(label);
            frame.setVisible(true);

            boolean inputValidSpecial = false;

            if (containsMagnetite && containsTheGeophysicist) {
                label.setText("***Your deck contains Magnetite and The Geophysicist***");
                System.out.println("***Your deck contains Magnetite and The Geophysicist***" +
                "\nWould you like to draw both of these cards? Press 'y' or 'n'");
                while (!inputValidSpecial) {
                    String input = reader.next();
                    if (input.equals("y")) {
                        cardChoice = new MCard("[The Geophysicist & Magnetite]", "", 0.0, 0.0, 0.0, 0.0, 0.0);
                        inputValidSpecial = true;
                    } else if (input.equals("n")) {
                        System.out.println("Continuing game as normal...");
                        inputValidSpecial = true;
                    } else {
                        System.out.println("\n" +
                                "***Error: Please enter proper input***" +
                                "\n");
                    }
                }
            }

            while (!choiceMade) {
                label.setText("Select your card");
                choiceMade = checkChoice();
            }
            return cardChoice;
        } else {
            return null;
        }
    }
    public Boolean checkChoice() {
        if (choiceMade) {
            return true;
        } else {
            return false;
        }
    }
}

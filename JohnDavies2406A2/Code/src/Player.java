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

    public volatile Card cardChoice;
    public volatile Boolean choiceMade = false;
    public volatile CardDataFetcher cardData = new CardDataFetcher();

    public Card playerTurn() {
        cardChoice = null;
        choiceMade = false;
        int n = 0;
        if (pCards.size() != 0) {
            Frame frame = new Frame("");
            frame.setTitle("Player " + playerNo + "'s hand");
            JPanel cardDisplay = new JPanel();

            cardDisplay.setLayout(new FlowLayout());

            boolean containsTheGeophysicist = false;
            boolean containsMagnetite = false;

            Font font = new Font("Arial", Font.BOLD, 20);
            JButton pass = new JButton("Pass");
            pass.setFont(font);
            cardDisplay.add(pass);

            pass.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    choiceMade = true;
                    cardChoice = null;
                    frame.dispose();
                }
            });

            for (Card c: pCards) {
                //Every time the loop iterates through a card, a button is generated for that card and is then added to cardDisplay
                JButton cImage = new JButton(c.image);
                cardDisplay.add(cImage);
                if (c.getName().equals("The Geophysicist")) {
                    containsTheGeophysicist = true;
                }
                if (c.getName().equals("Magnetite")) {
                    containsMagnetite = true;
                }
                //System.out.println(n + " " + c.getName());

                cImage.addActionListener(new ActionListener() {
                    @Override
                    // This method is executed when the button is pressed
                    public void actionPerformed(ActionEvent e) {
                        choiceMade = true;
                        cardChoice = c;
                        System.out.println(cardChoice.getName());
                        frame.dispose();
                    }
                });
                n++;
            }

            frame.add(cardDisplay);

            /*boolean inputValidSpecial = false;

            if (containsMagnetite && containsTheGeophysicist) {
                JLabel label = new JLabel();
                frame.removeAll();
                label.setText("Your deck contains Magnetite and The Geophysicist");
                label.setFont(new Font("Arial", Font.BOLD, 20));
                frame.add(label);
                System.out.println("***Your deck contains Magnetite and The Geophysicist***" +
                "\nWould you like to draw both of these cards? Press 'y' or 'n'");
                while (!inputValidSpecial) {
                    String input = reader.next();
                    if (input.equals("y")) {
                        cardChoice = new MCard("[The Geophysicist & Magnetite]", "", 0.0, 0.0, 0.0, 0.0, 0.0);
                        inputValidSpecial = true;
                    } else if (input.equals("n")) {
                        frame.remove(label);
                        frame.add(cardDisplay);
                        System.out.println("Continuing game as normal...");
                        inputValidSpecial = true;
                    } else {
                        System.out.println("\n" +
                                "***Error: Please enter proper input***" +
                                "\n");
                    }
                }
            }*/

            frame.setVisible(true);

            while (!choiceMade) {
            }

            System.out.println(choiceMade);

            return cardChoice;
        } else {
            return null;
        }
    }

    public volatile Boolean choice = true;
    public JButton yes = new JButton("Yes");
    public JButton no = new JButton("No");

    public Card checkPlayerChoice(Player p) {
        Card playerChoice = p.playerTurn();

        boolean choiceValid = false;
        // This while loop enables players to go back on their choice of cards
        // If they don't like their card's stats, they can go back and choose again
        while (!choiceValid) {
            choiceMade = false;
            JLabel cardInfo = new JLabel("", SwingConstants.CENTER);

            if (playerChoice != null) {
                Frame frame = new Frame("");
                frame.setTitle(playerChoice.getName() + "'s data");;
                frame.setLayout(new GridLayout(3,1));

                cardInfo.setFont(frame.font);

                JPanel top = new JPanel();
                JLabel cardIcon = new JLabel(playerChoice.image);
                top.add(cardIcon, BorderLayout.WEST);
                top.add(cardInfo, BorderLayout.EAST);
                frame.add(top);

                JLabel label = new JLabel("Continue with Selection?", SwingConstants.CENTER);
                label.setFont(frame.font);
                frame.add(label);

                JPanel bottom = new JPanel();
                bottom.add(yes);
                bottom.add(no);
                frame.add(bottom);

                yes.addActionListener(new ActionListener() {
                    @Override
                    // This method is executed when the button is pressed
                    public void actionPerformed(ActionEvent e) {
                        choice = true;
                        choiceMade = true;
                        frame.dispose();
                    }
                });
                no.addActionListener(new ActionListener() {
                    @Override
                    // This method is executed when the button is pressed
                    public void actionPerformed(ActionEvent e) {
                        choice = false;
                        choiceMade = true;
                        frame.dispose();
                    }
                });

                frame.setVisible(true);
            } else {
                choiceValid = true;
                return playerChoice;
            }

            if (playerChoice != null && !playerChoice.getName().equals("[The Geophysicist & Magnetite]")) {

                Boolean isTrumpCard = cardData.isTrumpCard(playerChoice.name);

                if (isTrumpCard) {
                    System.out.println("Category: " + playerChoice.getCategory());

                } else {

                    cardInfo.setText("<html>Hardness (averaged): " + playerChoice.getHardness() +
                            "<br>Specific gravity (averaged): " + playerChoice.getSpecificGravity() +
                            "<br>Cleavage: " + cardData.getCleavageString(playerChoice.getCleavage()) +
                            "<br>Crustal abundance: " + cardData.getCrustalAbundanceString(playerChoice.getCrustalAbundance()) +
                            "<br>Economic value: " + cardData.getEconomicValueString(playerChoice.getEconomicValue()) + "</html>");
                }

                while(!choiceMade) {
                }

                if (choice) {
                    p.pCards.remove(playerChoice);
                    choiceValid = true;
                } else {
                    playerChoice = p.playerTurn();
                }
            }
        }
        return playerChoice;
    }

    public void removeCard(int n) {
        pCards.remove(n);
    }
}

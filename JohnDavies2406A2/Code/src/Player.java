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
    public int n;
    public static CardDataFetcher cardData = new CardDataFetcher();

    public Card playerTurn() {
        cardChoice = null;
        choiceMade = false;
        n = 0;
        if (pCards.size() != 0) {
            Frame frame = new Frame("Player " + playerNo + "'s hand");
            JPanel cardDisplay = new JPanel();
            JLabel label = new JLabel("EXAMPLE", SwingConstants.CENTER);

            cardDisplay.setLayout(new FlowLayout());
            label.setPreferredSize(new Dimension(900,200));
            System.out.println("Your card(s):");

            boolean containsTheGeophysicist = false;
            boolean containsMagnetite = false;

            Font font = new Font("Arial", Font.BOLD, 20);
            JButton pass = new JButton("Pass");
            pass.setFont(font);
            cardDisplay.add(pass);

            pass.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cardChoice = null;
                    choiceMade = true;
                    frame.dispose();
                }
            });

            for (Card c: pCards) {
                JButton cImage = new JButton(c.image);
                cardDisplay.add(cImage);
                if (c.getName().equals("The Geophysicist")) {
                    containsTheGeophysicist = true;
                }
                if (c.getName().equals("Magnetite")) {
                    containsMagnetite = true;
                }
                System.out.println(n + " " + c.getName());

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
                n++;
            }


            frame.add(cardDisplay);
            frame.setVisible(true);

            boolean inputValidSpecial = false;

            if (containsMagnetite && containsTheGeophysicist) {
                frame.remove(cardDisplay);
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

    public static Card checkPlayerChoice(Player p) {
        Scanner reader = new Scanner(System.in);
        Card playerChoice = p.playerTurn();
        boolean choiceValid = false;
        // This while loop enables players to go back on their choice of cards
        // If they don't like their card's stats, they can go back and choose again
        while (!choiceValid) {
            if (playerChoice != null && !playerChoice.getName().equals("[The Geophysicist & Magnetite]")) {

                Boolean isTrumpCard = cardData.isTrumpCard(playerChoice.name);

                System.out.println(playerChoice.getName() + "'s data:");

                if (isTrumpCard) {
                    System.out.println("Category: " + playerChoice.getCategory());
                } else {
                    System.out.println("0 Hardness (averaged): " + playerChoice.getHardness() +
                            "\n1 Specific gravity (averaged): " + playerChoice.getSpecificGravity() +
                            "\n2 Cleavage: " + cardData.getCleavageString(playerChoice.getCleavage()) +
                            "\n3 Crustal abundance: " + cardData.getCrustalAbundanceString(playerChoice.getCrustalAbundance()) +
                            "\n4 Economic value: " + cardData.getEconomicValueString(playerChoice.getEconomicValue()));
                }

                System.out.print("Do you want to proceed with this card? Press 'y', or 'n' to proceed: ");

                String choice = reader.next();

                if (choice.equals("y")) {
                    choiceValid = true;
                } else if (choice.equals("n")) {
                    System.out.println("Select another card or pass.");
                    playerChoice = p.playerTurn();
                } else {
                    System.out.println("\n" +
                            "***Error: Please enter proper input***" +
                            "\n");
                }
            } else {
                return playerChoice;
            }
        }
        return playerChoice;
    }
    public void removeCard(int n) {
        pCards.remove(n);
    }
}

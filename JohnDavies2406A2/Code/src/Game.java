/**
 * Created by John on 9/2/2016.
 */
/*
 * ****************************
 * INTERNET CONNECTION REQUIRED
 * ****************************
 */

import javax.swing.*;
import java.awt.*;
import java.lang.*;
import java.util.*;

public class Game {
    /*
     * ****************************
     * INTERNET CONNECTION REQUIRED
     * ****************************
     */

    public static ArrayList<Player> players = new ArrayList<Player>();
    public static ArrayList<Card> deck = new ArrayList<Card>();
    public static CardDataFetcher cardData = new CardDataFetcher();

    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in);

        DeckConstructor deckConstructor = new DeckConstructor();

        // Table will contain the card currently in play
        Card table = new MCard("No card", "", 0.0, 0.0, 0.0, 0.0, 0.0);

        deck = deckConstructor.ConstructDeck(deck);

        int x = 55;

        for(Card c: deck) {
            if (!c.getCategory().equals("N/A")) {
                c.imageIcon = new ImageIcon(new ImageIcon("images/Slide" + x + ".jpg").getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
                c.image = c.imageIcon;
                x++;
            }
        }

        // Shuffle the deck
        //Collections.shuffle(deck);

        // Will contain current category
        String category = "No category";
        // Will contain value of card presently on the table (value in current category)
        Double categoryValue = 0.0;

        JOptionPane.showMessageDialog(null, "Welcome to the Mineral Super Trumps Game!");

        // Display a list of possible player counts users can choose from
        Object[] possibleValues = { 3, 4, 5 };
        Object userInput = JOptionPane.showInputDialog(null,
                "Choose how many players", "Input",
                JOptionPane.INFORMATION_MESSAGE, null,
                possibleValues, possibleValues[0]);
        int playerCount = (Integer) userInput;

        // Declare players. Their id numbers are automatically assigned
        for (int n = 1; n <= playerCount; n++) {
            players.add(new Player(n, new ArrayList<Card>()));
        }

        // Assigning cards to players
        for (Player s: players) {
            while(s.pCards.size() < 15) {
                s.pCards.add(deck.get(0));
                deck.remove(0);
            }
        }

        Boolean running = true;

        int round = 1;

        ArrayList<Integer> winners = new ArrayList<Integer>();

        String noCategory = "No category";

        boolean roundStarted = false;

        // This is where players take their turns
        while (running) {
            // A label is used to add a breaking point for the program when a winning condition is satisfied
            // This is using a normal break on the for loop right below would only make the current loop the last
            // The for loop would still continue until the last player has been iterated through
            outer:
            for (Player p : players) {
                boolean turnValid = false;
                // If the player has already emptied their deck and won, disregard their turn by making the program think they've passed and have had their turn
                if (!p.isPlaying) {
                    p.playerPassed = true;
                    turnValid = true;
                }

                // Check the number of players who have passed
                int playersPassed = playersPassed();

                // If player hasn't passed...
                if (!p.playerPassed) {
                    // If all but 1 player has passed, reset the category and announce the winner of the round
                    if (playersPassed == playerCount - 1) {
                        category = noCategory;
                        System.out.println("----------------------------------------" +
                                "\nPlayer " + p.playerNo + " won round " + round +
                                "\n----------------------------------------");
                        // Move to the next round
                        round++;
                        roundStarted = false;
                    }
                }

                // If this is a new round, and the player is still in the game, announce the new round
                if (!roundStarted && p.isPlaying) {
                    System.out.println("----------------------------------------" +
                            "\nRound " + round +
                            "\n----------------------------------------");
                    // Because this is a new round, all players who have passed are now back in
                    bringBackPlayers();
                }

                // If this player has passed, skip this turn
                if (p.playerPassed) {
                    System.out.println("Player " + p.playerNo + " is out of the round");
                    turnValid = true;
                } else {
                    // Because the program thinks winning players have passed, a check to see if they're still playing is needed
                    if (p.isPlaying) {
                        System.out.println("Player " + p.playerNo + "'s turn");
                    }
                }

                while (!turnValid) {
                    // Bring up interface for card selection
                    Card playerChoice = checkPlayerChoice(p);

                    // If the player has chosen to pass
                    if (playerChoice == null) {
                        if (p.isPlaying) {
                            // If the deck isn't empty, give the player a card
                            if (!deck.isEmpty()) {
                                p.pCards.add(deck.get(0));
                                System.out.println(deck.get(0).getName() + " added to your hand Player " + p.playerNo);
                                deck.remove(0);
                            } else {
                                System.out.println("\n" +
                                        "***Error: Deck is empty***" +
                                        "\n");
                            }
                            p.playerPassed = true;
                            turnValid = true;
                            roundStarted = true;
                        }
                    } else if (playerChoice.getName().equals("[The Geophysicist & Magnetite]")) {
                        // If the player drew The Geophysicist and the Magnetite combination, they win the game
                        winners.clear();
                        winners.add(p.playerNo);
                        running = false;
                        break outer;
                    } else {
                        roundStarted = true;
                        Boolean isTrumpCard = cardData.isTrumpCard(playerChoice.name);
                        // If a trump card is detected, the category is automatically changed
                        // Unless that card is The Geologist
                        // If so, select a category of your choice
                        if (isTrumpCard) {
                            if (playerChoice.name.equals("The Geologist")) {
                                System.out.println("Select a category: " +
                                        "\n0 Hardness" +
                                        "\n1 Specific gravity" +
                                        "\n2 Cleavage" +
                                        "\n3 Crustal abundance" +
                                        "\n4 Economic value");
                                System.out.print("Enter the category by inputting the number next to the category name: ");
                                String input = reader.next();
                                boolean inputValid = false;
                                while (!inputValid) {
                                    if (input.equals("0")) {
                                        category = "Hardness";
                                        inputValid = true;
                                    } else if (input.equals("1")) {
                                        category = "Specific gravity";
                                        inputValid = true;
                                    } else if (input.equals("2")) {
                                        category = "Cleavage";
                                        inputValid = true;
                                    } else if (input.equals("3")) {
                                        category = "Crustal abundance";
                                        inputValid = true;
                                    } else if (input.equals("4")) {
                                        category = "Economic value";
                                        inputValid = true;
                                    } else {
                                        System.out.println("\n" +
                                                "***Error: Please enter proper input***" +
                                                "\n");
                                    }
                                }
                                categoryValue = 0.0;
                                bringBackPlayers();
                                System.out.println("Category value has been reset" +
                                        "\nAll players who have passed are back in");
                            } else {
                                category = playerChoice.getCategory();
                                categoryValue = 0.0;
                                bringBackPlayers();
                                System.out.println("Category value has been reset" +
                                        "\nAll players who have passed are back in");
                            }
                            // When category changes, put the card from the table back to the deck
                            if (!table.getName().equals("No card")) {
                                deck.add(table);
                            }
                            // Reset the table
                            table = new MCard("No card", "", 0.0, 0.0, 0.0, 0.0, 0.0);

                            // Put the trump card back in the deck after use
                            deck.add(playerChoice);

                            System.out.println("----------------------------------------" +
                                    "\nNew category is " + category +
                                    "\n----------------------------------------");
                            turnValid = true;
                        } else {
                            // If a category doesn't already exist, then the player must choose one
                            if (category.equals(noCategory)) {
                                boolean inputValid = false;
                                // valueToBeat is the value players will be shown. Actual value players need to beat is a double
                                // Instead of decimal values, users will see values such as "trace" and "moderate", among others
                                String valueToBeat = "";

                                while (!inputValid) {
                                    System.out.print("Enter the category by inputting the number next to the category name: ");
                                    String input = reader.next();
                                    // String values were favored for comparisons to minimize the complexity of the input check
                                    // Anything else may have required a try/catch loop
                                    if (input.equals("0")) {
                                        category = "Hardness";
                                        categoryValue = playerChoice.getHardness();
                                        valueToBeat = Double.toString(categoryValue);
                                        inputValid = true;
                                    } else if (input.equals("1")) {
                                        category = "Specific gravity";
                                        categoryValue = playerChoice.getSpecificGravity();
                                        valueToBeat = Double.toString(categoryValue);
                                        inputValid = true;
                                    } else if (input.equals("2")) {
                                        category = "Cleavage";
                                        categoryValue = playerChoice.getCleavage();
                                        valueToBeat = cardData.getCleavageString(categoryValue);
                                        inputValid = true;
                                    } else if (input.equals("3")) {
                                        category = "Crustal abundance";
                                        categoryValue = playerChoice.getCrustalAbundance();
                                        valueToBeat = cardData.getCrustalAbundanceString(categoryValue);
                                        inputValid = true;
                                    } else if (input.equals("4")) {
                                        category = "Economic value";
                                        categoryValue = playerChoice.getEconomicValue();
                                        valueToBeat = cardData.getEconomicValueString(categoryValue);
                                        inputValid = true;
                                    } else {
                                        System.out.println("\n" +
                                                "***Error: Please enter proper input***" +
                                                "\n");
                                    }
                                }
                                // Put the card on the table
                                table = playerChoice;

                                System.out.println("----------------------------------------" +
                                        "\nCategory: " + category + " | value to beat: " + valueToBeat +
                                        "\n----------------------------------------");

                                turnValid = true;
                            } else {
                                Double playerCategoryValue = 0.0;
                                String valueToBeat = "";

                                // If a category already exists, automatically submit the accompanying value for checks
                                if (category.equals("Hardness")) {
                                    playerCategoryValue = playerChoice.getHardness();
                                    valueToBeat = Double.toString(playerCategoryValue);
                                } else if (category.equals("Specific gravity")) {
                                    playerCategoryValue = playerChoice.getSpecificGravity();
                                    valueToBeat = Double.toString(playerCategoryValue);
                                } else if (category.equals("Cleavage")) {
                                    playerCategoryValue = playerChoice.getCleavage();
                                    valueToBeat = cardData.getCleavageString(playerCategoryValue);
                                } else if (category.equals("Crustal abundance")) {
                                    playerCategoryValue = playerChoice.getCrustalAbundance();
                                    valueToBeat = cardData.getCrustalAbundanceString(playerCategoryValue);
                                } else if (category.equals("Economic value")) {
                                    playerCategoryValue = playerChoice.getEconomicValue();
                                    valueToBeat = cardData.getEconomicValueString(playerCategoryValue);
                                }

                                // This if check makes it possible to win a draw even if the card has the same value
                                // The game can moving faster this way
                                if (categoryValue > playerCategoryValue) {
                                    System.out.println("----------------------------------------" +
                                            "\nYour card's value in " + category + " is lower than " + table.name +
                                            "\nPlease select another card" +
                                            "\n----------------------------------------");
                                    p.pCards.add(playerChoice);
                                } else {
                                    System.out.println("----------------------------------------");
                                    if (!table.getName().equals("No card")) {
                                        System.out.println("Your card beat " + table.name + "!");
                                        deck.add(table);
                                    }
                                    table = playerChoice;
                                    categoryValue = playerCategoryValue;
                                    System.out.println("New value to beat for " + category + " is: " + valueToBeat +
                                            "\n----------------------------------------");
                                    turnValid = true;
                                }
                            }
                        }
                    }
                }
                // If the player emptied their deck after a drawing a card, put them down as a winner
                if (p.pCards.isEmpty()) {
                    p.isPlaying = false;
                    System.out.println("Player " + p.playerNo + " has no cards remaining");
                    if (winners.isEmpty()) {
                        winners.add(p.playerNo);
                        playerCount--;
                    } else {
                        if (!winners.contains(p.playerNo)) {
                            winners.add(p.playerNo);
                            playerCount--;
                        }
                    }
                }
                // If there is only 1 player left, end the game and announce the winners
                if (playerCount == 1) {
                    running = false;
                    break outer;
                }
            }
        }
        System.out.println("GAME OVER!! Winners: " +
                "\n------------------****------------------");
        for (int n: winners) {
            System.out.println("Player " + n);
        }
        System.out.println("------------------****------------------");
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
                    p.pCards.add(playerChoice);
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
    public static int playersPassed() {
        int playersPassed = 0;
        for (Player p: players) {
            if (p.playerPassed && p.isPlaying) {
                playersPassed++;
            }
        }
        return playersPassed;
    }
    public static void bringBackPlayers() {
        for (Player p: players) {
            p.playerPassed = false;
        }
    }
}

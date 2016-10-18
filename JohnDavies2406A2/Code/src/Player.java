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

    public Card playerTurn() {
        if (pCards.size() != 0) {
            Card cardChoice = null;
            int n = 0;
            System.out.println("Your card(s):");

            boolean containsTheGeophysicist = false;
            boolean containsMagnetite = false;
            for (Card c: pCards) {
                if (c.getName().equals("The Geophysicist")) {
                    containsTheGeophysicist = true;
                }
                if (c.getName().equals("Magnetite")) {
                    containsMagnetite = true;
                }
                System.out.println(n + " " + c.getName());
                n++;
            }
            boolean inputValidSpecial = false;
            boolean inputValid = false;
            if (containsMagnetite && containsTheGeophysicist) {
                System.out.println("***Your deck contains Magnetite and The Geophysicist***" +
                "\nWould you like to draw both of these cards? Press 'y' or 'n'");
                while (!inputValidSpecial) {
                    String input = reader.next();
                    if (input.equals("y")) {
                        cardChoice = new MCard("[The Geophysicist & Magnetite]", "", 0.0, 0.0, 0.0, 0.0, 0.0);
                        inputValidSpecial = true;
                        inputValid = true;
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

            while (!inputValid) {
                System.out.print("Enter 'p' to pass or choose card by typing the number next to their name: ");
                String input = reader.next();
                try {
                    if (input.equals("p")) {
                        cardChoice = null;
                        playerPassed = true;
                        inputValid = true;
                    } else {
                        cardChoice = pCards.get(Integer.parseInt(input));
                        pCards.remove(Integer.parseInt(input));
                        inputValid = true;
                    }
                } catch (Exception e) {
                    System.out.println("\n" +
                            "***Error: Please enter proper input***" +
                            "\n");
                }
            }
            return cardChoice;
        } else {
            return null;
        }
    }
}

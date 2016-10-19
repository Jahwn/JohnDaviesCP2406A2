/**
 * Created by John on 14/09/2016.
 */

import java.util.*;
import java.util.regex.*;

public class DeckConstructor {
    public ArrayList ConstructDeck(ArrayList<Card> deck) {
        // Declare the plist file reader class
        XML_Reader xml_reader = new XML_Reader();

        CardDataFetcher cardDataFetch = new CardDataFetcher();

        xml_reader.Reader();
        // Read the plist file and write to a string arraylist and return
        ArrayList<String> cardData = xml_reader.Reader();

        int n = 0;
        int j = 0;
        // temporarily holds card data
        ArrayList<String> tempCardData = new ArrayList<String>();
        // same as above but for super trump cards
        ArrayList<String> tempSCardData = new ArrayList<String>();

        for (String s :cardData) {
            // This would mean that the deck's size would go to 61 because 60 is not more than 60
            // Need to go to 61 because an unexpected card is in between 2 required super trump cards. To make sure that all trump cards are submitted, the size goes to 61
            // The unusual card is removed later at the bottom
            if (deck.size() > 60) {
                break;
            }
            if (s.equals("fileName")) {
                if (j < 55) {
                    tempCardData.add(cardData.get(n + 1));
                    j++;
                } else if ((j >= 55) && (j <= 60)) {
                    tempSCardData.add(cardData.get(n + 1));
                    j++;
                }
            } else if (s.equals("title")) {
                tempCardData.add(cardData.get(n + 1));
            } else if (s.equals("hardness")) {
                tempCardData.add(cardData.get(n + 1));
            } else if (s.equals("specific_gravity")) {
                tempCardData.add(cardData.get(n + 1));
            } else if (s.equals("cleavage")) {
                tempCardData.add(cardData.get(n + 1));
            } else if (s.equals("crustal_abundance")) {
                tempCardData.add(cardData.get(n + 1));
            } else if (s.equals("economic_value")) {
                tempCardData.add(cardData.get(n + 1));
            } else if (s.equals("The Miner")) {
                tempSCardData.add(s);
                tempSCardData.add(cardData.get(n + 2));
            } else if (s.equals("The Petrologist")) {
                tempSCardData.add(s);
                tempSCardData.add(cardData.get(n + 2));
            } else if (s.equals("The Gemmologist")) {
                tempSCardData.add(s);
                tempSCardData.add(cardData.get(n + 2));
            } else if (s.equals("The Mineralogist")) {
                tempSCardData.add(s);
                tempSCardData.add(cardData.get(n + 2));
            } else if (s.equals("The Geophysicist")) {
                tempSCardData.add(s);
                tempSCardData.add(cardData.get(n + 2));
            } else if (s.equals("The Geologist")) {
                tempSCardData.add(s);
                tempSCardData.add(cardData.get(n + 2));
            }

            // When the temporary array has filled up with 6 values, pass those values into the deck as cards and then empty the array
            if (tempCardData.size() == 7) {

                double hardness = cardDataFetch.getHardnessValue(tempCardData.get(2));
                double specificGravity = cardDataFetch.getSpecificGravityValue(tempCardData.get(3));
                double cleavage = cardDataFetch.getCleavage(tempCardData.get(4));
                double crustalAbundance = cardDataFetch.getCrustalAbundance(tempCardData.get(5));
                double economicValue = cardDataFetch.getEconomicValue(tempCardData.get(6));

                deck.add(new MCard(tempCardData.get(1), tempCardData.get(0), hardness, specificGravity, cleavage, crustalAbundance, economicValue));
                tempCardData.clear();
            }

            // Sames as above but only for 2 values because trump cards have only 2 important values: their name and category
            if (tempSCardData.size() == 3) {
                deck.add(new SCard(tempSCardData.get(0), tempSCardData.get(2), tempSCardData.get(1)));
                tempSCardData.clear();
            }
            n++;
        }
        // Removing a particular card that stored unusual values... Does not compromise the deck however
        deck.remove(deck.size() - 2);

        return deck;
    }
}

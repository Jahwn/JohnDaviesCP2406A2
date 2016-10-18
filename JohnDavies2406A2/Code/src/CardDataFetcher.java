import java.util.regex.*;
import java.util.*;

/**
 * Created by John on 9/15/2016.
 */
// This class converts data from string to numerical formats such double or integer values from the DeckConstructor class
public class CardDataFetcher {
    public Double getHardnessValue(String value) {
        double hardness = 0;
        List<Double> data = new ArrayList<Double>();
        Matcher m = Pattern.compile("(?!=\\d\\.\\d\\.)([\\d.]+)").matcher(value);
        while(m.find()) {
            double d = Double.parseDouble(m.group(1));
            data.add(d);
        }
        if (data.size() == 2) {
            hardness = (data.get(0) + data.get(1)) / 2;
        } else if (data.size() == 1) {
            hardness = data.get(0);
        } else {
            hardness = 0;
        }
        return hardness;
    }

    public Double getSpecificGravityValue(String value) {
        double specificGravity;
        List<Double> data = new ArrayList<Double>();
        Matcher m = Pattern.compile("(?!=\\d\\.\\d\\.)([\\d.]+)").matcher(value);
        while(m.find()) {
            double d = Double.parseDouble(m.group(1));
            data.add(d);
        }
        if (data.size() == 2) {
            specificGravity = (data.get(0) + data.get(1)) / 2;
        } else if (data.size() == 1) {
            specificGravity = data.get(0);
        } else {
            specificGravity = 0;
        }
        return specificGravity;
    }

    public Double getCrustalAbundance(String value) {
        Double crustalAbundance = 0.0;
        if (value.equals("ultratrace")) {
            crustalAbundance = 0.0;
        } else if (value.equals("trace")) {
            crustalAbundance = 1.0;
        } else if (value.equals("low")) {
            crustalAbundance = 2.0;
        } else if (value.equals("moderate")) {
            crustalAbundance = 3.0;
        } else if (value.equals("high")) {
            crustalAbundance = 4.0;
        } else if (value.equals("very high")) {
            crustalAbundance = 5.0;
        }

        return crustalAbundance;
    }
    public String getCrustalAbundanceString(Double value) {
        String crustalAbundance = "null";
        ArrayList<String> data = new ArrayList<String>(
                Arrays.asList("ultratrace", "trace", "low", "moderate", "high", "very high"));
        if (value != null) {
            crustalAbundance = data.get(value.intValue());
        }
        return crustalAbundance;
    }

    public Double getEconomicValue(String value) {
        Double economicValue = 0.0;
        if (value.equals("trivial")) {
            economicValue = 0.0;
        } else if (value.equals("low")) {
            economicValue = 1.0;
        } else if (value.equals("moderate")) {
            economicValue = 2.0;
        } else if (value.equals("high")) {
            economicValue = 3.0;
        } else if (value.equals("very high")) {
            economicValue = 4.0;
        } else if (value.equals("I'm rich!")) {
            economicValue = 5.0;
        }

        return economicValue;
    }

    public String getEconomicValueString(Double value) {
        String economicValue = "null";
        ArrayList<String> data = new ArrayList<String>(
                Arrays.asList("trivial", "low", "moderate", "high", "very high", "I'm rich!"));
        if (value != null) {
            economicValue = data.get(value.intValue());
        }
        return economicValue;
    }

    public Double getCleavage(String value) {
        Double cleavage = 0.0;
        if (value.equals("none")) {
            cleavage = 0.0;
        } else if (value.equals("poor/none")) {
            cleavage = 1.0;
        } else if (value.equals("1 poor")) {
            cleavage = 2.0;
        } else if (value.equals("2 poor")) {
            cleavage = 3.0;
        } else if (value.equals("1 good, 1 poor")) {
            cleavage = 4.0;
        } else if (value.equals("2 good")) {
            cleavage = 5.0;
        } else if (value.equals("3 good")) {
            cleavage = 6.0;
        } else if (value.equals("1 perfect")) {
            cleavage = 7.0;
        } else if (value.equals("1 perfect, 1 good")) {
            cleavage = 8.0;
        } else if (value.equals("1 perfect, 2 good")) {
            cleavage = 9.0;
        } else if (value.equals("2 perfect, 1 good")) {
            cleavage = 10.0;
        } else if (value.equals("3 perfect")) {
            cleavage = 11.0;
        } else if (value.equals("4 perfect")) {
            cleavage = 12.0;
        } else if (value.equals("6 perfect")) {
            cleavage = 13.0;
        }
        return cleavage;
    }
    public String getCleavageString(Double value) {
        String cleavage = "null";

        ArrayList<String> data = new ArrayList<String>(
                Arrays.asList("none", "poor/none", "1 poor", "2 poor", "1 good, 1 poor", "2 good", "3 good", "1 perfect",
                        "1 perfect, 1 good", "1 perfect, 2 good", "2 perfect, 1 good", "3 perfect", "4 perfect", "6 perfect"));
        if (value != null) {
            cleavage = data.get(value.intValue());
        }
        return cleavage;
    }

    public boolean isTrumpCard(String cName) {
        ArrayList<String> tCardName = new ArrayList<String>(
                Arrays.asList("The Miner", "The Petrologist", "The Gemmologist", "The Mineralogist", "The Geophysicist", "The Geologist"));
        Boolean isTrumpCard = false;
        for (String s: tCardName) {
            if (cName.equals(s)) {
                isTrumpCard = true;
            }
        }
        return isTrumpCard;
    }
}

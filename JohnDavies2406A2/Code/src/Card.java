/**
 * Created by John on 8/26/2016.
 */
import java.awt.Image;
import java.awt.image.*;
import java.io.*;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

public abstract class Card {
    public String name;
    public String imageName;
    Icon image;
    ImageIcon imageIcon;

    Card(String name, String imageName) {
        this.name = name;
        this.imageName = imageName;
        //this.image = new ImageIcon("images/" + imageName);
        this.imageIcon = new ImageIcon(new ImageIcon("images/" + imageName).getImage().getScaledInstance(135, 189, Image.SCALE_DEFAULT));
        this.image = imageIcon;
    }

    public void frame() {
        JFrame frame = new JFrame(name);

        JLabel label = new JLabel(image);

        frame.add(label);

        frame.pack();
        frame.setVisible(true);
    }

    abstract Double getHardness();
    abstract Double getSpecificGravity();
    abstract Double getCleavage();
    abstract Double getCrustalAbundance();
    abstract Double getEconomicValue();
    abstract String getCategory();

    public String getName() {
        return name;
    }
}

class MCard extends Card {
    public Double hardness;
    public Double specificGravity;
    public Double cleavage;
    public Double crustalAbundance;
    public Double economicValue;

    MCard(String name, String imageName, Double hardness, Double specificGravity, Double cleavage, Double crustalAbundance, Double economicValue) {

        super(name, imageName);
        this.hardness = hardness;
        this.specificGravity = specificGravity;
        this.cleavage = cleavage;
        this.crustalAbundance = crustalAbundance;
        this.economicValue = economicValue;

    }

    @Override
    Double getHardness() {
        return hardness;
    }
    Double getSpecificGravity() {
        return specificGravity;
    }
    Double getCleavage() {
        return cleavage;
    }
    Double getCrustalAbundance() {
        return crustalAbundance;
    }
    Double getEconomicValue() {
        return economicValue;
    }
    String getCategory() {
        return "N/A";
    }
}
class SCard extends Card {
    public String category;
    SCard(String name, String imageName, String category) {
        super(name, imageName);
        this.category = category;
    }

    @Override
    Double getHardness() {
        return null;
    }
    Double getSpecificGravity() {
        return null;
    }
    Double getCleavage() {
        return null;
    }
    Double getCrustalAbundance() {
        return null;
    }
    Double getEconomicValue() {
        return null;
    }
    String getCategory() {
        return category;
    }
}





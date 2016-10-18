/**
 * Created by John on 8/29/2016.
 */
/*
 * ****************************
 * INTERNET CONNECTION REQUIRED
 * ****************************
 */
import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.*;

//Note: 11 strings in XML elements
public class XML_Reader {
    public ArrayList Reader() {
        /*
         * ****************************
         * INTERNET CONNECTION REQUIRED
         * ****************************
         */

        final ArrayList<String> CARD_DATA = new ArrayList<String>();

        //declare a SAX parser
        SAXParserFactory factory = SAXParserFactory.newInstance();

        SAXParser saxParser = null;
        try {
            saxParser = factory.newSAXParser();
        } catch (ParserConfigurationException e1) {
            e1.printStackTrace();
        } catch (SAXException e1) {
            e1.printStackTrace();
        }

        DefaultHandler handler = new DefaultHandler() {

            boolean stringDetected = false, keyDetected = false;
            //This method is called every time the parser gets an open tag "<"
            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

                if(qName.equalsIgnoreCase("key")) {
                    keyDetected = true;
                }
                if(qName.equalsIgnoreCase("string")) {
                    stringDetected = true;
                }
            }

            //This method is called every time the parser comes across a closing tag ">"
            public void endElement(String uri, String localName, String qName) throws SAXException {

            }

            //This method reads the contents of the "<" tags
            public void characters(char ch[], int start, int length) throws SAXException {
                // Get the title
                if(keyDetected) {
                    CARD_DATA.add(new String(ch, start, length));
                    keyDetected = false;
                }
                // Get the value
                if(stringDetected) {
                    CARD_DATA.add(new String(ch, start, length));
                    stringDetected = false;
                }
            }
        };

        try {
            saxParser.parse("MstCards_151021.plist", handler);
        } catch (UnknownHostException e) {
            System.out.println("***Error: please check your internet connection***");
            System.exit(0);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return CARD_DATA;
    }
}

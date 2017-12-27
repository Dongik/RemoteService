package xyz.dongik.project.parser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by dongik on 17. 12. 23.
 */

public class XmlParser {
    String xml;
    XmlPullParserFactory factory;
    XmlPullParser xpp;
    StringReader sr;
    XmlParser(String xml) throws XmlPullParserException, IOException {
        this.xml = xml;
        factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        xpp = factory.newPullParser();
//        xpp.set
        sr = new StringReader(xml);
        xpp.setInput(sr);

    }



    ArrayList<String> getImgUrls()throws XmlPullParserException,IOException{
        ArrayList<String> urls = new ArrayList<>();
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
//            if(eventType == XmlPullParser.START_DOCUMENT) {
//                System.out.println("Start document");
//            } else if(eventType == XmlPullParser.START_TAG) {
//                System.out.println("Start tag "+xpp.getName());
//            } else if(eventType == XmlPullParser.END_TAG) {
//                System.out.println("End tag "+xpp.getName());
//            } else if(eventType == XmlPullParser.TEXT) {
//                System.out.println("Text "+xpp.getText());
//            }
//            xpp.getText();
            eventType = xpp.nextToken();
        }
//        System.out.println("End document");
        return urls;
    }

}

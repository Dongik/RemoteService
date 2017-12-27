package xyz.dongik.project.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dongik on 17. 12. 24.
 */
public class CustomParser {

    CustomParser(){
    }

    public List<String> parseImgUrls(String xml){
        List<String> imgUrls = new ArrayList<>();
        int len = xml.length();
        for(int i = 0;i<len ;i++) {
            char c = xml.charAt(i);
            if (c == '<') {// If '<' is found
                if (xml.substring(i, i + 4).contains("img")) {
                    int st, ed;
                    st = i;
                    //find index of '>'
                    for (; i < len; i++) {
                        if (xml.charAt(i) == '>') break;
                    }
                    ed = i;
                    //find index of src
                    String imgTag = xml.substring(st, ed);
                    String[] sp = imgTag.split("\"");
                    for (int j = 0; j < sp.length; j++) {
                        if (sp[j].contains("src")) {
                            String imgUrl = sp[j+1].trim();
                            if(imgUrl.startsWith("http")) {
                                imgUrls.add(imgUrl);
                            }
                            break;
                        }
                    }
//                    int src =
                }
            }
        }
        return imgUrls;
    }




}

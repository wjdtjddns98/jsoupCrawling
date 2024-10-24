package org.example;

import org.jsoup.nodes.Document;
import org.jsoup.*;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Project1 {
    public static void main(String[] args) {
        //네이버 스포츠 크롤링//
        String url = "https://sports.news.naver.com/wfootball/index";
        Document doc = null;

        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(doc);//

        Elements elements = doc.select("div.home_news");
        //제목 글자만 가져오기
        //System.out.println(elements);
        String title = elements.select("h2").text();
        System.out.println("=================================================");
        System.out.println(title);
        System.out.println("=================================================");
        //리스트의 Li 내용을 출력할떈 for 문을 이용
        for (Element el : elements.select("li")) {
            if(el.text().length() >= 45){
                System.out.println(el.text().substring(0, 45)+"...");
            }else{
                System.out.println(el.text());
            }
        }
    }
}

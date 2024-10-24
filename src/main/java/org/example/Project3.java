package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.Scanner;

public class Project3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 사용자로부터 주식 종목 코드를 입력받음
        System.out.print("조회 주식 종목 코드 : ");
        String stockCode = scanner.nextLine();

        String URL = "https://finance.naver.com/item/main.naver?code="+stockCode;
        Document doc = null;

        try {
            doc = Jsoup.connect(URL).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Elements todayList = doc.select(".new_totalinfo dl>dd");

        String time = todayList.get(0).text();
        String name = todayList.get(1).text().split(" ")[1];
        String juga = todayList.get(3).text().split(" ")[1];
        String dungRakrate = todayList.get(3).text().split(" ")[6];
        String siga = todayList.get(5).text().split(" ")[1];
        String goga = todayList.get(6).text().split(" ")[1];
        String zeoga = todayList.get(8).text().split(" ")[1];
        String georaeryang = todayList.get(10).text().split(" ")[1];

        String stype = todayList.get(3).text().split(" ")[3]; //상한가 상승 보합 하한가 하락 구분

        String vsYesterday = todayList.get(3).text().split(" ")[4];


        System.out.printf("================== %s ===================\n", name);
        System.out.println("주가: "+juga);
        System.out.println("등락률 : "+dungRakrate);
        System.out.println("시가 : "+siga);
        System.out.println("고가 : "+goga);
        System.out.println("저가 : "+zeoga);
        System.out.println("거래량 : "+georaeryang);
        System.out.println("타입 : "+stype);
        System.out.println("전일대비 : "+vsYesterday);
        System.out.println("기준 시간 : " + time);
    }
}

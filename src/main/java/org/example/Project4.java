package org.example;


import org.example.model.ExelVO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Project4 {
    static Scanner sc = new Scanner(System.in);
    static List<ExelVO> booklist = new ArrayList<>();
    public static void main(String[] args) {
        boolean running = true;
        do{
            menu();
            int key = Integer.parseInt(sc.nextLine());
            switch(key){
                case 1:
                    searchBook();
                    break;
                case 2:
                    listcheck();
                    break;
                case 3:
                    exelInput();
                    break;
                case 4:
                    System.out.println("프로그램을 종료합니다.");
                    running = false;
                    break;
            }
        } while (running);

    }

    private static void menu() {
        System.out.println("=========메뉴=========");
        System.out.println("1. 책검색");
        System.out.println("2. 책 리스트 확인");
        System.out.println("3. 책 리스트 엑셀 저장");
        System.out.println("4. 종료");
        System.out.print("번호선택 : ");
    }

    private static void searchBook() {
        System.out.print("책 검색 : ");
        String query = sc.nextLine().trim();
        ExelVO vo = getNaverApi(query);
        if(vo == null) return;

        System.out.print("검색된 책을 저장 하시겠습니까? (y/n) 선택 : ");
        if(sc.nextLine().toLowerCase().charAt(0) == 'y'){
            booklist.add(vo);
            System.out.println("저장 되었습니다.");
        } else if (sc.nextLine().toLowerCase().charAt(0) == 'n') {
            System.out.println("저장 되지 않습니다");
        }

    }

    /**
     * 검색어 입력을 받아 네이버 API로 책을 검색한 결과 리턴
     * @param query 검색어
     * @return 책 객체
     */
    private static ExelVO getNaverApi(String query) {

        try{
            String openApi = "https://openapi.naver.com/v1/search/book.json?query="
                    + URLEncoder.encode(query, "UTF-8");
            URL url = new URL(openApi);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("X-Naver-Client-Id", "dB8b__SEYw9fkaAtp8ZZ");
            conn.setRequestProperty("X-Naver-Client-Secret", "mWh7q8T4WD");
            /* System.out.println("응답 코드 : " + conn.getResponseCode()); */
            BufferedReader br;
            if(conn.getResponseCode() == 200){
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            }else{
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
            }

            StringBuilder sb = new StringBuilder();
            String Line;
            while ((Line = br.readLine()) != null) {
                sb.append(Line);
            }
            br.close();
            conn.disconnect();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(sb.toString());
            JSONArray arr = (JSONArray) jsonObject.get("items");

            if(arr.isEmpty()){
                System.out.println("검색된 책이 없습니다");
                return null;
            }

            JSONObject book = (JSONObject) arr.get(0);
            ExelVO vo = new ExelVO();

            System.out.println("제목 : " + book.get("title")+ "\t");
            System.out.println("작가 : " + book.get("author") + "\t");
            System.out.println("출판사 : " + book.get("publisher") + "\t");
            System.out.println("isbn : " + book.get("isbn") + "\t");
            System.out.println("출간일 : " + book.get("pubdate") + "\t");


            vo.setTitle(book.get("title").toString());
            vo.setAuthor(book.get("author").toString());
            vo.setCompany(book.get("publisher").toString());
            vo.setIsbn(book.get("isbn").toString());
            vo.setImgurl(book.get("image").toString());

            return vo;

        }catch(Exception e){
            e.printStackTrace();
        }
        return new ExelVO();
    }

    private static void listcheck() {
        System.out.println("저장된 리스트를 확인합니다.");
        if(booklist.isEmpty()){
            System.out.println("리스트에 저장된 책이 없습니다");
        }else {
            for (int i = 0; i < booklist.size(); i++) {
                ExelVO vo = booklist.get(i);
                System.out.println((i+1)+ ". " + vo.getTitle());
            }

        }
    }

    private static void exelInput() {
        System.out.println("3");
    }

    private static void exit() {
    }


}

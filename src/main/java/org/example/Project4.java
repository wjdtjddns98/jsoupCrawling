package org.example;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.example.model.ExelVO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Project4 {
    static Scanner sc = new Scanner(System.in);
    static List<ExelVO> booklist = new ArrayList<>();
    static HSSFWorkbook workBook = new HSSFWorkbook();

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

        while (true) {
            System.out.print("검색된 책을 저장 하시겠습니까? (y/n) 선택 : ");
            char a = sc.nextLine().toLowerCase().charAt(0);
            if (a == 'y') {
                booklist.add(vo);
                System.out.println("저장 되었습니다.");
                break;
            } else if (a == 'n') {
                System.out.println("저장 되지 않습니다.");
                break;
            } else {
                System.out.println("잘못 입력하셨습니다.");}
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
        try {
            HSSFSheet sheet = null;
            //가상의 엑셀 시트를 생성
            if(workBook.getSheet("Book SHEET") != null ) {
                sheet = workBook.getSheet("Book SHEET");
            } else {
                sheet = workBook.createSheet("BooK SHEET");
            }

            //엑셀 시트에 첫줄 만들기
            HSSFRow rowA = sheet.createRow(0);
            HSSFCell cellA = rowA.createCell(0);
            cellA.setCellValue(new HSSFRichTextString("책제목"));
            HSSFCell cellB = rowA.createCell(1);
            cellB.setCellValue(new HSSFRichTextString("저자"));
            HSSFCell cellC = rowA.createCell(2);
            cellC.setCellValue(new HSSFRichTextString("출판사"));
            HSSFCell cellD = rowA.createCell(3);
            cellD.setCellValue(new HSSFRichTextString("isbn"));
            HSSFCell cellE = rowA.createCell(4);
            cellE.setCellValue(new HSSFRichTextString("이미지주소"));


            //북리스트의 데이터를 엑셀 시트에 넣기
            int i=1; // 첫줄은 스킵, 1부터 시작
            for(ExelVO book : booklist) {
                HSSFRow row = sheet.createRow(i++);
                row.createCell(0).setCellType(CellType.STRING);
                row.createCell(0).setCellValue(book.getTitle());
                row.createCell(1).setCellType(CellType.STRING);
                row.createCell(1).setCellValue(book.getAuthor());
                row.createCell(2).setCellType(CellType.STRING);
                row.createCell(2).setCellValue(book.getCompany());
                row.createCell(3).setCellType(CellType.STRING);
                row.createCell(3).setCellValue(book.getIsbn());
                row.createCell(4).setCellType(CellType.STRING);
                row.createCell(4).setCellValue(book.getImgurl());
            }

            FileOutputStream fos = new FileOutputStream("BookList.xls");
            workBook.write(fos);
            fos.close();
            System.out.println("엑셀로 저장 성공!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void exit() {
    }


}

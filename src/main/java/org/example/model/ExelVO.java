package org.example.model;

/**
 * 엑셀파일에서 읽은 데이터 전달
 */
public class ExelVO {
    private String title;
    private String author;
    private String company;
    private String isbn;
    private String imgurl;

    public ExelVO() {
    }

    public ExelVO(String title, String author, String company, String isbn, String imgurl) {
        this.title = title;
        this.author = author;
        this.company = company;
        this.isbn = isbn;
        this.imgurl = imgurl;
    }

    public ExelVO(String title, String author, String company) {
        this.title = title;
        this.author = author;
        this.company = company;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    @Override
    public String toString() {
        return "ExelVO{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", company='" + company + '\'' +
                ", isbn='" + isbn + '\'' +
                ", imgurl='" + imgurl + '\'' +
                '}';
    }
}
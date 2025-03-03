package hu.petrik;

import java.sql.Date;

public class Book {
    private int id;
    private String title;
    private String author;
    private int publish_year;
    private int page_count;
    private Date created_at;
    private Date updated_at;

    public Book(int id, String title, String author, int publish_year, int page_count, Date created_at, Date updated_at) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publish_year = publish_year;
        this.page_count = page_count;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getPublish_year() {
        return publish_year;
    }

    public void setPublish_year(int publish_year) {
        this.publish_year = publish_year;
    }

    public int getPage_count() {
        return page_count;
    }

    public void setPage_count(int page_count) {
        this.page_count = page_count;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}

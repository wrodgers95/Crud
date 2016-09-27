package com.theironyard.charlotte;

public class Book {
    String title;
    String author;
    int id;
    static int numberOfBooks = 0;

//    String read;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;

//        this.read = read;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

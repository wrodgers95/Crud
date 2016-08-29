package com.theironyard.charlotte;

import java.util.ArrayList;

public class User {

    String name;
    String password;

    ArrayList<Book> books = new ArrayList<>();

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

}

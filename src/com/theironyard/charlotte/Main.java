package com.theironyard.charlotte;

import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;

public class Main {

    public static HashMap<String, User> users = new HashMap<>();

    public static void main(String[] args) {

        Spark.init();

        Spark.get(

                "/",

                ((request, response) -> {

                    // getting current user from users HashMap

                    Session session = request.session();

                    String name = session.attribute("loginName");

                    User user = users.get(name);

                    HashMap m = new HashMap<>();

                    // if no user exists, redirect to login screen else to go books screen
                    if (user == null) {

                        return new ModelAndView(m, "login.html");

                    } else {

                        m.put("name", user.name);

                        m.put("books", user.books);

                        return new ModelAndView(m, "home.html");
                    }
                }),
                new MustacheTemplateEngine()
        );

        Spark.post(

                // login screen
                "/login",

                ((request, response) -> {

                    String name = request.queryParams("loginName");
                    String password = request.queryParams("loginPassword");
                    User user = users.get(name);
                    user = new User(name, password);
                    users.put(name, user);

                    Session session = request.session();
                    session.attribute("loginName", name);

                    response.redirect("/");

                    return "";
                })
        );

        Spark.post(

                // Book entry page
                "/home",

                ((request, response) -> {

                    Session session = request.session();
                    String name = session.attribute("loginName");
                    User user = users.get(name);

                    String title = request.queryParams("title");
                    String author = request.queryParams("author");
//                    String read = request.queryParams("read");

                    Book newBook = new Book (title, author);
                    user.books.add(newBook);

                    response.redirect("/");

                    return "";
                })
        );

        Spark.post(

                // logout button
                "/logout",

                ((request, response) -> {

                    Session session = request.session();

                    // ending current session
                    session.invalidate();

                    // return to login
                    response.redirect("/");

                    return "";
                })

        );

        Spark.post(

                "/delete",

                ((request, response) -> {

                    Session session = request.session();

                    String name = session.attribute("loginName");

                    User user = users.get(name);

                    int bookEntry = Integer.valueOf(request.queryParams("deleteID"));

                    user.books.remove(bookEntry);

                    response.redirect("/");

                    return "";

                }));

        Spark.get (
                "/update-book/:id",
                ((request, response) -> {

                    Session session = request.session();

                    String name = session.attribute("loginName");

                    User user = users.get(name);

                    int bookID = Integer.valueOf(request.params("id"));

                    HashMap m = new HashMap<>();

                    m.put("id", bookID);

                    m.put("name", user.name);

                    m.put("books", user.books);

                    return new ModelAndView(m, "update.html");

                }),
                new MustacheTemplateEngine()

        );

        Spark.post (

                "/update-entry/:id",
                (request, response) -> {

                    Session session = request.session();

                    String name = session.attribute("loginName");

                    User user = users.get(name);

                    int bookEntry = Integer.valueOf(request.queryParams("updateID"));

                    String title = request.queryParams("title");
                    String author = request.queryParams("author");
                    Book newBook = new Book (title, author);

                    user.books.set(bookEntry, newBook);

                    response.redirect("/");

                    return "";
                }
        );
    }
}

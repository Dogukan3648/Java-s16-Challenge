package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Author extends Person{
    private List<Book> books;

    public Author(String name,List<Book> books) {
        super(name);
        this.books = books;
    }
    public Author(String name){
        super(name);
        this.books = new ArrayList<>();
    }

    public List<Book> getBooks(){
        return books;
    }
    public void setBooks(List<Book> books){
        this.books = books;
    }
    public void newBook(Book book){
        books.add(book);
    }
    public void showBooks(){
        for (Book book : books){
            System.out.println(book);
        }
    }
    public void removeBook(Book book){
        books.remove(book);
    }

    @Override
    public void whoYouAre() {
        System.out.println("I am an author.");
    }
}

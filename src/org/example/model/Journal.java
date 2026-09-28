package org.example.model;

import java.time.LocalDate;

public class Journal extends Book{

    public Journal(long bookId, Author author, String title, double price, String edition, LocalDate dateOfPurchase) {
        super(bookId, author, title, price, edition, dateOfPurchase);
    }

    @Override
    public void display() {
        System.out.println(this);
    }
}

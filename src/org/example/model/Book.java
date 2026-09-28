package org.example.model;

import org.example.enums.BookStatus;

import java.time.LocalDate;
import java.util.Objects;

public abstract class Book {
    private long bookId;
    private Author author;
    private String title;
    private double price;
    private String edition;
    private BookStatus status;
    private LocalDate dateOfPurchase;

    public Book(long bookId,Author author,String title, double price, String edition,LocalDate dateOfPurchase){
        this.bookId = bookId;
        this.author = author;
        this.title = title;
        this.price = price;
        this.edition = edition;
        this.dateOfPurchase = dateOfPurchase;
        this.status = BookStatus.AVAILABLE;
    }

    public long getBookId(){
        return bookId;
    }
    public Author getAuthor(){
        return author;
    }
    public void setAuthor(Author author){
        this.author = author;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public double getPrice(){
        return price;
    }
    public void setPrice(double price){
        this.price = price;
    }
    public String getEdition(){
        return edition;
    }
    public void setEdition(String edition){
        this.edition = edition;
    }
    public BookStatus getStatus(){
        return status;
    }
    public LocalDate getDateOfPurchase(){
        return dateOfPurchase;
    }
    public abstract void display();

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", author='" + author.getName() + '\'' +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", edition='" + edition + '\'' +
                ", status=" + status +
                ", dateOfPurchase=" + dateOfPurchase +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Book book)) return false;
        return bookId == book.bookId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(bookId);
    }
}

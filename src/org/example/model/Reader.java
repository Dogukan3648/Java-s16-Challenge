package org.example.model;

import java.util.HashSet;
import java.util.Set;

public class Reader extends Person{
    private MemberRecord memberRecord;

    private Set<Book> books;

    public Reader(MemberRecord memberRecord) {
        super(memberRecord.getName());
        this.books = new HashSet<>();
        this.memberRecord = memberRecord;
    }
    public Set<Book> getBooks(){
        return books;
    }
    public MemberRecord getMemberRecord(){
        return memberRecord;
    }

    public void addBook(Book book){
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
        System.out.println("I am a reader.");
    }

    @Override
    public String toString() {
        return "Reader{" +
                "memberId=" + memberRecord.getMemberId() +
                ", name='" + getName() + '\'' +
                '}';
    }
}

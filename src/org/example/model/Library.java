package org.example.model;

import org.example.enums.BookStatus;
import org.example.enums.InvoiceType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Library {

    private Map<Long, Book> books;
    private Map<Long, Reader> readers;
    private Map<Long, Reader> borrowedBookOwners;
    private List<Invoice> invoices;
    private long nextInvoiceId;
    private Map<Long, Invoice> activeBorrowInvoices;

    public Library() {
        this.books = new HashMap<>();
        this.readers = new HashMap<>();
        this.borrowedBookOwners = new HashMap<>();
        this.invoices = new ArrayList<>();
        this.nextInvoiceId = 1;
        this.activeBorrowInvoices = new HashMap<>();
    }

    public boolean addBook(Book book) {
        if (books.containsKey(book.getBookId())) {
            return false;
        }

        books.put(book.getBookId(), book);

        if (!book.getAuthor().getBooks().contains(book)) {
            book.getAuthor().newBook(book);
        }

        return true;
    }

    public boolean addReader(Reader reader) {
        long memberId = reader.getMemberRecord().getMemberId();

        if (readers.containsKey(memberId)) {
            return false;
        }

        readers.put(memberId, reader);

        return true;
    }

    public Book findBookById(long bookId) {
        return books.get(bookId);
    }

    public List<Book> findBooksByTitle(String title) {
        List<Book> result = new ArrayList<>();

        for (Book book : books.values()) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                result.add(book);
            }
        }

        return result;
    }

    public List<Book> findBooksByAuthor(String authorName) {
        List<Book> result = new ArrayList<>();

        for (Book book : books.values()) {
            if (book.getAuthor().getName().equalsIgnoreCase(authorName)) {
                result.add(book);
            }
        }

        return result;
    }

    public boolean removeBook(long bookId) {
        Book book = books.get(bookId);

        if (book == null) {
            return false;
        }

        if (borrowedBookOwners.containsKey(bookId)) {
            return false;
        }

        books.remove(bookId);
        book.getAuthor().removeBook(book);

        return true;
    }

    public boolean updateBook(long bookId,
                              Author newAuthor,
                              String newTitle,
                              double newPrice,
                              String newEdition) {

        Book book = books.get(bookId);

        if (book == null) {
            return false;
        }

        if (book.getAuthor() != newAuthor) {
            book.getAuthor().removeBook(book);

            if (!newAuthor.getBooks().contains(book)) {
                newAuthor.newBook(book);
            }

            book.setAuthor(newAuthor);
        }

        book.setTitle(newTitle);
        book.setPrice(newPrice);
        book.setEdition(newEdition);

        return true;
    }

    public List<Book> findBooksByCategory(Class<? extends Book> category) {
        List<Book> result = new ArrayList<>();

        for (Book book : books.values()) {
            if (category.isInstance(book)) {
                result.add(book);
            }
        }

        return result;
    }

    public Invoice borrowBook(long memberId, long bookId) {
        Reader reader = readers.get(memberId);
        Book book = books.get(bookId);

        if (reader == null || book == null) {
            return null;
        }

        if (book.getStatus() != BookStatus.AVAILABLE) {
            return null;
        }

        if (borrowedBookOwners.containsKey(bookId)) {
            return null;
        }

        MemberRecord memberRecord = reader.getMemberRecord();

        if (memberRecord.getNoBooksIssued() >= memberRecord.getMaxBookLimit()) {
            return null;
        }

        reader.addBook(book);
        memberRecord.increaseBooksIssued();

        borrowedBookOwners.put(bookId, reader);

        book.markAsBorrowed();

        Invoice invoice = new Invoice(
                nextInvoiceId++,
                reader,
                book,
                book.getPrice(),
                InvoiceType.BORROW
        );

        invoices.add(invoice);
        activeBorrowInvoices.put(bookId, invoice);

        return invoice;
    }

    public Invoice returnBook(long memberId, long bookId) {
        Reader reader = readers.get(memberId);
        Book book = books.get(bookId);

        if (reader == null || book == null) {
            return null;
        }

        Reader currentOwner = borrowedBookOwners.get(bookId);

        if (currentOwner != reader) {
            return null;
        }

        Invoice borrowInvoice = activeBorrowInvoices.get(bookId);

        if (borrowInvoice == null) {
            return null;
        }

        reader.removeBook(book);
        reader.getMemberRecord().decreaseBooksIssued();

        borrowedBookOwners.remove(bookId);
        activeBorrowInvoices.remove(bookId);

        book.markAsAvailable();

        Invoice refund = new Invoice(
                nextInvoiceId++,
                reader,
                book,
                borrowInvoice.getAmount(),
                InvoiceType.REFUND
        );

        invoices.add(refund);

        return refund;
    }
    public Reader findReaderById(long memberId){
        return readers.get(memberId);
    }
}
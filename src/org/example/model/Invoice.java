package org.example.model;

import org.example.enums.InvoiceType;
import java.time.LocalDateTime;

public class Invoice {
    private long invoiceId;
    private Reader reader;
    private Book book;
    private double amount;
    private InvoiceType type;
    private LocalDateTime createdAt;

    public Invoice(long invoiceId,Reader reader,Book book, double amount,InvoiceType type) {
        this.invoiceId = invoiceId;
        this.reader = reader;
        this.book = book;
        this.amount = amount;
        this.type = type;
        this.createdAt = LocalDateTime.now();
    }
    public long getInvoiceId(){
        return invoiceId;
    }
    public Reader getReader(){
        return reader;
    }

    public Book getBook() {
        return book;
    }

    public double getAmount() {
        return amount;
    }

    public InvoiceType getType() {
        return type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceId=" + invoiceId +
                ", reader=" + reader +
                ", book=" + book +
                ", amount=" + amount +
                ", type=" + type +
                ", createdAt=" + createdAt +
                '}';
    }
}

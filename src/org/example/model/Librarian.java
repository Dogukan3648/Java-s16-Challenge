package org.example.model;

public class Librarian {
    private String name;
    private String password;
    private Library library;

    public Librarian(String name,String password,Library library){
        this.name = name;
        this.password = password;
        this.library = library;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public Library getLibrary() {
        return library;
    }
    public Book searchBook(long bookId){
        return library.findBookById(bookId);
    }
    public Invoice issueBook(long memberId, long bookId){
        return library.borrowBook(memberId,bookId);
    }
   public boolean verifyMember(long memberId){
        return library.findReaderById(memberId) != null;
   }
   public Invoice returnBook(long memberId, long bookId){
        return library.returnBook(memberId, bookId);
   }
   public double calculateFine(int lateDays, double dailyFine) {
        if (lateDays <= 0 || dailyFine <= 0){
            return 0;
        }
        return lateDays * dailyFine;
   }
   public void createBill(Invoice invoice){
        if (invoice != null){
            System.out.println(invoice);
        }
   }

}

package org.example;

import org.example.model.*;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.List;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Library library = new Library();
        Librarian librarian = new Librarian("Doğukan","1234",library);

        boolean running = true;

        while (running){

            System.out.println();
            System.out.println("===== LIBRARY SYSTEM =====");
            System.out.println("1 - Add Book");
            System.out.println("2 - Search Book");
            System.out.println("3 - Update Book");
            System.out.println("4 - Delete Book");
            System.out.println("5 - List Books By Category");
            System.out.println("6 - List Books By Author");
            System.out.println("7 - Add Reader");
            System.out.println("8 - Borrow Book");
            System.out.println("9 - Return Book");
            System.out.println("0 - Exit");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice){
                case 1:
                    addBook(scanner,library);
                    break;
                case 2:
                    searchBook(scanner,library);
                    break;
                case 3:
                    updateBook(scanner,library);
                    break;
                case 4:
                    deleteBook(scanner,library);
                    break;
                case 5:
                    listBooksByCategory(scanner,library);
                    break;
                case 6:
                    listBooksByAuthor(scanner,library);
                    break;
                case 7:
                    addReader(scanner,library);
                    break;
                case 8:
                    borrowBook(scanner,librarian);
                    break;
                case 9:
                    returnBook(scanner,librarian);
                    break;
                case 0:
                    running = false;
                    System.out.println("Library system closed.");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
        scanner.close();

    }
    private static void addBook(Scanner scanner, Library library) {

        System.out.print("Book ID: ");
        long bookId = scanner.nextLong();
        scanner.nextLine();

        System.out.print("Author name: ");
        String authorName = scanner.nextLine();

        System.out.print("Book title: ");
        String title = scanner.nextLine();

        System.out.print("Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Edition: ");
        String edition = scanner.nextLine();

        System.out.print("Date of purchase (DD-MM-YYYY): ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dateOfPurchase = LocalDate.parse(scanner.nextLine(), formatter);

        System.out.println("1 - Journal");
        System.out.println("2 - Study Book");
        System.out.println("3 - Magazine");
        System.out.print("Select category: ");

        int category = scanner.nextInt();
        scanner.nextLine();

        Author author = new Author(authorName);
        Book book;

        switch (category) {
            case 1:
                book = new Journal(
                        bookId, author, title, price, edition, dateOfPurchase
                );
                break;

            case 2:
                book = new StudyBook(
                        bookId, author, title, price, edition, dateOfPurchase
                );
                break;

            case 3:
                book = new Magazine(
                        bookId, author, title, price, edition, dateOfPurchase
                );
                break;

            default:
                System.out.println("Invalid category.");
                return;
        }

        boolean added = library.addBook(book);

        if (added) {
            System.out.println("Book added successfully.");
        } else {
            System.out.println("A book with this ID already exists.");
        }
    }
    private static void searchBook(Scanner scanner, Library library) {

        System.out.println("1 - Search by ID");
        System.out.println("2 - Search by Title");
        System.out.println("3 - Search by Author");
        System.out.print("Select search type: ");

        int searchType = scanner.nextInt();
        scanner.nextLine();

        switch (searchType) {
            case 1:
                System.out.print("Book ID: ");
                long bookId = scanner.nextLong();
                scanner.nextLine();

                Book book = library.findBookById(bookId);

                if (book != null) {
                    System.out.println(book);
                } else {
                    System.out.println("Book not found.");
                }
                break;

            case 2:
                System.out.print("Book title: ");
                String title = scanner.nextLine();

                List<Book> booksByTitle = library.findBooksByTitle(title);

                if (booksByTitle.isEmpty()) {
                    System.out.println("Book not found.");
                } else {
                    for (Book currentBook : booksByTitle) {
                        System.out.println(currentBook);
                    }
                }
                break;

            case 3:
                System.out.print("Author name: ");
                String authorName = scanner.nextLine();

                List<Book> booksByAuthor = library.findBooksByAuthor(authorName);

                if (booksByAuthor.isEmpty()) {
                    System.out.println("Book not found.");
                } else {
                    for (Book currentBook : booksByAuthor) {
                        System.out.println(currentBook);
                    }
                }
                break;

            default:
                System.out.println("Invalid search type.");
        }
    }
    private static void updateBook(Scanner scanner, Library library) {

        System.out.print("Book ID: ");
        long bookId = scanner.nextLong();
        scanner.nextLine();

        Book book = library.findBookById(bookId);

        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        System.out.print("New author name: ");
        String authorName = scanner.nextLine();

        System.out.print("New title: ");
        String title = scanner.nextLine();

        System.out.print("New price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("New edition: ");
        String edition = scanner.nextLine();

        Author author;

        if (book.getAuthor().getName().equalsIgnoreCase(authorName)) {
            author = book.getAuthor();
        } else {
            author = new Author(authorName);
        }

        boolean updated = library.updateBook(
                bookId,
                author,
                title,
                price,
                edition
        );

        if (updated) {
            System.out.println("Book updated successfully.");
        } else {
            System.out.println("Book could not be updated.");
        }
    }
    private static void deleteBook(Scanner scanner, Library library) {

        System.out.print("Book ID: ");
        long bookId = scanner.nextLong();
        scanner.nextLine();

        boolean deleted = library.removeBook(bookId);

        if (deleted) {
            System.out.println("Book deleted successfully.");
        } else {
            System.out.println("Book could not be deleted.");
        }
    }
    private static void listBooksByCategory(Scanner scanner, Library library) {

        System.out.println("1 - Journal");
        System.out.println("2 - Study Book");
        System.out.println("3 - Magazine");
        System.out.print("Select category: ");

        int category = scanner.nextInt();
        scanner.nextLine();

        List<Book> books;

        switch (category) {
            case 1:
                books = library.findBooksByCategory(Journal.class);
                break;

            case 2:
                books = library.findBooksByCategory(StudyBook.class);
                break;

            case 3:
                books = library.findBooksByCategory(Magazine.class);
                break;

            default:
                System.out.println("Invalid category.");
                return;
        }

        if (books.isEmpty()) {
            System.out.println("No books found in this category.");
            return;
        }

        for (Book book : books) {
            System.out.println(book);
        }
    }
    private static void listBooksByAuthor(Scanner scanner, Library library) {

        System.out.print("Author name: ");
        String authorName = scanner.nextLine();

        List<Book> books = library.findBooksByAuthor(authorName);

        if (books.isEmpty()) {
            System.out.println("No books found for this author.");
            return;
        }

        for (Book book : books) {
            System.out.println(book);
        }
    }
    private static void addReader(Scanner scanner, Library library) {

        System.out.print("Member ID: ");
        long memberId = scanner.nextLong();
        scanner.nextLine();

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Address: ");
        String address = scanner.nextLine();

        System.out.print("Phone number: ");
        String phoneNo = scanner.nextLine();

        System.out.println("1 - Student");
        System.out.println("2 - Faculty");
        System.out.print("Select member type: ");

        int memberType = scanner.nextInt();
        scanner.nextLine();

        MemberRecord memberRecord;

        switch (memberType) {
            case 1:
                memberRecord = new Student(
                        memberId,
                        name,
                        address,
                        phoneNo
                );
                break;

            case 2:
                memberRecord = new Faculty(
                        memberId,
                        name,
                        address,
                        phoneNo
                );
                break;

            default:
                System.out.println("Invalid member type.");
                return;
        }

        Reader reader = new Reader(memberRecord);

        boolean added = library.addReader(reader);

        if (added) {
            System.out.println("Reader added successfully.");
        } else {
            System.out.println("A reader with this ID already exists.");
        }
    }
    private static void borrowBook(Scanner scanner, Librarian librarian) {

        System.out.print("Member ID: ");
        long memberId = scanner.nextLong();

        System.out.print("Book ID: ");
        long bookId = scanner.nextLong();
        scanner.nextLine();

        if (!librarian.verifyMember(memberId)) {
            System.out.println("Reader not found.");
            return;
        }

        if (librarian.searchBook(bookId) == null) {
            System.out.println("Book not found.");
            return;
        }

        Invoice invoice = librarian.issueBook(memberId, bookId);

        if (invoice == null) {
            System.out.println("Book could not be borrowed.");
            return;
        }

        System.out.println("Book borrowed successfully.");
        librarian.createBill(invoice);
    }
    private static void returnBook(Scanner scanner, Librarian librarian) {

        System.out.print("Member ID: ");
        long memberId = scanner.nextLong();

        System.out.print("Book ID: ");
        long bookId = scanner.nextLong();
        scanner.nextLine();

        if (!librarian.verifyMember(memberId)) {
            System.out.println("Reader not found.");
            return;
        }

        if (librarian.searchBook(bookId) == null) {
            System.out.println("Book not found.");
            return;
        }

        Invoice refund = librarian.returnBook(memberId, bookId);

        if (refund == null) {
            System.out.println("Book could not be returned.");
            return;
        }

        System.out.println("Book returned successfully.");
        librarian.createBill(refund);
    }
}
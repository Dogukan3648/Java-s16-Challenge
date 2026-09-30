package org.example;

import org.example.model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Library library = new Library();
        Librarian librarian = new Librarian("Doğukan", "1234", library);

        boolean running = true;

        while (running) {

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

            int choice = readInt(scanner, "Select an option: ");

            switch (choice) {

                case 1:
                    addBook(scanner, library);
                    break;

                case 2:
                    searchBook(scanner, library);
                    break;

                case 3:
                    updateBook(scanner, library);
                    break;

                case 4:
                    deleteBook(scanner, library);
                    break;

                case 5:
                    listBooksByCategory(scanner, library);
                    break;

                case 6:
                    listBooksByAuthor(scanner, library);
                    break;

                case 7:
                    addReader(scanner, library);
                    break;

                case 8:
                    borrowBook(scanner, librarian);
                    break;

                case 9:
                    returnBook(scanner, librarian);
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

        long bookId = readLong(scanner, "Book ID: ");

        System.out.print("Author name: ");
        String authorName = scanner.nextLine();

        System.out.print("Book title: ");
        String title = scanner.nextLine();

        double price = readDouble(scanner, "Price: ");

        System.out.print("Edition: ");
        String edition = scanner.nextLine();

        LocalDate dateOfPurchase =
                readDate(scanner, "Date of purchase (DD-MM-YYYY): ");

        System.out.println("1 - Journal");
        System.out.println("2 - Study Book");
        System.out.println("3 - Magazine");

        int category = readInt(scanner, "Select category: ");

        Author author = library.findOrCreateAuthor(authorName);
        Book book;

        switch (category) {

            case 1:
                book = new Journal(
                        bookId,
                        author,
                        title,
                        price,
                        edition,
                        dateOfPurchase
                );
                break;

            case 2:
                book = new StudyBook(
                        bookId,
                        author,
                        title,
                        price,
                        edition,
                        dateOfPurchase
                );
                break;

            case 3:
                book = new Magazine(
                        bookId,
                        author,
                        title,
                        price,
                        edition,
                        dateOfPurchase
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

        int searchType = readInt(scanner, "Select search type: ");

        switch (searchType) {

            case 1:

                long bookId = readLong(scanner, "Book ID: ");

                Book book = library.findBookById(bookId);

                if (book != null) {
                    book.display();
                } else {
                    System.out.println("Book not found.");
                }

                break;

            case 2:

                System.out.print("Book title: ");
                String title = scanner.nextLine();

                List<Book> booksByTitle =
                        library.findBooksByTitle(title);

                if (booksByTitle.isEmpty()) {

                    System.out.println("Book not found.");

                } else {

                    for (Book currentBook : booksByTitle) {
                        currentBook.display();
                    }
                }

                break;

            case 3:

                System.out.print("Author name: ");
                String authorName = scanner.nextLine();

                List<Book> booksByAuthor =
                        library.findBooksByAuthor(authorName);

                if (booksByAuthor.isEmpty()) {

                    System.out.println("Book not found.");

                } else {

                    for (Book currentBook : booksByAuthor) {
                        currentBook.display();
                    }
                }

                break;

            default:
                System.out.println("Invalid search type.");
        }
    }

    private static void updateBook(Scanner scanner, Library library) {

        long bookId = readLong(scanner, "Book ID: ");

        Book book = library.findBookById(bookId);

        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        System.out.print("New author name: ");
        String authorName = scanner.nextLine();

        System.out.print("New title: ");
        String title = scanner.nextLine();

        double price = readDouble(scanner, "New price: ");

        System.out.print("New edition: ");
        String edition = scanner.nextLine();

       Author author = library.findOrCreateAuthor(authorName);

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

        long bookId = readLong(scanner, "Book ID: ");

        boolean deleted = library.removeBook(bookId);

        if (deleted) {
            System.out.println("Book deleted successfully.");
        } else {
            System.out.println("Book could not be deleted.");
        }
    }

    private static void listBooksByCategory(
            Scanner scanner,
            Library library
    ) {

        System.out.println("1 - Journal");
        System.out.println("2 - Study Book");
        System.out.println("3 - Magazine");

        int category = readInt(scanner, "Select category: ");

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
            book.display();
        }
    }

    private static void listBooksByAuthor(
            Scanner scanner,
            Library library
    ) {

        System.out.print("Author name: ");
        String authorName = scanner.nextLine();

        List<Book> books =
                library.findBooksByAuthor(authorName);

        if (books.isEmpty()) {
            System.out.println("No books found for this author.");
            return;
        }

        for (Book book : books) {
            book.display();
        }
    }

    private static void addReader(
            Scanner scanner,
            Library library
    ) {

        long memberId = readLong(scanner, "Member ID: ");

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Address: ");
        String address = scanner.nextLine();

        System.out.print("Phone number: ");
        String phoneNo = scanner.nextLine();

        System.out.println("1 - Student");
        System.out.println("2 - Faculty");

        int memberType =
                readInt(scanner, "Select member type: ");

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
            System.out.println(
                    "A reader with this ID already exists."
            );
        }
    }

    private static void borrowBook(
            Scanner scanner,
            Librarian librarian
    ) {

        long memberId =
                readLong(scanner, "Member ID: ");

        long bookId =
                readLong(scanner, "Book ID: ");

        if (!librarian.verifyMember(memberId)) {
            System.out.println("Reader not found.");
            return;
        }

        if (librarian.searchBook(bookId) == null) {
            System.out.println("Book not found.");
            return;
        }

        Invoice invoice =
                librarian.issueBook(memberId, bookId);

        if (invoice == null) {
            System.out.println("Book could not be borrowed.");
            return;
        }

        System.out.println("Book borrowed successfully.");
        librarian.createBill(invoice);
    }

    private static void returnBook(
            Scanner scanner,
            Librarian librarian
    ) {

        long memberId =
                readLong(scanner, "Member ID: ");

        long bookId =
                readLong(scanner, "Book ID: ");

        if (!librarian.verifyMember(memberId)) {
            System.out.println("Reader not found.");
            return;
        }

        if (librarian.searchBook(bookId) == null) {
            System.out.println("Book not found.");
            return;
        }

        Invoice refund =
                librarian.returnBook(memberId, bookId);

        if (refund == null) {
            System.out.println("Book could not be returned.");
            return;
        }

        System.out.println("Book returned successfully.");
        librarian.createBill(refund);
    }

    private static int readInt(
            Scanner scanner,
            String prompt
    ) {

        while (true) {

            System.out.print(prompt);

            try {

                return Integer.parseInt(
                        scanner.nextLine().trim()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }
    }

    private static long readLong(
            Scanner scanner,
            String prompt
    ) {

        while (true) {

            System.out.print(prompt);

            try {

                return Long.parseLong(
                        scanner.nextLine().trim()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }
    }

    private static double readDouble(
            Scanner scanner,
            String prompt
    ) {

        while (true) {

            System.out.print(prompt);

            try {

                return Double.parseDouble(
                        scanner.nextLine().trim()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }
    }

    private static LocalDate readDate(
            Scanner scanner,
            String prompt
    ) {

        while (true) {

            System.out.print(prompt);

            try {

                return LocalDate.parse(
                        scanner.nextLine().trim(),
                        DATE_FORMATTER
                );

            } catch (DateTimeParseException e) {

                System.out.println(
                        "Please enter the date as DD-MM-YYYY."
                );
            }
        }
    }
}
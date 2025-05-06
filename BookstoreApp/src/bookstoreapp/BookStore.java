package bookstoreapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class BookStore extends Store {
    private static final String BOOK_FILE = "books.txt";
    private ObservableList<Book> books;

    public BookStore() {
        books = FXCollections.observableArrayList();
        loadBooks(); // Load books when the store is initialized
    }

    public ObservableList<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
    if (bookExists(book.getName())) {
        showAlert(AlertType.ERROR, "Error", "Book already added.");
    } else {
        books.add(book);
        saveBooks();
        }
    }

    public void removeBook(Book book) {
        books.remove(book);
        saveBooks();
    }

    private boolean bookExists(String name) {
        return books.stream().anyMatch(b -> b.getName().equalsIgnoreCase(name));
    }

    public void loadBooks() {
        File file = new File(BOOK_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            books.clear();
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 2) {
                    books.add(new Book(data[0].trim(), Double.parseDouble(data[1].trim())));
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void saveBooks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOK_FILE))) {
            for (Book book : books) {
                writer.write(book.getName() + "," + book.getPrice());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
package bookstoreapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;

public class CustomerScreen {
    private Scene scene;
    private int points;
    private StatusState status;
    private Label statusLabel;
    private final String username;
    private String customerName;
    private TableView<Book> bookTable;

    public CustomerScreen(Stage primaryStage, Scene mainPage, String username) {
        this.username = username;
        StackPane root = new StackPane();
        scene = new Scene(root, 500, 500);

        loadCustomerData();

        // Title
        statusLabel = new Label(getStatusMessage());

        // Table
        bookTable = new TableView<>();
        setupBookTable();
        loadBooks();

        // Buttons
        Button buyBookBtn = new Button("Buy");
        Button redeemPointsBtn = new Button("Redeem");
        Button logoutBtn = new Button("Logout");

        buyBookBtn.setPrefSize(100, 30);
        redeemPointsBtn.setPrefSize(100, 30);
        logoutBtn.setPrefSize(100, 30);

        buyBookBtn.setOnAction(e -> handleBuyBooks());
        redeemPointsBtn.setOnAction(e -> handleRedeemBooks());
        logoutBtn.setOnAction(e -> primaryStage.setScene(mainPage));

        HBox buttonBox = new HBox(80, buyBookBtn, redeemPointsBtn, logoutBtn);
        buttonBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(20, statusLabel, bookTable, buttonBox);
        layout.setAlignment(Pos.CENTER);

        root.getChildren().add(layout);
    }

    public Scene getScene() {
        return scene;
    }

    private void setupBookTable() {
        bookTable.setEditable(false);
        bookTable.setPrefWidth(500);
        bookTable.setPrefHeight(300);

        TableColumn<Book, String> nameCol = new TableColumn<>("Book Name");
        TableColumn<Book, Integer> priceCol = new TableColumn<>("Price");
        TableColumn<Book, CheckBox> selectCol = new TableColumn<>("Select");

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        selectCol.setCellValueFactory(new PropertyValueFactory<>("checkBox"));

        nameCol.setPrefWidth(166);
        priceCol.setPrefWidth(166);
        selectCol.setPrefWidth(166);

        nameCol.setStyle("-fx-alignment: CENTER;");
        priceCol.setStyle("-fx-alignment: CENTER;");
        selectCol.setStyle("-fx-alignment: CENTER;");

        bookTable.getColumns().setAll(nameCol, priceCol, selectCol);
    }

    private void loadBooks() {
        ObservableList<Book> books = FXCollections.observableArrayList();
        try (Scanner scanner = new Scanner(new FileReader("books.txt"))) {
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                if (data.length == 2) {
                    books.add(new Book(data[0].trim(), Double.parseDouble(data[1].trim())));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading books.txt");
        }
        bookTable.setItems(books);
    }

    private void loadCustomerData() {
        try (Scanner scanner = new Scanner(new FileReader("customer.txt"))) {
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                if (data.length >= 3 && data[0].equals(username)) {
                    customerName = data[0];
                    points = Integer.parseInt(data[2].trim());
                    updateStatus();
                    return;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading customer.txt");
        }
        customerName = "Customer";
        points = 0;
        updateStatus();
    }

    private List<Book> getSelectedBooks() {
        List<Book> selected = new ArrayList<>();
        for (Book book : bookTable.getItems()) {
            if (book.getCheckBox().isSelected()) {
                selected.add(book);
            }
        }
        return selected;
    }

    private void handleBuyBooks() {
        List<Book> selectedBooks = getSelectedBooks();
        if (selectedBooks.isEmpty()) return;

        for (Book book : selectedBooks) {
            points += book.getPrice() * 10;
        }

        updateStatus();
        updateCustomerPoints();
        refreshAfterAction();
    }

    private void handleRedeemBooks() {
        List<Book> selectedBooks = getSelectedBooks();
        if (selectedBooks.isEmpty()) return;

        for (Book book : selectedBooks) {
            int cost = (int) (book.getPrice() * 100);
            if (points < cost) return; // Not enough points
            points -= cost;
        }

        updateStatus();
        updateCustomerPoints();
        refreshAfterAction();
    }

    private void refreshAfterAction() {
        loadBooks();
        statusLabel.setText(getStatusMessage());
    }

    private void updateCustomerPoints() {
        List<String> updatedLines = new ArrayList<>();

        try (Scanner scanner = new Scanner(new FileReader("customer.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                if (data.length >= 3 && data[0].equals(username)) {
                    line = data[0] + "," + data[1] + "," + points;
                }
                updatedLines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error updating points in customer.txt");
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter("customer.txt"))) {
            for (String line : updatedLines) {
                writer.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error writing updated customer data.");
        }
    }

    private void updateStatus() {
        status = (points >= 1000) ? new GoldState() : new SilverState();
    }

    private String getStatusMessage() {
        return "Welcome, " + customerName + ". You have " + points + " points. Your status is " + status.displayStatus();
    }

    private interface StatusState {
        String displayStatus();
    }

    private static class SilverState implements StatusState {
        public String displayStatus() {
            return "Silver";
        }
    }

    private static class GoldState implements StatusState {
        public String displayStatus() {
            return "Gold";
        }
    }
}
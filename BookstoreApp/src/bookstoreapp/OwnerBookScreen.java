package bookstoreapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class OwnerBookScreen {

    private Scene ownerBookScene;
    private TableView<Book> table = new TableView<>();
    private ObservableList<Book> data = FXCollections.observableArrayList();
    private BookStore bookStore;

    public OwnerBookScreen(Stage primaryStage, Scene mainpage) {
        bookStore = new BookStore();
        StackPane root = new StackPane();
        ownerBookScene = new Scene(root, 500, 500);

        // Title
        Label title = new Label("Manage Books");
        title.setFont(new Font("Arial", 20));

        // Table Setup
        setupTable();
        loadBooks();

        // Input fields
        TextField nameField = new TextField();
        TextField priceField = new TextField();
        Label nameLabel = new Label("Name:");
        Label priceLabel = new Label("Price:");

        HBox inputBox = new HBox(20, nameLabel, nameField, priceLabel, priceField);
        inputBox.setAlignment(Pos.CENTER);

        // Buttons
        Button add = new Button("Add");
        Button delete = new Button("Delete");
        Button back = new Button("Back");

        add.setPrefSize(100, 30);
        delete.setPrefSize(100, 30);
        back.setPrefSize(100, 30);

        add.setOnAction(e -> {
            String name = nameField.getText().trim();
            String priceText = priceField.getText().trim();
            if (!name.isEmpty() && !priceText.isEmpty()) {
                addBook(name, priceText);
                nameField.clear();
                priceField.clear();
            }
        });

        delete.setOnAction(e -> deleteBook());
        back.setOnAction(e -> primaryStage.setScene(new OwnerScreen(primaryStage, mainpage).getScene()));

        HBox buttonBox = new HBox(80, add, delete, back);
        buttonBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(20, title, table, inputBox, buttonBox);
        layout.setAlignment(Pos.CENTER);

        root.getChildren().add(layout);
    }

    public Scene getScene() {
        return ownerBookScene;
    }

    private void setupTable() {
        table.setEditable(false);

        TableColumn<Book, String> nameCol = new TableColumn<>("Name");
        TableColumn<Book, Double> priceCol = new TableColumn<>("Price");

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        nameCol.setPrefWidth(250);
        priceCol.setPrefWidth(250);
        
        table.setPrefWidth(500);
        table.setPrefHeight(300);

        nameCol.setStyle("-fx-alignment: CENTER;");
        priceCol.setStyle("-fx-alignment: CENTER;");

        table.getColumns().setAll(nameCol, priceCol);
    }

    private void loadBooks() {
        data.clear();
        bookStore.loadBooks();
        data.setAll(bookStore.getBooks());
        table.setItems(data);
    }

    private void addBook(String name, String priceText) {
        try {
            double price = Double.parseDouble(priceText);
            Book newBook = new Book(name, price);
            bookStore.addBook(newBook);
            loadBooks();
        } catch (NumberFormatException e) {
            System.out.println("Invalid price format.");
        }
    }

    private void deleteBook() {
        Book selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            bookStore.removeBook(selected);
            loadBooks();
        }
    }
}
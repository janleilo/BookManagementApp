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

public class OwnerCustomerScreen {

    private Scene ownercust;
    private TableView<Customer> table = new TableView<>();
    private ObservableList<Customer> data = FXCollections.observableArrayList();
    private CustomerStore customerStore;

    public OwnerCustomerScreen(Stage primaryStage, Scene mainpage) {
        customerStore = new CustomerStore();
        StackPane root = new StackPane();
        ownercust = new Scene(root, 500, 500);

        // Title
        Label title = new Label("Manage Customers");
        title.setFont(new Font("Arial", 20));

        // Table Setup
        setupTable();
        loadCustomers();

        // Input fields
        TextField userField = new TextField();
        TextField passField = new TextField();
        Label userLabel = new Label("Username:");
        Label passLabel = new Label("Password:");

        HBox inputBox = new HBox(20, userLabel, userField, passLabel, passField);
        inputBox.setAlignment(Pos.CENTER);

        // Buttons
        Button add = new Button("Add");
        Button delete = new Button("Delete");
        Button back = new Button("Back");

        add.setPrefSize(100, 30);
        delete.setPrefSize(100, 30);
        back.setPrefSize(100, 30);

        add.setOnAction(e -> {
            String username = userField.getText().trim();
            String password = passField.getText().trim();
            if (!username.isEmpty() && !password.isEmpty()) {
                addCustomer(username, password);
                userField.clear();
                passField.clear();
            }
        });

        delete.setOnAction(e -> deleteCustomer());
        back.setOnAction(e -> primaryStage.setScene(new OwnerScreen(primaryStage, mainpage).getScene()));

        HBox buttonBox = new HBox(80, add, delete, back);
        buttonBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(20, title, table, inputBox, buttonBox);
        layout.setAlignment(Pos.CENTER);

        root.getChildren().add(layout);
    }

    public Scene getScene() {
        return ownercust;
    }

    private void setupTable() {
        table.setEditable(false);

        TableColumn<Customer, String> userCol = new TableColumn<>("Username");
        TableColumn<Customer, String> passCol = new TableColumn<>("Password");
        TableColumn<Customer, Integer> pointsCol = new TableColumn<>("Points");

        userCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        passCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        pointsCol.setCellValueFactory(new PropertyValueFactory<>("points"));

        userCol.setPrefWidth(166);
        passCol.setPrefWidth(166);
        pointsCol.setPrefWidth(166);
        
        table.setPrefWidth(500);
        table.setPrefHeight(300);

        userCol.setStyle("-fx-alignment: CENTER;");
        passCol.setStyle("-fx-alignment: CENTER;");
        pointsCol.setStyle("-fx-alignment: CENTER;");

        table.getColumns().setAll(userCol, passCol, pointsCol);
    }

    private void loadCustomers() {
        data.clear();
        customerStore.loadCustomers();
        data.setAll(customerStore.getCustomers());
        table.setItems(data);
    }

    private void addCustomer(String username, String password) {
        Customer newCustomer = new Customer(username, password, 0);
        customerStore.addCustomer(newCustomer);
        loadCustomers();
    }

    private void deleteCustomer() {
        Customer selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            customerStore.removeCustomer(selected);
            loadCustomers();
        }
    }
}

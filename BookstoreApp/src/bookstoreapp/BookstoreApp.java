package bookstoreapp;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class BookstoreApp extends Application {
    private Scene startScreen;

    @Override
    public void start(Stage primaryStage) {
        // Labels and fields
        Label welcome = new Label("Welcome to the Bookstore App");
        Label usernameLabel = new Label("Username:");
        Label passwordLabel = new Label("Password:");
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button btLog = new Button("Login");

        // Layout setup for rows
        HBox row1 = new HBox(20, usernameLabel, usernameField);
        HBox row2 = new HBox(20, passwordLabel, passwordField);
        row1.setAlignment(Pos.CENTER); // Center rows horizontally
        row2.setAlignment(Pos.CENTER);

        // VBox for vertical stacking
        VBox layout = new VBox(15, welcome, row1, row2, btLog);
        layout.setAlignment(Pos.CENTER); // Center VBox children vertically
        layout.setPadding(new Insets(20)); // Optional padding

        // StackPane to center the VBox on the screen
        StackPane root = new StackPane(layout);
        StackPane.setAlignment(layout, Pos.CENTER); // Center VBox in StackPane

        startScreen = new Scene(root, 500, 500);

        primaryStage.setTitle("Bookstore App");
        primaryStage.setScene(startScreen);
        primaryStage.show();

        // Login button action
        btLog.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            Login login = new Login(username, password);
            int result = login.checkUserandPass(username, password);

            clearFields(usernameField, passwordField);

            switch (result) {
                case 1 -> {
                    System.out.println("Move to admin screen");
                    primaryStage.setScene(new OwnerScreen(primaryStage, startScreen).getScene());
                }
                case 2 -> {
                    System.out.println("Move to customer screen");
                    primaryStage.setScene(new CustomerScreen(primaryStage, startScreen, username).getScene());
                }
                default -> {
                    System.out.println("Invalid credentials");
                    // Optionally show an alert:
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid username or password.", ButtonType.OK);
                    alert.showAndWait();
                }
            }
        });

        primaryStage.setOnCloseRequest(e -> {
            System.out.println("Closing");
            // Add file-saving or cleanup logic here if needed
        });
    }

    private void clearFields(TextField usernameField, PasswordField passwordField) {
        usernameField.clear();
        passwordField.clear();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

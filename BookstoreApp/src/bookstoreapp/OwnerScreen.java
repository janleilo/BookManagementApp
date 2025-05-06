package bookstoreapp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OwnerScreen {
    private final Scene scene;

    public OwnerScreen(Stage primaryStage, Scene mainPage) {
        // Root container
        StackPane root = new StackPane();
        scene = new Scene(root, 500, 500);

        // Buttons
        Button booksBtn = createButton("Books");
        Button customerBtn = createButton("Customer");
        Button logoutBtn = createButton("Logout");

        // Button layout
        VBox layout = new VBox(30, booksBtn, customerBtn, logoutBtn);
        layout.setAlignment(Pos.CENTER);
        root.getChildren().add(layout);

        // Event handlers
        booksBtn.setOnAction(e -> {
            OwnerBookScreen obs = new OwnerBookScreen(primaryStage, mainPage);
            primaryStage.setScene(obs.getScene());
        });

        customerBtn.setOnAction(e -> {
            OwnerCustomerScreen ocs = new OwnerCustomerScreen(primaryStage, mainPage);
            primaryStage.setScene(ocs.getScene());
        });

        logoutBtn.setOnAction(e -> primaryStage.setScene(mainPage));
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setPrefSize(150, 50);
        button.setStyle("-fx-font-size: 18px;");
        return button;
    }

    public Scene getScene() {
        return scene;
    }
}

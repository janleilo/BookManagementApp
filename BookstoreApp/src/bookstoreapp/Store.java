/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bookstoreapp;

import javafx.scene.control.Alert;

/**
 *
 * @author janleilo
 */
public class Store {
    public void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);  // No header text for error alerts
        alert.setContentText(message);
        alert.showAndWait();
    }
    
}

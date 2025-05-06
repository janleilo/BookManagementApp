package bookstoreapp;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Login {
    private final String username;
    private final String password;

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int checkUserandPass(String username, String password) {
        // Check for admin credentials
        if (username.equals("admin") && password.equals("admin")) {
            return 1; // Admin login
        }

        // Check for customer credentials
        try (Scanner scan = new Scanner(new FileReader("customer.txt"))) {
            while (scan.hasNextLine()) {
                String[] parts = scan.nextLine().split(",");
                if (parts.length >= 2) {
                    String storedUser = parts[0].trim();
                    String storedPass = parts[1].trim();

                    if (storedUser.equals(username) && storedPass.equals(password)) {
                        return 2; // Customer login
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading customer.txt: " + e.getMessage());
        }

        return 0; // Invalid login
    }
}
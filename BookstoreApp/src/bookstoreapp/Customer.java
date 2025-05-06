package bookstoreapp;

public class Customer {
    private String username;
    private String password;
    private int points;

    public Customer(String username, String password, int points) {
        this.username = username;
        this.password = password;
        this.points = points;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return username + ", " + password + ", " + points;
    }
}

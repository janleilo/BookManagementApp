package bookstoreapp;

import javafx.scene.control.CheckBox;

public class Book {
    private String name;
    private double price;
    private CheckBox checkBox;
    
    public Book(String n, double p){
        this.name= n;
        this.price=p;
        this.checkBox = new CheckBox();
    }
    public String getName(){
        return this.name;
    }
    public double getPrice(){
        return price;
    }
    
    public CheckBox getCheckBox() { 
    return checkBox; 
    }
    
    @Override
    public String toString(){
        return this.name + " - $"+ this.price;
    }

}

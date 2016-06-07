/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joieiscool;
/**
 *
 * @author Joie
 */
public class Book {
    private String author, title, isbn;
    private double price;

    public Book(String author, String title, String isbn, double price)
    {
        this.author = author;
        this.title = title;
        this.isbn = isbn;
        this.price = price; 
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public double getPrice() {
        return price;
    }
    
    @Override
    public String toString()
    {
        return String.format("%s: %s, ISBN:%s, $%.2f", author, title, isbn, price); 
    }
}

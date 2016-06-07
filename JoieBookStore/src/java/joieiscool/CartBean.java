/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joieiscool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
/**
 *
 * @author Joie
 */
@ManagedBean(name ="cartBean")
@SessionScoped
public class CartBean implements Serializable{
    private static Map<String, Book> booksMap = new HashMap<>();
    static {
        booksMap.put("0136053068", new Book("Deitel", "Java How to Program 8e", "0136053068", 99.00));
        booksMap.put("0132575663", new Book("Deitel", "Java How to Program 9e", "0132575663", 120.00));
        booksMap.put("032459951X", new Book("Farrell", "Java Programming 5e", "032459951X", 89.00));
        booksMap.put("013605332X", new Book("Deitel", "Visual C# 2008 How to Program 3e", "013605332X", 112.00));
        booksMap.put("013605305X", new Book("Deitel", "Visual Basic 2008 How to Program 3e", "013605305X", 109.80));
    }
    private static Map<String, Integer> shoppingCart = new HashMap<>(); 
    private static List<String> selectedBooks = new ArrayList<>(); 
    private String selectedChange;
    private double sum = 0.0;

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }
    
    public String getSelectedChange() {
        return selectedChange;
    }

    public void setSelectedChange(String selectedChange) {
        this.selectedChange = selectedChange;
    }

    public void setBooksMap(Map<String, Book> booksMap) {
        CartBean.booksMap = booksMap;
    }

    public void setShoppingCart(Map<String, Integer> shoppingCart) {
        CartBean.shoppingCart = shoppingCart;
    }

    public void setSelectedBooks(List<String> selectedBooks) {
        CartBean.selectedBooks = selectedBooks;
    }

    public Map<String, Book> getBooksMap() {
        return booksMap;
    }

    public Map<String, Integer> getShoppingCart() {
        return shoppingCart;
    }

    public List<String> getSelectedBooks() {
        selectedBooks.clear();
        return selectedBooks;
    }
    
    public List<SelectItem> getSelectableBooks()
    {
        List<SelectItem> ans = new ArrayList<>();
        booksMap.values().stream().forEach((b) -> {
            ans.add(new SelectItem(b.getIsbn(), b.toString()));
        });
        return ans;
    }
    
    public void addToCart()
    {
        for ( int i = 0; i < selectedBooks.size(); i++ )
        {
            String isbn = "";
            int quan;
            try
            {
                isbn = selectedBooks.get(i);
                quan = shoppingCart.get(isbn);
                quan++;
            }
            catch (NullPointerException e){
                quan = 1;
            }
            shoppingCart.put(isbn, quan);
        }
    }
    
    public List<SelectItem> getAddedBooks()
    {
        List <SelectItem> ans = new ArrayList<>();
        Set <String> set = shoppingCart.keySet();
        set.stream().forEach((String s) -> {
            String ss = booksMap.get(s).toString() + " * ";
            ss += shoppingCart.get(s) + " = $" + (shoppingCart.get(s)*booksMap.get(s).getPrice());
            ans.add(new SelectItem(shoppingCart.get(s),ss));
        });
        return ans;
    }
    
    public int getItemCount()
    {
        int ans = 0;
        ans = shoppingCart.values().stream().map((s) -> s).reduce(ans, Integer::sum);
        return ans;
    }
    
    public double getSubTotal()
    {
       double ans = 0.0;
       for ( String key : shoppingCart.keySet() )
       {
           int numOfBooks = shoppingCart.get(key);
           double price = (double)booksMap.get(key).getPrice();
           ans += (double)numOfBooks * price;
       }
       return ans;
    }
    
    public String getSubTotalString()
    {   
        return String.format("%.2f", getSubTotal());    
    }   
   
    public void more()
    {
        int w = shoppingCart.get(selectedChange);
        w++;
        shoppingCart.put(selectedChange,w);
    }
    
    public void less()
    {
        int w = shoppingCart.get(selectedChange);
        w--;
        if ( w == 0 )
        {
            shoppingCart.remove(selectedChange);
            selectedChange = "";
        }
        else
            shoppingCart.put(selectedChange, w);
    }  
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joiebookstore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
/**
 *
 * @author Joie
 */
@ManagedBean(name ="cartBean")
@SessionScoped
@ViewScoped
public class CartBean implements Serializable{
    private Map<String, Book> booksMap = new HashMap<>();
    private Map<String, Integer> shoppingCart = new HashMap<>(); 
    private List<String> selectedBooks = new ArrayList<>(); 
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
        this.booksMap = booksMap;
    }

    public void setShoppingCart(Map<String, Integer> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public void setSelectedBooks(List<String> selectedBooks) {
        this.selectedBooks = selectedBooks;
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
        for (Book b : booksMap.values() )
        {
            ans.add(new SelectItem(b.getIsbn(), b.toString()));
        }
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
        for (String s : set) 
        {
            String ss = booksMap.get(s).toString() + " * ";
            ss += shoppingCart.get(s) + " = $" + (shoppingCart.get(s)*booksMap.get(s).getPrice());
            ans.add(new SelectItem(shoppingCart.get(s),ss));
        }
        return ans;
    }
    
    public int getItemCount()
    {
        int ans = 0;
        for ( Integer i : shoppingCart.values() )
        {
            ans += i;
        }
        return 0;
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
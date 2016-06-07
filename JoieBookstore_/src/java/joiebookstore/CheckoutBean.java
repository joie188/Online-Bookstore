/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joiebookstore;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.xml.bind.JAXB;

@ManagedBean(name ="checkoutBean")
@SessionScoped
public class CheckoutBean implements Serializable {
     private String name, address, cardNumber;
     private double subtotal, tax, shipping, total; 

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getShipping() {
        return shipping;
    }

    public void setShipping(double shipping) {
        this.shipping = shipping;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    private void calculateTotal() {
        CartBean cartBean = new CartBean();
        double ans = 0.0;
        for ( String key : cartBean.getShoppingCart().keySet() )
        {
            int numOfBooks = cartBean.getShoppingCart().get(key);
            double price = (double)cartBean.getBooksMap().get(key).getPrice();
            ans += (double)numOfBooks * price;
        }
        setTotal(ans);
    }
    
    public String getPayDetail(){
        calculateTotal();
        return "Subtotal from my Shopping Card:\t" + round(getSubtotal(),2) +
                "\nSales tax at 7.75%:\t\t" + round((getSubtotal()*getTax()),2) + 
                "\nShipping and Handling:\t\t" + getShipping() + "\n\n" +
                "Total amount to pay:\t\t" + round(getTotal(),2);
    }
    
    public String getResult(){
        if(name != null && address != null && cardNumber != null)
        {
            calculateTotal();
            String url = "http://localhost:8080/JoiePaymentService/webresources/payment/" + 
                 name.replaceAll("\\s", "%20") + "/" + cardNumber + "/" + String.format("%.2f", total);
            return JAXB.unmarshal(url, String.class );
        }
        return " Click button to make payment";
    }
    
    public double round(double v, int numofplaces) {
        BigDecimal bd = new BigDecimal(v);
        bd = bd.setScale(numofplaces, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}

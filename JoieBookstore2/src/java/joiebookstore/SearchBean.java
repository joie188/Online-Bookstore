package joiebookstore;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.sql.rowset.CachedRowSet;

@ManagedBean (name = "searchBean")
@SessionScoped
public class SearchBean implements Serializable
{
   @Resource( name="jdbc/bookstore" )
   private DataSource dataSource;
   private CachedRowSet rowSet;
   private long first = 0;
   private long size = 0;
   private String searchText;
   @ManagedProperty(value="#{cartBean}")
   private CartBean cartBean;

   public void setCartBean(CartBean cartBean)
   {
       this.cartBean = cartBean;
   }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public long getFirst() {
        return first;
    }

    public void setFirst(long first) {
        this.first = first;
    }
   
   public ResultSet getBooks() throws SQLException
   {
       if(dataSource == null)
       {
           throw new SQLException("Unable to obtain DataSource");
       }
       Connection connection = dataSource.getConnection();
       if(connection == null)
       {
           throw new SQLException("Unable to connect to DataSource");
       }
       try {
           PreparedStatement statement;
           if(searchText != null && !searchText.isEmpty())
           {
                    statement = connection.prepareStatement( "SELECT * FROM BOOKS WHERE AUTHOR LIKE ? OR TITLE LIKE ? OR ISBN LIKE ?");
                    statement.setString(1,  "%" + searchText + "%");
                    statement.setString(2,  "%" + searchText + "%");
                    statement.setString(3,  "%" + searchText + "%");
           }
           else
               statement = connection.prepareStatement("SELECT * FROM BOOKS");
           rowSet = new com.sun.rowset.CachedRowSetImpl();
           rowSet.populate(statement.executeQuery());
           size = rowSet.size();
           first = (first < size? first: 0);
           return rowSet;
       }
       finally {
           connection.close();
       }
   }
   
   public void previous()
   {
        first = (first > 8? first - 8: 0);
   }
   
   public void next()
   {
        first = (first < size - 8? first + 8: first);
   }
   
   public boolean getDisablePrev()
   {
       if ( first == 0 )
           return true;
       return false;
   }
   
   public boolean getDisableNext()
   {
       if (first > 20)
           return true;
       return false;
   }
   
   public void populateBooksMap(ResultSet rowSet) throws SQLException
   {
       Map<String, Book> booksMap = new HashMap<>(); 
       while (rowSet.next())
       {
           Book b = new Book("","","",0.0);
           booksMap.put("", b);
       }
       cartBean.setBooksMap(booksMap);
       rowSet.beforeFirst();
   }
   
    public void addToCart(String isbn)
   {
       Map<String, Integer> shoppingCart = cartBean.getShoppingCart();
       Integer obj = shoppingCart.get(isbn);
       int qrt = (obj == null? 0: obj);
       shoppingCart.put(isbn, qrt + 1);
   }
}

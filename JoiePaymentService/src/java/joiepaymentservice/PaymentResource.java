package joiepaymentservice;
import java.io.StringWriter;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.xml.bind.JAXB;

@Path("payment")
public class PaymentResource 
{
    @GET
    @Path( "{name}/{account}/{amount}")
    @Produces("application/xml")
    
    public String pay(@PathParam("name") String name, 
                      @PathParam("account") String account, 
                      @PathParam("amount") double amount) 
    {
        String result = "";
        try {
        int a = Integer.parseInt(account);
        int whatthe = (int) ((Math.random() * 100000) + 1);
        if (a % 3 == 0)
            result += "Payment Finished: Your confirmation number is " + whatthe; 
        else
            result += "Payment Denied: Invalid account number";
        }
        catch (NumberFormatException e)
        {
            result += "Payment Denied: Card Number must be digits only";
        }
        StringWriter writer = new StringWriter();
        JAXB.marshal( result, writer );
        return writer.toString(); 
    }
}

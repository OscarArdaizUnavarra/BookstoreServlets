package servlets;


import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import model.cart.*;

/**
 * An HTTP Servlet that responds to the GET method of the
 * HTTP protocol.  It returns a form to the user that gathers data.
 * The form POSTs to another servlet.
 */
public class CashierServlet extends HttpServlet { 

    public void doGet (HttpServletRequest request,
                       HttpServletResponse response)
   throws ServletException, IOException
    {
        // Get the user's session and shopping cart
        HttpSession session = request.getSession(true);
        ShoppingCart cart =
            (ShoppingCart)session.getAttribute("examples.bookstore.cart");
        
        // If the user has no cart, create a new one
        if (cart == null) {
            cart = new ShoppingCart();
            session.setAttribute("examples.bookstore.cart", cart);
   			}

   // set content-type header before accessing Writer
        response.setContentType("text/html");
   PrintWriter out = response.getWriter();

   // then write the data of the response
        out.println("<html>" +
                    "<head><title> Cashier </title></head>" );
                              
        // Get the dispatcher; it gets the banner to the user
        RequestDispatcher dispatcher =
               getServletContext().getRequestDispatcher(
                  "/banner");
                                       
            if (dispatcher != null)
               dispatcher.include(request, response);

        // Determine the total price of the user's books
        double total = cart.getTotal();

        // Print out the total and the form for the user
        out.println("<p>Your total purchase amount is: " +
                    "<strong>" + Currency.format(total, request.getLocale()) + "</strong>" +
                    "<p>To purchase the items in your shopping cart," +
                    " please provide us with the following information:" +

                    "<form action=\"" +
                    response.encodeURL("/bookstore/receipt") +
                    "\" method=\"post\">" +

                    "<table>" +
                    "<tr>" +
                    "<td><strong>Name:</strong></td>" +
                    "<td><input type=\"text\" name=\"cardname\"" +
                    "value=\"Gwen Canigetit\" size=\"19\"></td>" +
                    "</tr>" +

                    "<tr>" +
                    "<td><strong>Credit Card Number:</strong></td>" +
                    "<td>" +
                    "<input type=\"text\" name=\"cardnum\" " +
                    "value=\"xxxx xxxx xxxx xxxx\" size=\"19\"></td>" +
                    "</tr>" +

                    "<tr>" +
                    "<td></td>" +
                    "<td><input type=\"submit\"" +
                    "value=\"Submit Information\"></td>" +
                    "</tr>" +

                    "</table>" +
                    "</form>" +
                    "</body>" +
                    "</html>");
        out.close();
    }

    public String getServletInfo() {
        return "The Cashier servlet takes the user's name and " +
               "credit card number so that the user can buy the books.";

    }
}

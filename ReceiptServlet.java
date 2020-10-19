package servlets;


import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * An HTTP servlet that responds to the POST method of the HTTP protocol.
 * It clears the shopping cart, thanks the user for the order,
 * and resets the page to the Bookstore's main page.
 */

public class ReceiptServlet extends HttpServlet { 

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException
    {
        // Get the user's session and shopping cart
        HttpSession session = request.getSession(true);
        // Payment received -- invalidate the session
        session.invalidate();
        
        // set content type header before accessing the Writer
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // then write the response
        out.println("<html>" +
                    "<head><title> Receipt </title>" +
                    "<meta http-equiv=\"refresh\" content=\"4; url=" +
                    "http://" + request.getHeader("Host") +
                    "/bookstore/enter;\">" +
                    "</head>");
                    
        // Get the dispatcher; it gets the banner to the user
        RequestDispatcher dispatcher =
               getServletContext().getRequestDispatcher(
                  "/banner");
                                       
            if (dispatcher != null)
               dispatcher.include(request, response);
        
        out.println("<h3>Thank you for purchasing your books from us " +
                    request.getParameter("cardname") + "." +
                    "<p>Please shop with us again soon!</h3>" +                   
                    "<p><i>This page automatically resets.</i>" +
                    "</body></html>");
        out.close();
    }

    public String getServletInfo() {
        return "The Receipt servlet clears the shopping cart, " +
               "thanks the user for the order, and resets the " +
               "page to the BookStore's main page.";
    }
}


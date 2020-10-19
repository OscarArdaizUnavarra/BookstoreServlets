package servlets;


import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import model.database.*;
import model.cart.*;

/**
 * This is a simple example of an HTTP Servlet.  It responds to the GET
 * method of the HTTP protocol. 
 */
public class BookDetailServlet extends HttpServlet {

   private BookDB bookDB;    	
    public void init() throws ServletException {
        bookDB =
            (BookDB)getServletContext().getAttribute("examples.bookstore.database");

        if (bookDB == null) {
        		bookDB = BookDB.instance();
                  getServletContext().setAttribute("examples.bookstore.database", bookDB);
        }
    }
      public void destroy() {
          bookDB = null;
      }
      
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

   // set content-type header before accessing the Writer
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

   // then write the response
        out.println("<html>" +
                    "<head><title>Book Description</title></head>");
                              
        // Get the dispatcher; it gets the banner to the user
        RequestDispatcher dispatcher =
               getServletContext().getRequestDispatcher(
                  "/banner");
                                       
            if (dispatcher != null)
               dispatcher.include(request, response);
                                       
        //Get the identifier of the book to display
        String bookId = request.getParameter("bookId");
        if (bookId != null) {

            // and the information about the book
            BookDetails bd = bookDB.getBookDetails(bookId);

            //Print out the information obtained
            out.println("<h2>" + bd.getTitle() + "</h2>" +

                        "&nbsp; By <em>" + bd.getFirstName() + " " +
                        bd.getSurname() + "</em> &nbsp; &nbsp; " +
                        "(" + bd.getYear() + ")<br> &nbsp; <br>" +

                        "<h4>Here's what the critics say: </h4>" +
                        "<blockquote>" + bd.getDescription() +
                        "</blockquote>" +

                        "<h4>Our price: " + Currency.format(bd.getPrice(), request.getLocale()) + "</h4>" +

                        "<p><strong><a href=\"" +
                        response.encodeURL("/bookstore/catalog?Add=" + bookId) +
                        "\"> Add To Your Shopping Cart</a>&nbsp;&nbsp;&nbsp;" +
                        "<a href=\"" + 
                                    response.encodeURL("/bookstore/catalog") + "\">Continue Shopping</a></p></strong>");
                                    

        }
        out.println("</body></html>");
        out.close();
    }

    public String getServletInfo() {
        return "The BookDetail servlet returns information about" +
               "any book that is available from the bookstore.";
    }

}

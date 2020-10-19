package servlets;


import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import model.database.*;
import model.cart.*;

/**
 * This is a simple example of an HTTP Servlet.  It responds to the GET
 * method of the HTTP protocol.
 */
public class CatalogServlet extends HttpServlet { 
      private BookDB bookDB;
   
      @Override
    public void init() throws ServletException {
        bookDB =
            (BookDB)getServletContext().getAttribute("examples.bookstore.database");

        if (bookDB == null) {
        		bookDB = BookDB.instance();
                  getServletContext().setAttribute("examples.bookstore.database", bookDB);
        }		
    }

      @Override
      public void destroy() {
            bookDB = null;
         }
      

      @Override
    public void doGet (HttpServletRequest request,
                       HttpServletResponse response)
   throws ServletException, IOException
    {
        // Get the user's session and shopping cart
   HttpSession session = request.getSession(true);
   ShoppingCart cart = (ShoppingCart)session.getAttribute("examples.bookstore.cart");

        // If the user has no cart, create a new one
        if (cart == null) {
            cart = new ShoppingCart();
            session.setAttribute("examples.bookstore.cart", cart);
        }

   // set content-type header before accessing the Writer
        response.setContentType("text/html");
          // then write the data of the response
          try (PrintWriter out = response.getWriter()) {
              // then write the data of the response
              out.println("<html>" +
                      "<head><title> Book Catalog </title></head>");
              
              // Get the dispatcher; it gets the banner to the user
              RequestDispatcher dispatcher =
                      getServletContext().getRequestDispatcher(
                              "/banner");
              
              if (dispatcher != null)
                  dispatcher.include(request, response);
              
              
              //Information on the books is from the database through its front end
              
              // Additions to the shopping cart
              String bookId = request.getParameter("Add");
              if (bookId != null) {
                  BookDetails book = bookDB.getBookDetails(bookId);
                  cart.add(bookId, book);
                  out.println("<p><h3>" +
                          "<font color=\"#ff0000\">" +
                          "You just added <i>" + book.getTitle() + "</i> "+
                          "to your shopping cart.</font></h3>");
              }
              
              //Give the option of checking cart or checking out if cart not empty
              if (cart.getNumberOfItems() > 0) {
                  out.println("<p><strong><a href=\"" +
                          response.encodeURL("/bookstore/showcart") +
                          "\"> Check Shopping Cart</a>&nbsp;&nbsp;&nbsp;" +
                          "<a href=\"" +
                          response.encodeURL("/bookstore/cashier") +
                          "\"> Buy Your Books</a>" +
                          "</p></strong>");
              }
              
              // Always prompt the user to buy more -- get and show the catalog
              out.println("<br> &nbsp;" +
                      "<h3>Please choose from our selections:</h3>" +
                      "<center> <table>");
              
              Collection c = bookDB.getBooks();
              Iterator i = c.iterator();
              while (i.hasNext()) {
                  BookDetails book = (BookDetails)i.next();
                  bookId = book.getBookId();
                  //Print out info on each book in its own two rows
                  out.println("<tr>" +
                          
                          "<td bgcolor=\"#ffffaa\">" +
                          "<a href=\"" +
                          response.encodeURL("/bookstore/bookdetails?bookId=" + bookId) +
                          "\"> <strong>" + book.getTitle() +
                          "&nbsp; </strong></a></td>" +
                          
                          "<td bgcolor=\"#ffffaa\" rowspan=2>" +
                          Currency.format(book.getPrice(), request.getLocale()) +
                          "&nbsp; </td>" +
                          
                          "<td bgcolor=\"#ffffaa\" rowspan=2>" +
                          "<a href=\"" +
                          response.encodeURL("/bookstore/catalog?Add=" + bookId)
                          + "\"> &nbsp; Add to Cart &nbsp;</a></td></tr>" +
                          
                          "<tr>" +
                          "<td bgcolor=\"#ffffff\">" +
                          "&nbsp; &nbsp; by <em> " + book.getFirstName() +
                          " " + book.getSurname() + "</em></td></tr>");
              }
              
              out.println("</table></center></body></html>");
          }
    }

      @Override
    public String getServletInfo() {
        return "The Catalog servlet adds books to the user's " +
               "shopping cart and prints the catalog.";

    }
}

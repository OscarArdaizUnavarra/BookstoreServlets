package servlets;


import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;


/**
 * This is a simple example of an HTTP Servlet.  It responds to the GET
 * method of the HTTP protocol.
 */
public class BannerServlet extends HttpServlet { 

    public void doGet (HttpServletRequest request,
                       HttpServletResponse response)
   throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();

   // then write the data of the response
        out.println("<body  bgcolor=\"#ffffff\">" +
                    "<center>" +
                    "<hr> <br> &nbsp;" +
                    "<h1>" +
                    "<font size=\"+3\" color=\"red\">Duke's </font>" +
                    "<font size=\"+3\" color=\"purple\">Bookstore</font>" +
                    "</h1>" +
                    "</center>" +
                    "<br> &nbsp; <hr> <br> ");
		}
    public void doPost (HttpServletRequest request,
                       HttpServletResponse response)
   throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();

   // then write the data of the response
        out.println("<body  bgcolor=\"#ffffff\">" +
                    "<center>" +
                    "<hr> <br> &nbsp;" +
                    "<h1>" +
                    "<font size=\"+4\" color=\"red\">Duke's </font>" +
                    "<font size=\"+3\" color=\"purple\">Bookstore</font>" +
                    "</h1>" +
                    "</center>" +
                    "<br> &nbsp; <hr> <br> ");
		}
}

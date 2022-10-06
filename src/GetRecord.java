import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
// Fetch data
/**
 * Servlet implementation class demo3
 */
@WebServlet("/GetRecord")
public class GetRecord extends HttpServlet {
    private static final long serialVersionUID = 1 ;

    String dns = "ec2-44-211-67-109.compute-1.amazonaws.com";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetRecord() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        String sql;
        Connection connection = null;
        Statement statement = null;
        PreparedStatement statement1 = null;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        String keyword = request.getParameter("keyword");
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        String title = "Fetch Book Details";
        String docType =
            "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";

        out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor = \"##CCCCFF\">\n" + //
            "<h1 align = \"center\">" + title + "</h1>\n");


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return;
        }

        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + dns + ":3306/mydb", "matt", "password");
        } catch (SQLException e2) {
            // TODO Auto-generated catch block
            out.println("Connection Failed!:\n" + e2.getMessage());
            out.print(e2.getStackTrace());
        }
        System.out.println("SUCCESS!!!! You made it, take control     your database now!");
        System.out.println("Creating statement...");

        sql = "SELECT * FROM books WHERE author=?";
        try {

            statement1 = connection.prepareStatement(sql);
            String theAuthor = keyword;
            statement1.setString(1, theAuthor);
     
        } catch (SQLException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        } 
        try {

            rs = statement1.executeQuery();
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        out.println("<table border=1 width=50% height=30%>");
        out.println("<tr><th><b>Author</b></th><th>Title</th><th>Genre</th><th>ISBN</th><th>Summary</th>");
        try {
            while (rs.next()) {
                //Retrieve by column name
            	String author = rs.getString("author");
                String t = rs.getString("title");
                String genre = rs.getString("genre");
                String isbn = rs.getString("isbn");
                String summary = rs.getString("summary");
           
                out.println("<tr><td><b>" + author + "</b></td><td>" + t + "</td><td>" + genre + "</td><td>" +  isbn + "</td><td>" + summary + "</td></tr>");
            }
            out.println("</body></html>");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }


    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
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
@WebServlet("/InsertRecord")
public class InsertRecord extends HttpServlet {
    private static final long serialVersionUID = 1;

    String dns = "ec2-44-211-67-109.compute-1.amazonaws.com";


    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertRecord() {
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

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        String title = "Database Insertion";
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
            out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return;
        }


        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + dns + ":3306/mydb", "matt", "password");
        } catch (SQLException e2) {
            // TODO Auto-generated catch block
            out.print("Connection Failed!:\n" + e2.getMessage());
            out.print(e2.getStackTrace());
        }
        System.out.println("SUCCESS!!!! You made it, take control     your database now!");
        System.out.println("Creating statement...");

        String booktitle = request.getParameter("title");
        String author = request.getParameter("author");
        String genre = request.getParameter("genre");
        String isbn = request.getParameter("isbn");
        String summary = request.getParameter("summary");
        
        sql = "insert into books (title,author,genre,isbn,summary) values(?,?,?,?,?);";

        try {

            statement1 = connection.prepareStatement(sql);
            statement1.setString(1, booktitle);
            statement1.setString(2, author);
            statement1.setString(3, genre);
            statement1.setString(4, isbn);
            statement1.setString(5, summary);

        } catch (SQLException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }

        try {
            statement1.executeUpdate();
            out.println("Record Successfuly Inserted Into Database");
            out.println("</body></html>");
        } catch (SQLException e1) {
            out.println("There was an error when attempting to insert database record: " + e1.getMessage());
            out.println("</body></html>");
            e1.printStackTrace();
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
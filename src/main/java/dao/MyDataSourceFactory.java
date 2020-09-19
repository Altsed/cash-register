package dao;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/1")
public class MyDataSourceFactory extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Context ctx = null;
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
//            ctx = new InitialContext();
//            DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/desk_db");
//
//            con = ds.getConnection();
//            stmt = con.createStatement();
//            PrintWriter out = response.getWriter();
//            response.setContentType("text/html");
//            out.print("Connection established!");
//            DriverManager.registerDriver(new org.postgresql.Driver ());
//            Class.forName("org.postgresql.Driver");
//            System.out.println("Driver registred");
            InitialContext cxt = new InitialContext();
            if ( cxt == null ) {
                throw new Exception("Uh oh -- no context!");
            }

            Context initContext = new InitialContext();
            Context envContext = (Context)initContext.lookup("java:comp/env");
            DataSource ds = (DataSource)envContext.lookup("jdbc/desk_db");
           // DataSource ds = (DataSource) cxt.lookup( "jdbc/desk_db" );

            con = ds.getConnection();

            PrintWriter out = response.getWriter();
            response.setContentType("text/html");
            out.print("Connection established!");

            if ( ds == null ) {
                throw new Exception("Data source not found!");
            }



        }catch(NamingException e){
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
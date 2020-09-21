package controller;
import dao.PoolConnectionBuilder;
import dao.PoolConnectionBuilderPostresqlImpl;

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
    PoolConnectionBuilder connectionBuilder = new PoolConnectionBuilderPostresqlImpl();


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }




}
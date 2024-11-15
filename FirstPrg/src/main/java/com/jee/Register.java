package com.jee;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.StructuredTaskScope.ShutdownOnSuccess;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Register extends HttpServlet
{
	private Connection con;
	private PreparedStatement pstmt;
	private int status;
	public static final String Insert_Query = "insert into `user` (name, email, mobile, password, address) values(?,?,?,?,?)";
	
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{

		String Password = req.getParameter("password");
		String ConfirmPassword = req.getParameter("confirmPassword");
		
		PrintWriter pw = resp.getWriter();
			 
	    if(Password.equals(ConfirmPassword)==true)
	    {
	    	
			String url = "jdbc:mysql://localhost:3306/octjdbc";
			String dbuser = "root";
			String dbpassword = "root";

			String name = req.getParameter("name");
			String email = req.getParameter("email");
			String mobile = req.getParameter("mobile");
			String password = req.getParameter("password");
			String address = req.getParameter("address");
	         
	    	try 
	    	{
				Class.forName("com.mysql.cj.jdbc.Driver");
				con = DriverManager.getConnection(url, dbuser, dbpassword);
				
				pstmt = con.prepareStatement(Insert_Query);
				pstmt.setString(1, name);
				pstmt.setString(2, email);
				pstmt.setString(3, mobile);
				pstmt.setString(4, password);
				pstmt.setString(5, address);
				
				status = pstmt.executeUpdate();
				
				if (status != 0) 
				{
	                resp.sendRedirect("success.html");
	            } else {
	                resp.sendRedirect("failure.html");
	            }
			} 
	    	catch (SQLException e) 
	    	{
				e.printStackTrace();
			}
	    	catch (ClassNotFoundException e) 
	    	{
				e.printStackTrace();
			}
			finally {
				try {
					if (pstmt != null) {
						pstmt.close();
					}
					if (con != null) {
						con.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
	    }
	    else
	    {
	    	 resp.sendRedirect("pwdmismatch.html");
	    }
	}
}


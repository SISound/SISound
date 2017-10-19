package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import model.db.UserDao;

/**
 * Servlet implementation class loginServlet
 */

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		
		try {
			boolean exist=UserDao.getInstance().loginConfirmation(username, password);
			if(exist){
				User u=UserDao.getInstance().getUser(username);
				request.getSession().setAttribute("user", u);
				request.getRequestDispatcher("main.jsp").forward(request, response);
			}
			else{
				request.setAttribute("error", "User does not exist!");
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

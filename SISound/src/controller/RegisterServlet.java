package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import model.db.UserDao;

@WebServlet("/register")
@MultipartConfig
public class RegisterServlet extends HttpServlet {
	
   	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   		response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
   		
   		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		String email = request.getParameter("email");
		System.out.println(password);
		System.out.println(password2);
		
		if(!password.equals(password2)){
			request.setAttribute("error", "passwords missmatch");
			request.getRequestDispatcher("register.jsp").forward(request, response);
			return;
		}
		
		try {
			if(!UserDao.getInstance().usernameExists(username) && !UserDao.getInstance().emailExists(email)){
				User u = new User(username, password, email);
				UserDao.getInstance().insertUser(u);
				request.getSession().setAttribute("user", u);
				request.getSession().setAttribute("logged", true);
				request.getRequestDispatcher("main.jsp").forward(request, response);
			} 
			else if(UserDao.getInstance().usernameExists(username)){
				request.setAttribute("error", "username is taken");
				request.getRequestDispatcher("register.jsp").forward(request, response);
				return;
			} 
			else {
				request.setAttribute("error", "e-mail already in use");
				request.getRequestDispatcher("register.jsp").forward(request, response);
				return;
			}
		} catch (SQLException e) {
			request.setAttribute("error", "database problem : " + e.getMessage());
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
	}

}

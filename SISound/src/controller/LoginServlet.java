package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Song;
import model.User;
import model.db.GenresDao;
import model.db.SongDao;
import model.db.UserDao;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		try {
			boolean exist=UserDao.getInstance().existsUser(username, password);
			if(exist){
				User u=UserDao.getInstance().getUser(username);
				request.getSession().setAttribute("user", u);
				request.getSession().setAttribute("logged", true);
				ServletContext application = getServletConfig().getServletContext();
				synchronized (application) {
					if(application.getAttribute("songs") == null){
						TreeSet<Song> songs = SongDao.getInstance().getAllSongs();
						application.setAttribute("songs", songs);
					}
					if(application.getAttribute("genres") == null){
						Map genres=GenresDao.getInstance().getAllGenres();
						application.setAttribute("genres", genres);
					}
				}
				request.getRequestDispatcher("main.jsp").forward(request, response);
			}
			else{
				request.setAttribute("error", "User does not exist!");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			request.setAttribute("error", "database problem : " + e.getMessage());
			//request.getRequestDispatcher("index.jsp").forward(request, response);
			response.getWriter().append(request.getAttribute("error").toString());
		}
	}

}

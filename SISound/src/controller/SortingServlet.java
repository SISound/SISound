package controller;

import java.awt.Desktop.Action;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Actions;
import model.Song;
import model.db.GenresDao;
import model.db.SongDao;

/**
 * Servlet implementation class SortingServlet
 */
@WebServlet("/sortingServlet")
public class SortingServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ServletContext application = getServletConfig().getServletContext();
			synchronized(application){
				if(request.getParameter("sorter").toString().equals("likes")){
					TreeSet<Song> songs =  new TreeSet<>((o1, o2)->o1.getActions().get(Actions.LIKE).size()-o1.getActions().get(Actions.LIKE).size());
					songs = SongDao.getInstance().getAllSongs();
					application.setAttribute("songs", songs);
				}
				if(request.getParameter("sorter").toString().equals("shares")){
					TreeSet<Song> songs =  new TreeSet<>((o1, o2)->o1.getActions().get(Actions.SHARE).size()-o1.getActions().get(Actions.SHARE).size());
					songs = SongDao.getInstance().getAllSongs();
					application.setAttribute("songs", songs);
				}
				if(application.getAttribute("genres") == null){
					Map genres=GenresDao.getInstance().getAllGenres();
					application.setAttribute("genres", genres);
				}
			}
			request.getRequestDispatcher("main.jsp").forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

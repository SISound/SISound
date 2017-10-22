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
@WebServlet("/SortingServlet")
public class SortingServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			ServletContext application = getServletConfig().getServletContext();
			synchronized(application){
				if(request.getParameter("sorter").toString().equals("likes")){
					response.getWriter().append("likes");
				}
				if(request.getParameter("sorter").toString().equals("shares")){
					response.getWriter().append("shares");
				}
				if(request.getParameter("sorter").toString().equals("listenings")){
					response.getWriter().append("listenings");
				}
			}
	}
}

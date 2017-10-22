package controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.TreeSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Song;
import model.User;
import model.db.SongDao;

@WebServlet("/SongServlet")
public class SongServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
		System.out.println(request.getParameter("songName")!=null);
		try {
			TreeSet<Song> songs=SongDao.getInstance().getAllSongs();
			for (Song song : songs) {
				File myFile = new File(UploadSongServlet.SONG_URL+song.getUrl());
				
				try (OutputStream out = response.getOutputStream()) {
				    Path path = myFile.toPath();
				    Files.copy(path, out);
				    out.flush();
				} catch (IOException e) {
				   e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
}

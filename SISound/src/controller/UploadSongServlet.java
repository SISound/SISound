package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import model.Song;
import model.User;
import model.db.GenresDao;
import model.db.SongDao;

/**
 * Servlet implementation class UploadSongServlet
 */
@WebServlet("/UploadSongServlet")
@MultipartConfig
public class UploadSongServlet extends HttpServlet {
	
	public static final String SONG_URL = "C:\\Users\\Workstation\\Desktop\\temp\\";
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("user")==null){
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
		else{
			ServletContext application = getServletConfig().getServletContext();
			User u=(User) request.getAttribute("user");
			Part songPart = request.getPart("song");
			InputStream fis = songPart.getInputStream();
			String fileName = Paths.get(songPart.getSubmittedFileName()).getFileName().toString();
			File myFile = new File(SONG_URL + "_" + u.getUsername() +"_" + fileName + ".mp3");
			if(!myFile.exists()){
				myFile.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(myFile);
			int b = fis.read();
			while(b != -1){
				fos.write(b);
				b = fis.read();
			}
			fis.close();
			fos.close();
			String songUrl = u.getUsername()+".mp3";
			Song song=new Song("new song", u, "ge", songUrl, LocalDateTime.now());
			try {
				SongDao.getInstance().uploadSong(song);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			synchronized (application) {
				TreeSet<Song> songs= (TreeSet<Song>) application.getAttribute("songs");
				songs.add(song);
				application.setAttribute("songs", songs);
			}
			request.getRequestDispatcher("main.jsp").forward(request, response);
		}
	}

}

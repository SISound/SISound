import java.sql.SQLException;
import java.time.LocalDateTime;

import model.Song;
import model.User;
import model.db.GenresDao;
import model.db.SongDao;
import model.db.UserDao;

public class Demo {

	public static void main(String[] args) {
		
		User u = null;
		try {
			u = UserDao.getInstance().getUser("user");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Song song = new Song("song24", u, "RNB", "asdf", LocalDateTime.now()); 
		
		try {
			System.out.println(u.getUserID());
			System.out.println(SongDao.getInstance().getSongsForUser(u));
		} catch (SQLException e) {
			System.out.println("ne staa");
		}
		
	}
}

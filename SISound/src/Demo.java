import java.sql.SQLException;

import model.User;
import model.db.UserDao;

public class Demo {

	public static void main(String[] args) {
		
		
		User u = new User("user", "123", "us@e.r");
		
		try {
			UserDao.getInstance().insertUser(u);
		} catch (SQLException e) {
			System.out.println("ne staa");
		}
		
	}
}

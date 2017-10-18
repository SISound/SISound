package model.db;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.LinkedHashSet;

import com.google.common.hash.Hashing;

import model.User;

public class TestingDemo {

	public static void main(String[] args) {
		User u=new User(1, null, null, "moni", "moni", "moni", null, null, null, null, null);
		
		try {
			LinkedHashSet<User> followers= UserDao.getInstance().getFollowers(u);
			for (User user : followers) {
				System.out.println(user.getUsername());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

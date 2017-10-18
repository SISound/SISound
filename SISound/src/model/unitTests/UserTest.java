package model.unitTests;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import model.User;
import model.db.UserDao;

public class UserTest {

//	@Test
//	public void gettingIdTest() throws SQLException {
//		User u = new User("iliya0", "iliya123", "ili0@y.a");
//		UserDao.getInstance().insertUser(u);
//		assertNotEquals(u.getUserID(), );
//	}
	
	@Test
	public void registerTest() throws SQLException {
		User u = new User("iliya", "iliya123", "ili@y.a");
		UserDao.getInstance().insertUser(u);
		assertTrue(UserDao.getInstance().loginConfirmation("iliya", "iliya123"));
		assertFalse(UserDao.getInstance().loginConfirmation("iliya", "iliya1234"));
		assertFalse(UserDao.getInstance().loginConfirmation("iliya1", "iliya123"));
	}
	
	@Test
	public void getTest() throws SQLException {
		User u = new User("iliya1", "iliya123", "ili@y.a1");
		UserDao.getInstance().insertUser(u);
		assertEquals(UserDao.getInstance().getUser("iliya1"), u);	
	}
	
	@Test
	public void searchByUsernameTest() throws SQLException {
		User u = new User("iliya2", "iliya123", "ili@y.a2");
		UserDao.getInstance().insertUser(u);
		assertEquals(UserDao.getInstance().getUser("iliya1"), u);	
	}
	
	@Test
	public void followTest() throws SQLException {
		User u1 = new User("iliya3", "iliya123", "ili@y.a3");
		User u2 = new User("iliya4", "iliya123", "ili@y.a4");
		User u3 = new User("iliya5", "iliya123", "ili@y.a5");
		
		UserDao.getInstance().insertUser(u1);
		UserDao.getInstance().insertUser(u2);
		UserDao.getInstance().insertUser(u3);
		
		assertEquals(UserDao.getInstance().getFollowers(u1).size(), 0);
		
		UserDao.getInstance().followUser(u2.getUserID(), u1.getUserID());
		assertEquals(UserDao.getInstance().getFollowers(u1).size(), 1);
		assertTrue(UserDao.getInstance().getFollowers(u1).contains(UserDao.getInstance().getUser("iliya4")));
		
		UserDao.getInstance().followUser(u3.getUserID(), u1.getUserID());
		assertEquals(UserDao.getInstance().getFollowers(u1).size(), 2);
		assertTrue(UserDao.getInstance().getFollowers(u1).contains(UserDao.getInstance().getUser("iliya4")));
		assertTrue(UserDao.getInstance().getFollowers(u1).contains(UserDao.getInstance().getUser("iliya5")));
	}
	
	@Test
	public void unfollowTest() throws SQLException {
		User u1 = new User("iliya6", "iliya123", "ili@y.a6");
		User u2 = new User("iliya7", "iliya123", "ili@y.a7");
		
		UserDao.getInstance().insertUser(u1);
		UserDao.getInstance().insertUser(u2);

		UserDao.getInstance().followUser(u2.getUserID(), u1.getUserID());
		assertEquals(UserDao.getInstance().getFollowers(u1).size(), 1);
	
		UserDao.getInstance().unfollowUser(u2.getUserID(), u1.getUserID());
		assertEquals(UserDao.getInstance().getFollowers(u1).size(), 0);
	}
	
	@Test
	public void loginTest() throws SQLException {
		User u = new User("iliya8", "iliya123", "ili8@y.a");
		UserDao.getInstance().insertUser(u);
		assertTrue(UserDao.getInstance().emailExists("ili8@y.a"));
		assertTrue(UserDao.getInstance().usernameExists("iliya8"));
//		assertTrue(UserDao.getInstance().passwordCheck("iliya123", "iliya8"));
	}
	
	@Test
	public void changeTest() throws SQLException {
		User u = new User("iliya9", "iliya123", "ili9@y.a");
		UserDao.getInstance().insertUser(u);
		
		UserDao.getInstance().emailChange("newili9@y.a", "iliya9");
		assertEquals(UserDao.getInstance().getUser("iliya9").getEmail(), "newili9@y.a");
		
		UserDao.getInstance().passwordChange("newiliya123", "iliya9");
		assertTrue(UserDao.getInstance().passwordCheck("newiliya123", "iliya9"));
	}
	
	@Test
	public void editProfileTest() throws SQLException {
		User u = new User("iliya10", "iliya123", "ili10@y.a");
		UserDao.getInstance().insertUser(u);
		
		u.setBio("bio");
		u.setCity("Lovech");
		u.setCountry("Bulgaria");

		UserDao.getInstance().editProfile(u);
		
		assertEquals(UserDao.getInstance().getUser("iliya10"), u);
	}
}

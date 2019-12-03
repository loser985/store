package dao;

import java.sql.SQLException;

import domain.User;

public interface UserDao {
	public void register(User user) throws SQLException;

	public User getByCode(String code) throws SQLException;

	public void update(User user) throws SQLException;

	public User login(String username, String password) throws SQLException;

	public User userByUsername(String username) throws  Exception;
}

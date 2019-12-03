package service;


import domain.User;

public interface UserService {
	public void register(User user) throws  Exception;

	public User active(String code) throws  Exception;

	public User login(String username, String password)throws  Exception;

	public User userByUsername(String username)throws  Exception;
}

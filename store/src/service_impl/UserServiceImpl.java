package service_impl;



import java.sql.SQLException;

import dao.UserDao;
import dao_impl.UserDaoImpl;
import domain.User;
import service.UserService;
import 工具.BeanFactory;
import 工具.MailUtils;

public class UserServiceImpl implements UserService{

	//注册
	public void register(User user) throws Exception {
		
		UserDao ud=(UserDao) BeanFactory.getBean("UserDao");
		ud.register(user);
		String emailMsg="欢迎您注册成为我们的一员，<a href='http://localhost:8080/store/user?method=active&code="+user.getCode()+"'>点此激活</a>";
		MailUtils.sendMail(user.getEmail(), emailMsg);
	}

	//激活
	public User active(String code) throws SQLException {
		UserDao ud=(UserDao) BeanFactory.getBean("UserDao");//(UserDao) BeanFactory.getBean("UserDao");
		User user=ud.getByCode(code);
		if(user==null){
			return null;
		}
		user.setState(1);
		ud.update(user);
		return user;
	}

	/**
	 * 登录
	 */
	public User login(String username, String password) throws Exception {
		UserDao ud =(UserDao) BeanFactory.getBean("UserDao");
		User user=ud.login(username,password);
		return user;
	}

	/**
	 * 通过用户名获取user
	 */
	public User userByUsername(String username) throws Exception {
		UserDao ud=(UserDao) BeanFactory.getBean("UserDao");
		User user=ud.userByUsername(username);
		
		return user;
	}


}

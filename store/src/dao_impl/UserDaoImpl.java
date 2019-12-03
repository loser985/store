package dao_impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import dao.UserDao;
import domain.User;
import 工具.jdbcUtils;

public class UserDaoImpl implements UserDao{

	//注册
	public void register(User user) throws SQLException {
		QueryRunner qr=new QueryRunner(jdbcUtils.getDataSource());
		String sql="insert into user values(?,?,?,?,?,?,?,?,?,?)";
		qr.update(sql, user.getUid(),user.getUsername(),user.getPassword(),
				user.getName(),user.getEmail(),user.getTelephone(),
				user.getBirthday(),user.getSex(),user.getState(),
				user.getCode()
				);
	}

	//根据激活码获取用户
	public User getByCode(String code) throws SQLException {
		QueryRunner qr=new QueryRunner(jdbcUtils.getDataSource());
		String sql="select * from user where code=? limit 1";
		
		return qr.query(sql, new BeanHandler<>(User.class),code);
	}

	//修改用户状态
	public void update(User user) throws SQLException {
		QueryRunner qr=new QueryRunner(jdbcUtils.getDataSource());
		String sql="update user set username=?,password=?,name=?,email=?,telephone=?,birthday=?,sex=?,state=?,code=? where uid=?";
		qr.update(sql,user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),user.getTelephone(),user.getBirthday(),user.getSex(),
				user.getState(),null,user.getUid());
	}

	/**
	 * 登陆
	 * @throws SQLException 
	 */
	public User login(String username, String password) throws SQLException {
		QueryRunner qr=new QueryRunner(jdbcUtils.getDataSource());
		String sql="select * from user where username =? and password=? limit 1";
		return qr.query(sql, new BeanHandler<>(User.class),username,password);
	}

	/**
	 * 通过用户名获取User
	 */
	public User userByUsername(String username) throws Exception {
		QueryRunner qr=new QueryRunner(jdbcUtils.getDataSource());
		String sql="select * from user where username =? limit 1";
		return qr.query(sql,new BeanHandler<>(User.class),username);
	}

}

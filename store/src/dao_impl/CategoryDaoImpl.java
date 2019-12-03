package dao_impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import dao.CategoryDao;
import domain.Category;
import 工具.UUIDUtils;
import 工具.jdbcUtils;

public class CategoryDaoImpl implements CategoryDao{

	@Override
	public List<Category> findAll() throws Exception {
		QueryRunner qr =new QueryRunner(jdbcUtils.getDataSource());
		String sql="select * from category";
		return qr.query(sql, new BeanListHandler<>(Category.class));
	}

	/**
	 * 删除分类
	 */
	@Override
	public void delete(String cid) throws Exception {
		QueryRunner qr=new QueryRunner(jdbcUtils.getDataSource());
		String sql="delete from category where cid=?";
		qr.update(sql,cid);
	}
	/**
	 * 添加分类
	 */

	@Override
	public void add(Category c) throws Exception {
		QueryRunner qr=new QueryRunner(jdbcUtils.getDataSource());
		String sql="insert into category values(?,?)";
		qr.update(sql,c.getCid(),c.getCname());
	}
	/**
	 * 通过id获取分类
	 */
	@Override
	public Category getById(String id) throws Exception {
		QueryRunner qr=new QueryRunner(jdbcUtils.getDataSource());
		String sql="select * from category where cid=?";
		return qr.query(sql, new BeanHandler<>(Category.class),id);
	}
	/**
	 * 修改分类
	 */
	@Override
	public void update(Category c) throws Exception {
		QueryRunner qr=new QueryRunner(jdbcUtils.getDataSource());
		String sql="update category set cname=? where cid=?";
		qr.update(sql, c.getCname(),c.getCid());
	}


}

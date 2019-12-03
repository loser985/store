package dao_impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import dao.ProductDao;
import domain.Product;
import servlet.productServlet;
import 工具.jdbcUtils;

public class ProductDaoImpl implements ProductDao {

	/**
	 * 查找最新商品
	 */
	public List<Product> findNew() throws Exception {
		QueryRunner qr=new QueryRunner(jdbcUtils.getDataSource());
		String sql="select * from product where pflag=0 order by pdate desc limit 9";
		return qr.query(sql, new BeanListHandler<>(Product.class));
	}

	/**
	 * 查找热门商品
	 */
	public List<Product> findHot() throws Exception {
		QueryRunner qr=new QueryRunner(jdbcUtils.getDataSource());
		String sql="select * from product where is_hot = 1 and pflag=0 order by pdate desc limit 9";
		return qr.query(sql,new BeanListHandler<>(Product.class));
	}

	/**
	 * 通过id查找商品详情
	 */
	public Product getById(String id) throws Exception {
		QueryRunner qr=new QueryRunner(jdbcUtils.getDataSource());
		String sql="select * from product where pid = ? limit 1";
		return qr.query(sql, new BeanHandler<>(Product.class),id);
	}
	/**
	 * 按类别分页查询商品
	 */
	public List<Product> findByPage(String cid, int currPage, int pageSize) throws Exception {
		QueryRunner qr=new QueryRunner(jdbcUtils.getDataSource());
		String sql="select * from product where cid=? and pflag=0 limit ?,?";
		return qr.query(sql, new BeanListHandler<>(Product.class),cid,(currPage-1)*pageSize,pageSize);
	}

	/**
	 * 按类别查询页数
	 */
	public int getTotalCount(String cid) throws Exception {
		QueryRunner qr=new QueryRunner(jdbcUtils.getDataSource());
		String sql="select count(*) from product where cid=? and pflag=0";
		return ((Long)qr.query(sql, new ScalarHandler(), cid)).intValue();
	}
	/**
	 * 查询所有商品
	 */
	@Override
	public List<Product> findByPage(int currPage, int pageSize,String flag) throws Exception {
		QueryRunner qr=new QueryRunner(jdbcUtils.getDataSource());
		String sql="select * from product where pflag=? limit ?,?";
		return qr.query(sql, new BeanListHandler<>(Product.class),flag,(currPage-1)*pageSize,pageSize);
	}
	/**
	 * 查询总页数
	 */
	@Override
	public int getTotalCount2(String flag) throws Exception {
		QueryRunner qr=new QueryRunner(jdbcUtils.getDataSource());
		String sql="select count(*) from product where pflag=?";
		return ((Long)qr.query(sql, new ScalarHandler(),flag)).intValue();
	}
	/**
	 * 添加商品
	 */
	@Override
	public void add(Product p) throws Exception {
		QueryRunner qr=new QueryRunner(jdbcUtils.getDataSource());
		String sql="insert into product values(?,?,?,?,?,?,?,?,?,?)";
		qr.update(sql,p.getPid(),p.getPname(),p.getMarket_price(),p.getShop_price(),
				p.getPimage(),p.getPdate(),p.getIs_hot(),p.getPdesc(),p.getPflag()
				,p.getCategory().getCid());
	}
	/**
	 * 修改商品上下架状态
	 */
	@Override
	public void updateFlag(String pid, int flag) throws Exception {
		QueryRunner qr = new QueryRunner(jdbcUtils.getDataSource());
		String sql="update product set pflag=? where pid=?";
		qr.update(sql,flag,pid);
	}
	/**
	 * 编辑商品
	 */
	@Override
	public void edit(Product p) throws Exception {
		QueryRunner qr = new QueryRunner(jdbcUtils.getDataSource());
		String sql="update product set pname=?,market_price=?,shop_price=?,pimage=?,pdate=?,is_hot=?,pdesc=?,cid=? where pid=?";
		qr.update(sql,p.getPname(),p.getMarket_price(),p.getShop_price(),p.getPimage(),p.getPdate(),p.getIs_hot(),p.getPdesc(),p.getCategory().getCid(),p.getPid());
	}
	/**
	 * 获取商品分类cid
	 */
	@Override
	public String getCid(String pid) throws Exception {
		QueryRunner qr = new QueryRunner(jdbcUtils.getDataSource());
		String sql="select cid from product where pid=?";
		return (String) qr.query(sql, new ScalarHandler(),pid);
	}

}

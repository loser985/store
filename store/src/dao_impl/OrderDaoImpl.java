package dao_impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.sun.mail.imap.protocol.UID;

import dao.OrderDao;
import domain.Order;
import domain.OrderItem;
import domain.Product;
import 工具.jdbcUtils;

public class OrderDaoImpl implements OrderDao{
	/**
	 * 添加订单
	 */
	@Override
	public void add(Order or) throws Exception {
		QueryRunner qr=new QueryRunner();
		String sql="insert into orders values(?,?,?,?,?,?,?,?)";
		qr.update(jdbcUtils.getConnection(), sql, or.getOid(),or.getOrdertime(),or.getTotal(),
				or.getState(),or.getAddress(),or.getName(),
				or.getTelephone(),or.getUser().getUid());
		
	}
	/**
	 * 添加订单项
	 */
	@Override
	public void addItem(OrderItem i) throws Exception {
		QueryRunner qr=new QueryRunner();
		String sql="insert into orderitem values(?,?,?,?,?)";
		qr.update(jdbcUtils.getConnection(), sql, i.getItemid(),i.getCount(),i.getSubtotal(),
				i.getProduct().getPid(),i.getOrder().getOid());
	}
	/**
	 * 分页查询所有订单
	 */
	@Override
	public List<Order> findAllByPage(String uid, int currPage, int pageSize) throws Exception {
		QueryRunner qr = new QueryRunner(jdbcUtils.getDataSource());
		String sql="select * from orders where uid=? order by ordertime desc  limit ?,?";
		List<Order> list = qr.query(sql, new BeanListHandler<>(Order.class),uid,(currPage-1)*pageSize,pageSize);
		
		sql="select * from orderitem oi,product p where oi.pid=p.pid and oi.oid=?";
		for (Order i:list) {
			List<Map<String, Object>> mlist = qr.query(sql, new MapListHandler(), i.getOid());
			for (Map<String, Object> m : mlist) {
				OrderItem oi=new OrderItem();
				BeanUtils.populate(oi, m);
				Product p=new Product();
				BeanUtils.populate(p, m);
				oi.setProduct(p);
				i.getItems().add(oi);
			}
		}
		return list;
	}
	/**
	 * 查询订单总页数
	 */
	@Override
	public int getTotalCount(String uid) throws Exception {
		QueryRunner qr = new QueryRunner(jdbcUtils.getDataSource());
		String sql="select count(*) from orders where uid = ?";
		return ((Long)qr.query(sql, new ScalarHandler(),uid)).intValue();
	}
	/**
	 * 查询单个订单详情
	 */
	@Override
	public Order getById(String oid) throws Exception {
		QueryRunner qr = new QueryRunner(jdbcUtils.getDataSource());
		String sql="select * from orders where oid=?";
		Order order = qr.query(sql, new BeanHandler<>(Order.class),oid);
		sql="select * from orderitem oi,product p where oi.pid=p.pid and oi.oid=?";
		List<Map<String, Object>> mlist = qr.query(sql, new MapListHandler(), oid);
		for (Map<String, Object> m : mlist) {
			OrderItem oi=new OrderItem();
			BeanUtils.populate(oi, m);
			Product p=new Product();
			BeanUtils.populate(p, m);
			oi.setProduct(p);
			order.getItems().add(oi);
		}
		
		return order;
	}
	/**
	 * 修改订单
	 */
	@Override
	public void update(Order order) throws Exception {
		QueryRunner qr = new QueryRunner(jdbcUtils.getDataSource());
		String sql="update orders set state=?,address=?,name=?,telephone=? where oid=? ";
		qr.update(sql, order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getOid());
	}
	/**
	 * 通过订单状态查询订单
	 */
	@Override
	public List<Order> findAllByState(String state) throws Exception {
		QueryRunner qr = new QueryRunner(jdbcUtils.getDataSource());
		String sql="select * from orders where 1=1";
		if(state!=null&&state.trim().length()>0){
			sql+=" and state=? order by ordertime desc";
			return qr.query(sql, new BeanListHandler<>(Order.class),state);
		}
		sql+=" order by ordertime desc";
		return qr.query(sql, new BeanListHandler<>(Order.class));
	}
	
}

package service_impl;

import java.sql.SQLException;
import java.util.List;

import dao.OrderDao;
import domain.Order;
import domain.OrderItem;
import domain.pageBean;
import service.OrderService;
import 工具.BeanFactory;
import 工具.jdbcUtils;

public class OrderServiceImpl implements OrderService{
	/**
	 * 添加订单
	 */
	@Override
	public void add(Order or) throws Exception {
		try {
			jdbcUtils.startTransaction();
			OrderDao od=(OrderDao)BeanFactory.getBean("OrderDao");
			//向订单表添加一个数据
			od.add(or);
			//向订单项表添加许多数据
			for(OrderItem i:or.getItems()){
				od.addItem(i);
			}
			jdbcUtils.commitAndClose();
		} catch (SQLException e) {
			e.printStackTrace();
			jdbcUtils.rollbackAndClose();
			throw e;
		}
	}
	/**
	 * 分页查询所有订单
	 */
	@Override
	public pageBean<Order> findAllByPage(String uid, int currPage, int pageSize) throws Exception {
		OrderDao od=(OrderDao) BeanFactory.getBean("OrderDao");
		List<Order> list=od.findAllByPage(uid,currPage,pageSize);
		int totalCount=od.getTotalCount(uid);
		
		return new pageBean<Order>(list, currPage, pageSize, totalCount);
	}
	/**
	 * 获取单个订单详情
	 */
	@Override
	public Order getById(String oid) throws Exception {
		OrderDao od=(OrderDao) BeanFactory.getBean("OrderDao");
		return od.getById(oid);
	}
	
	/**
	 * 修改订单
	 */
	@Override
	public void update(Order order) throws Exception {
		OrderDao od=(OrderDao) BeanFactory.getBean("OrderDao");
		od.update(order);
	}
	/**
	 * 通过订单状态查询订单
	 */
	@Override
	public List<Order> findAllByState(String state) throws Exception {
		OrderDao od=(OrderDao) BeanFactory.getBean("OrderDao");
		
		return od.findAllByState(state);
	}

}

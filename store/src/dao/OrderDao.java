package dao;

import java.util.List;

import domain.Order;
import domain.OrderItem;

public interface OrderDao {

	void add(Order or) throws Exception;

	void addItem(OrderItem i)throws Exception;

	List<Order> findAllByPage(String uid, int currPage, int pageSize)throws Exception;

	int getTotalCount(String uid)throws Exception;

	Order getById(String oid)throws Exception;

	void update(Order order)throws Exception;

	List<Order> findAllByState(String state)throws Exception;

}

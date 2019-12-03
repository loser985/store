package service;

import java.util.List;

import domain.Order;
import domain.pageBean;

public interface OrderService {

	void add(Order or)throws Exception;

	pageBean<Order> findAllByPage(String uid, int currPage, int pageSize)throws Exception;

	Order getById(String oid)throws Exception;

	void update(Order order)throws Exception;

	List<Order> findAllByState(String state)throws Exception;

}

package service;

import java.util.List;

import domain.Product;
import domain.pageBean;

public interface ProductService {

	List<Product> findNew() throws Exception;

	List<Product> findHot() throws Exception;

	Product getById(String id) throws Exception;

	pageBean<Product> findByPage(String cid, int currPage, int pageSize) throws Exception;

	pageBean<Product> findByPage(int currPage, int pageSize,String flag)throws Exception;

	void add(Product p)throws Exception;

	void updateFlag(String pid, String flag)throws Exception;

	void edit(Product p)throws Exception;

	String getCid(String pid)throws Exception;

}

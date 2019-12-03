package dao;

import java.util.List;

import domain.Product;

public interface ProductDao {

	List<Product> findNew() throws Exception;

	List<Product> findHot() throws Exception;

	Product getById(String id) throws Exception;

	List<Product> findByPage(String cid, int currPage, int pageSize) throws Exception;

	int getTotalCount(String cid) throws Exception;

	List<Product> findByPage(int currPage, int pageSize,String flag)throws Exception;

	int getTotalCount2(String flag)throws Exception;

	void add(Product p)throws Exception;

	void updateFlag(String pid, int flag)throws Exception;

	void edit(Product p)throws Exception;

	String getCid(String pid)throws Exception;

}

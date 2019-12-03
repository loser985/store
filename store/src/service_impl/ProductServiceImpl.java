package service_impl;

import java.util.List;

import dao.ProductDao;
import dao_impl.ProductDaoImpl;
import domain.Product;
import domain.pageBean;
import service.ProductService;
import 工具.BeanFactory;

public class ProductServiceImpl implements ProductService {

	/**
	 * 查找最新商品
	 */
	public List<Product> findNew() throws Exception {
		ProductDao pd=(ProductDao) BeanFactory.getBean("ProductDao");
		return pd.findNew();
	}

	/**
	 * 查找热门商品
	 */
	public List<Product> findHot() throws Exception {
		ProductDao pd=(ProductDao) BeanFactory.getBean("ProductDao");
		return pd.findHot();
	}

	/**
	 * 通过id查找商品详情
	 */
	public Product getById(String id) throws Exception {
		ProductDao pd= (ProductDao) BeanFactory.getBean("ProductDao");//(ProductDao) BeanFactory.getBean("ProductDao");
		return pd.getById(id);
	}
	
	/**
	 * 按类别分页查询商品
	 */
	public pageBean<Product> findByPage(String cid, int currPage, int pageSize) throws Exception {
		ProductDao pd=(ProductDao) BeanFactory.getBean("ProductDao");
		List<Product> list=pd.findByPage(cid,currPage,pageSize);
		int totalCount=pd.getTotalCount(cid);
		return new pageBean<Product>(list, currPage, pageSize, totalCount);
	}
	/**
	 * 分页查询商品
	 * @return 
	 */
	@Override
	public pageBean<Product> findByPage(int currPage, int pageSize,String flag) throws Exception {
		ProductDao pd=(ProductDao) BeanFactory.getBean("ProductDao");
		List<Product> list=pd.findByPage(currPage,pageSize,flag);
		int totalCount=pd.getTotalCount2(flag);
		return new pageBean<Product>(list, currPage, pageSize, totalCount);
	}
	/**
	 * 添加商品
	 */
	@Override
	public void add(Product p) throws Exception {
		ProductDao pd=(ProductDao) BeanFactory.getBean("ProductDao");
		pd.add(p);
	}
	/**
	 * 修改商品上下架标志
	 */
	@Override
	public void updateFlag(String pid, String flag) throws Exception {
		ProductDao pd=(ProductDao) BeanFactory.getBean("ProductDao");
		int f=Integer.parseInt(flag);
		if(f==0){
			f=1;
		}else{
			f=0;
		}
		pd.updateFlag(pid,f);		
	}
	/**
	 * 编辑商品
	 */
	@Override
	public void edit(Product p) throws Exception {
		ProductDao pd=(ProductDao) BeanFactory.getBean("ProductDao");
		pd.edit(p);
	}
	/**
	 * 获取商品分类cid
	 */
	@Override
	public String getCid(String pid) throws Exception {
		ProductDao pd=(ProductDao) BeanFactory.getBean("ProductDao");
		return pd.getCid(pid);
	}

}

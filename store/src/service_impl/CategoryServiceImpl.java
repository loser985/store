package service_impl;

import java.util.List;

import dao.CategoryDao;
import dao_impl.CategoryDaoImpl;
import domain.Category;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import service.CategoryService;
import 工具.BeanFactory;

public class CategoryServiceImpl implements CategoryService {

	@SuppressWarnings("unchecked")
	public List<Category> findAll() throws Exception {
		
		//1.创建缓存管理
		CacheManager cm = CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
		
		//2.获取指定的缓存
		Cache cache = cm.getCache("categoryCache");
		
		//3.通过缓存获取数据 将cache看成一个map即可
		Element element = cache.get("clist");
		
		List<Category> list=null;
		//4.判断数据
		if(element==null){
			//没有缓存则去数据库中查找
			CategoryDao cd= (CategoryDao) BeanFactory.getBean("CategoryDao");//(CategoryDao) BeanFactory.getBean("CategoryDao");
			list=cd.findAll();
			//将list放入缓存
			cache.put(new Element("clist", list));
			System.out.println("缓存中没有数据，从数据库中获取数据....");
		}else {
			list=(List<Category>) element.getObjectValue();
		}
		return list;
	}
	/**
	 * 删除分类
	 */
	@Override
	public void delete(String cid) throws Exception {
		CategoryDao cd=(CategoryDao) BeanFactory.getBean("CategoryDao");
		cd.delete(cid);
		CacheManager cm=CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
		Cache cache=cm.getCache("categoryCache");
		cache.remove("clist");
	}
	/**
	 * 添加分类
	 */
	@Override
	public void add(Category c) throws Exception {
		CategoryDao cd=(CategoryDao) BeanFactory.getBean("CategoryDao");
		cd.add(c);
		CacheManager cm=CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
		Cache cache=cm.getCache("categoryCache");
		cache.remove("clist");
	}
	/**
	 * 通过id获取分类
	 */
	@Override
	public Category getById(String id) throws Exception {
		CategoryDao cd=(CategoryDao) BeanFactory.getBean("CategoryDao");
		return cd.getById(id);
	}
	/**
	 * 修改分类
	 */
	@Override
	public void update(Category c) throws Exception {
		CategoryDao cd=(CategoryDao) BeanFactory.getBean("CategoryDao");
		cd.update(c);
		CacheManager cm=CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
		Cache cache=cm.getCache("categoryCache");
		cache.remove("clist");
	}
	

}

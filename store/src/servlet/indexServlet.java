package servlet;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Category;
import domain.Product;
import service.CategoryService;
import service.ProductService;
import service_impl.CategoryServiceImpl;
import service_impl.ProductServiceImpl;
import 工具.BeanFactory;

@WebServlet(urlPatterns={"/index"})
public class indexServlet extends baseServlet {

	/**
	 * 查找商品信息跳转到首页
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String index(HttpServletRequest request, HttpServletResponse response){
		ProductService ps=(ProductService) BeanFactory.getBean("ProductService");
		List<Product> newList = null;
		List<Product> hotList = null;
		try {
			newList = ps.findNew();
			hotList=ps.findHot();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		request.setAttribute("nlist", newList);
		request.setAttribute("hlist", hotList);
		
		return "/jsp/index.jsp";
	}
		
}

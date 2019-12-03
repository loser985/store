package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Category;
import service.CategoryService;
import service_impl.CategoryServiceImpl;
import 工具.BeanFactory;
import 工具.JsonUtil;

@WebServlet(urlPatterns={"/category"})
public class categoryServlet extends baseServlet {

	/**
	 * 查询所有分类
	 */
	private static final long serialVersionUID = -165722393957299635L;

	public String findAll(HttpServletRequest request, HttpServletResponse response) throws Exception{
		CategoryService cs=(CategoryService) BeanFactory.getBean("CategoryService");
		List<Category> list=cs.findAll();
		String json=JsonUtil.list2json(list);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(json);
		return null;
	}
	
	

}

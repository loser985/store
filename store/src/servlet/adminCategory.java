package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Category;
import service.CategoryService;
import 工具.BeanFactory;
import 工具.UUIDUtils;

@WebServlet(urlPatterns={"/adminCategory"})
public class adminCategory extends baseServlet {
	/**
	 * 查询所有分类
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String findAll(HttpServletRequest request ,HttpServletResponse response ) throws Exception{
		CategoryService cs=(CategoryService) BeanFactory.getBean("CategoryService");
		List<Category> list = cs.findAll();
		request.setAttribute("list", list);
		return "/admin/category/list.jsp";
	}
	/**
	 * 跳转添加页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String addUI(HttpServletRequest request,HttpServletResponse response) throws Exception{
		return "/admin/category/add.jsp";
	}
	/**
	 * 添加分类
	 * @throws Exception 
	 */
	public String add(HttpServletRequest request,HttpServletResponse response ) throws Exception{
		Category c=new Category();
		c.setCid(UUIDUtils.getCode());
		c.setCname(request.getParameter("cname"));
		
		CategoryService cs=(CategoryService) BeanFactory.getBean("CategoryService");
		cs.add(c);
		response.sendRedirect(request.getContextPath()+"/adminCategory?method=findAll");
		return null;
	}
	/**
	 * 删除分类
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public String delete(HttpServletRequest request,HttpServletResponse response ) throws Exception{
		String cid=request.getParameter("cid");
		CategoryService cs=(CategoryService) BeanFactory.getBean("CategoryService");
		
		cs.delete(cid);
		response.sendRedirect(request.getContextPath()+"/adminCategory?method=findAll");
		return null;
	}
	/**
	 * 通过Id获取分类
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public String getById(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id=request.getParameter("cid");
		CategoryService cs=(CategoryService) BeanFactory.getBean("CategoryService");
		Category c= cs.getById(id);
		request.setAttribute("bean", c);
		return "/admin/category/edit.jsp";
	}
	public String update(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Category c=new Category();
		c.setCid(request.getParameter("cid"));
		c.setCname(request.getParameter("cname"));
		CategoryService cs=(CategoryService) BeanFactory.getBean("CategoryService");
		cs.update(c);
		response.sendRedirect(request.getContextPath()+"/adminCategory?method=findAll");
		return null;
	}
}

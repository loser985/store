package servlet;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Product;
import domain.pageBean;
import service.ProductService;
import service_impl.ProductServiceImpl;
import 工具.BeanFactory;
import 工具.cookUtils;

@WebServlet(urlPatterns={"/product"})
public class productServlet extends baseServlet{
	/**
	 * 通过id获取单个商品详情
	 */
	private static final long serialVersionUID = 1L;

	public String getById(HttpServletRequest request,HttpServletResponse response){
		String id=request.getParameter("pid");
		ProductService ps=(ProductService) BeanFactory.getBean("ProductService");//(ProductService) BeanFactory.getBean("ProductService");
		Product bean = null;
		try {
			bean = ps.getById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//记录浏览记录
		String ids = null;
		Cookie c=cookUtils.getCookieByName("ids", request.getCookies());
		if(c!=null){
			ids = c.getValue();
			String[] arr= ids.split("-");
			List<String> alist=Arrays.asList(arr);
			LinkedList<String> list=new LinkedList<String>(alist);
			if(list.contains(id)){
				list.remove(id);
				list.addFirst(id);
			}else {
				if(list.size()>2){
					list.removeLast();
					list.addFirst(id);
				}else {
					list.addFirst(id);
				}
			}
			ids="";
			for(String i:list){
				ids+=i+"-";
			}
			ids=ids.substring(0, ids.length()-1);
		}else {
			ids=id;
		}
		c=new Cookie("ids", ids);
		c.setMaxAge(3600);
		c.setPath(request.getContextPath()+"/");
		response.addCookie(c);
		
		request.setAttribute("bean", bean);
		return "/jsp/product_info.jsp";
	}
	/**
	 * 分页查询数据
	 * @param request
	 * @param response
	 * @return
	 */
	public String findByPage(HttpServletRequest request,HttpServletResponse response){
		String cid=request.getParameter("cid");
		int currPage=Integer.parseInt(request.getParameter("currPage"));
		int pageSize=12;
		ProductService ps=(ProductService) BeanFactory.getBean("ProductService");
		pageBean<Product> pbean=null;
		try {
			pbean=ps.findByPage(cid,currPage,pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("pageBean", pbean);
		return "/jsp/product_list.jsp";
	}

}

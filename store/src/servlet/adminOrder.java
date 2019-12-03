package servlet;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Order;
import domain.OrderItem;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import service.OrderService;
import 工具.BeanFactory;
import 工具.JsonUtil;

@WebServlet(urlPatterns={"/adminOrder"})
public class adminOrder extends baseServlet{
	/**
	 * 通过状态获取订单
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String findAllByState(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String state=request.getParameter("state");
		OrderService os=(OrderService) BeanFactory.getBean("OrderService");
		List<Order> list=os.findAllByState(state);
		request.setAttribute("list", list);
		
		return "/admin/order/list.jsp";
	}
	/**
	 * 获取订单详情
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getDetailByOid(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		String oid=request.getParameter("oid");
		OrderService os=(OrderService) BeanFactory.getBean("OrderService");
		List<OrderItem> items = os.getById(oid).getItems();
		JsonConfig configJson = JsonUtil.configJson(new String[]{"class","itemid","order"});
		JSONArray fromObject = JSONArray.fromObject(items, configJson);
		response.getWriter().print(fromObject);
		return null;
	}
	public String updateState(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String oid=request.getParameter("oid");
		String state=request.getParameter("state");
		OrderService os=(OrderService) BeanFactory.getBean("OrderService");
		Order o = os.getById(oid);
		o.setState(Integer.parseInt(state));
		os.update(o);
		response.sendRedirect(request.getContextPath()+"/adminOrder?method=findAllByState&state=1");
		return null;
	}
}

package servlet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;

import org.ietf.jgss.Oid;

import domain.Cart;
import domain.CartItem;
import domain.Order;
import domain.OrderItem;
import domain.User;
import domain.pageBean;
import service.OrderService;
import 工具.BeanFactory;
import 工具.PaymentUtil;
import 工具.UUIDUtils;

import javax.servlet.http.HttpServletRequest;

@WebServlet(urlPatterns={"/order"})
public class orderServlet extends baseServlet{
	/**
	 * 提交订单
	 * @throws Exception 
	 */
	public String add(HttpServletRequest request,HttpServletResponse response) throws Exception{
		User user = (User) request.getSession().getAttribute("user");
		if(user==null){
			request.setAttribute("msg", "请先登录~");
			return "/jsp/msg.jsp";
		}
		Order or=new Order();
		or.setOid(UUIDUtils.getId());
		Date d=new Date();
		DateFormat df=DateFormat.getDateInstance(DateFormat.FULL);
		df=DateFormat.getDateTimeInstance(DateFormat.LONG,DateFormat.LONG);
		df=new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		or.setOrdertime(df.format(d));
		Cart cart=(Cart) request.getSession().getAttribute("cart");
		or.setTotal(cart.getTotal());
		or.setUser(user);
		for(CartItem i:cart.getItems()){
			OrderItem oi=new OrderItem();
			oi.setItemid(UUIDUtils.getId());
			oi.setCount(i.getCount());
			oi.setSubtotal(i.getSubtotal());
			oi.setProduct(i.getProduct());
			oi.setOrder(or);
			or.getItems().add(oi);
		}
		OrderService os=(OrderService) BeanFactory.getBean("OrderService");
		os.add(or);
		request.getSession().setAttribute("bean", or);
		request.getSession().removeAttribute("cart");
		return "/jsp/order_info.jsp";
	}
	/**
	 * 分页查询订单信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public String findAllByPage(HttpServletRequest request,HttpServletResponse response) throws Exception{
		int currPage=Integer.parseInt(request.getParameter("currPage"));
		int pageSize=3;
		User user=(User) request.getSession().getAttribute("user");
		if(user==null){
			request.setAttribute("msg", "请先登录~");
			return "/jsp/msg.jsp";
		}
		OrderService os=(OrderService) BeanFactory.getBean("OrderService");
		pageBean<Order> bean=os.findAllByPage(user.getUid(),currPage,pageSize);
		request.setAttribute("pb", bean);
		return "/jsp/order_list.jsp";
	}
	/**
	 * 获取单个订单详情
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getById(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String oid=request.getParameter("oid");
		OrderService os=(OrderService) BeanFactory.getBean("OrderService");
		Order order=os.getById(oid);
		request.setAttribute("bean", order);
		return "/jsp/order_info.jsp";
	}
	
	/**
	 * 付款
	 */
	public String pay(HttpServletRequest request,HttpServletResponse respone) throws Exception{
		//接受参数
		String address=request.getParameter("address");
		String name=request.getParameter("name");
		String telephone=request.getParameter("telephone");
		String oid=request.getParameter("oid");
		
		
		//通过id获取order
		OrderService s=(OrderService) BeanFactory.getBean("OrderService");
		Order order = s.getById(oid);
		
		order.setAddress(address);
		order.setName(name);
		order.setTelephone(telephone);
		
		//更新order
		s.update(order);
		

		// 组织发送支付公司需要哪些数据
		String pd_FrpId = request.getParameter("pd_FrpId");
		String p0_Cmd = "Buy";
		String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");
		String p2_Order = oid;
		String p3_Amt = "0.01";
		String p4_Cur = "CNY";
		String p5_Pid = "";
		String p6_Pcat = "";
		String p7_Pdesc = "";
		// 支付成功回调地址 ---- 第三方支付公司会访问、用户访问
		// 第三方支付可以访问网址
		String p8_Url = ResourceBundle.getBundle("merchantInfo").getString("responseURL");
		String p9_SAF = "";
		String pa_MP = "";
		String pr_NeedResponse = "1";
		// 加密hmac 需要密钥
		String keyValue = ResourceBundle.getBundle("merchantInfo").getString("keyValue");
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
				p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
				pd_FrpId, pr_NeedResponse, keyValue);
	
		
		//发送给第三方
		StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
		sb.append("p0_Cmd=").append(p0_Cmd).append("&");
		sb.append("p1_MerId=").append(p1_MerId).append("&");
		sb.append("p2_Order=").append(p2_Order).append("&");
		sb.append("p3_Amt=").append(p3_Amt).append("&");
		sb.append("p4_Cur=").append(p4_Cur).append("&");
		sb.append("p5_Pid=").append(p5_Pid).append("&");
		sb.append("p6_Pcat=").append(p6_Pcat).append("&");
		sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
		sb.append("p8_Url=").append(p8_Url).append("&");
		sb.append("p9_SAF=").append(p9_SAF).append("&");
		sb.append("pa_MP=").append(pa_MP).append("&");
		sb.append("pd_FrpId=").append(pd_FrpId).append("&");
		sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
		sb.append("hmac=").append(hmac);
		
		respone.sendRedirect(sb.toString());
		
		return null;
	}
	/**
	 * 支付回调
	 */
	public String callback(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String p1_MerId = request.getParameter("p1_MerId");
		String r0_Cmd = request.getParameter("r0_Cmd");
		String r1_Code = request.getParameter("r1_Code");
		String r2_TrxId = request.getParameter("r2_TrxId");
		String r3_Amt = request.getParameter("r3_Amt");
		String r4_Cur = request.getParameter("r4_Cur");
		String r5_Pid = request.getParameter("r5_Pid");
		String r6_Order = request.getParameter("r6_Order");
		String r7_Uid = request.getParameter("r7_Uid");
		String r8_MP = request.getParameter("r8_MP");
		String r9_BType = request.getParameter("r9_BType");
		String rb_BankId = request.getParameter("rb_BankId");
		String ro_BankOrderId = request.getParameter("ro_BankOrderId");
		String rp_PayDate = request.getParameter("rp_PayDate");
		String rq_CardNo = request.getParameter("rq_CardNo");
		String ru_Trxtime = request.getParameter("ru_Trxtime");
		// 身份校验 --- 判断是不是支付公司通知你
		String hmac = request.getParameter("hmac");
		String keyValue = ResourceBundle.getBundle("merchantInfo").getString(
				"keyValue");
		
		// 自己对上面数据进行加密 --- 比较支付公司发过来hamc
		boolean isValid = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
				r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid,
				r8_MP, r9_BType, keyValue);
		if (isValid) {
			// 响应数据有效
			if (r9_BType.equals("1")) {
				// 浏览器重定向
				System.out.println("111");
				request.setAttribute("msg", "您的订单号为:"+r6_Order+",金额为:"+r3_Amt+"已经支付成功,等待发货~~");
				
			} else if (r9_BType.equals("2")) {
				// 服务器点对点 --- 支付公司通知你
				System.out.println("付款成功！222");
				// 修改订单状态 为已付款
				// 回复支付公司
				response.getWriter().print("success");
			}
			
			//修改订单状态
			OrderService s=(OrderService) BeanFactory.getBean("OrderService");
			Order order = s.getById(r6_Order);
			order.setState(1);
			
			s.update(order);
			
		} else {
			// 数据无效
			System.out.println("数据被篡改！");
		}
		return "/jsp/msg.jsp";
	}
	public String updateState(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String oid=request.getParameter("oid");
		String currPage=request.getParameter("currPage");
		OrderService s=(OrderService) BeanFactory.getBean("OrderService");
		Order order = s.getById(oid);
		order.setState(order.getState()+1);
		s.update(order);
		response.sendRedirect(request.getContextPath()+"/order?method=findAllByPage&currPage="+currPage);
		return null;
	}
}

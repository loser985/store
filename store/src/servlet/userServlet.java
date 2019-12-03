package servlet;

import java.util.Date;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

import constant.constant;
import domain.User;
import service.UserService;
import service_impl.UserServiceImpl;
import 工具.BeanFactory;
import 工具.MD5Utils;
import 工具.MyConverter;
import 工具.UUIDUtils;
import 工具.cookUtils;

@WebServlet(urlPatterns = { "/user" })
public class userServlet extends baseServlet {

	/**
	 * 关于用户的servlet
	 */
	private static final long serialVersionUID = -2000261587638890915L;

	public String add(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("add执行了.....");
		return null;

	}

	// 定位主页面
	public String index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/jsp/index.jsp";
	}

	// 定位注册页面
	public String registUi(HttpServletRequest request, HttpServletResponse response) {
		return "/jsp/register.jsp";
	}

	// 注册
	public String register(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getSession().setAttribute("m", null);

		// 获取两个验证码校验
		String code1 = request.getParameter("checkcode").toLowerCase();
		String code2 = request.getSession().getAttribute("sessionCode").toString().toLowerCase();
		request.getSession().removeAttribute("sessionCode");
		if (code1 == null || code1.trim().length() == 0 || code2 == null) {
			request.getSession().setAttribute("msg1", "请输入验证码");
			return "/jsp/register.jsp";
		}
		if (!code1.equals(code2)) {
			HttpSession s = request.getSession();
			s.setAttribute("msg1", "验证码错误");
			return "/jsp/register.jsp";
		}

		User user = new User();
		ConvertUtils.register(new MyConverter(), Date.class);
		user.setUid(UUIDUtils.getId());
		user.setCode(UUIDUtils.getCode());
		BeanUtils.populate(user, request.getParameterMap());
		user.setPassword(MD5Utils.md5(user.getPassword()));
		UserService us = (UserService) BeanFactory.getBean("UserService");
		us.register(user);

		request.setAttribute("msg", "用户注册已成功，请去邮箱激活~~");
		return "/jsp/msg.jsp";
	}

	// 定位登录页面
	public String loginUi(HttpServletRequest request, HttpServletResponse response) {
		return "/jsp/login.jsp";
	}

	/**
	 * 用户激活
	 * 
	 * @throws Exception
	 */
	public String active(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String code = request.getParameter("code");
		UserService us = (UserService) BeanFactory.getBean("UserService");// (UserService) BeanFactory.getBean("UserService");
		User user = us.active(code);
		if (user == null) {
			request.setAttribute("msg", "激活失败，请重新激活~~");
		} else {
			request.setAttribute("msg", "激活成功~~");
		}
		return "/jsp/msg.jsp";

	}

	/**
	 * 登录
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getSession().setAttribute("m", null);

		// 获取两个验证码校验
		String code1 = request.getParameter("checkcode").toLowerCase();
		String code2 = request.getSession().getAttribute("sessionCode").toString().toLowerCase();
		request.getSession().removeAttribute("sessionCode");
		if (code1 == null || code1.trim().length() == 0 || code2 == null) {
			request.getSession().setAttribute("msg1", "请输入验证码");
			return "/jsp/login.jsp";
		}
		if (!code1.equals(code2)) {
			HttpSession s = request.getSession();
			s.setAttribute("msg1", "验证码错误");
			return "/jsp/login.jsp";
		}

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		password=MD5Utils.md5(password);
		UserService us = (UserService) BeanFactory.getBean("UserService");
		User user = us.login(username, password);
		if (user == null) {
			request.getSession().setAttribute("msg1", "用户名密码不匹配");
			return "/jsp/login.jsp";
		} else {
			if(constant.USER_IS_ACTIVE!=user.getState()){
				request.getSession().setAttribute("msg1", "用户未激活！");
				return "/jsp/login.jsp";
			}
			request.getSession().setAttribute("user", user);
		}
		
		//判断是否记住用户名
		if("ok".equals(request.getParameter("save"))){
			Cookie c=new Cookie("saveName", username);
			c.setMaxAge(3600);
			c.setPath(request.getContextPath()+"/");
			response.addCookie(c);
		}else {
			Cookie c=cookUtils.getCookieByName("saveName", request.getCookies());
			if(c!=null){
				c.setValue("");
				c.setMaxAge(3600);
				c.setPath(request.getContextPath()+"/");
				response.addCookie(c);
			}
		}
		//判断是否自动登录
		if("ok".equals(request.getParameter("auto"))){
			Cookie c=new Cookie("auto", username+"-"+password);
			c.setMaxAge(3600);
			c.setPath(request.getContextPath()+"/");
			response.addCookie(c);
		}else {
			Cookie c=cookUtils.getCookieByName("auto", request.getCookies());
			if(c!=null){
				c.setValue("");
				c.setMaxAge(3600);
				c.setPath(request.getContextPath()+"/");
				response.addCookie(c);
			}
		}
		response.sendRedirect(request.getContextPath());
		return null;

	}
	/**
	 * 退出登录
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public String logOut(HttpServletRequest request,HttpServletResponse response) throws Exception{
		request.getSession().invalidate();
		Cookie c=cookUtils.getCookieByName("auto", request.getCookies());
		c.setValue("");
		c.setMaxAge(3600);
		c.setPath(request.getContextPath()+"/");
		response.addCookie(c);
		return "/jsp/index.jsp";
		
	}
	/**
	 * 判断用户是否已注册
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public String checkUsername4Ajax(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String username = request.getParameter("value");
		UserService u=(UserService) BeanFactory.getBean("UserService");
		User user=u.userByUsername(username);
		if(user==null){
			response.getWriter().print("0");
		}else {
			response.getWriter().print("1");
		}
		return null;
		
	}

}

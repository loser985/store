package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.User;
import service.UserService;
import service_impl.UserServiceImpl;
import 工具.cookUtils;

@WebFilter(urlPatterns={"/*"})
public class autoLogin implements Filter {

	public void doFilter(ServletRequest request2, ServletResponse response2, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) request2;
		HttpServletResponse response = (HttpServletResponse) response2;
		if (request.getSession().getAttribute("user") == null && !request.getRequestURI().contains("/login")) {
			Cookie c = cookUtils.getCookieByName("auto", request.getCookies());
			if (c != null && c.getValue()!="") {
				String value = c.getValue();
				String username = value.split("-")[0];
				String password = value.split("-")[1];
				UserService us = new UserServiceImpl();
				User user = null;
				try {
					user = us.login(username, password);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (user != null) {
					request.getSession().setAttribute("user", user);
				}
			}else{
				
			}
		}
		chain.doFilter(request, response);

	}

	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void destroy() {

	}

}

package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.User;


public class Privilege implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request2, ServletResponse response2, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest) request2;
		HttpServletResponse response=(HttpServletResponse) response2;
		User user=(User) request.getAttribute("user");
		if(user ==null){
			request.setAttribute("msg", "权限不够，请先登录~~");
			request.getRequestDispatcher("/jsp/msg.jsp").forward(request, response);
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}

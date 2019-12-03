package servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns={"/base"})
public class baseServlet extends HttpServlet {

	/**
	 * 通用servlet
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// 1.获取子类
			Class clazz = this.getClass();
			
			// 2.获取方法
			String method = request.getParameter("method");
			if(method==null	){
				method="index";
			}
			
			// 3.获取方法对象
			Method m = clazz.getMethod(method, HttpServletRequest.class, HttpServletResponse.class);
			
			// 4.让方法执行 返回值为请求转发的路径
			String path=(String) m.invoke(this, request,response);
			
			// 5.判断s是否为空
			if(path!=null){
				request.getRequestDispatcher(path).forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	public String index(HttpServletRequest request,HttpServletResponse response) throws Exception{
		return null;
	}

}

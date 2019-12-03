package servlet;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Cart;
import domain.CartItem;
import domain.Product;
import service.ProductService;
import 工具.BeanFactory;

/**
 * 购物车
 * @author Administrator
 *
 */
@WebServlet(urlPatterns={"/cart"})
public class cartServlet extends baseServlet{
	/**
	 * 获取session中的购物车
	 * @param request
	 * @return
	 */
	public Cart getCart(HttpServletRequest request){
		Cart cart=(Cart) request.getSession().getAttribute("cart");
		if(cart==null){
			cart=new Cart();
			request.getSession().setAttribute("cart", cart);
		}
		return cart;
	}
	
	/**
	 * 添加购物车
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public String add(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//1.获取商品id和数量
		String pid=request.getParameter("pid");
		int count=Integer.parseInt(request.getParameter("count"));
		
		//2.获取product
		Product product=null;
		ProductService ps=(ProductService) BeanFactory.getBean("ProductService");
		try {
			product = ps.getById(pid);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		//组合购物项
		CartItem item=new CartItem(product, count);
		
		//放在购物车
		Cart cart = getCart(request);
		cart.add2Cart(item);
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		return null;
	}
	/**
	 * 从购物车中删除商品
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public String removeFromCart(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String pid=request.getParameter("pid");
		Cart cart = getCart(request);
		cart.removeFromCart(pid);
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		return null;
	}
	/**
	 * 清空购物车
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String clear(HttpServletRequest request,HttpServletResponse response) throws Exception{
		getCart(request).clearCart();;
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		return null;
	}
}

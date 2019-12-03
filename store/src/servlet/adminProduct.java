package servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.ietf.jgss.Oid;

import domain.Category;
import domain.Product;
import domain.pageBean;
import service.CategoryService;
import service.ProductService;
import 工具.BeanFactory;
import 工具.UUIDUtils;
import 工具.UploadUtils;

@WebServlet(urlPatterns={"/adminProduct"})
public class adminProduct extends baseServlet {
	/**
	 * 查询所有商品
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String findAll(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ProductService ps=(ProductService) BeanFactory.getBean("ProductService");
		int currPage=Integer.parseInt(request.getParameter("currPage"));
		int pageSize=10;
		String flag=request.getParameter("flag");
		pageBean<Product> p=ps.findByPage( currPage, pageSize,flag);
		request.setAttribute("pageBean", p);
		if(Integer.parseInt(flag)==0){
			return "/admin/product/list.jsp";
		}else {
			return "/admin/product/list2.jsp";
		}
	}
	/**
	 * 跳转添加商品页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String addUI(HttpServletRequest request,HttpServletResponse response) throws Exception{
		CategoryService cs=(CategoryService) BeanFactory.getBean("CategoryService");
		List<Category> clist = cs.findAll();
		request.setAttribute("clist", clist);
		return "/admin/product/add.jsp";
	}
	/**
	 * 添加商品
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public String add(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		HashMap<String, Object> map=new HashMap<String, Object>();
		
		DiskFileItemFactory factory=new DiskFileItemFactory();
		ServletFileUpload upload=new ServletFileUpload(factory);
		try {
			List<FileItem> list = upload.parseRequest(request);
			for (FileItem fi : list) {
				if(fi.isFormField()){
					map.put(fi.getFieldName(), fi.getString("utf-8"));
				}else {
					String name=fi.getName();
					String realName=UploadUtils.getRealName(name);
					String uuidName=UploadUtils.getUUIDName(realName);
					String path=this.getServletContext().getRealPath("/products/1");
					FileOutputStream os=new FileOutputStream(new File(path,uuidName));
					InputStream is=fi.getInputStream();
					IOUtils.copy(is, os);
					
					is.close();
					os.close();
					fi.delete();
					map.put(fi.getFieldName(), "products/1/"+uuidName);
				}
			}
			Product p=new Product();
			BeanUtils.populate(p, map);
			p.setPid(UUIDUtils.getId());
			Date d=new Date();
			DateFormat df=DateFormat.getDateInstance(DateFormat.FULL);
			df=DateFormat.getDateTimeInstance(DateFormat.LONG,DateFormat.LONG);
			df=new SimpleDateFormat("yyyy-MM-dd");
			p.setPdate(df.format(d));
			Category c=new Category();
			c.setCid((String)map.get("cid"));
			p.setCategory(c );
			ProductService ps=(ProductService) BeanFactory.getBean("ProductService");
			ps.add(p);
			response.sendRedirect(request.getContextPath()+"/adminProduct?method=findAll&flag=0&currPage=1");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "商品添加失败~");
			return "/jsp/msg.jsp";
		}
		return null;
	}
	/**
	 * 上下架商品
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String lf(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String pid=request.getParameter("pid");
		String flag=request.getParameter("flag");
		String currPage=request.getParameter("currPage");
		ProductService ps=(ProductService) BeanFactory.getBean("ProductService");
		try {
			ps.updateFlag(pid,flag);
		} catch (Exception e) {
			request.setAttribute("msg", "商品上下架失败~");
			return "/jsp/msg.jsp";
		}
		if(Integer.parseInt(flag)==0){
			response.sendRedirect(request.getContextPath()+"/adminProduct?method=findAll&flag=0&currPage="+currPage);
		}else {
			response.sendRedirect(request.getContextPath()+"/adminProduct?method=findAll&flag=1&currPage="+currPage);
		}
		return null;
	}
	/**
	 * 跳转编辑商品页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String editUI(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String pid=request.getParameter("pid");
		String flag=request.getParameter("flag");
		ProductService ps=(ProductService) BeanFactory.getBean("ProductService");
		Product p = ps.getById(pid);
		CategoryService cs=(CategoryService) BeanFactory.getBean("CategoryService");
		List<Category> c = cs.findAll();
		String cid=ps.getCid(pid);
		request.setAttribute("cid", cid);
		request.setAttribute("p", p);
		request.setAttribute("c", c);
		request.setAttribute("flag", flag);
		return "/admin/product/edit.jsp";
	}
	/**
	 * 编辑商品
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
		DiskFileItemFactory factory=new DiskFileItemFactory();
		ServletFileUpload upload=new ServletFileUpload(factory);
		try {
			Map<String , Object> map=new HashMap<String, Object>();
			List<FileItem> list = upload.parseRequest(request);
			for (FileItem fi : list) {
				if(fi.isFormField()){
					map.put(fi.getFieldName(), fi.getString("utf-8"));
				}else {
					String name=fi.getName();
					String realName = UploadUtils.getRealName(name);
					String uuidName = UploadUtils.getUUIDName(realName);
					String path=this.getServletContext().getRealPath("/products/1");
					FileOutputStream os=new FileOutputStream(new File(path,uuidName));
					InputStream is=fi.getInputStream();
					IOUtils.copy(is, os);
					is.close();
					os.close();
					fi.delete();
					map.put(fi.getFieldName(), "/products/1/"+uuidName);
				}
			}
			String flag=(String) map.get("flag");
			Product p=new Product();
			BeanUtils.populate(p, map);
			Date d=new Date();
			DateFormat df=DateFormat.getDateInstance(DateFormat.FULL);
			df=DateFormat.getDateTimeInstance(DateFormat.LONG,DateFormat.LONG);
			df=new SimpleDateFormat("yyyy-MM-dd");
			p.setPdate(df.format(d));
			Category c=new Category();
			c.setCid((String)map.get("cid"));
			p.setCategory(c);
			ProductService ps=(ProductService) BeanFactory.getBean("ProductService");
			ps.edit(p);
			response.sendRedirect(request.getContextPath()+"/adminProduct?method=findAll&flag="+flag+"&currPage=1");
		} catch (FileUploadException e) {
			e.printStackTrace();
			request.setAttribute("msg", "商品修改失败~");
			return "/jsp/msg.jsp";
		}
		return null;
	}
}

package domain;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
/**
 * 订单
 * @author Administrator
 *
 */
public class Order implements Serializable{
	private String oid;
	private String ordertime;//订单时间
	private Double total;//订单金额
	
	private Integer state=0;//订单状态
	private String address;
	private String name;
	
	private String telephone;
	private User user;//属于哪个用户
	private List<OrderItem> items=new LinkedList<OrderItem>();
	
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(String string) {
		this.ordertime = string;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<OrderItem> getItems() {
		return items;
	}
	public void setItems(List<OrderItem> items) {
		this.items = items;
	}
	

}

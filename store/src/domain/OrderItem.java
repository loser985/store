package domain;

import java.io.Serializable;
/**
 * 订单项
 * @author Administrator
 *
 */
public class OrderItem implements Serializable{
	private String itemid;
	private Integer count;
	private Double subtotal;
	
	private Product product;//包含哪个商品
	private Order order;//属于哪个订单
	
	public String getItemid() {
		return itemid;
	}
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}

}

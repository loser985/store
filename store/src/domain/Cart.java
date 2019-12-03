package domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart implements Serializable{
	//存放购物车项的集合 key:商品的id cartitem:购物车项 使用map集合便于删除单个购物车项
	private Map<String, CartItem> map=new LinkedHashMap<String, CartItem>();
	private Double total=0.0;//总金额
	
	public Collection<CartItem> getItems(){
		
		return map.values();
	}
	/**
	 * 添加到购物车
	 * @param item
	 */
	public void add2Cart(CartItem item){
		//获取商品的Id
		String pid=item.getProduct().getPid();
		//判断购物车中有无该商品
		if(map.containsKey(pid)){
			//有
			//获取该商品的购物项
			CartItem oItem=map.get(pid);
			//增加小计金额
			oItem.setCount(oItem.getCount()+item.getCount());
		}else {
			//没有
			//将购物车项添加进去
			map.put(pid, item);
		}
		//添加完成之后修改总金额
		total+=item.getSubtotal();
	}
	
	/**
	 * 从购物车删除指定购物项
	 * @return
	 */
	public void removeFromCart(String pid){
		//1.从集合中删除
		CartItem item=map.remove(pid);
		
		//2.修改金额
		total-=item.getSubtotal();
	}
	
	/**
	 * 清空购物车
	 * @return
	 */
	public void clearCart(){
		//1.map置空
		map.clear();
		//2.金额归零
		total=0.0;
	}
	public Map<String, CartItem> getMap() {
		return map;
	}
	public void setMap(Map<String, CartItem> map) {
		this.map = map;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
}

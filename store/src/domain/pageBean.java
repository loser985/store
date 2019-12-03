package domain;

import java.util.List;
/**
 * 分页查询数据
 * @author Administrator
 *
 * @param <E>
 */
public class pageBean<E> {
	private List<E> list;//当前页内容	查询
	private Integer currPage;//当前页码	传递
	private Integer pageSize;//每页显示的条数	固定
	private Integer totalCount;//总条数	查询
	private Integer totalPage;//总页数	计算
	public List<E> getList() {
		return list;
	}
	public void setList(List<E> list) {
		this.list = list;
	}
	public Integer getCurrPage() {
		return currPage;
	}
	public void setCurrPage(Integer currPage) {
		this.currPage = currPage;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public Integer getTotalPage() {
		return (int) Math.ceil(totalCount*1.0/pageSize);
	}
	public pageBean(List<E> list, Integer currPage, Integer pageSize, Integer totalCount) {
		super();
		this.list = list;
		this.currPage = currPage;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
	}
	
	
}

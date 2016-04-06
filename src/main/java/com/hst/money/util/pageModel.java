package com.hst.money.util;

import java.util.List;
/**
 * 分页模型
 * @author Administrator
 *
 * @param <T>
 */
public class pageModel<T> {

	private List<T> result;
	
	private int maxPage;
	
	private int dataCount;
	
	private int currentPage;
	
	private int numPerPage;
	
	public int getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getDataCount() {
		return dataCount;
	}

	public void setDataCount(int dataCount) {
		this.dataCount = dataCount;
	}

	public pageModel() {
		super();
	}

	/**
	 * @param result 结果列表
	 * @param maxPage 总页数
	 * @param dataCount 总记录数量
	 * @param currentPage 当前页号
	 * @param numPerPage 每页记录数
	 */
	public pageModel(List<T> result, int maxPage, int dataCount,int currentPage,int numPerPage) {
		super();
		this.result = result;
		this.maxPage = maxPage;
		this.dataCount = dataCount;
		this.currentPage = currentPage;
		this.numPerPage  = numPerPage;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public int getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}
	
}

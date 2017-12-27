package org.bng.core.page;

import java.io.Serializable;

/**
 * 
 * @author ZhangBin
 *
 */
public class Page implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean prePage;

	private boolean nextPage;

	private int everyPage;

	private int totalPage;

	private int currentPage;

	private int beginIndex;
	
	public Page() {
	}

	public Page(int everyPage) {
		this.everyPage = everyPage;
	}

	public Page(boolean prePage, boolean nextPage, int everyPage, int totalPage, int currentPage, int beginIndex) {
		this.prePage = prePage;
		this.nextPage = nextPage;
		this.everyPage = everyPage;
		this.totalPage = totalPage;
		this.currentPage = currentPage;
		this.beginIndex = beginIndex;
	}

	public boolean hasPrePage() {
		return prePage;
	}

	public void setPrePage(boolean prePage) {
		this.prePage = prePage;
	}

	public boolean hasNextPage() {
		return nextPage;
	}

	public void setNextPage(boolean nextPage) {
		this.nextPage = nextPage;
	}

	public int getEveryPage() {
		return everyPage;
	}

	public void setEveryPage(int everyPage) {
		this.everyPage = everyPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getBeginIndex() {
		return beginIndex;
	}

	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}
	
}

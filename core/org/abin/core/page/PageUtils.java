package org.abin.core.page;

import org.abin.core.util.ConfigUtils;

public class PageUtils {
	
	private static final int DEFAULT_CURRENT_PAGE = 1;
	
	public static Page createPage(Page page, int totalRecords) {
		return createPage(page.getEveryPage(), page.getCurrentPage(), totalRecords);
	}
	
	public static Page createPage(int everyPage, int currentPage, int totalRecords) {
		everyPage = getEveryPage(everyPage);
		currentPage = getCurrentPage(currentPage);
		int beginIndex = getBeginIndex(everyPage, currentPage);
		int totalPage = getTotalPage(everyPage, totalRecords);
		boolean prePage = hasPrePage(currentPage);
		boolean nextPage = hasNextPage(currentPage, totalPage);
		return new Page(prePage, nextPage, everyPage, totalPage, currentPage, beginIndex);
	}
	
	private static int getEveryPage(int everyPage) {
		String defaultEveryPage = ConfigUtils.getString("page.everyPage");
		return everyPage == 0 ? new Integer(defaultEveryPage).intValue() : everyPage;
	}
	
	private static int getCurrentPage(int currentPage) {
		return currentPage == 0 ? DEFAULT_CURRENT_PAGE : currentPage;
	}
	
	private static int getBeginIndex(int everyPage, int currentPage) {
		return (currentPage - 1) * everyPage;
	}
	
	private static int getTotalPage(int everyPage, int totalRecords) {
		int totalPage = 0;

		if (totalRecords % everyPage == 0)
			totalPage = totalRecords / everyPage;
		else
			totalPage = totalRecords / everyPage + 1;

		return totalPage;
	}
	
	private static boolean hasPrePage(int currentPage) {
		return currentPage == DEFAULT_CURRENT_PAGE ? false : true;
	}
	
	private static boolean hasNextPage(int currentPage, int totalPage) {
		return currentPage == totalPage || totalPage == 0 ? false : true;
	}

}

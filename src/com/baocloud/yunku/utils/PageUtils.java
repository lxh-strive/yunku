package com.baocloud.yunku.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页算法工具
 * 
 * @author wzr
 * 
 */
public class PageUtils implements Serializable {
	private static final long serialVersionUID = 7848895200933137866L;
	private static final int MOD = 2;
	/** 每一页显示的记录条数 */
	private int numPerPage = 10;
	/** 当前页号 */
	private int curPage = 1;
	/** 下一页 */
	private int lastPage;
	/** 上一页 */
	private int prePage;
	/** 每一页第一条记录序号 */
	private int firstResult;
	/** 每一页最后一条记录序号 */
	private int lastResult;
	/** 总记录数 */
	private int totalNum;
	/** 总页数 */
	private int totalPage = 1;
	private int displayCount = 10;
	/** 页面可以点击页号列表 */
	private List<Integer> displayList;

	public int getFirstResult() {

		if (this.curPage < 1) {
			this.curPage = 1;
		} else if (this.curPage > this.getTotalPage()) {

			this.curPage = totalPage;
		}
		this.firstResult = (this.curPage - 1) * this.numPerPage + 1;
		return firstResult;
	}

	public int getLastResult() {
		this.lastResult = this.curPage * this.numPerPage;
		this.lastResult = this.lastResult > this.totalNum ? this.totalNum
				: this.lastResult;
		return lastResult;
	}

	public int getCurPage() {
		return curPage;
	}

	public int getLastPage() {
		this.lastPage = this.curPage >= this.totalPage ? this.totalPage
				: this.curPage + 1;
		return lastPage;
	}

	public int getPrePage() {
		this.prePage = this.curPage <= 2 ? 1 : this.curPage - 1;
		return prePage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage > 0 ? curPage : 1;
	}

	public int getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}

	public void setTotalNum(int totalNum) {

		this.totalNum = totalNum;
		this.totalPage = (this.totalNum / this.numPerPage)
				+ (0 == this.totalNum % this.numPerPage ? 0 : 1);
	}

	public int getTotalNum() {
		return totalNum;
	}

	public int getTotalPage() {
		return totalPage;
	}

	/**
	 * 
	 * @return List
	 */
	public List<Integer> getDisplayList() {
		int n = (int) displayCount / MOD;
		int m = this.curPage;
		displayList = new ArrayList<Integer>();
		if (totalPage > displayCount) {
			if (m <= n) {
				for (int index = 1; index <= displayCount; index++) {
					displayList.add(Integer.valueOf(index));
				}

			} else {
				int start = m - n + 1;
				int end;
				if (start + displayCount > totalPage) {
					start = totalPage - displayCount + 1;
					end = totalPage;
				} else {
					start = m - n + 1;
					end = m - n + displayCount;
				}
				for (int index = start; index <= end; index++) {
					displayList.add(Integer.valueOf(index));
				}
			}

		} else {
			for (int index = 1; index <= totalPage; index++) {
				displayList.add(Integer.valueOf(index));
			}
		}

		return displayList;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
}

package eg.edu.guc.dbms.pages;

import java.util.StringTokenizer;

public class PageID {

	private String pageID;

	public PageID(String pageID) {
		this.pageID = pageID;
	}

	public String getPageID() {
		return pageID;
	}
	
	public String getTableName(){
	 StringTokenizer str = new StringTokenizer(pageID);
	 return str.nextToken();
	}
	
	public void setPageID(String pageID) {
		this.pageID = pageID;
	}

}
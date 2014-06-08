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
	 String[] str = pageID.split("_");
	 return str[0];
	}
	
	public int getTableNumber(){
		 String[] str = pageID.split("_");
		 System.out.println(str[0]);
		 return Integer.parseInt(str[1]);
		}
	
	public void setPageID(String pageID) {
		this.pageID = pageID;
	}

}
package eg.edu.guc.dbms.steps;

import java.io.IOException;

import eg.edu.guc.dbms.classes.BufferManager;
import eg.edu.guc.dbms.pages.Page;
import eg.edu.guc.dbms.pages.PageID;

public class PageWrite extends Step {

	public PageWrite() {
		// TODO Auto-generated constructor stub
	}
 	private BufferManager bm ;
 	private PageID pageID;
 	private Page page;

	@Override
	public void execute() {
		
		try {
			bm.write(pageID, page);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public PageWrite(BufferManager bm, PageID pageID, Page page) {
		super();
		this.bm = bm;
		this.pageID = pageID;
		this.page = page;
	}
	
}

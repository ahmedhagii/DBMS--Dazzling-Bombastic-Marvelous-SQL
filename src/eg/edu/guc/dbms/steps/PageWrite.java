package eg.edu.guc.dbms.steps;

import java.io.IOException;

import eg.edu.guc.dbms.classes.BufferManager;
import eg.edu.guc.dbms.exceptions.DBEngineException;
import eg.edu.guc.dbms.pages.Page;
import eg.edu.guc.dbms.pages.PageID;

public class PageWrite extends Step {

 	private BufferManager bm ;
 	private PageID pageID;
 	private Page page;

	public void execute(PageID pageID, Page page) throws DBEngineException {
		this.pageID = pageID;
		this.page = page;
		try {
			bm.write(pageID, page);
		} catch (IOException e) {
			throw new DBEngineException("There was a problem writing the page!");
		}
	}

	public PageWrite(BufferManager bm) {
		super();
		this.bm = bm;
	}	
}
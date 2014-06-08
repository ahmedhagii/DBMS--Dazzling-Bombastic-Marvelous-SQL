package eg.edu.guc.dbms.steps;

import java.io.IOException;

import eg.edu.guc.dbms.classes.BufferManager;
import eg.edu.guc.dbms.exceptions.DBEngineException;
import eg.edu.guc.dbms.pages.Page;
import eg.edu.guc.dbms.pages.PageID;

public class PageWrite extends Step {

	public PageWrite() {
		// TODO Auto-generated constructor stub
	}

	public void execute(BufferManager bufferManager, PageID pageID, Page page) throws DBEngineException{
		try {
			bufferManager.write(pageID, page);
		} catch (IOException e) {
			throw new DBEngineException("There was a problem writing the page!");
			//e.printStackTrace();
		}
	}
	
}

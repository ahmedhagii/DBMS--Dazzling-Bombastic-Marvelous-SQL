package eg.edu.guc.dbms.classes;

import java.io.IOException;
import java.util.Vector;

import eg.edu.guc.dbms.exceptions.DBEngineException;
import eg.edu.guc.dbms.pages.Page;
import eg.edu.guc.dbms.pages.PageID;
import eg.edu.guc.dbms.steps.PageRead;
import eg.edu.guc.dbms.steps.Step;

public class Transaction implements Runnable {
	// Transaction class runs in its own thread to execute 
	// a sequence of steps. 
	// A transaction object is not reusable, i.e. every incoming SQL 
	// statement will result in the creation of a new transaction 
	// object. 
	// A transaction is responsible for calling the BufferManager 
	// to read and write pages and also responsible for calling the 
	// LogManager to record the steps being executed. 
	// The execute method starts the thread associated with 
	// the Transaction to run the steps. 
	// When a transaction ends, make sure to clear all its 
	// attributes by setting them to null. This will help Java 
	// garbage collector to identify those objects as being unused 
	// and removes them from memory faster. 
	 
	// methods 
	private BufferManager bufManager ;
	private LogManager logManager;
	private Vector<Step> vSteps;
	private String type;
	private Page page;
	
	public void init( BufferManager bufManager, 
					  LogManager logManager, 
					  Vector<Step> vSteps,String type){
		this.bufManager = bufManager;
		this.logManager = logManager;
		this.vSteps = vSteps;
		this.type = type;
		
	}

	public void run() {
			// TODO Auto-generated method stub
		PageRead read = (PageRead) vSteps.get(0);
		read.execute(null);
		for(PageID pgid : read.getPages()){
			page = new Page();
			Boolean bModify = type.equals("write") ? true : false;
			try {
				System.out.println(pgid.getPageID());
			 bufManager.read(pgid, page, bModify);
				for(int i = 1 ; i <vSteps.size()-2;i++){
					vSteps.get(i).execute(page);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DBEngineException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		vSteps.get(vSteps.size()-1).execute(page);
	}

}
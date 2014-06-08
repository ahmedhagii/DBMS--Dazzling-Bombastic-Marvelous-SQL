package eg.edu.guc.dbms.classes;

import java.util.Vector;
import eg.edu.guc.dbms.steps.Step;

public class Transaction {
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
	
	public void init( BufferManager bufManager, 
					  LogManager logManager, 
					  Vector<Step> vSteps){
		
		this.bufManager = bufManager;
		this.logManager = logManager;
		this.vSteps = vSteps;
		
		
	}
	 
	public void execute( ){
		
		
		for(Step step : vSteps){
			step.execute();
		}
		
		
		
	}

}
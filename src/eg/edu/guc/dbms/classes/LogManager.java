package eg.edu.guc.dbms.classes;

import java.util.Hashtable;

import eg.edu.guc.dbms.pages.PageID;

public class LogManager {
	// LogManager 
	// LogManager is class shared by all running Transactions. 
	// It implements an undo/redo logging mechanism. The location 
	// of the log file is to be loaded from the DBApp.config file 
	// strTransID is a unique identifier for a transaction. You can 
	// use System.currentTimeMillis() concatenated with a random 
	// generated number to create such unique ID. 
	 
	// methods 
	 
	public void init( ) {
	} 
	 
	public synchronized void flushLog( ) {
	} 
	 
	public synchronized void recordStart( String strTransID ) {
	} 
	 
	public synchronized void recordUpdate( String strTransID, 
	 PageID page,  String strKeyValue, 
	 String strColName, 
	 Object objOld, 
	 Object objNew){
		
	}
	 
	public synchronized void recordInsert(String strTransID, 
	 PageID page, 
	 Hashtable<String,String> htblColValues){
		
	}
	 
	public synchronized void recordDelete(String strTransID, 
	 PageID page, 
	 String strKeyValue, 
	 Hashtable<String,String> htblColValues){
		
	}
	 
	public synchronized void recordCommit( String strTransID ){
		
	}

}

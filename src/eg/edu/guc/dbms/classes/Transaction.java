package eg.edu.guc.dbms.classes;

import java.io.IOException;

import java.util.Hashtable;
import java.sql.Time;
import java.util.Vector;

import eg.edu.guc.dbms.exceptions.DBEngineException;
import eg.edu.guc.dbms.pages.Page;
import eg.edu.guc.dbms.pages.PageID;
import eg.edu.guc.dbms.steps.Commit;
import eg.edu.guc.dbms.steps.PageRead;
import eg.edu.guc.dbms.steps.PageWrite;
import eg.edu.guc.dbms.steps.Step;
import eg.edu.guc.dbms.steps.TupleDelete;
import eg.edu.guc.dbms.steps.TupleInsert;
import eg.edu.guc.dbms.steps.TupleUpdate;

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
	private BufferManager bufManager;
	private LogManager logManager;
	private Vector<Step> vSteps;
	private String type;
	Page page;
	private String strTransID;

	public void init(BufferManager bufManager, LogManager logManager,
			Vector<Step> vSteps, String type) {
		this.bufManager = bufManager;
		this.logManager = logManager;
		this.vSteps = vSteps;
		this.type = type;
		strTransID = "" + System.currentTimeMillis() + ""
				+ ((int) Math.random() * 10000000);

	}
	
	public void run() {
		// TODO Auto-generated method stub
		logManager.recordStart(strTransID);
		PageRead read = (PageRead) vSteps.get(0);
		read.execute();
		for (PageID pgid : read.getPages()) {
			Page page = null;
			Boolean bModify = type.equals("write") ? true : false;
			try {
				bufManager.read(pgid, page, bModify);
				for (int i = 1; i < vSteps.size() - 2; i++) {
					if (vSteps.get(i).getClass().getName()
							.equalsIgnoreCase("TupleDelete")) {
						TupleDelete dt = (TupleDelete) vSteps.get(i);
						logManager.recordDelete(strTransID, pgid, null,
								dt.getHtblColNameValue());
					} else {
						if (vSteps.get(i).getClass().getName()
								.equalsIgnoreCase("TupleInsert")) {
							TupleInsert dt = (TupleInsert) vSteps.get(i);
							logManager.recordInsert(strTransID, pgid,
									dt.getHtblColNameValue());
						} else {
							if (vSteps.get(i).getClass().getName()
									.equalsIgnoreCase("TupleUpdate")) {
								TupleUpdate dt = (TupleUpdate) vSteps.get(i);
								String col = "";
								for (String s : dt.getHtblColNameValue()
										.keySet())
									col = s;
								logManager.recordUpdate(strTransID, pgid, null,
										col, (Object) dt.getHtblColNameValue(),
										(Object) dt.getHtblNewValues());
							} else {
								if (vSteps.get(i).getClass().getName()
										.equalsIgnoreCase("Commit")) {
									Commit dt = (Commit) vSteps.get(i);
									logManager.recordCommit(strTransID);
								}

							}
						}

					}
					vSteps.get(i).execute(page);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DBEngineException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			vSteps.get(1).execute(page);
		}
		
	}

}
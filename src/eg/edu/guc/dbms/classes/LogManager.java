package eg.edu.guc.dbms.classes;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
	ArrayList<String> log = new ArrayList<String>();
	public static String logCSV = "data\\log.csv";

	public LogManager() {
	}

	public void init() {
		log = new ArrayList<String>();
	}

	public synchronized void flushLog() {
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(logCSV));
			for (int i = 0; i < log.size(); i++) {
				writer.println(log.get(i));
			}
			writer.flush();
			writer.close();
			log = new ArrayList<String>();
		} catch (IOException e) {
			System.out.println("EXCEPTION");
			e.printStackTrace();
		}

	}

	public synchronized void recordStart(String strTransID) {
		String line = "<Start, " + strTransID + ">";
		log.add(line);
	}

	public synchronized void recordUpdate(String strTransID, PageID page,
			String strKeyValue, String strColName, Object objOld, Object objNew) {
		String line = "<Update, " + strTransID + ", " + page.getPageID() + ", "
				+ strKeyValue + ", " + strColName + ", " + objOld.toString()
				+ ", " + objNew.toString() + ">";
		log.add(line);
	}

	public synchronized void recordInsert(String strTransID, PageID page,
			Hashtable<String, String> htblColValues) {
		Object[] keysArray = htblColValues.keySet().toArray();
		String[] ValuesArray = new String[keysArray.length];
		for (int i = 0; i < ValuesArray.length; i++) {
			ValuesArray[i] = htblColValues.get(keysArray[i].toString());
		}
		String values = "";
		for (int j = 0; j < ValuesArray.length; j++) {
			values += "(" + keysArray[j] + ":" + ValuesArray[j] + "), ";
		}
		String line = "<Insert, " + strTransID + ", " + page.getPageID() + ", "
				+ values + ">";
		log.add(line);
	}

	public synchronized void recordDelete(String strTransID, PageID page,
			String strKeyValue, Hashtable<String, String> htblColValues) {
		Object[] keysArray = htblColValues.keySet().toArray();
		String[] ValuesArray = new String[keysArray.length];
		for (int i = 0; i < ValuesArray.length; i++) {
			ValuesArray[i] = htblColValues.get(keysArray[i].toString());
		}
		String values = "";
		for (int j = 0; j < ValuesArray.length; j++) {
			values += "(" + keysArray[j] + ":" + ValuesArray[j] + "), ";
		}
		String line = "<delete, " + strTransID + ", " + page.getPageID() +", " + values + ">";
		log.add(line);
	}

	public synchronized void recordCommit(String strTransID) {
		String line = "<Commit, " + strTransID + ">";
		log.add(line);
	}

}

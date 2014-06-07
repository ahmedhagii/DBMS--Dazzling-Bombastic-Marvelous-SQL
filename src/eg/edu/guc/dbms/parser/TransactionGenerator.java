package eg.edu.guc.dbms.parser;

import java.util.Hashtable;
import java.util.Vector;

import eg.edu.guc.dbms.classes.Transaction;
import eg.edu.guc.dbms.steps.Commit;
import eg.edu.guc.dbms.steps.PageRead;
import eg.edu.guc.dbms.steps.PageWrite;
import eg.edu.guc.dbms.steps.Step;
import eg.edu.guc.dbms.steps.TupleDelete;
import eg.edu.guc.dbms.steps.TupleInsert;

public class TransactionGenerator {

	public TransactionGenerator() {
		// TODO Auto-generated constructor stub
	}

	public Transaction getTransaction(Object[] objects, int type) {
		Transaction newTransaction = new Transaction(null, null);
		return newTransaction;
	}

	private Vector<Step> getDeleteSteps(Object[] objects) {
		String tableName = (String)objects[0];
		String strOperator = (String)objects[1];
		Hashtable<String, String> htblColNameValue = (Hashtable<String, String>)objects[2]; 
		Vector<Step> vector = new Vector<Step>();
		
		PageRead pageRead = new PageRead();
		TupleDelete tupleDelete = new TupleDelete(tableName, htblColNameValue, strOperator, null, null, null);
		Commit commit = new Commit();
		PageWrite pageWrite = new PageWrite();
		
		vector.add(pageRead);
		vector.add(tupleDelete);
		vector.add(commit);
		vector.add(pageWrite);
		
		return vector;
	}

	private Vector<Step> getInsertSteps(Object[] objects) {
		String tableName = (String)objects[0];
		Hashtable<String, String> htblColNameValue = (Hashtable<String, String>)objects[1]; 
		Vector<Step> vector = new Vector<Step>();
		
		PageRead pageRead = new PageRead();
		TupleInsert tupleInsert = new TupleInsert(null, null, tableName, null, htblColNameValue, null);
		Commit commit = new Commit();
		PageWrite pageWrite = new PageWrite();
		
		vector.add(pageRead);
		vector.add(tupleInsert);
		vector.add(commit);
		vector.add(pageWrite);
		
		return vector;
	}

	private Vector<Step> getCreateTableSteps(Object[] objects) {
		//TODO
	}

	private Vector<Step> getSelectSteps(Object[] objects) {
		String tableName = (String)objects[0];
		String strOperator = (String)objects[1];
		Hashtable<String, String> htblColNameValue = (Hashtable<String, String>)objects[2]; 
		Vector<Step> vector = new Vector<Step>();
		
		PageRead pageRead = new PageRead();
		TupleDelete tupleDelete = new TupleDelete(tableName, htblColNameValue, strOperator, null, null, null);
		Commit commit = new Commit();
		PageWrite pageWrite = new PageWrite();
		
		vector.add(pageRead);
		vector.add(tupleDelete);
		vector.add(commit);
		vector.add(pageWrite);
		
		return vector;
	}

	private Vector<Step> getCreateIndexSteps(Object[] objects) {
		String tableName = (String)objects[0];
		String strOperator = (String)objects[1];
		Hashtable<String, String> htblColNameValue = (Hashtable<String, String>)objects[2]; 
		Vector<Step> vector = new Vector<Step>();
		
		PageRead pageRead = new PageRead();
		TupleDelete tupleDelete = new TupleDelete(tableName, htblColNameValue, strOperator, null, null, null);
		Commit commit = new Commit();
		PageWrite pageWrite = new PageWrite();
		
		vector.add(pageRead);
		vector.add(tupleDelete);
		vector.add(commit);
		vector.add(pageWrite);
		
		return vector;
	}

	private Vector<Step> getUpdateSteps(Object[] objects) {
		String tableName = (String)objects[0];
		String strOperator = (String)objects[1];
		Hashtable<String, String> htblColNameValue = (Hashtable<String, String>)objects[2]; 
		Vector<Step> vector = new Vector<Step>();
		
		PageRead pageRead = new PageRead();
		TupleDelete tupleDelete = new TupleDelete(tableName, htblColNameValue, strOperator, null, null, null);
		Commit commit = new Commit();
		PageWrite pageWrite = new PageWrite();
		
		vector.add(pageRead);
		vector.add(tupleDelete);
		vector.add(commit);
		vector.add(pageWrite);
		
		return vector;
	}

}

package eg.edu.guc.dbms.parser;

import java.util.Hashtable;
import java.util.Vector;

import eg.edu.guc.dbms.classes.Transaction;
import eg.edu.guc.dbms.steps.Commit;
import eg.edu.guc.dbms.steps.PageRead;
import eg.edu.guc.dbms.steps.PageWrite;
import eg.edu.guc.dbms.steps.Select;
import eg.edu.guc.dbms.steps.Step;
import eg.edu.guc.dbms.steps.TupleDelete;
import eg.edu.guc.dbms.steps.TupleInsert;
import eg.edu.guc.dbms.steps.TupleUpdate;

public abstract class TransactionGenerator {

	public static Transaction getTransaction(Object[] objects, int type) {
		Vector<Step> vector = new Vector<Step>();
		switch(type) {
			case 0: vector = getCreateTableSteps(objects); break;
			case 1: vector = getCreateIndexSteps(objects); break;
			case 2: vector = getInsertSteps(objects); break;
			case 3: vector = getDeleteSteps(objects); break;
			case 4: vector = getUpdateSteps(objects); break;
			case 5: vector = getSelectSteps(objects); break;
		}
		Transaction newTransaction = new Transaction();
		newTransaction.init(null, null, vector, null);
		return newTransaction;
	}

	private static Vector<Step> getDeleteSteps(Object[] objects) {
		String tableName = (String)objects[0];
		String strOperator = (String)objects[1];
		Hashtable<String, String> htblColNameValue = (Hashtable<String, String>)objects[2]; 
		Vector<Step> vector = new Vector<Step>();
		
		PageRead pageRead = new PageRead();
		TupleDelete tupleDelete = new TupleDelete(tableName, htblColNameValue, strOperator, null, null, null); //mo3addal le tadamon el arguments
		Commit commit = new Commit();
		PageWrite pageWrite = new PageWrite(null);//mo3addal le tadamon el arguments
		
		vector.add(pageRead);
		vector.add(tupleDelete);
		vector.add(commit);
		vector.add(pageWrite);
		
		return vector;
	}

	private static Vector<Step> getInsertSteps(Object[] objects) {
		String tableName = (String)objects[0];
		Hashtable<String, String> htblColNameValue = (Hashtable<String, String>)objects[1]; 
		Vector<Step> vector = new Vector<Step>();
		
		PageRead pageRead = new PageRead();
		TupleInsert tupleInsert = new TupleInsert(null, null, null, tableName, htblColNameValue);
		Commit commit = new Commit();
		PageWrite pageWrite = new PageWrite(null);//same here
		
		vector.add(pageRead);
		vector.add(tupleInsert);
		vector.add(commit);
		vector.add(pageWrite);
		
		return vector;
	}

	private static Vector<Step> getCreateTableSteps(Object[] objects) {
		//TODO
		return null;
	}

	private static Vector<Step> getSelectSteps(Object[] objects) {
		String tableName = (String)objects[0];
		String strOperator = (String)objects[1];
		Hashtable<String, String> htblColNameValue = (Hashtable<String, String>)objects[2]; 
		Vector<Step> vector = new Vector<Step>();
		
		PageRead pageRead = new PageRead();
		Select select = new Select(null, null, null, tableName, htblColNameValue, strOperator);
		Commit commit = new Commit();
		
		vector.add(pageRead);
		vector.add(select);
		vector.add(commit);
		
		return vector;
	}

	private static Vector<Step> getCreateIndexSteps(Object[] objects) {
		//TODO
		return null;
	}

	private static Vector<Step> getUpdateSteps(Object[] objects) {

		String tableName = (String)objects[0];
		String strOperator = (String)objects[1];
		Hashtable<String, String> htblColNameValueCondition = (Hashtable<String, String>)objects[3];
		Hashtable<String, String> htblColNameValue = (Hashtable<String, String>)objects[4]; 
		Vector<Step> vector = new Vector<Step>();
		
		PageRead pageRead = new PageRead();
		TupleUpdate tupleUpdate = new TupleUpdate(tableName, htblColNameValue, htblColNameValueCondition, strOperator, null, null, null);
		Commit commit = new Commit();
		PageWrite pageWrite = new PageWrite(null); // ghayaaar
		
		vector.add(pageRead);
		vector.add(tupleUpdate);
		vector.add(commit);
		vector.add(pageWrite);
		
		return vector;
	}

}
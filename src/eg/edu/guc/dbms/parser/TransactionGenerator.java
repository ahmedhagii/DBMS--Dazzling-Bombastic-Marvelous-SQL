package eg.edu.guc.dbms.parser;

import java.util.Hashtable;
import java.util.Vector;

import eg.edu.guc.dbms.classes.BufferManager;
import eg.edu.guc.dbms.classes.Transaction;
import eg.edu.guc.dbms.steps.Commit;
import eg.edu.guc.dbms.steps.PageRead;
import eg.edu.guc.dbms.steps.PageWrite;
import eg.edu.guc.dbms.steps.Select;
import eg.edu.guc.dbms.steps.Step;
import eg.edu.guc.dbms.steps.TupleDelete;
import eg.edu.guc.dbms.steps.TupleInsert;
import eg.edu.guc.dbms.steps.TupleUpdate;
import eg.edu.guc.dbms.utils.CSVReader;
import eg.edu.guc.dbms.utils.Properties;
import eg.edu.guc.dbms.utils.btrees.BTreeFactory;

public abstract class TransactionGenerator {
	
	public static BufferManager bm;
	public static Properties prop;
	public static BTreeFactory bTreeFactory;
	public static CSVReader reader; 
	
	public void setBufferManager(BufferManager bm) {
		this.bm = bm;
	}
	
	public static Transaction getTransaction(Object[] objects, int type) {
		Vector<Step> vector = new Vector<Step>();
		String typeOp = "";
		switch(type) {
			case 0: vector = getCreateTableSteps(objects); break;
			case 1: vector = getCreateIndexSteps(objects); break;
			case 2: vector = getInsertSteps(objects); typeOp = "write";break;
			case 3: vector = getDeleteSteps(objects); typeOp = "write";break;
			case 4: vector = getUpdateSteps(objects); typeOp = "write";break;
			case 5: vector = getSelectSteps(objects); break;
		}
		Transaction newTransaction = new Transaction();
		newTransaction.init(bm, null, vector, typeOp);
		return newTransaction;
	}

	private static Vector<Step> getDeleteSteps(Object[] objects) {
		String tableName = (String)objects[0];
		String strOperator = (String)objects[1];
		Hashtable<String, String> htblColNameValue = (Hashtable<String, String>)objects[2]; 
		Vector<Step> vector = new Vector<Step>();
		
		PageRead pageRead = new PageRead(reader, prop, bTreeFactory, htblColNameValue, tableName);
		TupleDelete tupleDelete = new TupleDelete(tableName, htblColNameValue, strOperator, reader, prop, bTreeFactory); //mo3addal le tadamon el arguments
		Commit commit = new Commit();

		PageWrite pageWrite = new PageWrite(bm);
		
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
		
		PageRead pageRead = new PageRead(reader, prop, bTreeFactory, htblColNameValue, tableName);
		TupleInsert tupleInsert = new TupleInsert(bTreeFactory, reader, prop, tableName, htblColNameValue);
		Commit commit = new Commit();
		PageWrite pageWrite = new PageWrite(bm);
		
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
		
		PageRead pageRead = new PageRead(reader, prop, bTreeFactory, htblColNameValue, tableName);
		Select select = new Select(bTreeFactory, reader, prop, tableName, htblColNameValue, strOperator);
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
		Hashtable<String, String> htblColNameValueCondition = (Hashtable<String, String>)objects[2];
		Hashtable<String, String> htblColNameValue = (Hashtable<String, String>)objects[3]; 
		Vector<Step> vector = new Vector<Step>();
		
		PageRead pageRead = new PageRead(reader, prop, bTreeFactory, htblColNameValueCondition, tableName);
		TupleUpdate tupleUpdate = new TupleUpdate(tableName, htblColNameValueCondition, htblColNameValue, strOperator, reader, prop, bTreeFactory);
		Commit commit = new Commit();
		PageWrite pageWrite = new PageWrite(bm);

		vector.add(pageRead);
		vector.add(tupleUpdate);
		vector.add(commit);
		vector.add(pageWrite);
		
		return vector;
	}

}
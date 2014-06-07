package eg.edu.guc.dbms.classes;

import java.util.Hashtable;
import java.util.Iterator;

import eg.edu.guc.dbms.exceptions.DBAppException;
import eg.edu.guc.dbms.exceptions.DBEngineException;

public class TransactionManager {
	// TransactionManager will contain the indices B+ tree(s).
	// TransactionManager is responsible for planning the execution
	// of an SQL. For this assignment, you are going to implement
	// that in the form of generating a sequence of steps using the
	// steps concrete classes mentioned above. The A1 implementation
	// of the three methods insertIntoTable, deleteFromTable and
	// selectFromTable will be partially substituted by those
	// concrete steps. The new method updateTable is similar to them.
	// You still have to use the B+ tree if there is one for the
	// column you are processing. However, you cannot read/write
	// directly to disk as you did in A1. You will have to create a
	// transaction object, initialize it with the steps, and run the
	// transaction object in its own thread. The transaction object
	// will in turn talk to the buffer manager to read/write the
	// pages.
	// The createTable and createIndex should work as before. You can
	// still write to metadata.csv from TransactionManager to
	// document the creation of tables and indices.

	// methods

	public void init() {

	}

	public void createTable(String strTableName,
			Hashtable<String, String> htblColNameType,
			Hashtable<String, String> htblColNameRefs, String strKeyColName)
			throws DBAppException {

	}

	public void createIndex(String strTableName, String strColName)
			throws DBAppException {

	}

	public void insertIntoTable(String strTableName,
			Hashtable<String, String> htblColNameValue) throws DBAppException {

	}

	public void updateTable(String strTableName,
			Hashtable<String, String> htblColNameValue) throws DBAppException {

	}

	public void deleteFromTable(String strTableName,
			Hashtable<String, String> htblColNameValue, String strOperator)
			throws DBAppException {

	}

	public Iterator selectFromTable(String strTable,
			Hashtable<String, String> htblColNameValue, String strOperator)
			throws DBAppException {
		return null;

	}

	public void saveAll() throws DBEngineException {

	}
}

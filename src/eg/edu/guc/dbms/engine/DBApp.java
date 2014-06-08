package eg.edu.guc.dbms.engine;

import java.util.Hashtable;
import java.util.Iterator;

import eg.edu.guc.dbms.classes.BufferManager;
import eg.edu.guc.dbms.classes.LogManager;
import eg.edu.guc.dbms.classes.RecoveryManager;
import eg.edu.guc.dbms.classes.TransactionManager;
import eg.edu.guc.dbms.commands.CreateIndex;
import eg.edu.guc.dbms.commands.CreateTableCommand;
import eg.edu.guc.dbms.commands.DeleteCommand;
import eg.edu.guc.dbms.commands.InsertCommand;
import eg.edu.guc.dbms.commands.SelectCommand;
import eg.edu.guc.dbms.exceptions.DBEngineException;
import eg.edu.guc.dbms.parser.SQLParser;
import eg.edu.guc.dbms.parser.TransactionGenerator;
import eg.edu.guc.dbms.utils.CSVReader;
import eg.edu.guc.dbms.utils.Properties;
import eg.edu.guc.dbms.utils.btrees.BTreeFactory;

public class DBApp {

	BTreeFactory bTreeFactory;
	CSVReader reader;
	Properties properties;
	SQLParser sqlParser;
	BufferManager bm;
	TransactionManager tm;
	LogManager lm;
	RecoveryManager rm;
	
	
	public DBApp() {
		this.init();
	}

	public void init() {
		this.reader = new CSVReader();
		this.properties = new Properties(reader);
		this.bTreeFactory = new BTreeFactory(properties.getBTreeN());
		
		
		this.bm = new BufferManager(reader);
		this.tm = new TransactionManager();
		this.lm = new LogManager();
		this.rm = new RecoveryManager();
		this.sqlParser = new SQLParser(tm);
		
		TransactionGenerator.prop = properties;
		TransactionGenerator.bTreeFactory = bTreeFactory;
		TransactionGenerator.bm = bm;
		TransactionGenerator.reader = reader;
		
		Thread bmThread = new Thread(bm);
		bmThread.start();

	}
	
	public static void main (String[]a) throws DBEngineException{
		DBApp d = new DBApp();
		String sql = "select *\n" + "from Department\n" + "where Location= henak";
		d.sqlParser.SQLParser(sql);
		d.tm.printInfo();
		d.tm.execute();
	}

	public void createTable(String strTableName,

	Hashtable<String, String> htblColNameType,
			Hashtable<String, String> htblColNameRefs, String strKeyColName)
			throws DBEngineException {
		CreateTableCommand newTable = new CreateTableCommand(strTableName,
				htblColNameType, htblColNameRefs, strKeyColName, this.reader,
				this.bTreeFactory, properties);
		newTable.execute();

	}

	public void createIndex(String strTableName, String strColName)
			throws DBEngineException {
		CreateIndex createIndex = new CreateIndex(strTableName, strColName,
				this.properties, reader, bTreeFactory);
		createIndex.execute();
	}

	public void insertIntoTable(String strTableName, Hashtable<String,String> htblColNameValue)
										throws DBEngineException {
		InsertCommand insertCommand = new InsertCommand(this.bTreeFactory, reader, strTableName, properties, htblColNameValue, null);
		insertCommand.execute();
	}

	public void deleteFromTable(String strTableName,
								Hashtable<String,String> htblColNameValue,
								String strOperator)
										throws DBEngineException {
		DeleteCommand delete = new DeleteCommand(false, null, strTableName, htblColNameValue, strOperator, reader,properties,bTreeFactory, null);
		delete.execute(); 
	
	}

	public Iterator< Hashtable<String, String >> selectFromTable(String strTable,
									Hashtable<String,String> htblColNameValue,
									String strOperator)
											throws DBEngineException {
		SelectCommand selectCommand = new SelectCommand(this.bTreeFactory, this.reader,properties, strTable, htblColNameValue, strOperator, null);
		selectCommand.execute();
		Iterator<Hashtable<String, String>> results = selectCommand
				.getResults().iterator();
		if (results.hasNext() == false) {
			return null;
		} else {
			return results;
		}
	}

	public void saveAll() throws DBEngineException {
		this.bTreeFactory.saveAll();
	}
}

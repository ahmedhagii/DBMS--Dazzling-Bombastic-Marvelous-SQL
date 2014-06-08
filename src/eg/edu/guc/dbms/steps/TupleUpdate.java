package eg.edu.guc.dbms.steps;

import java.util.ArrayList;
import java.util.Hashtable;

import eg.edu.guc.dbms.commands.DeleteCommand;
import eg.edu.guc.dbms.commands.InsertCommand;
import eg.edu.guc.dbms.commands.SelectCommand;
import eg.edu.guc.dbms.exceptions.DBEngineException;
import eg.edu.guc.dbms.pages.Page;
import eg.edu.guc.dbms.parser.TransactionGenerator;
import eg.edu.guc.dbms.utils.CSVReader;
import eg.edu.guc.dbms.utils.Properties;
import eg.edu.guc.dbms.utils.btrees.BTreeFactory;

public class TupleUpdate extends Step {
	private String  strTableName;
	private Hashtable<String,String> htblColNameValue;
	private String strOperator;
	private CSVReader reader;
	private Properties properties;
	private BTreeFactory btfactory;
	private Hashtable<String, String> htblNewValues;
	
	public TupleUpdate(String strTableName,
			Hashtable<String, String> htblColNameValue,
			Hashtable<String,String> htblNewValuesString, String strOperator,
			CSVReader reader, Properties properties, BTreeFactory btfactory) {
		super();
		this.strTableName = strTableName;
		this.htblColNameValue = htblColNameValue;
		this.htblNewValues = htblNewValues;
		this.strOperator = strOperator;
		this.reader = reader;
		this.properties = properties;
		this.btfactory = btfactory;
	}

	public void execute(Page page) {
		SelectCommand select = new SelectCommand(btfactory, reader, properties, strOperator, htblColNameValue, strOperator, page);
		try {
			select.execute();
		} catch (DBEngineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<Hashtable<String,String>> selectedRecords = select.getResults();
		for(int i = 0 ; i < selectedRecords.size() ;i++){
			for(String s : htblNewValues.keySet()){
				selectedRecords.get(i).put(s, htblNewValues.get(s));
			}
			
		}
		DeleteCommand delete = new DeleteCommand(strOperator, htblColNameValue, strOperator, reader, properties, btfactory, page);
		try {
			delete.execute();
		} catch (DBEngineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Hashtable<String,String> htbl : selectedRecords){
		InsertCommand insert = new InsertCommand(btfactory, reader, strOperator, properties, htbl, page);
		try {
			insert.execute();
		} catch (DBEngineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
		
		
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

}

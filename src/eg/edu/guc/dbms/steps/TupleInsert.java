package eg.edu.guc.dbms.steps;

import java.util.Hashtable;

import eg.edu.guc.dbms.commands.InsertCommand;
import eg.edu.guc.dbms.exceptions.DBEngineException;
import eg.edu.guc.dbms.pages.Page;
import eg.edu.guc.dbms.utils.CSVReader;
import eg.edu.guc.dbms.utils.Properties;
import eg.edu.guc.dbms.utils.btrees.BTreeFactory;

public class TupleInsert extends Step {

	public TupleInsert(BTreeFactory btFactory, CSVReader reader, Properties properties, String tableName, Hashtable<String,String> htblColNameValue) 
	{	
		super();
		this.btFactory = btFactory;
		this.reader = reader;
		this.tableName = tableName;
		this.properties = properties;
		this.htblColNameValue = htblColNameValue;
		
		// TODO Auto-generated constructor stub
	}
	private BTreeFactory btFactory;
	private CSVReader reader;
	private String tableName;
	private Properties properties;
	private Hashtable<String,String>htblColNameValue;
	
	


	public void execute(Page page) {
		// TODO Auto-generated method stub
	InsertCommand command =	new InsertCommand(btFactory, reader, tableName, properties, htblColNameValue, page);
		try {
			command.execute();
		} catch (DBEngineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}




	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

}

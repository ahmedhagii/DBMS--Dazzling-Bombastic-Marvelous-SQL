package eg.edu.guc.dbms.steps;

import java.util.Hashtable;

import eg.edu.guc.dbms.commands.DeleteCommand;
import eg.edu.guc.dbms.exceptions.DBEngineException;
import eg.edu.guc.dbms.interfaces.CSVReaderInterface;
import eg.edu.guc.dbms.pages.Page;
import eg.edu.guc.dbms.utils.CSVReader;
import eg.edu.guc.dbms.utils.Properties;
import eg.edu.guc.dbms.utils.btrees.BTreeFactory;

public class TupleDelete extends Step {

	
	private String  strTableName;
	private Hashtable<String,String> htblColNameValue;
	private String strOperator;
	private CSVReader reader;
	private Properties properties;
	private BTreeFactory btfactory;
	
	public TupleDelete(String strTableName, Hashtable<String,String>htblColNameValue,String strOperator, CSVReader reader, Properties properties,
			BTreeFactory btfactory) {
		super(strTableName, htblColNameValue);
		this.strTableName = strTableName;
		this.htblColNameValue = htblColNameValue;
		this.strOperator = strOperator;
		this.reader = reader;
		this.properties = properties;
		this.btfactory = btfactory;		
		// TODO Auto-generated constructor stub
	}

	public void execute(Page page) {
		DeleteCommand command = new DeleteCommand(false, null, strTableName, htblColNameValue, strOperator, reader, properties, btfactory, page);
		try {
			command.execute();
		} catch (DBEngineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void execute() {
		// TODO Auto-generated method stub
		
	}

}

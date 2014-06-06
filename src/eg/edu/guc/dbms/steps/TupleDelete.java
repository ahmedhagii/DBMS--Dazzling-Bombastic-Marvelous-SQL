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
	private Hashtable<String,String> htblColnameValue;
	private String strOperator;
	private CSVReader reader;
	private Properties properties;
	private BTreeFactory btfactory;
	private Page page;
	
	
	
	
	public TupleDelete() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		DeleteCommand command = new DeleteCommand(strTableName, htblColNameValue, strOperator, reader, properties, btfactory, page);
		try {
			command.execute();
		} catch (DBEngineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

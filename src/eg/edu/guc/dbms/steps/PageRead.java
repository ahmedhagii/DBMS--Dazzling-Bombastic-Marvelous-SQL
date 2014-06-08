package eg.edu.guc.dbms.steps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

import eg.edu.guc.dbms.exceptions.DBEngineException;
import eg.edu.guc.dbms.pages.Page;
import eg.edu.guc.dbms.pages.PageID;
import eg.edu.guc.dbms.utils.CSVReader;
import eg.edu.guc.dbms.utils.Properties;
import eg.edu.guc.dbms.utils.btrees.BTreeAdopter;
import eg.edu.guc.dbms.utils.btrees.BTreeFactory;

public class PageRead extends Step {

	private Properties properties;
	private BTreeFactory btfactory;
	private ArrayList<PageID> pages;
	private CSVReader read;
	
	public PageRead(CSVReader read, Properties properties, BTreeFactory btfactory,
			Hashtable<String, String> htblColNameValue, String tableName) {
		super(tableName, htblColNameValue);
		this.read = read;
		this.properties = properties;
		this.btfactory = btfactory;
		this.htblColNameValue = htblColNameValue;
	}

	public ArrayList<PageID> getPages() {
		return pages;
	}

	public void execute(Page page) {
		Set<String> keys = htblColNameValue.keySet();
		pages = new ArrayList<PageID>();
		for (String key : keys) {
			if (properties.isIndexed(this.tableName, key)) {
				BTreeAdopter tree = null;
				try {
					tree = btfactory.getBtree(this.tableName, key);
				} catch (DBEngineException e) {
				}
				try {
					ArrayList<String> pointers = (ArrayList<String>) tree
							.find(htblColNameValue.get(key));
					if(pointers == null) {
						PageID pdID = new PageID(this.tableName + "_" + read.getLastPageIndex(this.tableName));
						pages.add(pdID);
						continue;
					}
					for (String s : pointers) {
						System.out.println("Pointer " + s);
						PageID pgID = new PageID(s.split(" ")[0] + "_" + s.split(" ")[1]);
						pages.add(pgID);
					}
				} catch (IOException e) {
				}
			}

		}
	}

	
	public void execute() {
		// TODO Auto-generated method stub
		
	}
}
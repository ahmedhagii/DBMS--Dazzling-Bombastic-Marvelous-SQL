package eg.edu.guc.dbms.steps;

import java.util.Hashtable;

import eg.edu.guc.dbms.pages.Page;

public abstract class Step {

	String tableName;
	Hashtable<String, String> htblColNameValue;
	public abstract void execute (Page page);
}
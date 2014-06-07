package eg.edu.guc.dbms.parser;

import java.util.Hashtable;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eg.edu.guc.dbms.exceptions.DBEngineException;
import eg.edu.guc.dbms.utils.TypesConstants;


public class SQLParser {

	public static void SQLParser(String sql) {
		sql = sql.toLowerCase();
		// System.out.println(select + "\n" + from + "\n" + where);
	}
	
	public Object[] createTableParser(String sql) throws DBEngineException {
		String create = sql.substring(sql.indexOf("table"), sql.indexOf("(")).trim();
		String attr = sql.substring(sql.indexOf("(") + 1, sql.indexOf(";")-1).trim();
		
		String tableName = getTableName(create);
		Hashtable<String, String> htblColNameValue = createAttributesGen(attr);
		Set<String> yy = htblColNameValue.keySet();
		for(String uu : yy) {
			System.out.println(uu);
		}
		Object[] ret = {tableName, htblColNameValue};
		return ret;
	}
	
	public Object[] deleteParser(String sql) throws DBEngineException {
		String delete = sql.substring(sql.indexOf("from"), sql.indexOf("where")).trim();
		String where = sql.substring(sql.indexOf("where") + 5).trim();

		String tableName = getTableName(delete);
		String strOperator = getOp(where);
		Hashtable<String, String> htblColNameValue = whereHashtableGen(where,"\\s*,\\s*");
		
		Object[] ret = {tableName, strOperator, htblColNameValue};
		return ret;
	}
	
	public Object[] updateParser(String sql) throws DBEngineException {
		String update = sql.substring(sql.indexOf("update"), sql.indexOf("set")).trim();
		String set = sql.substring(sql.indexOf("set") + 3, sql.indexOf("where")).trim();
		String where = sql.substring(sql.indexOf("where") + 5).trim();

		String tableName = getTableName(update);
		String strOperator = getOp(where);
		Hashtable<String, String> htblColNameValueCondition = whereHashtableGen(where, strOperator);
		Hashtable<String, String> htblColNameValue = whereHashtableGen(set,"\\s*,\\s*");
		
		Object[] ret = {tableName, strOperator, htblColNameValueCondition, htblColNameValue};
		return ret;
	}
	
	public Object[] insertParser(String sql) throws DBEngineException {
		String into = sql.substring(sql.indexOf("into"), sql.indexOf("values"));
		String values = sql.substring(sql.indexOf("values"));

		String tableName = getTableName(into);
		Hashtable<String, String> htblColNameValue = insertHashtableGen(into, values);
		
		Object[] ret = {tableName, htblColNameValue};
		return ret;
	}

	public Object[] selectParser(String sql) throws DBEngineException {
		//TODO projection
		String select = sql.substring(sql.indexOf("select"),sql.indexOf("from"));
		String from = sql.substring(sql.indexOf("from"), sql.indexOf("where"));
		String where = sql.substring(sql.indexOf("where"));

		String tableName = getTableName(from);
		String strOperator = getOp(where);
		Hashtable<String, String> htblColNameValue = whereHashtableGen(where,strOperator);
		
		Object[] ret = {tableName, strOperator, htblColNameValue};
		return ret;
	}
	
	private Hashtable<String, String> createAttributesGen(String attr) throws DBEngineException {
		
		String[] arr = attr.split("\\s*,\\s*");
		Hashtable<String, String> table = new Hashtable<String, String>();
		Matcher m;
		for(String s : arr) {
			m = Pattern.compile("(.*)\\s*int").matcher(s);
			if (m.find()) {
				table.put(m.group(1), TypesConstants.INTEGER);
				continue;
			}
			
			m = Pattern.compile("(.*)\\s*varchar(\\s*\\d*\\s*)").matcher(s);
			if (m.find()) {
				table.put(m.group(1), TypesConstants.STRING);
				continue;
			}
			
			m = Pattern.compile("(.*)\\s*date(\\s*)").matcher(s);
			if (m.find()) {
				table.put(m.group(1), TypesConstants.DATE);
				continue;
			}
			
			m = Pattern.compile("(.*)\\s*bit").matcher(s);
			if (m.find()) {
				table.put(m.group(1), TypesConstants.BIT);
				continue;
			}
			throw new DBEngineException("Invalid data type or a semicolone is missing!");
		}
		return table;
	}
	
	private Hashtable<String, String> whereHashtableGen(String where, String strOp)
			throws DBEngineException {
		String[] cond = where.split(strOp);
		Hashtable<String, String> table = new Hashtable<String, String>();

		for (String s : cond) {
			String pattern = "(.*)\\s*=\\s*'*(\\w*)'*";
			// Create a Pattern object
			Pattern r = Pattern.compile(pattern);

			// Now create matcher object.
			Matcher m = r.matcher(s);

			if (m.find()) {
				table.put(m.group(1), m.group(2));
			} else {
				throw new DBEngineException("Invalid where statement");
			}

		}
		return table;
	}
	
	private Hashtable<String, String> insertHashtableGen(String cols, String values)
			throws DBEngineException {
		
		Hashtable<String, String> table = new Hashtable<String, String>();

		cols = cols.substring(cols.indexOf("(")+1, cols.indexOf(")"));
		String[] colNames = cols.split("\\s*,\\s*");
		
		values = values.substring(values.indexOf("(")+1, values.indexOf(")"));
		String[] actualValues = values.split("\\s*,\\s*");
		
		for(int i=0;i<actualValues.length;i++)
			actualValues[i] = actualValues[i].substring(1, actualValues[i].length()-1);
		
		if(colNames.length != actualValues.length)
			throw new DBEngineException("Values and column names are not well formated!");
		
		for(int i=0;i<colNames.length;i++)
			table.put(colNames[i], actualValues[i]);
		
		return table;
	}
	
	private String getTableName(String from) {
		return from.split("\\s+")[1];
	}
	
	private String getOp(String where) {
		if(where.contains("or"))
			return "or";
		return "and";
	}
	
	public static void main(String args[]) throws DBEngineException {
		SQLParser s = new SQLParser();
		
		String sql = "CREATE TABLE Persons\n"+
"(\n"+
"PersonID int,\n"+
"LastName varchar(255),\n"+
"FirstName varchar(255),\n"+
"Address varchar(255),\n"+
"City varchar(255)\n"+
");";
		
//		String y = "LastName  varchar(  255 )";
//		Matcher m = Pattern.compile("(.*)\\s*varchar(\\s*\\d*\\s*)").matcher(y);
//		if(m.find()) {
//			System.out.println(m.group(0) + " " + m.group(1) + " ");
//			System.out.println("HAHAHA");
//		}
		sql = sql.toLowerCase();
		Object[] arr = s.createTableParser(sql);
		
		System.out.println((String)arr[0]);
//		System.out.println((String)arr[1]);
		System.out.println( ((Hashtable<String, String>)arr[1]).keySet());
		Set<String> yy = ((Hashtable<String, String>)arr[1]).keySet();
		for(String uu : yy) {
			System.out.println(uu);
		}
//		System.out.println( ((Hashtable<String, String>)arr[3]).keySet());
		
		
		
	}
	
}
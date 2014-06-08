package eg.edu.guc.dbms.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

import eg.edu.guc.dbms.exceptions.DBEngineException;
import eg.edu.guc.dbms.interfaces.Command;
import eg.edu.guc.dbms.pages.Page;
import eg.edu.guc.dbms.utils.CSVReader;
import eg.edu.guc.dbms.utils.Properties;
import eg.edu.guc.dbms.utils.btrees.BTreeAdopter;
import eg.edu.guc.dbms.utils.btrees.BTreeFactory;

public class SelectCommand implements Command {
	BTreeFactory btfactory;
	CSVReader reader;
	String tableName;
	Hashtable<String, String> htblColNameValue;
	String strOperator;
	Properties properties;
	Page page;

	// The final arraylist of objects
	ArrayList<Hashtable<String, String>> results;

	// The arraylist of results pointer
	ArrayList<String> resultPointers;

	// The partial results before merging using OR or AND
	ArrayList<ArrayList<String>> partialRecords;

	public SelectCommand(BTreeFactory btfactory, CSVReader reader,
			Properties properties, String tableName,
			Hashtable<String, String> htblColNameValue, String strOperator,Page page) {
		this.btfactory = btfactory;
		this.reader = reader;
		this.tableName = tableName;
		this.htblColNameValue = htblColNameValue;
		this.strOperator = strOperator;
		this.properties = properties;
		this.page = page;
	}

	private ArrayList<String> intersect(ArrayList<String> resultsPointer,
			ArrayList<String> arrayList) {

		ArrayList<String> ret = new ArrayList<String>();

		for (String element : resultsPointer) {
			if (arrayList.contains(element)) {
				ret.add(element);
			}
		}

		return ret;
	}

	private ArrayList<String> union(ArrayList<String> resultsPointer,
			ArrayList<String> arrayList) {

		ArrayList<String> ret = resultsPointer;

		for (String element : arrayList) {
			if (!ret.contains(element)) {
				ret.add(element);
			}
		}

		return ret;

	}

	
	public void execute() throws DBEngineException {
		/*if (properties.getData().get(tableName) == null) {
			throw new DBEngineException("This table doesn't exist");
		} else*/ if (htblColNameValue == null && strOperator == null) {
			selectAll();
		} else {
			validate();
			normalSelect();
			mergeResults();
			convertPointers();
		}

	}

	private void validate() throws DBEngineException {

		if (!strOperator.equals("AND") && !strOperator.equals("OR")) {
			throw new DBEngineException("Unknown Opertator");
		}

		
		Set<String>columns = page.getTuples().get(0).keySet();

		Set<String> keys = this.htblColNameValue.keySet();

		for (String key : keys) {
			if (!columns.contains(key)) {
				throw new DBEngineException("Wrong Column Name");
			}
		}

	}

	private void normalSelect() throws DBEngineException {
		Set<String> keys = this.htblColNameValue.keySet();

		this.partialRecords = new ArrayList<ArrayList<String>>();

		for (String key : keys) {
				ArrayList<String> partialRecord = new ArrayList<String>();
					ArrayList<Hashtable<String, String>> res = page.getTuples();
					for (int j = 0; j < res.size(); j++) {
						System.out.println("Ynahhaaaaar " + res.get(j).get(key) + " " + htblColNameValue.get(key));
						if (res.get(j) != null
								&& res.get(j).get(key)
										.equals(htblColNameValue.get(key))) {
							System.out.println("HEEEEEEEEEEEEH");
							String pointer = ""+j;
							partialRecord.add(pointer);
						}
					}
				
				partialRecords.add(partialRecord);
			
		}

	}

	private void mergeResults() throws DBEngineException {
		this.resultPointers = new ArrayList<String>();
		if (partialRecords.size() == 0) {
			// DO NOTHING
		} else {
			this.resultPointers = partialRecords.get(0);
			for (int i = 1; i < partialRecords.size(); i++) {
				if (strOperator.equals("AND")) {
					this.resultPointers = intersect(this.resultPointers,
							partialRecords.get(i));
				} else {
					this.resultPointers = union(this.resultPointers,
							partialRecords.get(i));
				}
			}
		}

	}

	private void selectAll() throws DBEngineException {

		this.resultPointers = new ArrayList<String>();
		this.results = new ArrayList<Hashtable<String, String>>();

	//	int tablePages = reader.getLastPageIndex(this.tableName);

			ArrayList<Hashtable<String, String>> res = page.getTuples();
			for (int j = 0; j < res.size(); j++) {
				if (res.get(j) == null) { // Deleted Record
					continue;
				} else {
					String pointer = ""+j;
					resultPointers.add(pointer);
					results.add(res.get(j));
				}

			}
		

	}

	private void convertPointers() throws DBEngineException {
		this.results = new ArrayList<Hashtable<String, String>>();

		for (String result : this.resultPointers) {
			String[] row = result.split(" ");
			Hashtable<String, String> record = page.getTuples().get(Integer.parseInt(result));
			this.results.add(record);
		}
	}

	public ArrayList<String> getResultPointers() {
		return this.resultPointers;
	}

	public ArrayList<Hashtable<String, String>> getResults() {
		return this.results;
	}

}

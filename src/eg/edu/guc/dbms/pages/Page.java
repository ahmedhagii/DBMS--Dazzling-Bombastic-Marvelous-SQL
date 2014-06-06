package eg.edu.guc.dbms.pages;

import java.util.ArrayList;
import java.util.Hashtable;

public class Page {
	
	String tableName;
	int pinCount;
	ArrayList<Hashtable<String,String>> tuples = new ArrayList<Hashtable<String,String>>();
	
	public Page() {
		this.pinCount = 0;
	}

	public ArrayList<Hashtable<String,String>> getTuples() {
		return tuples;
	}
	
	public void insertTuple(Hashtable<String,String>tuple){
		tuples.add(tuple);
	}
		
	public void setTuples(ArrayList<Hashtable<String,String>> tuples) {
		this.tuples = tuples;
	}

	public void deleteTuples(int row){
		tuples.add(row, null);
		
	}

	public void setTableName(String name) {
		this.tableName = name;
	}
	
	public void setPinCount(int number) {
		this.pinCount= number;
	}
	
	public int getPinCount() {
		return this.pinCount;
	}


}
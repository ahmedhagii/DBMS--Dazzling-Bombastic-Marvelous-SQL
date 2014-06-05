package eg.edu.guc.dbms.pages;

import java.util.ArrayList;

public class Page {
	
	String tableName;
	ArrayList<String> tuples = new ArrayList<String>();
	
	public Page() {
		// TODO Auto-generated constructor stub
	}

	public ArrayList<String> getTuples() {
		return tuples;
	}

	public void setTuples(ArrayList<String> tuples) {
		this.tuples = tuples;
	}

}
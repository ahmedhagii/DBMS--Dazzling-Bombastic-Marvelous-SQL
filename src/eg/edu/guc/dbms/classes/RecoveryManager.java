package eg.edu.guc.dbms.classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Queue;
import java.util.Stack;

import eg.edu.guc.dbms.exceptions.DBEngineException;
import eg.edu.guc.dbms.pages.Page;
import eg.edu.guc.dbms.pages.PageID;
import eg.edu.guc.dbms.steps.TupleDelete;
import eg.edu.guc.dbms.steps.TupleInsert;
import eg.edu.guc.dbms.utils.CSVReader;
import eg.edu.guc.dbms.utils.Properties;
import eg.edu.guc.dbms.utils.btrees.BTreeFactory;

public class RecoveryManager {
	// RecoveryManager.recover() is run every time the DBMS 
	// is started. It implements an undo/redo recovery policy. 
	// The location of the log file must be loaded from the 
	// file DBApp.config
	BufferManager bufr;
	CSVReader redr;
	Properties properties;
	 String filePath = "data\\log.csv";
	 BTreeFactory btfactory;
	// methods 
	 public RecoveryManager(BufferManager bufr,CSVReader redr,Properties properties,BTreeFactory btfactory){
		 
		 this.bufr = bufr ;
		 this.redr = redr;
		 this.properties = properties;
		 this.btfactory = btfactory;
	 }
	 
	public  Hashtable<String,String> no3ak() throws IOException {
		Hashtable<String,String> elnoo3 = new Hashtable<String,String>();
		File file = new File(filePath);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = new String();
		String transID = new String();

		while(reader.ready()){
		line = reader.readLine();
		line = line.substring(1,line.length()-1);
		String[] split = line.split(", ");
		transID = split[1];
		if(split[0].equals("Start")){
			elnoo3.put(transID,"undo");
		}
		  if(split[0].equals("Commit")){
				elnoo3.put(transID,"redo");
		  }

		}
		return elnoo3;
		
	}
	public void undo(String step) throws IOException, DBEngineException{
	String[] split = step.split(", ");
		if(split[0].equals("Insert")){
		String pageid = split[2];
		Hashtable<String,String>htblColNameValue = new Hashtable<String,String>();
		for(int i=3;i<split.length;i++){
			String etneen = split[i];
			etneen= etneen.substring(1,etneen.length()-1);
			String[] pair = etneen.split(":");
			htblColNameValue.put(pair[0], pair[1]);
		}
		Page h = new Page();
		PageID k = new PageID(pageid);
		bufr.read(k, h, true);
		TupleDelete delete = new TupleDelete(k.getTableName(), 
				htblColNameValue, "AND", redr, properties, btfactory);
		delete.execute(h);
		
		} else if (split[0].equals("delete")){
			String pageid = split[2];
			Hashtable<String,String>htblColNameValue = new Hashtable<String,String>();
			for(int i=3;i<split.length;i++){
				String etneen = split[i];
				etneen= etneen.substring(1,etneen.length()-1);
				String[] pair = etneen.split(":");
				htblColNameValue.put(pair[0], pair[1]);
			}
			Page h = new Page();
			PageID k = new PageID(pageid);
			bufr.read(k, h, true);
			TupleInsert insert = new TupleInsert(btfactory, 
			redr, properties,  k.getTableName(),htblColNameValue );
			insert.execute(h);
			
		}
		
	}
	
	public void redo (String line){
		
	}
	
	public void recover( ) throws IOException, DBEngineException{
		Hashtable<String,String> elnoo3 = no3ak();
		File file = new File(filePath);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = new String();
		String transID = new String();
		Stack<String> undo = new Stack<String>();
		ArrayList<String> redo = new ArrayList<String>();
		
		while(reader.ready()){
		line = reader.readLine();
		line=line.substring(1,line.length()-1);
		String[] split = line.split(", ");
		transID = split[1];
		if(split[0].equals("Commit")||split[0].equals("Start")){
			continue;
		}
		if(elnoo3.get(transID).equals("redo")){
		redo.add(line);
		}
		if(elnoo3.get(transID).equals("undo")){
			undo.push(line);
		}
		
		while(!undo.isEmpty()){
			undo(undo.pop());
		}
		
	}
}

}
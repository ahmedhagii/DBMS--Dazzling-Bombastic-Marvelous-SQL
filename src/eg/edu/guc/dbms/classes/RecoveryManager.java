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

public class RecoveryManager {
	// RecoveryManager.recover() is run every time the DBMS 
	// is started. It implements an undo/redo recovery policy. 
	// The location of the log file must be loaded from the 
	// file DBApp.config 
	 String filePath = "data\\log.csv";
	// methods 
	public  Hashtable<String,String> no3ak() throws IOException {
		Hashtable<String,String> elnoo3 = new Hashtable<String,String>();
		File file = new File(filePath);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = new String();
		String transID = new String();

		while(reader.ready()){
		line = reader.readLine();
		line.substring(1,line.length()-1);
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
	public void redo(ArrayList<String> transaction){
		
	}
	
	public void undo (String line){
		
	}
	
	public void recover( ) throws IOException{
		Hashtable<String,String> elnoo3 = no3ak();
		File file = new File(filePath);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = new String();
		String transID = new String();
		Stack<String> undo = new Stack<String>();
		ArrayList<String> redo = new ArrayList<String>();
		
		while(reader.ready()){
		line = reader.readLine();
		line.substring(1,line.length()-1);
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
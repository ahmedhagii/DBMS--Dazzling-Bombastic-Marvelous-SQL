package eg.edu.guc.dbms.tests;

import java.util.ArrayList;
import java.util.Hashtable;

import eg.edu.guc.dbms.commands.DeleteCommand;
import eg.edu.guc.dbms.commands.SelectCommand;
import eg.edu.guc.dbms.exceptions.DBEngineException;
import eg.edu.guc.dbms.pages.Page;

public class Mamdou7Tests {

	
	public static void testSelect() throws DBEngineException{
		//ArrayList
		ArrayList<Hashtable<String,String>> tuples = new ArrayList<Hashtable<String,String>>();
		
		//hashtables
		for(int i = 0 ; i < 100 ; i++){
			
			
			Hashtable<String,String> tuple = new Hashtable<String,String>();
			tuple.put("name", "ashraf"+i);
			tuple.put("id", ""+i);
			tuples.add(tuple);
		}
		
		//selecting on
		Hashtable<String,String> colNameValue = new Hashtable<String,String>();
		colNameValue.put("id", ""+1);
		
		//page
		Page page = new Page();
		page.setTuples(tuples);
		
		
		page.setTuples(tuples);
		
		SelectCommand test = new SelectCommand(null, null, null, null, colNameValue, "AND", page);
		test.execute();
		for(Hashtable<String,String> htbl : test.getResults()){
			System.out.println(htbl.get("name"));
		}}
	
	
		public void testInsert(){
			ArrayList<Hashtable<String,String>> tuples = new ArrayList<Hashtable<String,String>>();
			
			//hashtables
			for(int i = 0 ; i < 100 ; i++){
				
				
				Hashtable<String,String> tuple = new Hashtable<String,String>();
				tuple.put("name", "ashraf"+i);
				tuple.put("id", ""+i);
				tuples.add(tuple);
			}
			
			//selecting on
			Hashtable<String,String> colNameValue = new Hashtable<String,String>();
			colNameValue.put("id", ""+1);
			
			//page
			Page page = new Page();
			page.setTuples(tuples);
			
			
			page.setTuples(tuples);
			
			
			
			
			
			
			
			
			
			
			
			
		}
	
	
	
	
	
	
		public static void testDelete(){
			
		ArrayList<Hashtable<String, String>> tuples = new ArrayList<Hashtable<String, String>>();

		// hashtables
		for (int i = 0; i < 100; i++) {

			Hashtable<String, String> tuple = new Hashtable<String, String>();
			tuple.put("name", "ashraf" + i);
			tuple.put("id", "" + i);
			tuples.add(tuple);
		}

		// selecting on
		Hashtable<String, String> colNameValue = new Hashtable<String, String>();
		colNameValue.put("id", "" + 1);
		colNameValue.put("name", "ashraf2");

		// page
		Page page = new Page();
		page.setTuples(tuples);

		page.setTuples(tuples);
	for(Hashtable<String,String> htbl : page.getTuples()){
			
			System.out.println(htbl);
		}
	System.out.println("=======");
		DeleteCommand test = new DeleteCommand(null,colNameValue,"OR",null,null,null,page);	
		try {
			test.execute();
		} catch (DBEngineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Hashtable<String,String> htbl : page.getTuples()){
			
			System.out.println(htbl);
		}
		
			
			
			
			
			
			
			
			
			
			
			
		}
	
	
	
	
	
	
	
	
	
	
	
		
		public static void main(String[]args){
			
			
			testDelete();
			
			
			
			
			
			
		}
		
		
}

package eg.edu.guc.dbms.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

import eg.edu.guc.dbms.classes.BufferManager;
import eg.edu.guc.dbms.exceptions.DBEngineException;
import eg.edu.guc.dbms.pages.Page;
import eg.edu.guc.dbms.pages.PageID;
import eg.edu.guc.dbms.utils.CSVReader;

public class AtefTests {
	public static void main (String [] args) throws IOException, DBEngineException{
		CSVReader x = new CSVReader();
		BufferManager y = new BufferManager(x);
		 y.init();
		Page H = new Page();
		PageID J = new PageID("Employee_0");
	//	System.out.println(y.UsedSlots.size());
		y.read(J, H, true);
	//	System.out.println(y.UsedSlots.size());
		ArrayList<Hashtable<String,String>> tuples =x.loadPage("Employee",0);
	//	String print = new String();
	//	for(int i=0;i<tuples.size();i++){
	//		print.concat(tuples.get(i).toString()+"\n");
//		}
		Scanner delay = new Scanner(System.in);
		System.out.println(H.toString());
		System.out.println("Enter rakam ye3atalny shwaya");
		int asdasd = delay.nextInt();
		J = new PageID("Department_0");
		Page K = new Page();
		y.read(J, K, true);
		
		
		//System.out.println(tuples);
		//H.setTuples(tuples);
		//System.out.println(H.getTableName());

		
	}

}

package eg.edu.guc.dbms.classes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Scanner;

import eg.edu.guc.dbms.exceptions.DBEngineException;
import eg.edu.guc.dbms.pages.Page;
import eg.edu.guc.dbms.pages.PageID;
import eg.edu.guc.dbms.utils.CSVReader;

public class BufferManager {

	// BufferManager manages the reading and writing of pages from
	// disk. It maintains two lists: list of empty slots, and list of
	// used slots. DBApp.config has two new parameters
	// (MinimumEmptyBufferSlots/MaximumUsedBufferSlots) to
	// indicate the min and max size of the lists.
	// Upon requesting a page via read(), the BufferManager will
	// load it from disk if it is not already in memory and look for
	// an empty slot to store it in. The slot is moved from empty
	// list and added to used list.
	// BufferManager should have an internal thread that runs
	// every 2 minutes to remove the least recently used (LRU) page
	// from memory. LRU is described on page 748 of the textbook and
	// in lecture slides. If the page has changed since loaded from
	// disk(dirty), then write it to disk, otherwise only delete from
	// memory.
	// Note that if the empty list reaches the minimum, or the full
	// list reaches maximum, then you must run the LRU algorithm
	// immediately.
	// BufferManager should always return a copy of the page
	// when the read method is called.
	// BufferManager.read() method will accept a flag indicating
	// whether the page is being read for modification or not.
	// PageID and Page classes are left for you to define.

	int MinimumEmptyBufferSlots;
	int MaximumUsedBufferSlots;
	int usedSlots;
	int EmptySlots;
	HashMap<PageID,Page> UsedSlots;
	HashMap<PageID,Boolean> modified ;
	CSVReader reader;
	
	// methods
	public void init() {
	this.UsedSlots = new HashMap<PageID,Page>();
	this.modified = new HashMap<PageID,Boolean>();
	this.MinimumEmptyBufferSlots = 3 ;
	this.MaximumUsedBufferSlots = 1;
	this.usedSlots = 0;
	this.EmptySlots = MaximumUsedBufferSlots;
	}
	
	public  BufferManager(CSVReader reader){
		this.reader = reader;
	}

	public synchronized void read(PageID pageID, Page page, boolean bModify) throws DBEngineException, IOException {
		
		if (UsedSlots.containsKey(pageID)){
			page = new Page ();
//			UsedSlots.get(pageID).setPinCount(UsedSlots.get(pageID).getPinCount()+1);
			page = UsedSlots.get(pageID);
			return;
		} else if 
			(usedSlots >= MaximumUsedBufferSlots && EmptySlots <= MinimumEmptyBufferSlots){
			PageID x = UsedSlots.keySet().iterator().next();
			if (modified.get(x)) 
			        write(x,UsedSlots.get(x));
		      modified.remove(x);
		      UsedSlots.remove(x);

		} else {	
			usedSlots ++;  EmptySlots--;
		}
//		page.setPinCount(1);
		page.setTableName(pageID.getTableName());
		page.setTuples(reader.loadPage(pageID.getTableName(), pageID.getTableNumber()));
		UsedSlots.put(pageID, page);
		modified.put(pageID,bModify);
	}

	public synchronized void write(PageID pageID, Page page) throws IOException {
		System.out.println("ANA HNA");
		reader.writePage(pageID.getTableName(), page.getTuples(),
				"data/"+pageID.getTableName()+"_"+pageID.getTableNumber());
	}
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

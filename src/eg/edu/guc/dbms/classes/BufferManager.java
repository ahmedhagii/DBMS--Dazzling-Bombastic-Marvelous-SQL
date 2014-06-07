package eg.edu.guc.dbms.classes;

import eg.edu.guc.dbms.pages.Page;
import eg.edu.guc.dbms.pages.PageID;

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
	
	
	// methods
	public void init() {

	}

	public synchronized void read(PageID pageID, Page page, boolean bModify) {
		
	}

	public synchronized void write(PageID pageID, Page page) {

	}

}
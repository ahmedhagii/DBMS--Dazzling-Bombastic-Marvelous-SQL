package eg.edu.guc.dbms.utils;

import java.io.IOException;
import java.util.TimerTask;

import eg.edu.guc.dbms.classes.BufferManager;

public class FlushTimer extends TimerTask {
	
	
	
	private BufferManager bufManager;
	
	public FlushTimer(BufferManager bufManager){
		
		super();
		this.bufManager = bufManager;
		
		
		
	}
	
	
	
	
	
	
	@Override
	public void run() {
		
		try {
			bufManager.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

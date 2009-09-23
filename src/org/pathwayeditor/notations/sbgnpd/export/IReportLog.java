package org.pathwayeditor.notations.sbgnpd.export;
import java.util.Iterator;



public interface IReportLog {

	void reportError(String msg);
	
	void reportWarning(String msg);
	
	void reportInfo(String msg);
	
	boolean hasErrors();
	
	boolean hasWarnings();
	
	boolean isEmpty();
	
	int numErrors();
	
	int numWarnings();
	
	int numInfoMessages();
	
	int totalNumMessages();
	
	Iterator<IReportItem> messageIterator();

	void reportError(String string, Throwable ex);
	
}

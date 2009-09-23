package org.pathwayeditor.notations.sbgnpd.export;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.pathwayeditor.notations.sbgnpd.export.IReportItem.InfoType;


public class ReportLog implements IReportLog {
	private List<IReportItem> reports;
	
	public ReportLog(){
		this.reports = new LinkedList<IReportItem>();
	}
	
	public boolean hasInfoType(InfoType type){
		Iterator<IReportItem> iter = reports.iterator();
		boolean retVal = false;
		while(iter.hasNext() && !retVal){
			IReportItem item = iter.next();
			retVal = item.getType().equals(type);
		}
		return retVal;
	}
	
	public int countInfoType(InfoType type){
		Iterator<IReportItem> iter = reports.iterator();
		int retVal = 0;
		while(iter.hasNext()){
			IReportItem item = iter.next();
			if(item.getType().equals(type)){
				retVal ++;
			}
		}
		return retVal;
	}
	
	public boolean hasErrors() {
		return hasInfoType(InfoType.ERROR);
	}

	public boolean hasWarnings() {
		return hasInfoType(InfoType.WARNING);
	}

	public boolean isEmpty() {
		return this.reports.isEmpty();
	}

	public Iterator<IReportItem> messageIterator() {
		return this.reports.iterator();
	}

	public int numErrors() {
		return countInfoType(InfoType.ERROR);
	}

	public int numInfoMessages() {
		return countInfoType(InfoType.INFO);
	}

	public int numWarnings() {
		return countInfoType(InfoType.WARNING);
	}

	private void createNewItem(final InfoType type, final String msg){
		this.reports.add(new IReportItem(){

			public String getMessage() {
				return msg;
			}

			public InfoType getType() {
				return type;
			}
			
		});
	}
	
	public void reportError(String msg) {
		createNewItem(InfoType.ERROR, msg);
	}

	public void reportError(String msg, Throwable ex) {
		StringBuilder buf = new StringBuilder(msg);
		buf.append(" Exception thrown was: ");
		buf.append(ex.getMessage());
		createNewItem(InfoType.ERROR, buf.toString());
	}

	public void reportInfo(String msg) {
		createNewItem(InfoType.INFO, msg);
	}

	public void reportWarning(String msg) {
		createNewItem(InfoType.WARNING, msg);
	}

	public int totalNumMessages() {
		return this.reports.size();
	}

	@Override
	public String toString(){
		StringBuilder buf = new StringBuilder();
		for(IReportItem item : this.reports){
			String type = "Info   ";
			if(item.getType().equals(InfoType.ERROR)){
				type = "Error  ";
			}
			else if(item.getType().equals(InfoType.WARNING)){
				type = "Warning";
			}
			buf.append(type);
			buf.append(": ");
			buf.append(item.getMessage());
			buf.append("\n");
		}
		return buf.toString();
	}
}

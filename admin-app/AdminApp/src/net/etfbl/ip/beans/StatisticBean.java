package net.etfbl.ip.beans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class StatisticBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public String readLog() {
		ResourceBundle bundle =
			      PropertyResourceBundle.getBundle("net.etfbl.ip.beans.statistic");
	    StringBuilder sb = new StringBuilder();
	    try {
	    	String path = bundle.getString("log.file.path");
	    	File file = new File(path);
	    	BufferedReader br = new BufferedReader(new FileReader(file));
	    	sb = new StringBuilder();
	    	String line;
	    	while ((line = br.readLine()) != null) {
	    		sb.append(line).append("<br>");
	    	}
	    	br.close();
	    }catch(IOException e) {
	    	e.printStackTrace();
	    }
	    return sb.toString();
	  }
}

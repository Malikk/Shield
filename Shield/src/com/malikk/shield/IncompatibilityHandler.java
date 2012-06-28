package com.malikk.shield;

import java.util.ArrayList;

public class IncompatibilityHandler {

	Shield plugin;
	
	ArrayList<String> known = new ArrayList<String>();
	
	public IncompatibilityHandler(Shield instance){
		plugin = instance;
	}
	
	public void incompatible(String name, String method, String lookingFor, String instead){
		String warning = name + "." + method;
		
		if (!known.contains(warning)){
			plugin.logWarning(String.format("%s API does not support '%s' for %s. Instead, %s", name, method, lookingFor, instead));
			known.add(warning);
		}
		
	}
	
}

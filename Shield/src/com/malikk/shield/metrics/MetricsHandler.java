package com.malikk.shield.metrics;

import java.io.IOException;

import com.malikk.shield.Shield;
import com.malikk.shield.plugins.Protect;

public class MetricsHandler {

	Shield plugin;
	Metrics metrics = null;
	
	public MetricsHandler(Shield instance){
		plugin = instance;
		
		try {
			metrics = new Metrics(plugin);
			startMetrics();
		} catch (IOException e) {
			plugin.logWarning("Failed to start plugin Metrics.");
		}
	}
	
	private void startMetrics(){
		
		setGraphPlotters();
		
		metrics.start();
	}
	
	private void setGraphPlotters(){
		metrics.addCustomData(new Metrics.Plotter("WorldGuard"){

			@Override
			public int getValue() {
				return getUsage(plugin.worldGuard);
			}
			
		});
		
		metrics.addCustomData(new Metrics.Plotter("Residence"){

			@Override
			public int getValue() {
				return getUsage(plugin.residence);
			}
			
		});
		
		metrics.addCustomData(new Metrics.Plotter("Regios"){

			@Override
			public int getValue() {
				return getUsage(plugin.regios);
			}
			
		});
		
		metrics.addCustomData(new Metrics.Plotter("PreciousStones"){

			@Override
			public int getValue() {
				return getUsage(plugin.preciousStones);
			}
			
		});
	}
	
	private int getUsage(Protect c){
		return (c != null ? 1 : 0);
	}
}

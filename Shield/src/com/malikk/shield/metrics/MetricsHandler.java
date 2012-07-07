package com.malikk.shield.metrics;

import java.io.IOException;

import com.malikk.shield.Shield;
import com.malikk.shield.metrics.Metrics.Graph;
import com.malikk.shield.plugins.Protect;

public class MetricsHandler {

	Shield plugin;
	
	public MetricsHandler(Shield instance){
		plugin = instance;
		
		startMetrics();
	}
	
	private void startMetrics(){
		
		try {
			Metrics metrics = new Metrics(plugin);
			
			Graph graph = metrics.createGraph("Protection Plugins Used");
			setGraphPlotters(graph);
			
			metrics.start();
			
		} catch (IOException e) {
			plugin.logWarning("Failed to start plugin Metrics.");
		}
		
	}
	
	private void setGraphPlotters(Graph graph){
		graph.addPlotter(new Metrics.Plotter("WorldGuard"){

			@Override
			public int getValue() {
				return getUsage(plugin.worldGuard);
			}
			
		});
		
		graph.addPlotter(new Metrics.Plotter("Residence"){

			@Override
			public int getValue() {
				return getUsage(plugin.residence);
			}
			
		});
		
		graph.addPlotter(new Metrics.Plotter("Regios"){

			@Override
			public int getValue() {
				return getUsage(plugin.regios);
			}
			
		});
		
		graph.addPlotter(new Metrics.Plotter("PreciousStones"){

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

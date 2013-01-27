package com.malikk.shield.metrics;

import java.io.IOException;

import com.malikk.shield.Shield;
import com.malikk.shield.plugins.Protect;
import com.malikk.shield.plugins.ProtectInfo;

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

		for (ProtectInfo info: ProtectInfo.values()){
			final Protect p = info.getProtectObject();
			metrics.addCustomData(new Metrics.Plotter(info.getName()){

				@Override
				public int getValue() {
					return getUsage(p);
				}

			});
		}
	}

	private int getUsage(Protect c){
		return (c != null ? 1 : 0);
	}
}

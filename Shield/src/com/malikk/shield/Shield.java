/*
 * Copyright 2012 Jordan Hobgood
 * 
 * This file is part of Shield.
 *
 * Shield is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Shield is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Shield.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.malikk.shield;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import com.malikk.shield.flags.*;
import com.malikk.shield.metrics.MetricsHandler;
import com.malikk.shield.plugins.*;
import com.malikk.shield.regions.RegionManager;


/**
 * <b>Shield</b> - A collection of common Bukkit protection plugin API
 * <p>
 * For help implementing Shield, please visit the <a href="https://github.com/Malikk/Shield/wiki">Shield Wiki</a>
 * <p>
 * <img src = "https://dl.dropbox.com/u/59837317/Shield/LogoSmall.png">
 * @author Malikk
 * @version Beta 1.2, 1/27/13
 */
public class Shield extends JavaPlugin{

	protected Logger logger = null;

	private boolean foundPlugin = false;

	//Shield Classes
	public PartialSupportNotifier notifier = new PartialSupportNotifier(this);
	public FlagPersister flagPersister = new FlagPersister(this);
	public ShieldConfig config = new ShieldConfig(this);
	public ProtectionManager pm = new ProtectionManager(this);
	public FlagManager fm = new FlagManager(this);
	public RegionManager rm = new RegionManager(this);

	@Override
	public void onEnable(){

		logger = getLogger();

		registerAPI();

		try {
			loadPlugins();
		} catch (Exception e) {
			e.printStackTrace();
		}

		config.loadConfig();

		Flag.shield = this;
		flagPersister.load();

		new MetricsHandler(this);

		log("Enabled");
	}

	@Override
	public void onDisable(){
		flagPersister.save();

		getServer().getScheduler().cancelTasks(this);

		log("Disabled");
	}

	public void log(String msg){
		logger.log(Level.INFO, msg);
	}

	public void logWarning(String msg){
		logger.log(Level.WARNING, msg);
	}

	public void logSevere(String msg){
		logger.log(Level.SEVERE, msg);
	}

	private void registerAPI(){
		ShieldAPI api = new ShieldAPIManager(this);
		Bukkit.getServicesManager().register(ShieldAPI.class, api, this, ServicePriority.Normal);
	}

	/**
	 * Gets the Shield API Interface
	 * @return ShieldAPI
	 */
	public ShieldAPI getAPI(){
		RegisteredServiceProvider<ShieldAPI> provider = getServer().getServicesManager().getRegistration(com.malikk.shield.ShieldAPI.class);
		ShieldAPI api = provider.getProvider();
		return api;
	}

	private void loadPlugins() throws IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{

		log("Scanning for supported protection plugins...");

		for (ProtectInfo plugin: ProtectInfo.values()){
			if (foundClass(plugin.getPack())){
				plugin.setProtectObject(plugin.getProtectClass().getConstructor(Shield.class).newInstance(this));
				plugin.getProtectObject().sendDetectMessage();
			}
		}

		if (foundPlugin == false){
			log("No supported protection plugins found.");
		}
	}

	private boolean foundClass(String className){
		try{
			Class.forName(className);
			foundPlugin = true;
			return true;
		}catch (Exception e){
			return false;
		}
	}

}

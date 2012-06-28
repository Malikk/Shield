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

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import com.malikk.shield.flags.*;
import com.malikk.shield.metrics.Metrics;
import com.malikk.shield.plugins.*;
import com.malikk.shield.regions.RegionManager;


/**
 * <b>Shield</b> - A collection of common Bukkit protection plugin API
 * <p>
 * For help implementing Shield, please visit the <a href="https://github.com/Malikk/Shield">Shield Github</a>
 * <p>
 * <img src="/Users/Jordan/git/Shield/Shield/lib/Logo.png">
 * @author Malikk
 * @version v0.1, 6-13-2012
 */
public class Shield extends JavaPlugin{
	
	protected Logger log = Logger.getLogger("Minecraft");
	PluginDescriptionFile pdfile = null;
	
	private boolean foundPlugin = false;
	
	//Plugin Classes
	public Protect_PreciousStones preciousStones = null;
	public Protect_Regios regios = null;
	public Protect_Residence residence = null;
	public Protect_WorldGuard worldGuard = null;
	
	//Shield Classes
	public IncompatibilityHandler incompat = new IncompatibilityHandler(this);
	public FlagPersister flagPersister = new FlagPersister(this);
	public ShieldConfig config = new ShieldConfig(this);
	public ShieldPluginManager pm = new ShieldPluginManager(this);
	public FlagManager fm = new FlagManager(this);
	public RegionManager rm = new RegionManager(this);
	
	public void onEnable(){
		pdfile = this.getDescription();
		
		loadPlugins();
		registerAPI();
		
		config.loadConfig();
		
		//flagPersister.load();
		
		startMetrics();
		
		log("Enabled");
	}
	
	public void onDisable(){
		flagPersister.save();
		
		log("Disabled");
	}
	
	public void log(String msg){
		log.info("[" + pdfile.getName() + "] " + msg);
	}
	
	public void logWarning(String msg){
		log.warning("[" + pdfile.getName() + "] " + msg);
	}
	
	public void logSevere(String msg){
		log.severe("[" + pdfile.getName() + "] " + msg);
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
	
	private void loadPlugins(){
		
		log("Scanning for supported protection plugins...");
		
		//Attempt to load PreciousStones
		if (packageExists("net.sacredlabyrinth.Phaed.PreciousStones.PreciousStones")){
			preciousStones = new Protect_PreciousStones(this);
			log(String.format("Detected PreciousStones: %s", preciousStones.isEnabled() ? "Hooked" : "Waiting"));
		}
		
		//Attempt to load Regios
		if (packageExists("couk.Adamki11s.Regios.Main.Regios")){
			regios = new Protect_Regios(this);
			log(String.format("Detected Regios: %s", regios.isEnabled() ? "Hooked" : "Waiting"));
		}
		
		//Attempt to load Residence
		if (packageExists("com.bekvon.bukkit.residence.Residence")){
			residence = new Protect_Residence(this);
			log(String.format("Detected Residence: %s", residence.isEnabled() ? "Hooked" : "Waiting"));
		}
		
		//Attempt to load WorldGuard
		if (packageExists("com.sk89q.worldguard.bukkit.WorldGuardPlugin")){
			worldGuard = new Protect_WorldGuard(this);
			log(String.format("Detected WorldGuard: %s", worldGuard.isEnabled() ? "Hooked" : "Waiting"));
		}
		
		if (foundPlugin == false){
			log("No supported protection plugins found.");
		}
	}
	
	private boolean packageExists(String...packages){
		try{
			for (String pkg : packages){
				Class.forName(pkg);
			}
			foundPlugin = true;
			return true;
		}catch (Exception e){
			return false;
		}
	}
	
	public void startMetrics(){
		try{
			Metrics metrics = new Metrics(this);
			metrics.start();
		}catch(Exception e){
			//ignore exception
		}
	}

}

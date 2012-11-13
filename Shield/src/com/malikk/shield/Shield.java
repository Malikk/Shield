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

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import com.malikk.shield.flags.Flag;
import com.malikk.shield.flags.FlagManager;
import com.malikk.shield.flags.FlagPersister;
import com.malikk.shield.metrics.MetricsHandler;
import com.malikk.shield.plugins.PartialSupportNotifier;
import com.malikk.shield.plugins.Protect_PreciousStones;
import com.malikk.shield.plugins.Protect_Regios;
import com.malikk.shield.plugins.Protect_Residence;
import com.malikk.shield.plugins.Protect_Towny;
import com.malikk.shield.plugins.Protect_WorldGuard;
import com.malikk.shield.plugins.ProtectionManager;
import com.malikk.shield.regions.RegionManager;


/**
 * <b>Shield</b> - A collection of common Bukkit protection plugin API
 * <p>
 * For help implementing Shield, please visit the <a href="https://github.com/Malikk/Shield/wiki">Shield Wiki</a>
 * <p>
 * <img src = "https://dl.dropbox.com/u/59837317/Shield/LogoSmall.png">
 * @author Malikk
 * @version Beta 1.0.0.1, 8-17-2012
 */
public class Shield extends JavaPlugin{
	
	private boolean foundPlugin = false;
	
	//Plugin Classes
	public Protect_PreciousStones preciousStones = null;
	public Protect_Regios regios = null;
	public Protect_Residence residence = null;
	public Protect_WorldGuard worldGuard = null;
	public Protect_Towny towny = null;
	
	//Shield Classes
	public PartialSupportNotifier notifier = new PartialSupportNotifier(this);
	public FlagPersister flagPersister = new FlagPersister(this);
	public ShieldConfig config = new ShieldConfig(this);
	public ProtectionManager pm = new ProtectionManager(this);
	public FlagManager fm = new FlagManager(this);
	public RegionManager rm = new RegionManager(this);
	
	@Override
    public void onEnable(){
		
		loadPlugins();
		registerAPI();
		
		this.config.loadConfig();
		
		Flag.shield = this;
		
		this.flagPersister.load();
		
		new MetricsHandler(this);
		
		log("Enabled");
	}
	
	@Override
    public void onDisable(){
		this.flagPersister.save();
		
		getServer().getScheduler().cancelTasks(this);
		
		log("Disabled");
	}
	
	public void log(String msg){
	        getLogger().log(Level.INFO, msg);
	}
	
	public void logWarning(String msg){
	        getLogger().log(Level.WARNING, msg);
	}
	
	public void logSevere(String msg){
	        getLogger().log(Level.SEVERE, msg);
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
		if (foundClass("net.sacredlabyrinth.Phaed.PreciousStones.PreciousStones")){
			this.preciousStones = new Protect_PreciousStones(this);
			log(String.format("Detected PreciousStones: %s", this.preciousStones.isEnabled() ? "Hooked v" + this.preciousStones.getVersion() : "Waiting"));
		}
		
		//Attempt to load Regios
		if (foundClass("couk.Adamki11s.Regios.Main.Regios")){
			this.regios = new Protect_Regios(this);
			log(String.format("Detected Regios: %s", this.regios.isEnabled() ? "Hooked v" + this.regios.getVersion() : "Waiting"));
		}
		
		//Attempt to load Residence
		if (foundClass("com.bekvon.bukkit.residence.Residence")){
			this.residence = new Protect_Residence(this);
			log(String.format("Detected Residence: %s", this.residence.isEnabled() ? "Hooked v" + this.residence.getVersion() : "Waiting"));
		}
		
		//Attempt to load WorldGuard
		if (foundClass("com.sk89q.worldguard.bukkit.WorldGuardPlugin")){
			this.worldGuard = new Protect_WorldGuard(this);
			log(String.format("Detected WorldGuard: %s", this.worldGuard.isEnabled() ? "Hooked v" + this.worldGuard.getVersion() : "Waiting"));
		}
		
		//Attempt to load Towny
		if (foundClass("com.palmergames.bukkit.towny.Towny")){
			this.towny = new Protect_Towny(this);
			log(String.format("Detected Towny: %s", this.towny.isEnabled() ? "Hooked v" + this.towny.getVersion() : "Waiting"));
		}
		
		if (this.foundPlugin == false){
			log("No supported protection plugins found.");
		}
	}
	
	private boolean foundClass(String className){
		try{
			Class.forName(className);
			this.foundPlugin = true;
			return true;
		}catch (Exception e){
			return false;
		}
	}
	
}

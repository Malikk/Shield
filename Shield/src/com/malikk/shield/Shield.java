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
 * @version Beta 1.0.0.1, 8-17-2012
 */
public class Shield extends JavaPlugin{

	protected Logger logger = null;

	private boolean foundPlugin = false;

	//Plugin Classes
	public Protect_PreciousStones preciousStones = null;
	public Protect_Regios regios = null;
	public Protect_Residence residence = null;
	public Protect_WorldGuard worldGuard = null;
	public Protect_Towny towny = null;
	public Protect_AntiShare antishare = null;

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

		loadPlugins();
		registerAPI();

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

	private void loadPlugins(){

		log("Scanning for supported protection plugins...");

		//Attempt to load PreciousStones
		if (foundClass("net.sacredlabyrinth.Phaed.PreciousStones.PreciousStones")){
			preciousStones = new Protect_PreciousStones(this);
			log(String.format("Detected PreciousStones: %s", preciousStones.isEnabled() ? "Hooked v" + preciousStones.getVersion() : "Waiting"));
		}

		//Attempt to load Regios
		if (foundClass("net.jzx7.regios.RegiosPlugin")){
			regios = new Protect_Regios(this);
			log(String.format("Detected Regios: %s", regios.isEnabled() ? "Hooked v" + regios.getVersion() : "Waiting"));
		}

		//Attempt to load Residence
		if (foundClass("com.bekvon.bukkit.residence.Residence")){
			residence = new Protect_Residence(this);
			log(String.format("Detected Residence: %s", residence.isEnabled() ? "Hooked v" + residence.getVersion() : "Waiting"));
		}

		//Attempt to load WorldGuard
		if (foundClass("com.sk89q.worldguard.bukkit.WorldGuardPlugin")){
			worldGuard = new Protect_WorldGuard(this);
			log(String.format("Detected WorldGuard: %s", worldGuard.isEnabled() ? "Hooked v" + worldGuard.getVersion() : "Waiting"));
		}

		//Attempt to load Towny
		if (foundClass("com.palmergames.bukkit.towny.Towny")){
			towny = new Protect_Towny(this);
			log(String.format("Detected Towny: %s", towny.isEnabled() ? "Hooked v" + towny.getVersion() : "Waiting"));
		}
		
		// Attempt to load AntiShare
		if (foundClass("com.turt2live.antishare.AntiShare")){
			antishare=new Protect_AntiShare(this);
			log(String.format("Detected AntiShare: %s", antishare.isEnabled() ? "Hooked v" + antishare.getVersion() : "Waiting"));
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

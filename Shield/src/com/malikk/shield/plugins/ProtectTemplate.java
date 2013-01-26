package com.malikk.shield.plugins;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import com.malikk.shield.Shield;

/**
 * These is a template to reduce code duplication.
 * 
 * @author Malikk, IDragonfire
 * 
 */
public abstract class ProtectTemplate implements Listener, Protect {

	protected Shield shield;

	protected final String name;
	protected final String pack;
	protected Plugin plugin = null;

	public ProtectTemplate(Shield instance, String name, String pack) {
		this.shield = instance;
		this.name = name;
		this.pack = pack;

		PluginManager pm = shield.getServer().getPluginManager();
		pm.registerEvents(this, shield);

		// Load plugin if it was loaded before Shield
		if (plugin == null) {
			Plugin p = shield.getServer().getPluginManager().getPlugin(name);

			if (p != null && p.isEnabled() && p.getClass().getName().equals(pack)) {
				hook(p);
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPluginEnable(PluginEnableEvent event) {
		if (plugin == null) {
			Plugin p = shield.getServer().getPluginManager().getPlugin(name);

			if (p != null && p.isEnabled() && p.getClass().getName().equals(pack)) {
				hook(p);
				shield.log(String.format("Hooked %s v" + getVersion(), name));
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPluginDisable(PluginDisableEvent event) {
		if (plugin!= null) {
			if (event.getPlugin().getDescription().getName().equals(name)) {
				unhook();
			}
		}
	}

	protected void hook(Plugin p) {
		plugin = p;
		init();
	}

	private void unhook(){
		plugin = null;
		shield.log(String.format("%s unhooked.", name));
	}

	@Override
	public boolean isEnabled() {
		return (plugin == null ? false : true);
	}

	@Override
	public String getPluginName() {
		return name;
	}

	@Override
	public String getVersion() {
		return plugin.getDescription().getVersion();
	}

}
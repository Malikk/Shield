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

	protected ProtectInfo info;
	protected Plugin plugin = null;

	public ProtectTemplate(Shield instance, ProtectInfo info) {
		this.shield = instance;
		this.info = info;

		PluginManager pm = shield.getServer().getPluginManager();
		pm.registerEvents(this, shield);

		// Load plugin if it was loaded before Shield
		if (plugin == null) {
			Plugin p = shield.getServer().getPluginManager().getPlugin(info.getName());

			if (p != null && p.isEnabled() && p.getClass().getName().equals(info.getPack())) {
				hook(p);
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPluginEnable(PluginEnableEvent event) {
		if (plugin == null) {
			Plugin p = shield.getServer().getPluginManager().getPlugin(info.getName());

			if (p != null && p.isEnabled() && p.getClass().getName().equals(info.getPack())) {
				hook(p);
				sendHookMessage();
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPluginDisable(PluginDisableEvent event) {
		if (plugin!= null) {
			if (event.getPlugin().getDescription().getName().equals(info.getName())) {
				unhook();
			}
		}
	}

	protected void hook(Plugin p) {
		plugin = p;
		shield.pm.addClassToInstantiatedSet(info.getProtectObject());
		init();
	}

	protected void unhook(){
		plugin = null;
	}

	@Override
	public boolean isEnabled() {
		return (plugin == null ? false : true);
	}

	@Override
	public String getPluginName() {
		return info.getName();
	}

	@Override
	public String getVersion() {
		return plugin.getDescription().getVersion();
	}

	@Override
	public void sendDetectMessage(){
		shield.log(String.format("Detected %s: %s", getPluginName(), isEnabled() ? "Hooked v" + getVersion() : "Waiting"));
	}

	@Override
	public void sendHookMessage(){
		shield.log(String.format("Hooked %s v" + getVersion(), info.getName()));
	}

	@Override
	public void sendUnhookMessage(){
		shield.log(String.format("%s unhooked.", getPluginName()));
	}

}
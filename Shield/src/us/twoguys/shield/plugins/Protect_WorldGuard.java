package us.twoguys.shield.plugins;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;

import us.twoguys.shield.*;

public class Protect_WorldGuard implements Protection, Listener{
	
	Shield shield;
	
	private final String name = "WorldGuard";
	private final String pack = "com.sk89q.worldguard.bukkit.WorldGuardPlugin";
	protected WorldGuardPlugin protect = null;
	
	public Protect_WorldGuard(Shield instance){
		this.shield = instance;
		PluginManager pm = shield.getServer().getPluginManager();
		pm.registerEvents(this, shield);
		
        //Load plugin if it was loaded before Shield
        if (protect == null) {
            Plugin p = shield.getServer().getPluginManager().getPlugin(name);
            
            if (p != null && p.isEnabled() && p.getClass().getName().equals(pack)) {
                protect = (WorldGuardPlugin) p;
                shield.log(String.format("%s hooked.", name));
            }
        }
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPluginEnable(PluginEnableEvent event) {
		if (protect == null) {
			Plugin p = shield.getServer().getPluginManager().getPlugin(name);

			if (p != null && p.isEnabled() && p.getClass().getName().equals(pack)) {
				protect = (WorldGuardPlugin) p;
				shield.log(String.format("%s hooked.", name));
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPluginDisable(PluginDisableEvent event) {
		if (protect != null) {
			if (event.getPlugin().getDescription().getName().equals(name)) {
				protect = null;
				shield.log(String.format("%s unhooked.", name));
			}
		}
	}

	@Override
	public boolean isEnabled() {
		if (protect == null) {
			return false;
		}else{
			return protect.isEnabled();
		}
	}
	
	@Override
	public String getName(){
		return name;
	}
	
	@Override
	public boolean isInRegion(Player player){
		ApplicableRegionSet regionSet = protect.getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation());
		
		if (regionSet.size() > 0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean canBuild(Player player) {
		return protect.canBuild(player, player.getLocation());
	}

	@Override
	public boolean canBuild(Player player, Location loc) {
		return protect.canBuild(player, loc);
	}
	
}

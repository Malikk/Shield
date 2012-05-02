package us.twoguys.shield.plugins;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

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
		return (protect == null ? false : protect.isEnabled());
	}
	
	@Override
	public String getPluginName() {
		return name;
	}
	
	@Override
	public boolean isInRegion(Entity entity) {
		ApplicableRegionSet regionSet = getAppRegionSet(entity);
		
		return (regionSet.size() > 0 ? true : false);
	}
	
	@Override
	public String getRegionOccupiedBy(Entity entity) {
		ApplicableRegionSet regionSet = getAppRegionSet(entity);
		ProtectedRegion priority = null;
		
		for (ProtectedRegion region: regionSet){
			if (priority == null){
				priority = region;
			}else if (region.getPriority() > priority.getPriority()){
				priority = region;
			}
		}
		
		return (priority == null ? null : priority.getId());
	}
	
	@Override
	public boolean isInRegion(Location loc) {
		return (getAppRegionSet(loc) != null ? true : false);
	}
	
	@Override
	public boolean isInRegion(Location loc, String regionName) {
		if (ProtectedRegion.isValidId(regionName)){return false;}
		
		ProtectedRegion region = protect.getRegionManager(loc.getWorld()).getRegion(regionName);
		
		return (region.contains(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()) == true ? true: false);
	}

	@Override
	public boolean canBuild(Player player) {
		return protect.canBuild(player, player.getLocation());
	}

	@Override
	public boolean canBuild(Player player, Location loc) {
		return protect.canBuild(player, loc);
	}
	
	@Override
	public boolean canUse(Player player){
		ApplicableRegionSet regionSet = getAppRegionSet((Entity)player);
		LocalPlayer lPlayer = protect.wrapPlayer(player);
		
		if (regionSet.size() == 0){return true;}
		
		return (regionSet.allows(DefaultFlag.USE, lPlayer) ? true : false);
	}
	
	@Override
	public boolean canUse(Player player, Location loc){
		ApplicableRegionSet regionSet = getAppRegionSet(loc);
		LocalPlayer lPlayer = protect.wrapPlayer(player);
		
		if (regionSet.size() == 0){return true;}
		
		return (regionSet.allows(DefaultFlag.USE, lPlayer) ? true : false);
	}
	
	@Override
	public boolean canOpen(Player player){
		ApplicableRegionSet regionSet = getAppRegionSet((Entity)player);
		LocalPlayer lPlayer = protect.wrapPlayer(player);
		
		if (regionSet.size() == 0){return true;}
		
		return (regionSet.allows(DefaultFlag.CHEST_ACCESS, lPlayer) ? true : false);
	}
	
	@Override
	public boolean canOpen(Player player, Location loc){
		ApplicableRegionSet regionSet = getAppRegionSet(loc);
		LocalPlayer lPlayer = protect.wrapPlayer(player);
		
		if (regionSet.size() == 0){return true;}
		
		return (regionSet.allows(DefaultFlag.CHEST_ACCESS, lPlayer) ? true : false);
	}
	
	public ApplicableRegionSet getAppRegionSet(Entity entity){
		return protect.getRegionManager(entity.getWorld()).getApplicableRegions(entity.getLocation());
	}
	
	public ApplicableRegionSet getAppRegionSet(Location loc){
		return protect.getRegionManager(loc.getWorld()).getApplicableRegions(loc);
	}

}

package com.malikk.shield.plugins;

import java.util.HashSet;

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

import com.malikk.shield.Shield;
import com.malikk.shield.regions.ShieldRegion;

/**
 * These is a template to reduce code duplication.
 * 
 * @author IDragonfire
 * 
 */
public abstract class ProtectTemplate implements Listener, Protect {

    protected Shield shield;

    protected final String name;
    protected final String pack;
    protected static Plugin protect = null;

    public ProtectTemplate(Shield instance, String name, String pack) {
        shield = instance;
        this.name = name;
        this.pack = pack;

        PluginManager pm = shield.getServer().getPluginManager();
        pm.registerEvents(this, shield);

        // Load plugin if it was loaded before Shield
        // TODO: not needed, if has soft dependency
        if (protect == null) {
            Plugin p = shield.getServer().getPluginManager().getPlugin(name);

            if (p != null && p.isEnabled()
                    && p.getClass().getName().equals(pack)) {
                protect = p;
                init(p);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginEnable(PluginEnableEvent event) {
        // TODO: over event in plugin
        if (protect == null) {
            Plugin p = shield.getServer().getPluginManager().getPlugin(name);

            if (p != null && p.isEnabled()
                    && p.getClass().getName().equals(pack)) {
                init(p);
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

    private void init(Plugin p) {
        protect = p;
        shield.pm.addClassToInstantiatedSet(shield.worldGuard);
        shield.log(String.format("Hooked %s v" + getVersion(), name));
    }

    public boolean isEnabled() {
        return (protect == null ? false : true);
    }

    @Override
    public String getPluginName() {
        return name;
    }

    @Override
    public String getVersion() {
        return protect.getDescription().getVersion();
    }

    @Override
    public boolean canBuild(Player player) {
        return canBuild(player, player.getLocation());
    }

    @Override
    public boolean canUse(Player player) {
        return canUse(player, player.getLocation());
    }

    @Override
    public boolean canOpen(Player player) {
        return canOpen(player, player.getLocation());
    }

    @Override
    public boolean isInRegion(Location loc) {
        return (getRegions(loc).size() > 0 ? true : false);
    }

    @Override
    public boolean isInRegion(Entity entity) {
        return (getRegions(entity).size() > 0 ? true : false);
    }

    @Override
    public HashSet<ShieldRegion> getRegions(Entity entity) {
        return getRegions(entity.getLocation());
    }
}
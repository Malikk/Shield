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

package com.malikk.shield.plugins;

import java.util.HashSet;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.malikk.shield.Shield;
import com.malikk.shield.groups.ShieldGroup;
import com.malikk.shield.regions.ShieldRegion;
import com.palmergames.bukkit.towny.Towny;

/**
 * Towny
 * @version v0.82.0.0 for CB 1.3.2-R3.0
 */
public class Protect_Towny extends ProtectTemplate {

	private Towny protect = null;

	public Protect_Towny(Shield instance){
		super(instance, ProtectInfo.TOWNY);
	}

	@Override
	public void init(){
		//Leave this out until the class is finished
		//shield.pm.addClassToInstantiatedSet(shield.towny);
		protect = (Towny) plugin;
	}

	@Override
	protected void hook(Plugin p){
		//Overriding template hook method and leaving blank. This is so the class isn't used while it's not finished.
		shield.logWarning("There is currently no support for Towny. It only has a placeholder class and is hooked.");
	}

	@Override
	public HashSet<ShieldRegion> getRegions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashSet<ShieldRegion> getRegions(Entity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashSet<ShieldRegion> getRegions(Location loc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isInRegion(Entity entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInRegion(Location loc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canBuild(Player player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canBuild(Player player, Location loc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canUse(Player player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canUse(Player player, Location loc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canOpen(Player player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canOpen(Player player, Location loc) {
		// TODO Auto-generated method stub
		return false;
	}

	//Region info Getters
	@Override
	public boolean contains(ShieldRegion region, Location loc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ShieldGroup getOwners(ShieldRegion region) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ShieldGroup getMembers(ShieldRegion region) {
		// TODO Auto-generated method stub
		return null;
	}

}


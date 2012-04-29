package us.twoguys.shield.plugins;

import us.twoguys.shield.*;

public class WorldGuard implements Protection{
	
	Shield plugin;
	
	public WorldGuard(Shield instance){
		plugin = instance;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

}

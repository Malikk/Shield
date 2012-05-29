package us.twoguys.shield;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import us.twoguys.shield.plugins.Protect;

public class ShieldPluginManager implements Protect{
	
	Shield plugin;
	static ArrayList<String> plugins = new ArrayList<String>();
	
	public ShieldPluginManager(Shield instance){
		plugin = instance;
	}
	
	public boolean isValidRegion(String region){
		ArrayList<String> names = getRegions();
		
		return (names.contains(region) ? true : false);
	}
	
	@Override
	public ArrayList<String> getRegions(){
		Object[] args = {};
		
		@SuppressWarnings("unchecked")
		ArrayList<String> outcomes = (ArrayList<String>) getOutcomes("getRegions", "ArrayList<String>", args);
		
		return outcomes;
	}
	
	@Override
	public ArrayList<String> getRegions(Entity entity){
		Object[] args = {entity};
		
		@SuppressWarnings("unchecked")
		ArrayList<String> outcomes = (ArrayList<String>) getOutcomes("getRegions", "ArrayList<String>", args);
		
		return outcomes;
	}
	
	@Override
	public ArrayList<String> getRegions(Location loc){
		Object[] args = {loc};
		
		@SuppressWarnings("unchecked")
		ArrayList<String> outcomes = (ArrayList<String>) getOutcomes("getRegions", "ArrayList<String>", args);
		
		return outcomes;
	}
	
	/**
	 * Returns true if ANY Protect classes return true
	 */
	@Override
	public boolean isInRegion(Entity entity) {
		Object[] args = {entity};
		
		@SuppressWarnings("unchecked")
		ArrayList<Boolean> outcomes = (ArrayList<Boolean>) getOutcomes("isInRegion", "boolean", args);
		
		for (boolean outcome: outcomes){
			if (outcome == true){
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns true if ANY Protect classes return true
	 */
	@Override
	public boolean isInRegion(Location loc) {
		Object[] args = {loc};
		
		@SuppressWarnings("unchecked")
		ArrayList<Boolean> outcomes = (ArrayList<Boolean>) getOutcomes("isInRegion", "boolean", args);
		
		for (boolean outcome: outcomes){
			if (outcome == true){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Only returns true if EVERY Protect class returns true
	 */
	@Override
	public boolean canBuild(Player player) {
		Object[] args = {player};
		
		@SuppressWarnings("unchecked")
		ArrayList<Boolean> outcomes = (ArrayList<Boolean>) getOutcomes("canBuild", "boolean", args);
		
		for (boolean outcome: outcomes){
			if (outcome == false){
				return false;
			}
		}
		return true;
	}

	/**
	 * Only returns true is EVERY Protect class returns true
	 */
	@Override
	public boolean canBuild(Player player, Location loc) {
		Object[] args = {player, loc};
		
		@SuppressWarnings("unchecked")
		ArrayList<Boolean> outcomes = (ArrayList<Boolean>) getOutcomes("canBuild", "boolean", args);
		
		for (boolean outcome: outcomes){
			if (outcome == false){
				return false;
			}
		}
		return true;
	}

	/**
	 * Only returns true is EVERY Protect class returns true
	 */
	@Override
	public boolean canUse(Player player) {
		Object[] args = {player};
		
		@SuppressWarnings("unchecked")
		ArrayList<Boolean> outcomes = (ArrayList<Boolean>) getOutcomes("canUse", "boolean", args);
		
		for (boolean outcome: outcomes){
			if (outcome == false){
				return false;
			}
		}
		return true;
	}

	/**
	 * Only returns true is EVERY Protect class returns true
	 */
	@Override
	public boolean canUse(Player player, Location loc) {
		Object[] args = {player, loc};
		
		@SuppressWarnings("unchecked")
		ArrayList<Boolean> outcomes = (ArrayList<Boolean>) getOutcomes("canUse", "boolean", args);
		
		for (boolean outcome: outcomes){
			if (outcome == false){
				return false;
			}
		}
		return true;
	}

	/**
	 * Only returns true is EVERY Protect class returns true
	 */
	@Override
	public boolean canOpen(Player player) {
		Object[] args = {player};
		
		@SuppressWarnings("unchecked")
		ArrayList<Boolean> outcomes = (ArrayList<Boolean>) getOutcomes("canOpen", "boolean", args);
		
		for (boolean outcome: outcomes){
			if (outcome == false){
				return false;
			}
		}
		return true;
	}

	/**
	 * Only returns true is EVERY Protect class returns true
	 */
	@Override
	public boolean canOpen(Player player, Location loc) {
		Object[] args = {player, loc};
		
		@SuppressWarnings("unchecked")
		ArrayList<Boolean> outcomes = (ArrayList<Boolean>) getOutcomes("canOpen", "boolean", args);
		
		for (boolean outcome: outcomes){
			if (outcome == false){
				return false;
			}
		}
		return true;
	}
	
	public void addClassToInstantiatedPluginClassesArrayList(String className){
		plugins.add("Protect_" + className);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<?> getOutcomes(String methodName, String outcomeType, Object[] args){
		ArrayList<String> temp = (ArrayList<String>) plugins.clone();
		ArrayList<Boolean> booleanOutcomes = new ArrayList<Boolean>();
		ArrayList<String> stringOutcomes = new ArrayList<String>();
		
		plugin.log("------------" + methodName + "------------");
		
		for (String className: temp){
			Class<?> protect = null;
			try {
				protect = Class.forName("us.twoguys.shield.plugins." + className, true, this.getClass().getClassLoader());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			Object obj = null;
			try {
				obj = ((Class<?>)protect).getConstructor(Class.forName("us.twoguys.shield.Shield")).newInstance(plugin);
			} catch (Exception e){
				e.printStackTrace();
			}
			
			Method[] methods = protect.getDeclaredMethods();
			
			boolean assigned = false;
			
			for (Method method: methods){
				
				if (assigned == true){break;}
				
				Class<?>[] methodArgsTypes = method.getParameterTypes();
				Class<?>[] argsTypes = new Class<?>[args.length];
				
				int counter = 0;
				
				for (Object arg: args){
					if (arg instanceof Player){
						argsTypes[counter] = Player.class;
						counter++;
					}else if (arg instanceof Entity){
						argsTypes[counter] = Entity.class;
						counter++;
					}else if (arg instanceof Location){
						argsTypes[counter] = Location.class;
						counter++;
					}
				}
				
				if (method.getName().equalsIgnoreCase(methodName) && methodArgsTypes.length == argsTypes.length){
					
					if (outcomeType.equalsIgnoreCase("boolean")){
						boolean outcome = false;
						
						try {
							if (methodArgsTypes.length == 0){
								outcome = (Boolean) method.invoke(obj);
								assigned = true;
							}else if (methodArgsTypes.length == 1){
								if (equalsOrExtends(argsTypes[0], methodArgsTypes[0])){
									outcome = (Boolean) method.invoke(obj, args[0]);
									assigned = true;
								}
							}else if (methodArgsTypes.length == 2){
								if (equalsOrExtends(argsTypes[0], methodArgsTypes[0])){
									outcome = (Boolean) method.invoke(obj, args[0], args[1]);
									assigned = true;
								}else if (equalsOrExtends(argsTypes[0], methodArgsTypes[1])){
									outcome = (Boolean) method.invoke(obj, args[1], args[0]);
									assigned = true;
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						if (assigned == true){
							plugin.log(protect.getSimpleName() + ": " + outcome);
							booleanOutcomes.add(outcome);
						}
						
					}else if (outcomeType.equalsIgnoreCase("ArrayList<String>")){
						ArrayList<String> outcome = new ArrayList<String>();
						
						try {
							if (methodArgsTypes.length == 0){
								outcome = (ArrayList<String>) method.invoke(obj);
								assigned = true;
							}else if (methodArgsTypes.length == 1){
								if (equalsOrExtends(argsTypes[0], methodArgsTypes[0])){
									outcome = (ArrayList<String>) method.invoke(obj, args[0]);
									assigned = true;
								}
							}else if (methodArgsTypes.length == 2){
								if (equalsOrExtends(argsTypes[0], methodArgsTypes[0])){
									outcome = (ArrayList<String>) method.invoke(obj, args[0], args[1]);
									assigned = true;
								}else if (equalsOrExtends(argsTypes[0], methodArgsTypes[1])){
									outcome = (ArrayList<String>) method.invoke(obj, args[1], args[0]);
									assigned = true;
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						if (assigned == true){
							for (String s: outcome){
								plugin.log(protect.getSimpleName() + ": " + s);
								stringOutcomes.add(s);
							}
						}
					}
				}
			}
		}
		if (outcomeType.equalsIgnoreCase("boolean")){
			return booleanOutcomes;
		}else if (outcomeType.equalsIgnoreCase("ArrayList<String>")){
			return stringOutcomes;
		}else{
			return booleanOutcomes;
		}
	}
	
	public boolean equalsOrExtends(Class<?> c1, Class<?> c2){
		if (c1 == c2){
			return true;
		}else if (c2.isAssignableFrom(c1)){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean isEnabled() {
		// Dont use
		return false;
	}

	@Override
	public String getPluginName() {
		// Dont use
		return null;
	}

}

package com.malikk.shield.plugins;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.malikk.shield.Shield;
import com.malikk.shield.regions.ShieldRegion;

public class MethodInvoker {
	
	Shield shield;
	
	public static ArrayList<String> plugins = new ArrayList<String>();
	private ArrayList<Boolean> booleanOutcomes = new ArrayList<Boolean>();
	private ArrayList<ShieldRegion> regionOutcomes = new ArrayList<ShieldRegion>();
	
	private Method method = null;
	private Class<?>[] methodArgsTypes = null;
	private Class<?>[] passedArgsTypes = null;
	
	private Object[] args = null;
	private Object obj = null;
	private boolean assigned;
	
	private boolean Boolean = false;
	private boolean ArrayListRegion = false;
	
	public MethodInvoker(Shield instance){
		shield = instance;
	}

	public ArrayList<?> invoke(String methodName, String outcomeType, Object[] args, String plugin){
		
		//plugin.log("------------" + methodName + "------------");
		
		if (outcomeType.equalsIgnoreCase("boolean")){
			Boolean = true;
		}else if (outcomeType.equalsIgnoreCase("ArrayList<Region>")){
			ArrayListRegion = true;
		}
		
		this.args = args;
		
		for (String className: plugins){
			
			//Check if a plugin was specified
			if (plugin != null && !className.contains(plugin)){
				continue;
			}
			
			//plugin.log(className);
			
			Class<?> protect = null;
			try {
				protect = Class.forName("us.twoguys.shield.plugins." + className, true, this.getClass().getClassLoader());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			try {
				obj = ((Class<?>)protect).getConstructor(Class.forName("us.twoguys.shield.Shield")).newInstance(plugin);
			} catch (Exception e){
				e.printStackTrace();
			}
			
			Method[] methods = protect.getDeclaredMethods();
			
			assigned = false;
			
			for (Method method: methods){
				
				if (assigned == true){break;}
				
				this.method = method;
				methodArgsTypes = method.getParameterTypes();
				passedArgsTypes = new Class<?>[args.length];
				
				int counter = 0;
				
				for (Object arg: args){
					if (arg instanceof Player){
						passedArgsTypes[counter] = Player.class;
						counter++;
					}else if (arg instanceof Entity){
						passedArgsTypes[counter] = Entity.class;
						counter++;
					}else if (arg instanceof Location){
						passedArgsTypes[counter] = Location.class;
						counter++;
					}
				}
				
				if (method.getName().equalsIgnoreCase(methodName) && methodArgsTypes.length == passedArgsTypes.length){
					
					if (Boolean){
						setBooleanOutcomes();
					}else if (ArrayListRegion){
						setRegionOutcomes();
					}
				}
			}
		}
		
		//Return appropriate Outcomes
		if (Boolean){
			return booleanOutcomes;
		}else if (ArrayListRegion){
			return regionOutcomes;
		}else{
			return booleanOutcomes;
		}
	}
	
	public void setBooleanOutcomes(){
		boolean outcome = false;
		
		try {
			if (methodArgsTypes.length == 0){
				outcome = (Boolean) method.invoke(obj);
				assigned = true;
			}else if (methodArgsTypes.length == 1){
				if (equalsOrExtends(passedArgsTypes[0], methodArgsTypes[0])){
					outcome = (Boolean) method.invoke(obj, args[0]);
					assigned = true;
				}
			}else if (methodArgsTypes.length == 2){
				if (equalsOrExtends(passedArgsTypes[0], methodArgsTypes[0])){
					outcome = (Boolean) method.invoke(obj, args[0], args[1]);
					assigned = true;
				}else if (equalsOrExtends(passedArgsTypes[0], methodArgsTypes[1])){
					outcome = (Boolean) method.invoke(obj, args[1], args[0]);
					assigned = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (assigned == true){
			//plugin.log(protect.getSimpleName() + ": " + outcome);
			booleanOutcomes.add(outcome);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void setRegionOutcomes(){
		ArrayList<ShieldRegion> outcome = new ArrayList<ShieldRegion>();

		try {
			if (methodArgsTypes.length == 0){
				outcome = (ArrayList<ShieldRegion>) method.invoke(obj);
				assigned = true;
			}else if (methodArgsTypes.length == 1){
				if (equalsOrExtends(passedArgsTypes[0], methodArgsTypes[0])){
					outcome = (ArrayList<ShieldRegion>) method.invoke(obj, args[0]);
					assigned = true;
				}
			}else if (methodArgsTypes.length == 2){
				if (equalsOrExtends(passedArgsTypes[0], methodArgsTypes[0])){
					outcome = (ArrayList<ShieldRegion>) method.invoke(obj, args[0], args[1]);
					assigned = true;
				}else if (equalsOrExtends(passedArgsTypes[0], methodArgsTypes[1])){
					outcome = (ArrayList<ShieldRegion>) method.invoke(obj, args[1], args[0]);
					assigned = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (assigned == true){
			for (ShieldRegion s: outcome){
				//plugin.log(protect.getSimpleName() + ": " + s.getName());
				regionOutcomes.add(s);
			}
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
	
}

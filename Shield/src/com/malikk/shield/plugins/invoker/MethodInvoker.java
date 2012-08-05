package com.malikk.shield.plugins.invoker;

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
	private Location locOutcome = null;
	
	private Method method = null;
	private Class<?>[] methodArgsTypes = null;
	private Class<?>[] passedArgsTypes = null;
	
	private Object[] args = null;
	private Object obj = null;
	private boolean assigned;
	
	private boolean Boolean = false;
	private boolean ArrayListRegion = false;
	private boolean location = false;
	
	public MethodInvoker(Shield instance){
		shield = instance;
	}

	public Object invoke(String methodName, OutcomeType type, Object[] args, String plugin){
		
		shield.log("------------" + methodName + "------------");
		
		if (type == OutcomeType.BOOLEAN){
			Boolean = true;
		}else if (type == OutcomeType.REGION_ARRAY){
			ArrayListRegion = true;
		}else if (type == OutcomeType.LOCATION){
			location = true;
		}
		
		this.args = args;
		
		for (String className: plugins){
			
			//Check if a plugin was specified
			if (plugin != null && !className.contains(plugin)){
				continue;
			}
			
			shield.log(className);
			
			Class<?> protect = null;
			try {
				protect = Class.forName("com.malikk.shield.plugins." + className, true, this.getClass().getClassLoader());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			try{
				obj = protect.getConstructor(com.malikk.shield.Shield.class).newInstance(shield);
			}catch(Exception e){
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
					}else if (arg instanceof ShieldRegion){
						passedArgsTypes[counter] = ShieldRegion.class;
					}
				}
				
				if (method.getName().equalsIgnoreCase(methodName) && methodArgsTypes.length == passedArgsTypes.length){
					
					if (Boolean){
						setBooleanOutcomes();
					}else if (ArrayListRegion){
						setRegionOutcomes();
					}else if (location){
						setLocation();
					}
				}
			}
		}
		
		//Return appropriate Outcomes
		if (Boolean){
			return booleanOutcomes;
		}else if (ArrayListRegion){
			return regionOutcomes;
		}else if (location){
			return locOutcome;
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
			//shield.log(protect.getSimpleName() + ": " + outcome);
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
				//shield.log(protect.getSimpleName() + ": " + s.getName());
				regionOutcomes.add(s);
			}
		}
		
	}
	
	public void setLocation(){
		try {
			if (methodArgsTypes.length == 0){
				locOutcome = (Location) method.invoke(obj);
				assigned = true;
			}else if (methodArgsTypes.length == 1){
				if (equalsOrExtends(passedArgsTypes[0], methodArgsTypes[0])){
					locOutcome = (Location) method.invoke(obj, args[0]);
					assigned = true;
				}
			}else if (methodArgsTypes.length == 2){
				if (equalsOrExtends(passedArgsTypes[0], methodArgsTypes[0])){
					locOutcome = (Location) method.invoke(obj, args[0], args[1]);
					assigned = true;
				}else if (equalsOrExtends(passedArgsTypes[0], methodArgsTypes[1])){
					locOutcome = (Location) method.invoke(obj, args[1], args[0]);
					assigned = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
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

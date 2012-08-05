Shield
======

A collection of common Bukkit protection plugin API

##Supported Plugins
The following plugins are all supported by Shield. Fully supported plugins are capable of all Shield's methods while partially supported plugins may not have certain functions.

Fully Supported:
* Residence (http://dev.bukkit.org/server-mods/residence/)
* WorldGuard (http://dev.bukkit.org/server-mods/worldguard/)
* PreciousStones (http://dev.bukkit.org/server-mods/preciousstones/)

Partially Supported:
* Regios (http://dev.bukkit.org/server-mods/regios/)

###Adding Support for an Unsupported plugin
If you have a plugin that you wish to be supported by Shield, fork the project and fill out the the blank protect_Class template and I'll be happy to add it. 
If you are not a developer, feel free to submit an issue and I'll see if I can add it in myself.

##Hooking in and Using Shield
I have put a lot of time and effort into making Shield as easy to use as possible. Just follow these easy steps.

####Step 1
The first thing you'll need to do is add either a soft depend or full depend to your plugin.yml. This insures that your plugin will load after Shield, so that Shield is available to you.

```code
depend: [Shield]
```
or
```code
softdepend: [Shield]
```
####Step 2
Then, add these fields to the top of your class, as you will be needing these objects. Make sure you've already configured your build path and import the two classes.
	
```java
public Shield shield = null;
public ShieldAPI api = null;
```

####Step 3
Next, on your onEnable, we are going to actually hook in Shield. This code will get an instance of Shield, and then get its api class.

```java
Plugin x = plugin.getServer().getPluginManager().getPlugin("Shield");
if (x != null && x instanceof Shield){
	shield = (Shield) x;
	api = shield.getAPI();
	plugin.log(String.format("Hooked %s %s", shield.getDescription().getName(), shield.getDescription().getVersion()));
}else{
	plugin.log("Shield was not found.");
}
```
Now the two class objects we added as fields to your class are set.

####Step 4
Now that you have set up your 'api' variable, you can use it to get ALL of Shield's API methods in one place. It couldn't be simpler.

```java
boolean inRegion = api.isInRegion(loc);
```

```java
ArrayList<ShieldRegion> regions = api.getRegions();
```

```java
ShieldRegion region = api.getPriorityRegion(location);
```

```java
boolean canBuild = api.canBuild(player);
```

Shield
======

A collection of common Bukkit protection plugin API

##Hooking in Shield
I have put a lot of time and effort into making Shield as easy to use as possible. Just follow these easy steps.
	
####Step 1
First, add these fields to the top of your class, as you will be needing these objects. Make sure you've already configured your build path and import the two classes.
	
```java
public Shield shield = null;
public ShieldAPI api = null;
```

####Step 2
Next, on your onEnable, we are going to actually hook in Shield. This code will get an instance of Shield, and then get its api class.

```java
Plugin x = plugin.getServer().getPluginManager().getPlugin("Shield");
if (x != null & x instanceof Shield){
	shield = (Shield) x;
	api = shield.getAPI();
	plugin.log(String.format("Hooked %s %s", shield.getDescription().getName(), shield.getDescription().getVersion()));
}else{
	plugin.log("Shield was not found.");
}
```
Now the two class objects we added as fields to your class are set.

##Using Shield
Now that you have set up your 'api' variable, you can use it to get ALL of Shield's API methods in one place. It couldn't be simpler.
package com.malikk.shield.plugins;

public enum ProtectInfo {

	WORLDGUARD("WorldGuard",			Protect_WorldGuard.class, 		"com.sk89q.worldguard.bukkit.WorldGuardPlugin"),
	RESIDENCE("Residence", 				Protect_Residence.class, 		"com.bekvon.bukkit.residence.Residence"),
	REGIOS("Regios", 					Protect_Regios.class,			"net.jzx7.regios.RegiosPlugin"),
	ANTISHARE("AntiShare", 				Protect_AntiShare.class,		"com.turt2live.antishare.AntiShare"),
	FACTIONS("Factions", 				Protect_Factions.class,			"com.massivecraft.factions.Factions"),
	TOWNY("Towny", 						Protect_Towny.class,			"com.palmergames.bukkit.towny.Towny"),
	PRECIOUSSTONES("PreciousStones", 	Protect_PreciousStones.class,	"net.sacredlabyrinth.Phaed.PreciousStones.PreciousStones");

	private String name, pack;
	private Protect p;
	private Class<?> c;

	private ProtectInfo(String name, Class<?> c, String pack){
		this.name = name;
		this.pack = pack;
		this.c = c;
	}

	public String getName(){
		return name;
	}

	public String getPack(){
		return pack;
	}

	public Protect getProtectObject(){
		return p;
	}

	public Class<?> getProtectClass(){
		return c;
	}

	public void setProtectObject(Object object){
		this.p = (Protect) object;
	}
}

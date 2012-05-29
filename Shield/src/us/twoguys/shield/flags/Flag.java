package us.twoguys.shield.flags;

import java.io.Serializable;
import java.util.ArrayList;

import org.bukkit.entity.Player;

public class Flag implements Serializable{

	private static final long serialVersionUID = -2914461212107421563L;
	
	private String name;
	private String region;
	private ArrayList<Player> players;
	private boolean value;

	public Flag(String flag, String region, ArrayList<Player> players, boolean value){
		this.name = flag;
		this.region = region;
		this.players = players;
		this.value = value;
	}
	
	public String getName(){
		return name;
	}
	
	public String getRegion(){
		return region;
	}
	
	public ArrayList<Player> getPlayers(){
		return players;
	}
	
	public boolean getValue(){
		return value;
	}
	
	public void setPlayers(ArrayList<Player> players){
		this.players = players;
	}
	
	public void setValue(boolean value){
		this.value = value;
	}
	
	public void addPlayer(Player player){
		players.add(player);
	}
	
	public void removePlayer(Player player){
		players.remove(player);
	}
}

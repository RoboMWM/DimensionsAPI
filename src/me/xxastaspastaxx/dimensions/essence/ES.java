package me.xxastaspastaxx.dimensions.essence;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class ES{
	
	public static HashMap<Player, Integer> pess = new HashMap<Player, Integer>();
	
	
	
	public static void addEssence(Player p, int amo) {
		
		int a = checkEssence(p);
		int na = a + amo;
		
		pess.put(p, na);
	}
	
	public static void removeEssence(Player p, int amo) {
		
		int a = checkEssence(p);
		int na = a - amo;
		
		pess.put(p, na);
	}
	
	public static void setEssence(Player p, int amo) {
		int na = amo;
		
		pess.put(p, na);
	}
	
	public static int checkEssence(Player p) {
		
		int a = pess.get(p);
		

		return a;
	}
	
	public static void resetEssence(Player p) {
		
		pess.put(p, 0);

	}
	//------------------------------------------------------------\\

}

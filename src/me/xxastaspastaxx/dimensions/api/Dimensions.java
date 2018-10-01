package me.xxastaspastaxx.dimensions.api;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.xxastaspastaxx.dimensions.Main;

public class Dimensions {

	public static boolean isInPortal(Player p) {
		if (getPortal(p.getLocation())!=null) {
			return true;
		}
		return false;
	}
	
	public static boolean isInPortal(Player p, String portalName) {
		if (Main.getInstance().portalClass.isInPortal(new CPortal(portalName, p.getLocation()))) {
			return true;
		}
		return false;
	}
	
	public static boolean isInPortal(Player p, CPortal cportal) {
		if (Main.getInstance().portalClass.isInPortal(cportal)) {
			return true;
		}
		return false;
	}
	
	public static CPortal getPortal(Location ploc) {
		return Main.getInstance().portalClass.getPortal(ploc);
	}
	
}

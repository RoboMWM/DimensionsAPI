package me.xxastaspastaxx.dimensions.factionsland;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import me.xxastaspastaxx.dimensions.Main;
import me.xxastaspastaxx.dimensions.api.CPortal;
import net.redstoneore.legacyfactions.entity.Board;
import net.redstoneore.legacyfactions.entity.Faction;
import net.redstoneore.legacyfactions.entity.FactionColl;
import net.redstoneore.legacyfactions.locality.Locality;

public class FactionLandsLF implements Listener {
	//This class is used for "LegacyFactions" plugin
	  public FactionLandsLF(Plugin pl) {
		  
		  
		    Bukkit.getServer().getPluginManager().registerEvents(this, pl);
		  }

	//LegacyFactions\\
	  //TODO you could merge these two functions and classes
		public boolean isNotOnClaimedLand(Player p, CPortal cportal, World w) {
			
			if (Main.getInstance().usingfac && cportal.getFactionTerritoriesEnabled()) {
			
			boolean enable = true;
			boolean aenemy = false;
			boolean aally = false;
			boolean aneutral = false;
			boolean atruce = false;
			boolean amember = false;
			
			boolean enemy = false;
			boolean ally = false;
			boolean neutral = false;
			boolean truce = false;
			boolean member = false;
			
			Location tploc = new Location(w, p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ());
			
			Locality ftploc = Locality.of(tploc);
			Faction faction = Board.get().getFactionAt(ftploc);
			
			boolean isdf = false;
			
					if (faction.isWilderness()) {
						isdf = true;
					}
			
			
			Faction pfaction = FactionColl.get(p);
			
			if (!cportal.getFactionTerritoriesEnabled()) {
				enable = false;
			}
			
			if (!cportal.getFactionTerritoriesEnemyAllowed()) {
				aenemy = true;
			}
			if (!cportal.getFactionTerritoriesAllyAllowed()) {
				aally = true;
			}
			if (!cportal.getFactionTerritoriesNeutralAllowed()) {
				if (!isdf) {	
					aneutral = true;
				}
			}
			if (!cportal.getFactionTerritoriesMemberAllowed()) {
				if (!isdf) {
					amember = true;
				}
			}
			if (!cportal.getFactionTerritoriesTruceAllowed()) {
				if (!isdf) {
					atruce = true;
				}
			}
			
			if (aenemy) {
				if (pfaction.getRelationTo(faction).toString().equalsIgnoreCase("ENEMY")) {
					enemy = true;
				} else {
					enemy = false;
				}
			} else {
				enemy = false;
			}
			
			if (aally) {
				if (pfaction.getRelationTo(faction).toString().equalsIgnoreCase("ALLY")) {
					ally = true;
				} else {
					ally = false;
				}
			} else {
				ally = false;
			}
			
			if (aneutral) {
				if (pfaction.getRelationTo(faction).toString().equalsIgnoreCase("NEUTRAL")) {
					neutral = true;
				} else {
					neutral = false;
				}
			} else {
				neutral = false;
			}
			
			if (atruce) {
				if (pfaction.getRelationTo(faction).toString().equalsIgnoreCase("TRUCE")) {
					truce = true;
				} else {
					truce = false;
				}
			} else {
				truce = false;
			}
			
			if (amember) {
				if (pfaction.getRelationTo(faction).toString().equalsIgnoreCase("MEMBER")) {
					member = true;
				} else {
					member = false;
				}
			} else {
				member = false;
			}
			
			if (isdf) {
				enable = false;
			}
			if (enable) {
				if (enemy) {
					return false;
				} else if (ally) {
					return false;
				} else if (neutral) {
					return false;
				} else if (member) {
					return false;
				} else if (truce) {
					return false;
				} else {
					return true;
				}
			} else {
				return true;	
			}
			} else {
				return true;
			}
			
		}
		
		public String isOnClaimedLandType(Player p, CPortal cportal, World w) {
			
			boolean enable = true;
			boolean enemy = false;
			boolean ally = false;
			boolean neutral = false;
			boolean truce = false;
			boolean member = false;
			
			Location tploc = new Location(w, p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ());

			Locality ftploc = Locality.of(tploc);
			Faction faction = Board.get().getFactionAt(ftploc);
			
			Faction pfaction = FactionColl.get(p);
			
			if (!cportal.getFactionTerritoriesEnabled()) {
				enable = false;
			}
				if (pfaction.getRelationTo(faction).toString().equalsIgnoreCase("ENEMY")) {
					enemy = true;
				} else {
					enemy = false;
				}
				if (pfaction.getRelationTo(faction).toString().equalsIgnoreCase("ALLY")) {
					ally = true;
				} else {
					ally = false;
				}
			
				if (pfaction.getRelationTo(faction).toString().equalsIgnoreCase("NEUTRAL")) {
					neutral = true;
				} else {
					neutral = false;
				}
			
				if (pfaction.getRelationTo(faction).toString().equalsIgnoreCase("MEMBER")) {
					member = true;
				} else {
					member = false;
				}
			
				if (pfaction.getRelationTo(faction).toString().equalsIgnoreCase("TRUCE")) {
					truce = true;
				} else {
					truce = false;
				}
			
			if (enable) {
				if (enemy || ally || neutral || truce || member) {
					if (enemy) {
						return "§cEnemy§7";
					} else if (ally) {
						return "§2Ally§7";
					} else if (neutral) {
						return "§1Neutral§7";
					} else if (truce) {
						return "§6Truce§7";
					} else if (member) {
						return "§eMember§7";
					}
				} else {
					return "§cError§7";
				}
			}
			
			return "§cError§7";
		}	  
	  


}

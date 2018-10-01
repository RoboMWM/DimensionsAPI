package me.xxastaspastaxx.dimensions.worldguard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import me.xxastaspastaxx.dimensions.api.CPortal;

public class WorldGuardRegion implements Listener {
	
	  public WorldGuardRegion(Plugin pl) {
		  
		  
		    Bukkit.getServer().getPluginManager().registerEvents(this, pl);
		  }

	  private WorldGuardPlugin getWorldGuard() {
		    Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
		 
		    // WorldGuard may not be loaded
		    if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
		        return null; // Maybe you want throw an exception instead
		    }
		 
		    return (WorldGuardPlugin) plugin;
		}
	
	  //merge the two functions as well
	  public boolean isInRegion(Player p, CPortal cportal) {
		  
		  WorldGuardPlugin guard = getWorldGuard();
		    RegionManager manager = guard.getRegionManager(cportal.getTeleportLocation().getWorld());
		    ApplicableRegionSet set = manager.getApplicableRegions(cportal.getTeleportLocation());
		    
		    if (cportal.getWorldGuardEnabled()) {
			    if (set.size() > 0) {
					for (String drg : cportal.getWorldGuardAreas()) {
				    	for (ProtectedRegion rg : set) {
				    		if (drg.contentEquals(rg.getId())) {
					    		return true;
				    		}
				    	}
					}
			    }
		    }
		  
		  return false;
	  }

	  public String getRegionName(CPortal cportal) {
	  
		  WorldGuardPlugin guard = getWorldGuard();
		    RegionManager manager = guard.getRegionManager(cportal.getTeleportLocation().getWorld());
		    ApplicableRegionSet set = manager.getApplicableRegions(cportal.getTeleportLocation());
		    
		    if (set.size() > 0) {
				
				for (String drg : cportal.getWorldGuardAreas()) {
			    	for (ProtectedRegion rg : set) {
			    		if (drg.contentEquals(rg.getId())) {
				    		return drg;
			    		}
			    	}
				}
		    }
		    
		  return "§4§l§nError§6";
	  }
	  
}

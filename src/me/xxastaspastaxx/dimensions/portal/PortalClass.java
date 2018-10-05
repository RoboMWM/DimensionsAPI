package me.xxastaspastaxx.dimensions.portal;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import me.xxastaspastaxx.dimensions.ExtraTags;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.xxastaspastaxx.dimensions.Main;
import me.xxastaspastaxx.dimensions.api.CPortal;
import me.xxastaspastaxx.dimensions.essence.ES;
import me.xxastaspastaxx.dimensions.events.CreateCPortalEvent;
import me.xxastaspastaxx.dimensions.events.DestroyCPortalEvent;
import me.xxastaspastaxx.dimensions.events.EnterCPortalEvent;
//import net.milkbowl.vault.Vault;
//import net.milkbowl.vault.economy.Economy;

public class PortalClass implements Listener {
	//The whole plugin is in here
	
	
	public ArrayList<String> portalList = new ArrayList<String>();
	
	File lbfo = new File("plugins/Dimensions/Portals");
	public File[] lbfi = lbfo.listFiles(new FilenameFilter() {
	      public boolean accept(File dir, String name) {
	    	  portalList.add(name);
	           return name.toLowerCase().endsWith(".yml");
	      }
	});
	
	//private Economy economy;
	
	public PortalClass(Plugin pl) {

//	    if ((Bukkit.getPluginManager().getPlugin("Vault") instanceof Vault))
//	    {
//	      RegisteredServiceProvider<Economy> service = Bukkit.getServicesManager().getRegistration(Economy.class);
//	      if (service != null) {
//	        economy = ((Economy)service.getProvider());
//	      }
//	    }
	    Bukkit.getServer().getPluginManager().registerEvents(this, pl);
	 }
	
	//Players inside portal are stored here so they will not be teleported back and glass will stay air for the player
	public static ArrayList<Player> us = new ArrayList<Player>();
	
	File dms = new File("plugins/Dimensions/Settings.yml");
	YamlConfiguration dmsf = YamlConfiguration.loadConfiguration(dms);
	
	//These are used to verify that it is a portal
	public HashMap<CPortal, Integer> down = new HashMap<CPortal, Integer>();
	public HashMap<CPortal, Integer> up = new HashMap<CPortal, Integer>();
	
	public HashMap<CPortal, Integer> east = new HashMap<CPortal, Integer>();
	public HashMap<CPortal, Integer> west = new HashMap<CPortal, Integer>();
	
	public HashMap<CPortal, Integer> north = new HashMap<CPortal, Integer>();
	public HashMap<CPortal, Integer> south = new HashMap<CPortal, Integer>();
	
	int rad = Integer.parseInt(dmsf.getString("MaxPortalRadius"));
	
	/**/
	//Get the portal the player is currently standing at
	  public CPortal getPortal(Location ploc) {
				 for (String fl : portalList) {
					 if (isInPortal(new CPortal(fl.replace(".yml", ""), ploc))) {
					 return new CPortal(fl.replace(".yml", ""), ploc);
					 }
				 }
		  return null;
	  }
	
	  //If player is in any portal
	  @SuppressWarnings("deprecation")
		public boolean isInPortal(CPortal cportal) {
		  
		  Location ploc = cportal.getLocation();
		  
		  boolean isDown = false;
		  boolean isUp = false;
		  
		  boolean isEast = false;
		  boolean isWest = false;
		  
		  boolean isNorth = false;
		  boolean isSouth = false;
		  
	  		if (true) {
			    Location location = ploc.clone();
			    for (int blocks = 1; blocks <= rad; blocks++) {
			        location.add(0, -1, 0);
			        if (cportal.getData()==-1) {
				        if (location.getBlock().getType() == cportal.getMaterial()) {
				        	down.put(cportal, (int) (location.getY()-ploc.getY()));
				        	isDown=true;
				            break;
				        }
			        } else {
				        if (location.getBlock().getType() == cportal.getMaterial() && location.getBlock().getState().getRawData() == cportal.getData()) {
				        	down.put(cportal, (int) (location.getY()-ploc.getY()));
				        	isDown=true;
				            break;
				        }
			        }

			    }
	  		}
	  		if (true) {
	  		    Location location = ploc.clone();
	  		    for (int blocks = 1; blocks <= rad; blocks++) {
	  		        location.add(0, 1, 0);
	  		        if (cportal.getData()==-1) {
	  			        if (location.getBlock().getType() == cportal.getMaterial()) {
	  			        	up.put(cportal, (int) (location.getY()-ploc.getY()));
	  			        	isUp=true;
	  			            break;
	  			        }
	  		        } else {
	  			        if (location.getBlock().getType() == cportal.getMaterial() && location.getBlock().getState().getRawData() == cportal.getData()) {
	  			        	up.put(cportal, (int) (location.getY()-ploc.getY()));
	  			        	isUp=true;
	  			            break;
	  			        }	
	  		        }
	  		    }
	  		}
		    
	  		if (true) {
	  		    Location location = ploc.clone();
	  		    for (int blocks = 1; blocks <= rad; blocks++) {
	  		        location.add(1, 0, 0);
	  		        if (cportal.getData()==-1) {
	  			        if (location.getBlock().getType() == cportal.getMaterial()) {
	  			        	east.put(cportal, (int) (location.getX()-ploc.getX()));
	  			        	isEast=true;
	  			            break;
	  			        }
	  		        } else {
	  			        if (location.getBlock().getType() == cportal.getMaterial() && location.getBlock().getState().getRawData() == cportal.getData()) {
	  			        	east.put(cportal, (int) (location.getX()-ploc.getX()));
	  			        	isEast=true;
	  			            break;
	  			        }	
	  		        }
	  		    }
	  		}
	  		if (true) {
	  		    Location location = ploc.clone();
	  		    for (int blocks = 1; blocks <= rad; blocks++) {
	  		        location.add(-1, 0, 0);
	  		        if (cportal.getData()==-1) {
	  			        if (location.getBlock().getType() == cportal.getMaterial()) {
	  			        	west.put(cportal, (int)(location.getX()-ploc.getX()));
	  			        	isWest=true;
	  			            break;
	  			        }
	  		        } else {
	  			        if (location.getBlock().getType() == cportal.getMaterial() && location.getBlock().getState().getRawData() == cportal.getData()) {
	  			        	west.put(cportal, (int)(location.getX()-ploc.getX()));
	  			        	isWest=true;
	  			            break;
	  			        }	
	  		        }
	  		    }
	  		}
	  		if (true) {
	  		    Location location = ploc.clone();
	  		    for (int blocks = 1; blocks <= rad; blocks++) {
	  		        location.add(0, 0, 1);
	  		        if (cportal.getData()==-1) {
	  			        if (location.getBlock().getType() == cportal.getMaterial()) {
	  			        	south.put(cportal, (int)(location.getZ()-ploc.getZ()));
	  			        	isSouth=true;
	  			            break;
	  			        }
	  		        } else {
	  			        if (location.getBlock().getType() == cportal.getMaterial() && location.getBlock().getState().getRawData() == cportal.getData()) {
	  			        	south.put(cportal, (int)(location.getZ()-ploc.getZ()));
	  			        	isSouth=true;
	  			            break;
	  			        }	
	  		        }
	  		    }
	  		}
	  		if (true) {
	  		    Location location = ploc.clone();
	  		    for (int blocks = 1; blocks <= rad; blocks++) {
	  		        location.add(0, 0, -1);
	  		        if (cportal.getData()==-1) {
	  			        if (location.getBlock().getType() == cportal.getMaterial()) {
	  			        	north.put(cportal, (int)(location.getZ()-ploc.getZ()));
	  			        	isNorth=true;
	  			            break;
	  			        }
	  		        } else {
	  			        if (location.getBlock().getType() == cportal.getMaterial() && location.getBlock().getState().getRawData() == cportal.getData()) {
	  			        	north.put(cportal, (int)(location.getZ()-ploc.getZ()));
	  			        	isNorth=true;
	  			            break;
	  			        }	
	  		        }
	  		    }
	  		}
	  		
	  		//
		    if (isDown && isUp && isEast && isWest) {
		    	if (isFullPortalEW(cportal)) {
		    		return true;
		    	} else {
		    		return false;
		    	}
		    } else if (isDown && isUp && isNorth && isSouth) {
		    	if(isFullPortalNS(cportal)) {
		    		return true;
		    	} else {
		    		return false;
		    	}
		    } else {
		    	return false;
		    }
		}
	  
	  //Code here is repeating code to see if portal is actually a portal and how big it is
	  //vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv\\
	  @SuppressWarnings("deprecation")
	public boolean isFullPortalEW(CPortal cportal) {
		  Location ploc = cportal.getLocation();
		  
		  boolean isFEast = false; 
		  boolean isTEast = false; 

		  boolean isFWest = false; 
		  boolean isTWest = false; 
		  
		  boolean isWEast = false;
		  boolean isWWest = false;
		  
		  if (true) {
				int eastf = 0;
			    Location location = ploc.clone();
			    location.add(0, down.get(cportal), 0);
			    for (int blocks = 1; blocks <= rad; blocks++) {
			        location.add(1, 0, 0);
			        if (cportal.getData()==-1) {
				        if (location.getBlock().getType() == cportal.getMaterial()) {
					        eastf += 1;
				        	if (eastf==east.get(cportal)) {
					        	eastf-=1;
					        	location.add(-1, 0, 0);
				        		isFEast=true;
				        		break;
				        	}
				        } else if (eastf==(east.get(cportal)-1)) {
				        	eastf-=1;
				        	location.add(-1, 0, 0);
				        	isFEast=true;
			        		break;
			        	} else {eastf -= 1;}
			        } else {
				        if (location.getBlock().getType() == cportal.getMaterial() && location.getBlock().getState().getRawData() == cportal.getData()) {
					        eastf += 1;
				        	if (eastf==east.get(cportal)) {
					        	eastf-=1;
					        	location.add(-1, 0, 0);
					        	isFEast=true;
				        		break;
				        	}
				        } else if (eastf==(east.get(cportal)-1)) {
				        	eastf-=1;
				        	location.add(-1, 0, 0);
				        	isFEast=true;
			        		break;
			        	} else {eastf -= 1;}	
			        }
			    }
		  }
		  if (true) {
				int eastf = 0;
			    Location location = ploc.clone();
			    location.add(0, down.get(cportal), 0);
			    for (int blocks = 1; blocks <= rad; blocks++) {
			        location.add(-1, 0, 0);
			        if (cportal.getData()==-1) {
				        if (location.getBlock().getType() == cportal.getMaterial()) {
					        eastf -= 1;
				        	if (eastf==west.get(cportal)) {
					        	eastf+=1;
					        	location.add(1, 0, 0);
					        	isFWest=true;
				        		break;
				        	}
				        } else if (eastf==(west.get(cportal)+1)) {
				        	eastf+=1;
				        	location.add(1, 0, 0);
				        	isFWest=true;
			        		break;
			        	} else {eastf += 1;}
			        } else {
				        if (location.getBlock().getType() == cportal.getMaterial() && location.getBlock().getState().getRawData() == cportal.getData()) {
					        eastf -= 1;
				        	if (eastf==west.get(cportal)) {
					        	eastf+=1;
					        	location.add(1, 0, 0);
					        	isFWest=true;
				        		break;
				        	}
				        } else if (eastf==(west.get(cportal)+1)) {
				        	eastf+=1;
				        	location.add(1, 0, 0);
				        	isFWest=true;
			        		break;
			        	} else {eastf += 1;}	
			        }
			    }
		  }
		  if (true) {
				int eastf = 0;
			    Location location = ploc.clone();
			    location.add(0, up.get(cportal), 0);
			    for (int blocks = 1; blocks <= rad; blocks++) {
			        location.add(-1, 0, 0);
			        if (cportal.getData()==-1) {
				        if (location.getBlock().getType() == cportal.getMaterial()) {
					        eastf -= 1;
				        	if (eastf==west.get(cportal)) {
					        	eastf+=1;
					        	location.add(1, 0, 0);
					        	isTWest=true;
				        		break;
				        	}
				        } else if (eastf==(west.get(cportal)+1)) {
				        	eastf+=1;
				        	location.add(1, 0, 0);
				        	isTWest=true;
			        		break;
			        	} else {eastf += 1;}
			        } else {
				        if (location.getBlock().getType() == cportal.getMaterial() && location.getBlock().getState().getRawData() == cportal.getData()) {
					        eastf -= 1;
				        	if (eastf==west.get(cportal)) {
				        		eastf+=1;
					        	location.add(1, 0, 0);
					        	isTWest=true;
				        		break;
				        	}
				        } else if (eastf==(west.get(cportal)+1)) {
				        	eastf+=1;
				        	location.add(1, 0, 0);
				        	isTWest=true;
			        		break;
			        	} else {eastf += 1;}	
			        }
			    }
		  }
		  if (true) {
				int eastf = 0;
			    Location location = ploc.clone();
			    location.add(0, up.get(cportal), 0);
			    for (int blocks = 1; blocks <= rad; blocks++) {
			        location.add(1, 0, 0);
			        if (cportal.getData()==-1) {
				        if (location.getBlock().getType() == cportal.getMaterial()) {
					        eastf += 1;
				        	if (eastf==east.get(cportal)) {
					        	eastf-=1;
					        	location.add(-1, 0, 0);
					        	isTEast=true;
				        		break;
				        	}
				        } else if (eastf==(east.get(cportal)-1)) {
				        	eastf-=1;
				        	location.add(-1, 0, 0);
				        	isTEast=true;
			        		break;
			        	} else {eastf -= 1;}
			        } else {
				        if (location.getBlock().getType() == cportal.getMaterial() && location.getBlock().getState().getRawData() == cportal.getData()) {
					        eastf += 1;
				        	if (eastf==east.get(cportal)) {
					        	eastf-=1;
					        	location.add(-1, 0, 0);
					        	isTEast=true;
				        		break;
				        	}
				        } else if (eastf==(east.get(cportal)-1)) {
				        	eastf-=1;
				        	location.add(-1, 0, 0);
				        	isTEast=true;
			        		break;
			        	} else {eastf -= 1;}	
			        }
			    }
		  }
		  if (true) {
				int eastf = 0;
			    Location location = ploc.clone();
			    location.add(east.get(cportal), 0, 0);
			    for (int blocks = 1; blocks <= rad; blocks++) {
			        location.add(0, 1, 0);
			        if (cportal.getData()==-1) {
				        if (location.getBlock().getType() == cportal.getMaterial()) {
					        eastf += 1;
				        	if (eastf==up.get(cportal)) {
					        	eastf-=1;
					        	location.add(0, -1, 0);
					        	isWEast=true;
				        		break;
				        	}
				        } else if (eastf==(up.get(cportal)-1)) {
				        	eastf-=1;
				        	location.add(0, -1, 0);
				        	isWEast=true;
			        		break;
			        	} else {eastf -= 1;}
			        } else {
				        if (location.getBlock().getType() == cportal.getMaterial() && location.getBlock().getState().getRawData() == cportal.getData()) {
					        eastf += 1;
				        	if (eastf==up.get(cportal)) {
					        	eastf-=1;
					        	location.add(0, -1, 0);
					        	isWEast=true;
				        		break;
				        	}
				        } else if (eastf==(up.get(cportal)-1)) {
				        	eastf-=1;
				        	location.add(0, -1, 0);
				        	isWEast=true;
			        		break;
			        	} else {eastf -= 1;}	
			        }
			    }
		  }
		  if (true) {
				int eastf = 0;
			    Location location = ploc.clone();
			    location.add(west.get(cportal), 0, 0);
			    for (int blocks = 1; blocks <= rad; blocks++) {
			        location.add(0, 1, 0);
			        if (cportal.getData()==-1) {
				        if (location.getBlock().getType() == cportal.getMaterial()) {
					        eastf += 1;
				        	if (eastf==up.get(cportal)) {
					        	eastf-=1;
					        	location.add(0, -1, 0);
					        	isWWest=true;
				        		break;
				        	}
				        } else if (eastf==(up.get(cportal)-1)) {
				        	eastf-=1;
				        	location.add(0, -1, 0);
				        	isWWest=true;
			        		break;
			        	} else {eastf -= 1;}
			        } else {
				        if (location.getBlock().getType() == cportal.getMaterial() && location.getBlock().getState().getRawData() == cportal.getData()) {
					        eastf += 1;
				        	if (eastf==up.get(cportal)) {
				        		eastf-=1;
					        	location.add(0, -1, 0);
					        	isWWest=true;
				        		break;
				        	}
				        } else if (eastf==(up.get(cportal)-1)) {
				        	eastf-=1;
				        	location.add(0, -1, 0);
				        	isWWest=true;
			        		break;
			        	} else {eastf -= 1;}	
			        }
			    }
		  }
		  
		  /**/
		  if (isFEast&&isTEast&&isFWest&&isTWest&&isWEast&&isWWest) {
			 return true; 
		  } else {
			  return false;
		  }
	  }
	  
	  @SuppressWarnings("deprecation")
	public boolean isFullPortalNS(CPortal cportal) {
		  Location ploc = cportal.getLocation();
		  
		  boolean isFSouth = false; 
		  boolean isTSouth = false; 

		  boolean isFNorth = false; 
		  boolean isTNorth = false; 
		  
		  boolean isWSouth = false;
		  boolean isWNorth = false;
		  
		  if (true) {
				int eastf = 0;
			    Location location = ploc.clone();
			    location.add(0, down.get(cportal), 0);
			    for (int blocks = 1; blocks <= rad; blocks++) {
			        location.add(0, 0, 1);
			        if (cportal.getData()==-1) {
				        if (location.getBlock().getType() == cportal.getMaterial()) {
					        eastf += 1;
				        	if (eastf==south.get(cportal)) {
					        	eastf-=1;
					        	location.add(0, 0, -1);
					        	isFSouth=true;
				        		break;
				        	}
				        } else if (eastf==(south.get(cportal)-1)) {
				        	eastf-=1;
				        	location.add(0, 0, -1);
				        	isFSouth=true;
			        		break;
			        	} else {eastf -= 1;}
			        } else {
				        if (location.getBlock().getType() == cportal.getMaterial() && location.getBlock().getState().getRawData() == cportal.getData()) {
					        eastf += 1;
				        	if (eastf==south.get(cportal)) {
					        	eastf-=1;
					        	location.add(0, 0, -1);
					        	isFSouth=true;
				        		break;
				        	}
				        } else if (eastf==(south.get(cportal)-1)) {
				        	eastf-=1;
				        	location.add(0, 0, -1);
				        	isFSouth=true;
			        		break;
			        	} else {eastf -= 1;}	
			        }
			    }
		  }
		  if (true) {
			  	int eastf = 0;
			  	Location location = ploc.clone();
			    location.add(0, down.get(cportal), 0);
			    for (int blocks = 1; blocks <= rad; blocks++) {
			        location.add(0, 0, -1);
			        if (cportal.getData()==-1) {
				        if (location.getBlock().getType() == cportal.getMaterial()) {
					        eastf -= 1;
				        	if (eastf==north.get(cportal)) {
					        	eastf+=1;
					        	location.add(0, 0, 1);
					        	isFNorth=true;
				        		break;
				        	}
				        } else if (eastf==(north.get(cportal)+1)) {
				        	eastf+=1;
				        	location.add(0, 0, 1);
				        	isFNorth=true;
			        		break;
			        	} else {eastf += 1;}
			        } else {
				        if (location.getBlock().getType() == cportal.getMaterial() && location.getBlock().getState().getRawData() == cportal.getData()) {
					        eastf -= 1;
				        	if (eastf==north.get(cportal)) {
					        	eastf+=1;
					        	location.add(0, 0, 1);
					        	isFNorth=true;
				        		break;
				        	}
				        } else if (eastf==(north.get(cportal)+1)) {
				        	eastf+=1;
				        	location.add(0, 0, 1);
				        	isFNorth=true;
			        		break;
			        	} else {eastf += 1;}	
			        }
			    }
		  }
		  if (true) {
				int eastf = 0;
			    Location location = ploc.clone();
			    location.add(0, up.get(cportal), 0);
			    for (int blocks = 1; blocks <= rad; blocks++) {
			        location.add(0, 0, -1);
			        if (cportal.getData()==-1) {
				        if (location.getBlock().getType() == cportal.getMaterial()) {
					        eastf -= 1;
				        	if (eastf==north.get(cportal)) {
					        	eastf+=1;
					        	location.add(0, 0, 1);
					        	isTNorth=true;
				        		break;
				        	}
				        } else if (eastf==(north.get(cportal)+1)) {
				        	eastf+=1;
				        	location.add(0, 0, 1);
				        	isTNorth=true;
			        		break;
			        	} else {eastf += 1;}	
			        } else {
				        if (location.getBlock().getType() == cportal.getMaterial() && location.getBlock().getState().getRawData() == cportal.getData()) {
					        eastf -= 1;
				        	if (eastf==north.get(cportal)) {
					        	eastf+=1;
					        	location.add(0, 0, 1);
					        	isTNorth=true;
				        		break;
				        	}
				        } else if (eastf==(north.get(cportal)+1)) {
				        	eastf+=1;
				        	location.add(0, 0, 1);
				        	isTNorth=true;
			        		break;
			        	} else {eastf += 1;}
			        }
			    }
		  }
		  if (true) {
				int eastf = 0;
			    Location location = ploc.clone();
			    location.add(0, up.get(cportal), 0);
			    for (int blocks = 1; blocks <= rad; blocks++) {
			        location.add(0, 0, 1);
			        if (cportal.getData()==-1) {
				        if (location.getBlock().getType() == cportal.getMaterial()) {
					        eastf += 1;
				        	if (eastf==south.get(cportal)) {
					        	eastf-=1;
					        	location.add(0, 0, -1);
					        	isTSouth=true;
				        		break;
				        	}
				        } else if (eastf==(south.get(cportal)-1)) {
				        	eastf-=1;
				        	location.add(0, 0, -1);
				        	isTSouth=true;
			        		break;
			        	} else {eastf -= 1;}
			        } else {
				        if (location.getBlock().getType() == cportal.getMaterial() && location.getBlock().getState().getRawData() == cportal.getData()) {
					        eastf += 1;
				        	if (eastf==south.get(cportal)) {
					        	eastf-=1;
					        	location.add(0, 0, -1);
					        	isTSouth=true;
				        		break;
				        	}
				        } else if (eastf==(south.get(cportal)-1)) {
				        	eastf-=1;
				        	location.add(0, 0, -1);
				        	isTSouth=true;
			        		break;
			        	} else {eastf -= 1;}	
			        }
			    }
		  }
		  if (true) {
				int eastf = 0;
			    Location location = ploc.clone();
			    location.add(0, 0, south.get(cportal));
			    for (int blocks = 1; blocks <= rad; blocks++) {
			        location.add(0, 1, 0);
			        if (cportal.getData()==-1) {
				        if (location.getBlock().getType() == cportal.getMaterial()) {
					        eastf += 1;
				        	if (eastf==up.get(cportal)) {
					        	eastf-=1;
					        	location.add(0, -1, 0);
					        	isWSouth=true;
				        		break;
				        	}
				        } else if (eastf==(up.get(cportal)-1)) {
				        	eastf-=1;
				        	location.add(0, -1, 0);
				        	isWSouth=true;
			        		break;
			        	} else {
				        	eastf -= 1;}
			        } else {
				        if (location.getBlock().getType() == cportal.getMaterial() && location.getBlock().getState().getRawData() == cportal.getData()) {
					        eastf += 1;
				        	if (eastf==up.get(cportal)) {
					        	eastf-=1;
					        	location.add(0, -1, 0);
					        	isWSouth=true;
				        		break;
				        	}
				        } else if (eastf==(up.get(cportal)-1)) {
				        	eastf-=1;
				        	location.add(0, -1, 0);
				        	isWSouth=true;
			        		break;
			        	} else {eastf -= 1;}	
			        }
			    }
		  }
		  if (true) {
				int eastf = 0;
			    Location location = ploc.clone();
			    location.add(0, 0, north.get(cportal));
			    for (int blocks = 1; blocks <= rad; blocks++) {
			        location.add(0, 1, 0);
			        if (cportal.getData()==-1) {
				        if (location.getBlock().getType() == cportal.getMaterial()) {
					        eastf += 1;
				        	if (eastf==up.get(cportal)) {
					        	eastf-=1;
					        	location.add(0, -1, 0);
					        	isWNorth=true;
				        		break;
				        	}
				        } else if (eastf==(up.get(cportal)-1)) {
				        	eastf-=1;
				        	location.add(0, -1, 0);
				        	isWNorth=true;
			        		break;
			        	} else {eastf -= 1;}
			        } else {
				        if (location.getBlock().getType() == cportal.getMaterial() && location.getBlock().getState().getRawData() == cportal.getData()) {
					        eastf += 1;
				        	if (eastf==up.get(cportal)) {
					        	eastf-=1;
					        	location.add(0, -1, 0);
					        	isWNorth=true;
				        		break;
				        	}
				        } else if (eastf==(up.get(cportal)-1)) {
				        	eastf-=1;
				        	location.add(0, -1, 0);
				        	isWNorth=true;
			        		break;
			        	} else {eastf -= 1;}	
			        }
			    }
		  }
		  
		  /**/
		  if (isFSouth&&isTSouth&&isFNorth&&isTNorth&&isWSouth&&isWNorth) {
			 return true; 
		  } else {
			  return false;
		  }
	  }
	  
  /**/
	  
	  @SuppressWarnings("deprecation")
	public boolean hasGlassEW(CPortal cportal) {
		  
		  boolean isEUp = false;
		  boolean isWUp = false;
		  boolean isEDown = false;
		  boolean isWDown = false;
		  
		  Location ploc = cportal.getLocation();
		  
		  if (true) {
			    Location location = ploc.clone();
			    for (int blocks = 1; blocks <= rad; blocks++) {
			        location.add(1, 0, 0);
			        if (cportal.getData()==-1) {
				        if (((location.getBlock().getType().equals(Material.STAINED_GLASS_PANE) && location.getBlock().getState().getRawData() == cportal.getGlassData()) && location.getBlock().getState().getRawData() == cportal.getGlassData())||(location.getBlock().getType().equals(cportal.getMaterial()))) {
				        }else {break;}
			        } else {
				        if (((location.getBlock().getType().equals(Material.STAINED_GLASS_PANE) && location.getBlock().getState().getRawData() == cportal.getGlassData()) && location.getBlock().getState().getRawData() == cportal.getGlassData())||(location.getBlock().getType().equals(cportal.getMaterial())&&location.getBlock().getState().getRawData() == cportal.getData())) {
				        }else {break;}
			        }
			        if (up.get(cportal) == 1) {
			        	if (location.getY() == ploc.getY()+1) {isEUp=true; break;}
			        } else {
			        	if (location.getY() == ploc.getY()+up.get(cportal)) {isEUp=true; break;}
			        }
			        if (location.getBlock().getType() == cportal.getMaterial()) {
			        	location.add(-(east.get(cportal)+1), 0, 0);
			        	location.add(0, 1, 0);
			        }
			    }
		  }
		  if (true) {
			    Location location = ploc.clone();
			    for (int blocks = 1; blocks <= rad; blocks++) {
			        location.add(-1, 0, 0);
			        if (cportal.getData()==-1) {
				        if (((location.getBlock().getType().equals(Material.STAINED_GLASS_PANE) && location.getBlock().getState().getRawData() == cportal.getGlassData()) && location.getBlock().getState().getRawData() == cportal.getGlassData())||(location.getBlock().getType().equals(cportal.getMaterial()))) {
				        }else {break;}
			        } else {
				        if (((location.getBlock().getType().equals(Material.STAINED_GLASS_PANE) && location.getBlock().getState().getRawData() == cportal.getGlassData()) && location.getBlock().getState().getRawData() == cportal.getGlassData())||(location.getBlock().getType().equals(cportal.getMaterial())&&location.getBlock().getState().getRawData() == cportal.getData())) {
				        }else {break;}
			        }
			        if (up.get(cportal) == 1) {
			        	if (location.getY() == ploc.getY()+1) {isWUp=true; break;}
			        } else {
			        	if (location.getY() == ploc.getY()+up.get(cportal)) {isWUp=true; break;}
			        }
			        if (location.getBlock().getType() == cportal.getMaterial()) {
			        	location.add(-(west.get(cportal)-1), 0, 0);
			        	location.add(0, 1, 0);
			        }
			    }
		  }
		  if (true) {
			    Location location = ploc.clone();
			    for (int blocks = 1; blocks <= rad; blocks++) {
			        location.add(1, 0, 0);
			        if (cportal.getData()==-1) {
				        if (((location.getBlock().getType().equals(Material.STAINED_GLASS_PANE) && location.getBlock().getState().getRawData() == cportal.getGlassData()) && location.getBlock().getState().getRawData() == cportal.getGlassData())||(location.getBlock().getType().equals(cportal.getMaterial()))) {
				        }else {break;}
			        } else {
				        if (((location.getBlock().getType().equals(Material.STAINED_GLASS_PANE) && location.getBlock().getState().getRawData() == cportal.getGlassData()) && location.getBlock().getState().getRawData() == cportal.getGlassData())||(location.getBlock().getType().equals(cportal.getMaterial())&&location.getBlock().getState().getRawData() == cportal.getData())) {
				        }else {break;}
			        }
			        if (down.get(cportal) == 1) {
			        	if (location.getY() == ploc.getY()+1) {isEDown=true; break;}
			        } else {
			        	if (location.getY() == ploc.getY()+down.get(cportal)) {isEDown=true; break;}
			        }
			        if (location.getBlock().getType() == cportal.getMaterial()) {
			        	location.add(-(east.get(cportal)+1), 0, 0);
			        	location.add(0, -1, 0);
			        }
			    }
		  }
		  if (true) {
			    Location location = ploc.clone();
			    for (int blocks = 1; blocks <= rad; blocks++) {
			        location.add(-1, 0, 0);
			        if (cportal.getData()==-1) {
				        if (((location.getBlock().getType().equals(Material.STAINED_GLASS_PANE) && location.getBlock().getState().getRawData() == cportal.getGlassData()) && location.getBlock().getState().getRawData() == cportal.getGlassData())||(location.getBlock().getType().equals(cportal.getMaterial()))) {
				        }else {break;}
			        } else {
				        if (((location.getBlock().getType().equals(Material.STAINED_GLASS_PANE) && location.getBlock().getState().getRawData() == cportal.getGlassData()) && location.getBlock().getState().getRawData() == cportal.getGlassData())||(location.getBlock().getType().equals(cportal.getMaterial())&&location.getBlock().getState().getRawData() == cportal.getData())) {
				        }else {break;}
			        }
			        if (down.get(cportal) == 1) {
			        	if (location.getY() == ploc.getY()+1) {isWDown=true; break;}
			        } else {
			        	if (location.getY() == ploc.getY()+down.get(cportal)) {isWDown=true; break;}
			        }
			        if (location.getBlock().getType() == cportal.getMaterial()) {
			        	location.add(-(west.get(cportal)-1), 0, 0);
			        	location.add(0, -1, 0);
			        }
			    }
		  }

		  if (isEUp && isWUp && isEDown && isWDown) {
			  return true;
		  } else {
			  return false;
		  }
	  }

	  @SuppressWarnings("deprecation")
	public boolean hasGlassNS(CPortal cportal) {

		  boolean isNUp = false;
		  boolean isSUp = false;
		  boolean isNDown = false;
		  boolean isSDown = false;

		  Location ploc = cportal.getLocation();

		  if (true) {
			    Location location = ploc.clone();
			    for (int blocks = 1; blocks <= rad; blocks++) {
			        location.add(0, 0, 1);
			        if (cportal.getData()==-1) {
				        if (((location.getBlock().getType().equals(Material.STAINED_GLASS_PANE) && location.getBlock().getState().getRawData() == cportal.getGlassData()) && location.getBlock().getState().getRawData() == cportal.getGlassData())||(location.getBlock().getType().equals(cportal.getMaterial()))) {
				        }else {break;}
			        } else {
				        if (((location.getBlock().getType().equals(Material.STAINED_GLASS_PANE) && location.getBlock().getState().getRawData() == cportal.getGlassData()) && location.getBlock().getState().getRawData() == cportal.getGlassData())||(location.getBlock().getType().equals(cportal.getMaterial())&&location.getBlock().getState().getRawData() == cportal.getData())) {
				        }else {break;}
			        }
			        if (up.get(cportal) == 1) {
			        	if (location.getY() == ploc.getY()+1) {isSUp=true; break;}
			        } else {
			        	if (location.getY() == ploc.getY()+up.get(cportal)) {isSUp=true; break;}
			        }
			        if (location.getBlock().getType() == cportal.getMaterial()) {
			        	location.add(0, 0, -(south.get(cportal)+1));
			        	location.add(0, 1, 0);
			        }
			    }
		  }
		  if (true) {
			    Location location = ploc.clone();
			    for (int blocks = 1; blocks <= rad; blocks++) {
			        location.add(0, 0, -1);
			        if (cportal.getData()==-1) {
				        if (((location.getBlock().getType().equals(Material.STAINED_GLASS_PANE) && location.getBlock().getState().getRawData() == cportal.getGlassData()) && location.getBlock().getState().getRawData() == cportal.getGlassData())||(location.getBlock().getType().equals(cportal.getMaterial()))) {
				        }else {break;}
			        } else {
				        if (((location.getBlock().getType().equals(Material.STAINED_GLASS_PANE) && location.getBlock().getState().getRawData() == cportal.getGlassData()) && location.getBlock().getState().getRawData() == cportal.getGlassData())||(location.getBlock().getType().equals(cportal.getMaterial())&&location.getBlock().getState().getRawData() == cportal.getData())) {
				        }else {break;}
			        }
			        if (up.get(cportal) == 1) {
			        	if (location.getY() == ploc.getY()+1) {isNUp=true; break;}
			        } else {
			        	if (location.getY() == ploc.getY()+up.get(cportal)) {isNUp=true; break;}
			        }
			        if (location.getBlock().getType() == cportal.getMaterial()) {
			        	location.add(0, 0, -(north.get(cportal)-1));
			        	location.add(0, 1, 0);
			        }
			    }
		  }
		  if (true) {
			    Location location = ploc.clone();
			    for (int blocks = 1; blocks <= rad; blocks++) {
			        location.add(0, 0, 1);
			        if (cportal.getData()==-1) {
				        if (((location.getBlock().getType().equals(Material.STAINED_GLASS_PANE) && location.getBlock().getState().getRawData() == cportal.getGlassData()) && location.getBlock().getState().getRawData() == cportal.getGlassData())||(location.getBlock().getType().equals(cportal.getMaterial()))) {
				        }else {break;}
			        } else {
				        if (((location.getBlock().getType().equals(Material.STAINED_GLASS_PANE) && location.getBlock().getState().getRawData() == cportal.getGlassData()) && location.getBlock().getState().getRawData() == cportal.getGlassData())||(location.getBlock().getType().equals(cportal.getMaterial())&&location.getBlock().getState().getRawData() == cportal.getData())) {
				        }else {break;}
			        }
			        if (down.get(cportal) == 1) {
			        	if (location.getY() == ploc.getY()+1) {isSDown=true; break;}
			        } else {
			        	if (location.getY() == ploc.getY()+down.get(cportal)) {isSDown=true; break;}
			        }
			        if (location.getBlock().getType() == cportal.getMaterial()) {
			        	location.add(0, 0, -(south.get(cportal)+1));
			        	location.add(0, -1, 0);
			        }
			    }
		  }
		  if (true) {
			    Location location = ploc.clone();
			    for (int blocks = 1; blocks <= rad; blocks++) {
			        location.add(0, 0, -1);
			        if (cportal.getData()==-1) {
				        if (((location.getBlock().getType().equals(Material.STAINED_GLASS_PANE) && location.getBlock().getState().getRawData() == cportal.getGlassData()) && location.getBlock().getState().getRawData() == cportal.getGlassData())||(location.getBlock().getType().equals(cportal.getMaterial()))) {
				        }else {break;}
			        } else {
				        if (((location.getBlock().getType().equals(Material.STAINED_GLASS_PANE) && location.getBlock().getState().getRawData() == cportal.getGlassData()) && location.getBlock().getState().getRawData() == cportal.getGlassData())||(location.getBlock().getType().equals(cportal.getMaterial())&&location.getBlock().getState().getRawData() == cportal.getData())) {
				        }else {break;}
			        }
			        if (down.get(cportal) == 1) {
			        	if (location.getY() == ploc.getY()+1) {isNDown=true; break;}
			        } else {
			        	if (location.getY() == ploc.getY()+down.get(cportal)) {isNDown=true; break;}
			        }
			        if (location.getBlock().getType() == cportal.getMaterial()) {
			        	location.add(0, 0, -(north.get(cportal)-1));
			        	location.add(0, -1, 0);
			        }
			    }
		  }


		  if (isNUp && isSUp && isNDown && isSDown) {
			  return true;
		  } else {
			  return false;
		  }
	  }
	  //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\\
/**/

	  @SuppressWarnings("deprecation")
	public boolean canUsePortal(Player p, CPortal cportal, boolean charge, boolean msg) {
		  													//  ^				^
		  													//When its verified that player can use portal

		  int hasperm = 0;
		  int hasmoney = 0;
		  int hasess = 0;

		if (cportal.isEnabled()) {
			if (cportal.getPermissionEnabled()) {
				if (p.hasPermission(cportal.getPermissionNode())) {
					hasperm=1;
				}
			} else {
				hasperm=-1;
			}

			if (cportal.getChargeEnabled()) {
				if (economy.getBalance(p)>=cportal.getChargeAmount()) {
					hasmoney=1;
				}
			} else {
				hasmoney=-1;
			}

			if (cportal.getEssenceEnabled()) {
				if (ES.checkEssence(p)>=cportal.getEssenceAmount()) {
					hasess=1;
				}
			} else {
				hasess=-1;
			}

			if (hasperm!=0 && hasmoney!=0 && hasess!=0) {
				if(charge) {

					if (hasmoney==1) {
						economy.withdrawPlayer(p, cportal.getChargeAmount());
					}
					if (hasess==1) {
						ES.removeEssence(p, cportal.getEssenceAmount());
					}
				}
				return true;
			} else {
				if (msg) {
					us.add(p);
					p.sendBlockChange(p.getLocation(), Material.AIR, (byte) 0);
					if (hasperm==0) {
						p.sendMessage(cportal.getNoPermissionMessage().replace("{player}", p.getName()));
					} else if (hasmoney==0) {
						p.sendMessage(cportal.getNotEnoughMoneyMessage().replace("{player}", p.getName()));
					} else if (hasess==0) {
						p.sendMessage(cportal.getNotEnoughEssenceMessage().replace("{player}", p.getName()));
					}
				}
			}
		}
		return false;
	  }

  /**/

	  @SuppressWarnings("deprecation")
	public void usePortal(Player p, CPortal cportal) {
		  //When everything is ready and player is good to go use this so it will teleport him to the other world, find near portals or create new, etc etc etc
			World w = cportal.getWorld();
			boolean re = false;

			Location loc = new Location(w, cportal.getLocation().getX(), cportal.getLocation().getY(), cportal.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch());

			File pdLp = new File("plugins/Dimensions/PlayerData/"+p.getName()+"/LastPortal.yml");
			YamlConfiguration pdlpf = YamlConfiguration.loadConfiguration(pdLp);

	          List<String> lup = pdlpf.getStringList("LastUsedP");
	          List<String> luw = pdlpf.getStringList("LastUsedW");

	          if (!lup.isEmpty() && lup.contains(cportal.getName())) {
	        	  for (int x=0;x<=lup.size();++x) {
	        		  String port = lup.get(x);
	        		  String world = luw.get(x);
	        		  World worldd = Bukkit.getServer().getWorld(world);

			        	  if (cportal.getName().equals(port)) {
			        		  luw.remove(world);
			        		  lup.remove(port);
			        		  pdlpf.set("LastUsedW", luw);
			        		  pdlpf.set("LastUsedP", lup);
			      			try {
			    				pdlpf.save(pdLp);
			    			} catch (IOException e) {
			    				e.printStackTrace();
			    			}

			      				re=true;
			        		  loc.setWorld(worldd);
			        		  cportal.setTeleportLocation(loc);
			        		  break;
			        	  }
	        	  	}
	          } else {
	        	  if (loc.getWorld().equals(p.getWorld())) {
	        		  World world = Bukkit.getServer().getWorld(dmsf.getString("MainWorld"));
	        		  loc.setWorld(world);
	        		  cportal.setTeleportLocation(loc);
	        	  } else {
		        	  luw.add(p.getWorld().getName());
	        		  lup.add(cportal.getName());
	        		  pdlpf.set("LastUsedW", luw);
	        		  pdlpf.set("LastUsedP", lup);
		  			try {
						pdlpf.save(pdLp);
					} catch (IOException e) {
						e.printStackTrace();
					}
	        	  }
	          }


	          //---------------------------------------------------------------
	          if (isNotOnClaimedLand(p, cportal)) {
					if (!isInRegion(p, cportal)) {

			boolean spoa = false;

			boolean isOnLand = false;

			if (cportal.getWorldType().equalsIgnoreCase("sky") && !re) {
				  isOnLand = true;
			      cportal.setTeleportLocation(loc);
	        } else {
	  	      if (cportal.getSpawnOnAirEnabled()) {
		    	  isOnLand = true;
		      } else {
			        double ni = loc.getY();
			        double y = loc.getY();
					while (!isOnLand) {
						if (loc.getBlock().getType() != Material.AIR) {
							loc.setY(y);
							isOnLand = true;
						} else {
							if (y <= 0) {
								loc.setY(ni);
								isOnLand = true;
								spoa = true;
							} else {
								y -= 1;
								loc.setY(y);
							}
						}
					}
					cportal.setTeleportLocation(loc);
		      }
	        }

			if (isOnLand == true) {
				loc = new Location(loc.getWorld(), loc.getX(), loc.getY()+1, loc.getZ());
		          if (cportal.getRandomLocationEnabled()) {
			            Random random = new Random();
			            int x = random.nextInt(2001) - 1000;
			            int z = random.nextInt(2001) - 1000;
			            loc.setX(x);
			            loc.setZ(z);
			            cportal.setTeleportLocation(loc);
			          }

			          if (re == false) {
				          if (cportal.getWorldType().equalsIgnoreCase("normal")) {
				        	  cportal.setTeleportLocation(loc);
				          } else if (cportal.getWorldType().equalsIgnoreCase("nether")) {
				        	  loc.setX(Math.floor(loc.getX()*8));
				        	  loc.setZ(Math.floor(loc.getZ()*8));
				        	  cportal.setTeleportLocation(loc);
				          } else if (cportal.getWorldType().equalsIgnoreCase("end")) {
				        	  loc.setX(Main.getInstance().getRandom(-100, 100));
				        	  loc.setZ(Main.getInstance().getRandom(-100, 100));
				        	  cportal.setTeleportLocation(loc);
				          } else if (cportal.getWorldType().equalsIgnoreCase("sky")) {
				        	  isOnLand = false;
						      if (cportal.getSpawnOnAirEnabled()) {
						    	  isOnLand = true;
						      } else {
							        double ni = loc.getY();
							        double sy = 250;

							        double nx = loc.getY();
							        double x = loc.getY();
							        double mx = 0;

									while (!isOnLand) {
										if (loc.getBlock().getType() != Material.AIR) {
											loc.setY(sy);
											loc.setX(x);
											isOnLand = true;
										} else {
												if (mx >= 100) {
													loc.setY(ni);
													loc.setX(nx);
													isOnLand = true;
													spoa = true;
												} else {
													if (sy <= 0) {
														mx += 1;
														x += 1;
														sy = 250;
														loc.setX(x);
														loc.setY(sy);
													} else {
														sy -= 1;
														loc.setY(sy);
													}
												}
										}
									}
						      }
				        	  cportal.setTeleportLocation(loc);
				          } else if (cportal.getWorldType().contains(":")) {

				        	 int sc = Integer.parseInt(cportal.getWorldType().split(":")[0])/Integer.parseInt(cportal.getWorldType().split(":")[1]);


				        	  loc.setX(cportal.getTeleportLocation().getX()*sc);
				        	  loc.setZ(cportal.getTeleportLocation().getZ()*sc);

				        	  cportal.setTeleportLocation(loc);
				          }
			          } else if(re == true) {
				          if (cportal.getWorldType().equalsIgnoreCase("normal")) {
				        	  cportal.setTeleportLocation(loc);
				          } else if (cportal.getWorldType().equalsIgnoreCase("nether")) {
				        	  loc.setX(Math.floor(loc.getX()/8));
				        	  loc.setZ(Math.floor(loc.getZ()/8));
				        	  cportal.setTeleportLocation(loc);
				          } else if (cportal.getWorldType().equalsIgnoreCase("end")) {
				        	  cportal.setTeleportLocation(loc);
				          } else if (cportal.getWorldType().equalsIgnoreCase("sky")) {
				        	  cportal.setTeleportLocation(loc);
				          } else if (cportal.getWorldType().contains(":")) {
					        	 int sc = Integer.parseInt(cportal.getWorldType().split(":")[0])/Integer.parseInt(cportal.getWorldType().split(":")[1]);


					        	  loc.setX(cportal.getTeleportLocation().getX()/sc);
					        	  loc.setZ(cportal.getTeleportLocation().getZ()/sc);

					        	  cportal.setTeleportLocation(loc);
				          }
			          }

				      cportal.setTeleportLocation(loc);
				if (testForNearPortal(cportal.getTeleportLocation().getBlock(), 50, cportal.getMaterial(), cportal.getData(), cportal.getGlassData())) {
					String[] cords = testForNearPortalLoc(cportal.getTeleportLocation().getBlock(), 50, cportal.getMaterial(), cportal.getData(), cportal.getGlassData()).split(" ");
					double cx = Double.parseDouble(cords[0]);
					double cy = Double.parseDouble(cords[1]);
					double cz = Double.parseDouble(cords[2]);
					Location nloc = new Location(loc.getWorld(), cx, cy, cz);
					Location nnloc = new Location(loc.getWorld(), cx, cy+1, cz);
					if (nloc.getBlock().getRelative(BlockFace.EAST).getType().equals(cportal.getGlassData())) {
						nnloc.add(2,-1,0.5);
					}

					if (nloc.getBlock().getRelative(BlockFace.WEST).getType().equals(cportal.getGlassData())) {
						nnloc.add(-2,-1,-0.5);
					}

					if (nloc.getBlock().getRelative(BlockFace.SOUTH).getType().equals(cportal.getGlassData())) {
						nnloc.add(0.5,-1,2);
					}

					if (nloc.getBlock().getRelative(BlockFace.NORTH).getType().equals(cportal.getGlassData())) {
						nnloc.add(-0.5,-1,-2);
					}
					cportal.setTeleportLocation(nnloc);
					EnterCPortalEvent event = new EnterCPortalEvent(p, cportal.getLocation(), cportal);
					 Bukkit.getServer().getPluginManager().callEvent(event);
					 if (!event.isCancelled()) {
						if (canUsePortal(p, cportal, true, false)) {
							p.teleport(nnloc);
							if (cportal.getSendMessageEnabled()) {
								p.sendMessage(cportal.getMessage().replace("{player}", p.getName()));
							}
							p.teleport(nnloc);
						}
					}
					us.add(p);
				} else {

					Location nloctp = new Location(cportal.getTeleportLocation().getWorld(), cportal.getTeleportLocation().getX(), cportal.getTeleportLocation().getY()-1, cportal.getTeleportLocation().getZ()+1.5);
					cportal.setTeleportLocation(nloctp);
					EnterCPortalEvent event = new EnterCPortalEvent(p, cportal.getLocation(), cportal);
					 Bukkit.getServer().getPluginManager().callEvent(event);
					 if (!event.isCancelled()) {
						if (canUsePortal(p, cportal, true, false)) {
							p.teleport(nloctp);
							if (cportal.getSendMessageEnabled()) {
								p.sendMessage(cportal.getMessage().replace("{player}", p.getName()));
							}
						}
					}

					if (cportal.getBuildExitPortalEnabled()) {

					      if (cportal.getSpawnOnAirEnabled() || spoa) {
								p.getLocation().getBlock().getRelative(BlockFace.DOWN).setType(cportal.getMaterial());
								p.getLocation().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.SOUTH).getRelative(BlockFace.EAST).setType(cportal.getMaterial());
								p.getLocation().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.SOUTH, 2).getRelative(BlockFace.EAST).setType(cportal.getMaterial());
								p.getLocation().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.SOUTH).getRelative(BlockFace.WEST).setType(cportal.getMaterial());
								p.getLocation().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.SOUTH, 2).getRelative(BlockFace.WEST).setType(cportal.getMaterial());
					      }

					p.getLocation().getBlock().getRelative(BlockFace.DOWN).setType(cportal.getMaterial());
					p.getLocation().getBlock().setType(cportal.getMaterial());
					p.getLocation().getBlock().getRelative(BlockFace.UP).setType(cportal.getMaterial());
					p.getLocation().getBlock().getRelative(BlockFace.UP,2).setType(cportal.getMaterial());
					p.getLocation().getBlock().getRelative(BlockFace.UP,3).setType(cportal.getMaterial());

					p.getLocation().getBlock().getRelative(BlockFace.SOUTH).getRelative(BlockFace.DOWN).setType(cportal.getMaterial());
					p.getLocation().getBlock().getRelative(BlockFace.SOUTH).setType(cportal.getGlassData());
					p.getLocation().getBlock().getRelative(BlockFace.SOUTH).getRelative(BlockFace.UP).setType(cportal.getGlassData());
					p.getLocation().getBlock().getRelative(BlockFace.SOUTH).getRelative(BlockFace.UP,2).setType(cportal.getGlassData());
					p.getLocation().getBlock().getRelative(BlockFace.SOUTH).getRelative(BlockFace.UP,3).setType(cportal.getMaterial());

					p.getLocation().getBlock().getRelative(BlockFace.SOUTH, 2).getRelative(BlockFace.DOWN).setType(cportal.getMaterial());
					p.getLocation().getBlock().getRelative(BlockFace.SOUTH, 2).setType(cportal.getGlassData());
					p.getLocation().getBlock().getRelative(BlockFace.SOUTH, 2).getRelative(BlockFace.UP).setType(cportal.getGlassData());
					p.getLocation().getBlock().getRelative(BlockFace.SOUTH, 2).getRelative(BlockFace.UP,2).setType(cportal.getGlassData());
					p.getLocation().getBlock().getRelative(BlockFace.SOUTH, 2).getRelative(BlockFace.UP,3).setType(cportal.getMaterial());

					p.getLocation().getBlock().getRelative(BlockFace.SOUTH, 3).getRelative(BlockFace.DOWN).setType(cportal.getMaterial());
					p.getLocation().getBlock().getRelative(BlockFace.SOUTH, 3).setType(cportal.getMaterial());
					p.getLocation().getBlock().getRelative(BlockFace.SOUTH, 3).getRelative(BlockFace.UP).setType(cportal.getMaterial());
					p.getLocation().getBlock().getRelative(BlockFace.SOUTH, 3).getRelative(BlockFace.UP,2).setType(cportal.getMaterial());
					p.getLocation().getBlock().getRelative(BlockFace.SOUTH, 3).getRelative(BlockFace.UP,3).setType(cportal.getMaterial());

					p.teleport(p.getLocation().add(0,0,1));
					}
					us.add(p);
				}
			}
					} else {
						String m = cportal.getWorldGuardDenyMessage();
						m = m.replace("{player}", p.getName()).replace("{region}", getRegionName(p, cportal)).replace("&", "�");
						p.sendMessage(m);
						us.add(p);
					}
				} else {
					String m = cportal.getFactionTerritoriesDenyMessage();
					m = m.replace("{player}", p.getName()).replace("{type}", getClaimedLandType(p, cportal)).replace("&", "�");
					p.sendMessage(m);
					us.add(p);
				}
	  }

		public boolean testForNearPortal(Block block, int radius, Material mat, Material glass) {
			for(int i = -radius; i <= radius; i++) {
			    for(int j = -radius; j <= radius; j++) {
			        for(int k = -radius; k <= radius; k++) {
						if(block.getRelative(i, j, k).getType() == mat) {
							Location nloc = new Location(block.getWorld(), (block.getLocation().getX()+i), (block.getLocation().getY()+j), (block.getLocation().getZ()+k));
							for (BlockFace face : BlockFace.values())
							{
								switch(face)
								{
									case NORTH:
									case SOUTH:
									case EAST:
									case WEST:
										if (nloc.getBlock().getRelative(face).getType().equals(glass))
											return true;
								}
							}
						}
			        }
			    }
			}
			return false;
		}

		public String testForNearPortalLoc(Block block, int radius, Material mat, Material glass) {
			for(int i = -radius; i <= radius; i++) {
			    for(int j = -radius; j <= radius; j++) {
			        for(int k = -radius; k <= radius; k++) {
			            if(block.getRelative(i, j, k).getType() == mat) {
			            	Location nloc = new Location(block.getWorld(), (block.getLocation().getX()+i), (block.getLocation().getY()+j), (block.getLocation().getZ()+k));
			            	for (BlockFace face : BlockFace.values())
							{
								switch(face)
								{
									case NORTH:
									case SOUTH:
									case EAST:
									case WEST:
										if (nloc.getBlock().getRelative(face).getType().equals(glass))
											return (block.getLocation().getX()+i)+" "+(block.getLocation().getY()+j)+" "+(block.getLocation().getZ()+k);
								}
							}
			            }
			        }
			    }
			}
			return null;
		}


		//This will return if portal is legit (aka if it is lit and doesnt have missing blocks etc)
		public boolean isInFullPortal(CPortal cportal) {
			 if (isInPortal(cportal)) {
				 boolean hsg = false;
					 if (hasGlassEW(cportal)) {
						 hsg=true;
					 } else if (hasGlassNS(cportal)) {
						 hsg=true;
					 }
				 if (hsg) {
					 if ((cportal.getLocation().getBlock().getType().equals(cportal.getGlassData()))||cportal.getLocation().getBlock().getType().equals(cportal.getMaterial())) {
						 return true;
					 }
				 }
			 }
			 return false;
		}


	  /*LIGHT PORTAL*/

	  @SuppressWarnings("deprecation")
	public boolean canLightPortalEW(CPortal cportal) {
		  boolean isEUp = false;
		  boolean isWUp = false;

		  boolean isEDown = false;
		  boolean isWDown = false;

		  Location ploc = cportal.getLocation();

		  if (true) {
			    Location location = ploc.clone();
			    for (int blocks = 1; blocks <= rad; blocks++) {
			        location.add(1, 0, 0);
			        if (location.getBlock().getType().equals(Material.AIR)||location.getBlock().getType().equals(Material.FIRE)) {
				        }else {
				        	if (location.getBlock().getType() == cportal.getMaterial() || location.getBlock().getType().equals(cportal.getGlassData())) {
				        	} else {
				        		if (cportal.getData()==-1) {
				        			if (location.getBlock().getType() == cportal.getMaterial()) {
				        			} else {break;}
				        		} else {break;}
				        	}
				        }
			        if (up.get(cportal) == 1) {
			        	if (location.getY() == ploc.getY()+1) {isEUp=true; break;}
			        } else {
			        	if (location.getY() == ploc.getY()+up.get(cportal)) {isEUp=true; break;}
			        }
			        if (location.getBlock().getType() == cportal.getMaterial()) {
			        	location.add(-(east.get(cportal)+1), 0, 0);
			        	location.add(0, 1, 0);
			        }
			    }
		  }
		  if (true) {
			    Location location = ploc.clone();
			    for (int blocks = 1; blocks <= rad; blocks++) {
			        location.add(-1, 0, 0);
			        if (location.getBlock().getType().equals(Material.AIR)||location.getBlock().getType().equals(Material.FIRE)) {
				        }else {
				        	if (location.getBlock().getType() == cportal.getMaterial() || location.getBlock().getType().equals(cportal.getGlassData())) {
				        	} else {
				        		if (true) {
				        			if (location.getBlock().getType() == cportal.getMaterial()) {
				        			} else {break;}
				        		} else {break;}
				        	}
				        }
			        if (up.get(cportal) == 1) {
			        	if (location.getY() == ploc.getY()+1) {isWUp=true; break;}
			        } else {
			        	if (location.getY() == ploc.getY()+up.get(cportal)) {isWUp=true; break;}
			        }
			        if (location.getBlock().getType() == cportal.getMaterial()) {
			        	location.add(-(west.get(cportal)-1), 0, 0);
			        	location.add(0, 1, 0);
			        }
			    }
		  }
		  if (true) {
			    Location location = ploc.clone();
			    for (int blocks = 1; blocks <= rad; blocks++) {
			        location.add(1, 0, 0);
			        if (location.getBlock().getType().equals(Material.AIR)||location.getBlock().getType().equals(Material.FIRE)) {
				        }else {
				        	if (location.getBlock().getType() == cportal.getMaterial() || location.getBlock().getType().equals(cportal.getGlassData())) {
				        	} else {
				        		if (cportal.getData()==-1) {
				        			if (location.getBlock().getType() == cportal.getMaterial()) {
				        			} else {break;}
				        		} else {break;}
				        	}
				        }
			        if (down.get(cportal) == 1) {
			        	if (location.getY() == ploc.getY()+1) {isEDown=true; break;}
			        } else {
			        	if (location.getY() == ploc.getY()+down.get(cportal)) {isEDown=true; break;}
			        }
			        if (location.getBlock().getType() == cportal.getMaterial()) {
			        	location.add(-(east.get(cportal)+1), 0, 0);
			        	location.add(0, -1, 0);
			        }
			    }
		  }
		  if (true) {
			    Location location = ploc.clone();
			    for (int blocks = 1; blocks <= rad; blocks++) {
			        location.add(-1, 0, 0);
			        if (location.getBlock().getType().equals(Material.AIR)||location.getBlock().getType().equals(Material.FIRE)) {
				        }else {
				        	if (location.getBlock().getType() == cportal.getMaterial() || location.getBlock().getType().equals(cportal.getGlassData())) {
				        	} else {
				        		if (cportal.getData()==-1) {
				        			if (location.getBlock().getType() == cportal.getMaterial()) {
				        			} else {break;}
				        		} else {break;}
				        	}
				        }
			        if (down.get(cportal) == 1) {
			        	if (location.getY() == ploc.getY()+1) {isWDown=true; break;}
			        } else {
			        	if (location.getY() == ploc.getY()+down.get(cportal)) {isWDown=true; break;}
			        }
			        if (location.getBlock().getType() == cportal.getMaterial()) {
			        	location.add(-(west.get(cportal)-1), 0, 0);
			        	location.add(0, -1, 0);
			        }
			    }
		  }

		  if (isEUp && isWUp && isEDown && isWDown) {
			  return true;
		  } else {
			  return false;
		  }
	  }

	  @SuppressWarnings("deprecation")
	public boolean canLightPortalNS(CPortal cportal) {
		  boolean isNUp = false;
		  boolean isSUp = false;

		  boolean isNDown = false;
		  boolean isSDown = false;

		  Location ploc = cportal.getLocation();
		  if (true) {
			    Location location = ploc.clone();
			    for (int blocks = 1; blocks <= rad; blocks++) {
			        location.add(0, 0, 1);
			        if (location.getBlock().getType().equals(Material.AIR)||location.getBlock().getType().equals(Material.FIRE)) {
				        }else {
				        	if (location.getBlock().getType() == cportal.getMaterial() || location.getBlock().getType().equals(cportal.getGlassData())) {
				        	} else {
				        		if (cportal.getData()==-1) {
				        			if (location.getBlock().getType() == cportal.getMaterial()) {
				        			} else {break;}
				        		} else {break;}
				        	}
				        }
			        if (up.get(cportal) == 1) {
			        	if (location.getY() == ploc.getY()+1) {isSUp=true; break;}
			        } else {
			        	if (location.getY() == ploc.getY()+up.get(cportal)) {isSUp=true; break;}
			        }
			        if (location.getBlock().getType() == cportal.getMaterial()) {
			        	location.add(0, 0, -(south.get(cportal)+1));
			        	location.add(0, 1, 0);
			        }
			    }
		  }
		  if (true) {
			    Location location = ploc.clone();
			    for (int blocks = 1; blocks <= rad; blocks++) {
			        location.add(0, 0, -1);
			        if (location.getBlock().getType().equals(Material.AIR)||location.getBlock().getType().equals(Material.FIRE)) {
				        }else {
				        	if (location.getBlock().getType() == cportal.getMaterial() || location.getBlock().getType().equals(cportal.getGlassData())) {
				        	} else {
				        		if (cportal.getData()==-1) {
				        			if (location.getBlock().getType() == cportal.getMaterial()) {
				        			} else {break;}
				        		} else {break;}
				        	}
				        }
			        if (up.get(cportal) == 1) {
			        	if (location.getY() == ploc.getY()+1) {isNUp=true; break;}
			        } else {
			        	if (location.getY() == ploc.getY()+up.get(cportal)) {isNUp=true; break;}
			        }
			        if (location.getBlock().getType() == cportal.getMaterial()) {
			        	location.add(0, 0, -(north.get(cportal)-1));
			        	location.add(0, 1, 0);
			        }
			    }
		  }
		  if (true) {
			    Location location = ploc.clone();
			    for (int blocks = 1; blocks <= rad; blocks++) {
			        location.add(0, 0, 1);
			        if (location.getBlock().getType().equals(Material.AIR)||location.getBlock().getType().equals(Material.FIRE)) {
				        }else {
				        	if (location.getBlock().getType() == cportal.getMaterial() || location.getBlock().getType().equals(cportal.getGlassData())) {
				        	} else {
				        		if (cportal.getData()==-1) {
				        			if (location.getBlock().getType() == cportal.getMaterial()) {
				        			} else {break;}
				        		} else {break;}
				        	}
				        }
			        if (down.get(cportal) == 1) {
			        	if (location.getY() == ploc.getY()+1) {isSDown=true; break;}
			        } else {
			        	if (location.getY() == ploc.getY()+down.get(cportal)) {isSDown=true; break;}
			        }
			        if (location.getBlock().getType() == cportal.getMaterial()) {
			        	location.add(0, 0, -(south.get(cportal)+1));
			        	location.add(0, -1, 0);
			        }
			    }
		  }
		  if (true) {
			    Location location = ploc.clone();
			    for (int blocks = 1; blocks <= rad; blocks++) {
			        location.add(0, 0, -1);
			        if (location.getBlock().getType().equals(Material.AIR)||location.getBlock().getType().equals(Material.FIRE)) {
				        }else {
				        	if (location.getBlock().getType() == cportal.getMaterial() || location.getBlock().getType().equals(cportal.getGlassData())) {
				        	} else {
				        		if (cportal.getData()==-1) {
				        			if (location.getBlock().getType() == cportal.getMaterial()) {
				        			} else {break;}
				        		} else {break;}
				        	}
				        }
			        if (down.get(cportal) == 1) {
			        	if (location.getY() == ploc.getY()+1) {isNDown=true; break;}
			        } else {
			        	if (location.getY() == ploc.getY()+down.get(cportal)) {isNDown=true; break;}
			        }
			        if (location.getBlock().getType() == cportal.getMaterial()) {
			        	location.add(0, 0, -(north.get(cportal)-1));
			        	location.add(0, -1, 0);
			        }
			    }
		  }
		  if (isNUp && isSUp && isNDown && isSDown) {
			  return true;
		  } else {
			  return false;
		  }
	  }

	  @SuppressWarnings("deprecation")
	public void lightPortalEW(CPortal cportal) {
		  Location ploc = cportal.getLocation();

		  if (true) {
			    Location location = ploc.clone();
			    for (int blocks = 1; blocks <= rad; blocks++) {
			        location.add(1, 0, 0);
			        if (location.getBlock().getType().equals(Material.AIR)||location.getBlock().getType().equals(Material.FIRE)) {
			        	location.getBlock().setType(Material.STAINED_GLASS_PANE);
			        	location.getBlock().setData((byte) cportal.getGlassData());
				        }else {
				        	if (location.getBlock().getType() == cportal.getMaterial() || location.getBlock().getType().equals(cportal.getGlassData())) {
				        	} else {
				        		if (cportal.getData()==-1) {
				        			if (location.getBlock().getType() == cportal.getMaterial()) {
				        			} else {break;}
				        		} else {break;}
				        	}
				        }
			        if (up.get(cportal) == 1) {
			        	if (location.getY() == ploc.getY()+1) {break;}
			        } else {
			        	if (location.getY() == ploc.getY()+up.get(cportal)) {break;}
			        }
			        if (location.getBlock().getType() == cportal.getMaterial()) {
			        	location.add(-(east.get(cportal)+1), 0, 0);
			        	location.add(0, 1, 0);
			        }
			    }
		  }
		  if (true) {
			    Location location = ploc.clone();
			    for (int blocks = 1; blocks <= rad; blocks++) {
			        location.add(-1, 0, 0);
			        if (location.getBlock().getType().equals(Material.AIR)||location.getBlock().getType().equals(Material.FIRE)) {
			        	location.getBlock().setType(Material.STAINED_GLASS_PANE);
			        	location.getBlock().setData((byte) cportal.getGlassData());
				        }else {
				        	if (location.getBlock().getType() == cportal.getMaterial() || location.getBlock().getType().equals(cportal.getGlassData())) {
				        	} else {
				        		if (cportal.getData()==-1) {
				        			if (location.getBlock().getType() == cportal.getMaterial()) {
				        			} else {break;}
				        		} else {break;}
				        	}
				        }
			        if (up.get(cportal) == 1) {
			        	if (location.getY() == ploc.getY()+1) {break;}
			        } else {
			        	if (location.getY() == ploc.getY()+up.get(cportal)) {break;}
			        }
			        if (location.getBlock().getType() == cportal.getMaterial()) {
			        	location.add(-(west.get(cportal)-1), 0, 0);
			        	location.add(0, 1, 0);
			        }
			    }
		  }
		  if (true) {
			    Location location = ploc.clone();
			    for (int blocks = 1; blocks <= rad; blocks++) {
			        location.add(1, 0, 0);
			        if (location.getBlock().getType().equals(Material.AIR)||location.getBlock().getType().equals(Material.FIRE)) {
			        	location.getBlock().setType(Material.STAINED_GLASS_PANE);
			        	location.getBlock().setData((byte) cportal.getGlassData());
				        }else {
				        	if (location.getBlock().getType() == cportal.getMaterial() || location.getBlock().getType().equals(cportal.getGlassData())) {
				        	} else {
				        		if (cportal.getData()==-1) {
				        			if (location.getBlock().getType() == cportal.getMaterial()) {
				        			} else {break;}
				        		} else {break;}
				        	}
				        }
			        if (down.get(cportal) == 1) {
			        	if (location.getY() == ploc.getY()+1) {break;}
			        } else {
			        	if (location.getY() == ploc.getY()+down.get(cportal)) {break;}
			        }
			        if (location.getBlock().getType() == cportal.getMaterial()) {
			        	location.add(-(east.get(cportal)+1), 0, 0);
			        	location.add(0, -1, 0);
			        }
			    }
		  }
		  if (true) {
			    Location location = ploc.clone();
			    for (int blocks = 1; blocks <= rad; blocks++) {
			        location.add(-1, 0, 0);
			        if (location.getBlock().getType().equals(Material.AIR)||location.getBlock().getType().equals(Material.FIRE)) {
			        	location.getBlock().setType(Material.STAINED_GLASS_PANE);
			        	location.getBlock().setData((byte) cportal.getGlassData());
				        }else {
				        	if (location.getBlock().getType() == cportal.getMaterial() || location.getBlock().getType().equals(cportal.getGlassData())) {
				        	} else {
				        		if (cportal.getData()==-1) {
				        			if (location.getBlock().getType() == cportal.getMaterial()) {
				        			} else {break;}
				        		} else {break;}
				        	}
				        }
			        if (down.get(cportal) == 1) {
			        	if (location.getY() == ploc.getY()+1) {break;}
			        } else {
			        	if (location.getY() == ploc.getY()+down.get(cportal)) {break;}
			        }
			        if (location.getBlock().getType() == cportal.getMaterial()) {
			        	location.add(-(west.get(cportal)-1), 0, 0);
			        	location.add(0, -1, 0);
			        }
			    }
		  }
	  }

	  @SuppressWarnings("deprecation")
	public void lightPortalNS(CPortal cportal) {
		  Location ploc = cportal.getLocation();

		  if (true) {
			    Location location = ploc.clone();
			    for (int blocks = 1; blocks <= rad; blocks++) {
			        location.add(0, 0, 1);
			        if (location.getBlock().getType().equals(Material.AIR)||location.getBlock().getType().equals(Material.FIRE)) {
			        	location.getBlock().setType(Material.STAINED_GLASS_PANE);
			        	location.getBlock().setData((byte) cportal.getGlassData());
				        }else {
				        	if (location.getBlock().getType() == cportal.getMaterial() || location.getBlock().getType().equals(cportal.getGlassData())) {
				        	} else {
				        		if (cportal.getData()==-1) {
				        			if (location.getBlock().getType() == cportal.getMaterial()) {
				        			} else {break;}
				        		} else {break;}
				        	}
				        }
			        if (up.get(cportal) == 1) {
			        	if (location.getY() == ploc.getY()+1) {break;}
			        } else {
			        	if (location.getY() == ploc.getY()+up.get(cportal)) {break;}
			        }
			        if (location.getBlock().getType() == cportal.getMaterial()) {
			        	location.add(0, 0, -(south.get(cportal)+1));
			        	location.add(0, 1, 0);
			        }
			    }
		  }
		if (true) {
		    Location location = ploc.clone();
		    for (int blocks = 1; blocks <= rad; blocks++) {
		        location.add(0, 0, -1);
		        if (location.getBlock().getType().equals(Material.AIR)||location.getBlock().getType().equals(Material.FIRE)) {
		        	location.getBlock().setType(Material.STAINED_GLASS_PANE);
		        	location.getBlock().setData((byte) cportal.getGlassData());
			        }else {
			        	if (location.getBlock().getType() == cportal.getMaterial() || location.getBlock().getType().equals(cportal.getGlassData())) {
			        	} else {
			        		if (cportal.getData()==-1) {
			        			if (location.getBlock().getType() == cportal.getMaterial()) {
			        			} else {break;}
			        		} else {break;}
			        	}
			        }
		        if (up.get(cportal) == 1) {
		        	if (location.getY() == ploc.getY()+1) {break;}
		        } else {
		        	if (location.getY() == ploc.getY()+up.get(cportal)) {break;}
		        }
		        if (location.getBlock().getType() == cportal.getMaterial()) {
		        	location.add(0, 0, -(north.get(cportal)-1));
		        	location.add(0, 1, 0);
		        }
		    }
		}
		if (true) {
		    Location location = ploc.clone();
		    for (int blocks = 1; blocks <= rad; blocks++) {
		        location.add(0, 0, 1);
		        if (location.getBlock().getType().equals(Material.AIR)||location.getBlock().getType().equals(Material.FIRE)) {
		        	location.getBlock().setType(Material.STAINED_GLASS_PANE);
		        	location.getBlock().setData((byte) cportal.getGlassData());
			        }else {
			        	if (location.getBlock().getType() == cportal.getMaterial() || location.getBlock().getType().equals(cportal.getGlassData())) {
			        	} else {
			        		if (cportal.getData()==-1) {
			        			if (location.getBlock().getType() == cportal.getMaterial()) {
			        			} else {break;}
			        		} else {break;}
			        	}
			        }
		        if (down.get(cportal) == 1) {
		        	if (location.getY() == ploc.getY()+1) {break;}
		        } else {
		        	if (location.getY() == ploc.getY()+down.get(cportal)) {break;}
		        }
		        if (location.getBlock().getType() == cportal.getMaterial()) {
		        	location.add(0, 0, -(south.get(cportal)+1));
		        	location.add(0, -1, 0);
		        }
		    }
		}
		  if (true) {
			    Location location = ploc.clone();
			    for (int blocks = 1; blocks <= rad; blocks++) {
			        location.add(0, 0, -1);
			        if (location.getBlock().getType().equals(Material.AIR)||location.getBlock().getType().equals(Material.FIRE)) {
			        	location.getBlock().setType(Material.STAINED_GLASS_PANE);
			        	location.getBlock().setData((byte) cportal.getGlassData());
				        }else {
				        	if (location.getBlock().getType() == cportal.getMaterial() || location.getBlock().getType().equals(cportal.getGlassData())) {
				        	} else {
				        		if (cportal.getData()==-1) {
				        			if (location.getBlock().getType() == cportal.getMaterial()) {
				        			} else {break;}
				        		} else {break;}
				        	}
				        }
			        if (down.get(cportal) == 1) {
			        	if (location.getY() == ploc.getY()+1) {break;}
			        } else {
			        	if (location.getY() == ploc.getY()+down.get(cportal)) {break;}
			        }
			        if (location.getBlock().getType() == cportal.getMaterial()) {
			        	location.add(0, 0, -(north.get(cportal)-1));
			        	location.add(0, -1, 0);
			        }
			    }
		  }
	  }

	  /**/
		@SuppressWarnings("deprecation")
		@EventHandler
		  public void onBlockPlace(BlockPlaceEvent e) {

			  Block b = e.getBlockPlaced();

			  //Light the portal
			 if (b.getType()== Material.FIRE) {
				 CPortal cportal = getPortal(b.getLocation());
				 if (cportal!=null) {
					 if (cportal.isEnabled()) {
						 if (isInPortal(cportal)) {
							 if (canLightPortalEW(cportal)) {
								  int eastf = east.get(cportal);
								  int westf = Math.abs(west.get(cportal))-1;

								  int upf = up.get(cportal);
								  int downf = Math.abs(down.get(cportal))-1;

								if ((eastf+westf>=cportal.getMinWidth()) && (upf+downf>=cportal.getMinHeight())) {
									CreateCPortalEvent event = new CreateCPortalEvent(e.getPlayer(), e.getBlockPlaced().getLocation(), cportal, upf+downf, eastf+westf);
									 Bukkit.getServer().getPluginManager().callEvent(event);
									 if (!event.isCancelled()) {
										 cportal.getLocation().getBlock().setType(Material.STAINED_GLASS_PANE);
								        cportal.getLocation().getBlock().setData((byte) cportal.getGlassData());
								        lightPortalEW(cportal);
									 }
								}
							 } else if (canLightPortalNS(cportal)) {
								  int northf = Math.abs(north.get(cportal))-1;
								  int southf = south.get(cportal);
								  int upf = up.get(cportal);
								  int downf = Math.abs(down.get(cportal))-1;
								if ((northf+southf>=cportal.getMinWidth()) && (upf+downf>=cportal.getMinHeight())) {
									CreateCPortalEvent event = new CreateCPortalEvent(e.getPlayer(), e.getBlockPlaced().getLocation(), cportal, upf+downf, northf+southf);
									 Bukkit.getServer().getPluginManager().callEvent(event);
									 if (!event.isCancelled()) {
							        	cportal.getLocation().getBlock().setType(Material.STAINED_GLASS_PANE);
							        	cportal.getLocation().getBlock().setData((byte) cportal.getGlassData());
							        	lightPortalNS(cportal);
									 }
								}
							 }
						 }
					 }
				 }
			 }

		  }
		/*LIGHT PORTAL*/

			@SuppressWarnings("deprecation")
			@EventHandler
			  public void onBlockBreak(BlockBreakEvent e) {
				@SuppressWarnings("unused")
				Player p = e.getPlayer();
				  Block b = e.getBlock();


				  //If a block of the portal is broken all of the glass should be gone
				  //or if a block of glass is broken should do the same thing
				  //thats what this thing does
					 if (b.getType()== Material.STAINED_GLASS_PANE) {
						  CPortal cportal = getPortal(b.getLocation());
						  if (cportal!=null) {
						  if (isInPortal(cportal)) {
							if (hasGlassEW(cportal)) {
								DestroyCPortalEvent event = new DestroyCPortalEvent(e.getPlayer(), e.getBlock().getLocation(), cportal);
								 Bukkit.getServer().getPluginManager().callEvent(event);
								 if (!event.isCancelled()) {
									 destroyLightEW(cportal);
								 } else {
									 e.setCancelled(true);
								 }
							} else if (hasGlassNS(cportal)) {
								DestroyCPortalEvent event = new DestroyCPortalEvent(e.getPlayer(), e.getBlock().getLocation(), cportal);
								 Bukkit.getServer().getPluginManager().callEvent(event);
								 if (!event.isCancelled()) {
									 destroyLightNS(cportal);
								 } else {
									 e.setCancelled(true);
								 }
							 }
						  }
						  }
					 } else {
						 Location nloc = b.getLocation();
						 if (nloc.getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.STAINED_GLASS_PANE)||nloc.getBlock().getRelative(BlockFace.UP).getType().equals(Material.STAINED_GLASS_PANE)||nloc.getBlock().getRelative(BlockFace.EAST).getType().equals(Material.STAINED_GLASS_PANE)||nloc.getBlock().getRelative(BlockFace.WEST).getType().equals(Material.STAINED_GLASS_PANE)||nloc.getBlock().getRelative(BlockFace.SOUTH).getType().equals(Material.STAINED_GLASS_PANE)||nloc.getBlock().getRelative(BlockFace.NORTH).getType().equals(Material.STAINED_GLASS_PANE)) {
										e.setCancelled(true);
										if (b.getRelative(BlockFace.DOWN).getType().equals(Material.STAINED_GLASS_PANE)) {
											  CPortal cportal = getPortal(b.getRelative(BlockFace.DOWN).getLocation());
											  if (cportal!=null) {
											  if (isInPortal(cportal)) {
												  if (b.getType()==cportal.getMaterial() && (cportal.getData()==-1||b.getState().getRawData()==cportal.getData())) {
													if (hasGlassEW(cportal)) {
														DestroyCPortalEvent event = new DestroyCPortalEvent(e.getPlayer(), e.getBlock().getLocation(), cportal);
														 Bukkit.getServer().getPluginManager().callEvent(event);
														 if (!event.isCancelled()) {
															 destroyLightEW(cportal);
															 b.getRelative(BlockFace.DOWN).setType(Material.AIR);
														 } else {
															 return;
														 }
													} else if (hasGlassNS(cportal)) {
														DestroyCPortalEvent event = new DestroyCPortalEvent(e.getPlayer(), e.getBlock().getLocation(), cportal);
														 Bukkit.getServer().getPluginManager().callEvent(event);
														 if (!event.isCancelled()) {
															 destroyLightNS(cportal);
															 b.getRelative(BlockFace.DOWN).setType(Material.AIR);
														 } else {
															 return;
														 }
													 }
												  }
												}
											  }
										} else if (b.getRelative(BlockFace.UP).getType().equals(Material.STAINED_GLASS_PANE)) {
											  CPortal cportal = getPortal(b.getRelative(BlockFace.UP).getLocation());
											  if (cportal!=null) {
											  if (isInPortal(cportal)) {
												  if (b.getType()==cportal.getMaterial() && (cportal.getData()==-1||b.getState().getRawData()==cportal.getData())) {
														if (hasGlassEW(cportal)) {
															DestroyCPortalEvent event = new DestroyCPortalEvent(e.getPlayer(), e.getBlock().getLocation(), cportal);
															 Bukkit.getServer().getPluginManager().callEvent(event);
															 if (!event.isCancelled()) {
																 destroyLightEW(cportal);
																 b.getRelative(BlockFace.UP).setType(Material.AIR);
															 } else {
																 return;
															 }
														} else if (hasGlassNS(cportal)) {
															DestroyCPortalEvent event = new DestroyCPortalEvent(e.getPlayer(), e.getBlock().getLocation(), cportal);
															 Bukkit.getServer().getPluginManager().callEvent(event);
															 if (!event.isCancelled()) {
																 destroyLightNS(cportal);
																 b.getRelative(BlockFace.UP).setType(Material.AIR);
															 } else {
																 return;
															 }
														 }
											  		}
												  }
											  }
										} else if (b.getRelative(BlockFace.WEST).getType().equals(Material.STAINED_GLASS_PANE)) {
											  CPortal cportal = getPortal(b.getRelative(BlockFace.WEST).getLocation());
											  if (cportal!=null) {
											  if (isInPortal(cportal)) {
												  if (b.getType()==cportal.getMaterial() && (cportal.getData()==-1||b.getState().getRawData()==cportal.getData())) {
														if (hasGlassEW(cportal)) {
															DestroyCPortalEvent event = new DestroyCPortalEvent(e.getPlayer(), e.getBlock().getLocation(), cportal);
															 Bukkit.getServer().getPluginManager().callEvent(event);
															 if (!event.isCancelled()) {
																 destroyLightEW(cportal);
																 b.getRelative(BlockFace.WEST).setType(Material.AIR);
															 } else {
																 return;
															 }
														} else if (hasGlassNS(cportal)) {
															DestroyCPortalEvent event = new DestroyCPortalEvent(e.getPlayer(), e.getBlock().getLocation(), cportal);
															 Bukkit.getServer().getPluginManager().callEvent(event);
															 if (!event.isCancelled()) {
																 destroyLightNS(cportal);
																 b.getRelative(BlockFace.WEST).setType(Material.AIR);
															 } else {
																 return;
															 }
														 }
												  	}
												  }
											  }
										} else if (b.getRelative(BlockFace.EAST).getType().equals(Material.STAINED_GLASS_PANE)) {
											  CPortal cportal = getPortal(b.getRelative(BlockFace.EAST).getLocation());
											  if (cportal!=null) {
											  if (isInPortal(cportal)) {
												  if (b.getType()==cportal.getMaterial() && (cportal.getData()==-1||b.getState().getRawData()==cportal.getData())) {
													if (hasGlassEW(cportal)) {
														DestroyCPortalEvent event = new DestroyCPortalEvent(e.getPlayer(), e.getBlock().getLocation(), cportal);
														 Bukkit.getServer().getPluginManager().callEvent(event);
														 if (!event.isCancelled()) {
															 destroyLightEW(cportal);
															 b.getRelative(BlockFace.EAST).setType(Material.AIR);
														 } else {
															 return;
														 }
													} else if (hasGlassNS(cportal)) {
														DestroyCPortalEvent event = new DestroyCPortalEvent(e.getPlayer(), e.getBlock().getLocation(), cportal);
														 Bukkit.getServer().getPluginManager().callEvent(event);
														 if (!event.isCancelled()) {
															 destroyLightNS(cportal);
															 b.getRelative(BlockFace.EAST).setType(Material.AIR);
														 } else {
															 return;
														 }
													 }
												  	}
												  }
											  }
										} else if (b.getRelative(BlockFace.NORTH).getType().equals(Material.STAINED_GLASS_PANE)) {
											  CPortal cportal = getPortal(b.getRelative(BlockFace.NORTH).getLocation());
											  if (cportal!=null) {
											  if (isInPortal(cportal)) {
												  if (b.getType()==cportal.getMaterial() && (cportal.getData()==-1||b.getState().getRawData()==cportal.getData())) {
													if (hasGlassEW(cportal)) {
														DestroyCPortalEvent event = new DestroyCPortalEvent(e.getPlayer(), e.getBlock().getLocation(), cportal);
														 Bukkit.getServer().getPluginManager().callEvent(event);
														 if (!event.isCancelled()) {
															 destroyLightEW(cportal);
															 b.getRelative(BlockFace.NORTH).setType(Material.AIR);
														 } else {
															 return;
														 }
													} else if (hasGlassNS(cportal)) {
														DestroyCPortalEvent event = new DestroyCPortalEvent(e.getPlayer(), e.getBlock().getLocation(), cportal);
														 Bukkit.getServer().getPluginManager().callEvent(event);
														 if (!event.isCancelled()) {
															 destroyLightNS(cportal);
															 b.getRelative(BlockFace.NORTH).setType(Material.AIR);
														 } else {
															 return;
														 }
													 }
												  }
											  	}
											}
										} else if (b.getRelative(BlockFace.SOUTH).getType().equals(Material.STAINED_GLASS_PANE)) {
											  CPortal cportal = getPortal(b.getRelative(BlockFace.SOUTH).getLocation());
											  if (cportal!=null) {
											  if (isInPortal(cportal)) {
												  if (b.getType()==cportal.getMaterial()) {
													if (hasGlassEW(cportal)) {
														DestroyCPortalEvent event = new DestroyCPortalEvent(e.getPlayer(), e.getBlock().getLocation(), cportal);
														 Bukkit.getServer().getPluginManager().callEvent(event);
														 if (!event.isCancelled()) {
															 destroyLightEW(cportal);
															 b.getRelative(BlockFace.SOUTH).setType(Material.AIR);
														 } else {
															 return;
														 }
													} else if (hasGlassNS(cportal)) {
														DestroyCPortalEvent event = new DestroyCPortalEvent(e.getPlayer(), e.getBlock().getLocation(), cportal);
														 Bukkit.getServer().getPluginManager().callEvent(event);
														 if (!event.isCancelled()) {
															 destroyLightNS(cportal);
															 b.getRelative(BlockFace.SOUTH).setType(Material.AIR);
														 } else {
															 return;
														 }
													 }
												  }
											  	}
											}
										}
										e.setCancelled(false);
						 }

					 }

			  }

		  @SuppressWarnings("deprecation")
			public void destroyLightEW(CPortal cportal) {
			  Location ploc = cportal.getLocation();

			  if (true) {
				    Location location = ploc.clone();
				    for (int blocks = 1; blocks <= rad; blocks++) {
				        location.add(1, 0, 0);
				        if (location.getBlock().getType().equals(Material.STAINED_GLASS_PANE)||location.getBlock().getType().equals(Material.AIR)) {
				        	location.getBlock().setType(Material.AIR);
					        }else {
					        	if (location.getBlock().getType() == cportal.getMaterial() || location.getBlock().getType().equals(cportal.getGlassData())) {
					        	} else {
					        		if (cportal.getData()==-1) {
					        			if (location.getBlock().getType() == cportal.getMaterial()) {
					        			} else {break;}
					        		} else {break;}
					        	}
					        }
				        if (up.get(cportal) == 1) {
				        	if (location.getY() == ploc.getY()+1) {break;}
				        } else {
				        	if (location.getY() == ploc.getY()+up.get(cportal)) {break;}
				        }
				        if (location.getBlock().getType() == cportal.getMaterial()) {
				        	location.add(-(east.get(cportal)+1), 0, 0);
				        	location.add(0, 1, 0);
				        }
				    }
			  }
			  if (true) {
				    Location location = ploc.clone();
				    for (int blocks = 1; blocks <= rad; blocks++) {
				        location.add(-1, 0, 0);
				        if (location.getBlock().getType().equals(Material.STAINED_GLASS_PANE)||location.getBlock().getType().equals(Material.AIR)) {
				        	location.getBlock().setType(Material.AIR);
					        }else {
					        	if (location.getBlock().getType() == cportal.getMaterial() || location.getBlock().getType().equals(cportal.getGlassData())) {
					        	} else {
					        		if (cportal.getData()==-1) {
					        			if (location.getBlock().getType() == cportal.getMaterial()) {
					        			} else {break;}
					        		} else {break;}
					        	}
					        }
				        if (up.get(cportal) == 1) {
				        	if (location.getY() == ploc.getY()+1) {break;}
				        } else {
				        	if (location.getY() == ploc.getY()+up.get(cportal)) {break;}
				        }
				        if (location.getBlock().getType() == cportal.getMaterial()) {
				        	location.add(-(west.get(cportal)-1), 0, 0);
				        	location.add(0, 1, 0);
				        }
				    }
			  }
			  if (true) {
				    Location location = ploc.clone();
				    for (int blocks = 1; blocks <= rad; blocks++) {
				        location.add(1, 0, 0);
				        if (location.getBlock().getType().equals(Material.STAINED_GLASS_PANE)||location.getBlock().getType().equals(Material.AIR)) {
				        	location.getBlock().setType(Material.AIR);
					        }else {
					        	if (location.getBlock().getType() == cportal.getMaterial() || location.getBlock().getType().equals(cportal.getGlassData())) {
					        	} else {
					        		if (cportal.getData()==-1) {
					        			if (location.getBlock().getType() == cportal.getMaterial()) {
					        			} else {break;}
					        		} else {break;}
					        	}
					        }
				        if (down.get(cportal) == 1) {
				        	if (location.getY() == ploc.getY()+1) {break;}
				        } else {
				        	if (location.getY() == ploc.getY()+down.get(cportal)) {break;}
				        }
				        if (location.getBlock().getType() == cportal.getMaterial()) {
				        	location.add(-(east.get(cportal)+1), 0, 0);
				        	location.add(0, -1, 0);
				        }
				    }
			  }
			  if (true) {
				    Location location = ploc.clone();
				    for (int blocks = 1; blocks <= rad; blocks++) {
				        location.add(-1, 0, 0);
				        if (location.getBlock().getType().equals(Material.STAINED_GLASS_PANE)||location.getBlock().getType().equals(Material.AIR)) {
				        	location.getBlock().setType(Material.AIR);
					        }else {
					        	if (location.getBlock().getType() == cportal.getMaterial() || location.getBlock().getType().equals(cportal.getGlassData())) {
					        	} else {
					        		if (cportal.getData()==-1) {
					        			if (location.getBlock().getType() == cportal.getMaterial()) {
					        			} else {break;}
					        		} else {break;}
					        	}
					        }
				        if (down.get(cportal) == 1) {
				        	if (location.getY() == ploc.getY()+1) {break;}
				        } else {
				        	if (location.getY() == ploc.getY()+down.get(cportal)) {break;}
				        }
				        if (location.getBlock().getType() == cportal.getMaterial()) {
				        	location.add(-(west.get(cportal)-1), 0, 0);
				        	location.add(0, -1, 0);
				        }
				    }
			  }
			  }

			  @SuppressWarnings("deprecation")
			public void destroyLightNS(CPortal cportal) {
				  Location ploc = cportal.getLocation();

				  if (true) {
					    Location location = ploc.clone();
					    for (int blocks = 1; blocks <= rad; blocks++) {
					        location.add(0, 0, 1);
					        if (location.getBlock().getType().equals(Material.STAINED_GLASS_PANE)||location.getBlock().getType().equals(Material.AIR)) {
					        	location.getBlock().setType(Material.AIR);
						        }else {
						        	if (location.getBlock().getType() == cportal.getMaterial() || location.getBlock().getType().equals(cportal.getGlassData())) {
						        	} else {
						        		if (cportal.getData()==-1) {
						        			if (location.getBlock().getType() == cportal.getMaterial()) {
						        			} else {break;}
						        		} else {break;}
						        	}
						        }
					        if (up.get(cportal) == 1) {
					        	if (location.getY() == ploc.getY()+1) {break;}
					        } else {
					        	if (location.getY() == ploc.getY()+up.get(cportal)) {break;}
					        }
					        if (location.getBlock().getType() == cportal.getMaterial()) {
					        	location.add(0, 0, -(south.get(cportal)+1));
					        	location.add(0, 1, 0);
					        }
					    }
				  }
				if (true) {
				    Location location = ploc.clone();
				    for (int blocks = 1; blocks <= rad; blocks++) {
				        location.add(0, 0, -1);
				        if (location.getBlock().getType().equals(Material.STAINED_GLASS_PANE)||location.getBlock().getType().equals(Material.AIR)) {
				        	location.getBlock().setType(Material.AIR);
					        }else {
					        	if (location.getBlock().getType() == cportal.getMaterial() || location.getBlock().getType().equals(cportal.getGlassData())) {
					        	} else {
					        		if (cportal.getData()==-1) {
					        			if (location.getBlock().getType() == cportal.getMaterial()) {
					        			} else {break;}
					        		} else {break;}
					        	}
					        }
				        if (up.get(cportal) == 1) {
				        	if (location.getY() == ploc.getY()+1) {break;}
				        } else {
				        	if (location.getY() == ploc.getY()+up.get(cportal)) {break;}
				        }
				        if (location.getBlock().getType() == cportal.getMaterial()) {
				        	location.add(0, 0, -(north.get(cportal)-1));
				        	location.add(0, 1, 0);
				        }
				    }
				}
				if (true) {
				    Location location = ploc.clone();
				    for (int blocks = 1; blocks <= rad; blocks++) {
				        location.add(0, 0, 1);
				        if (location.getBlock().getType().equals(Material.STAINED_GLASS_PANE)||location.getBlock().getType().equals(Material.AIR)) {
				        	location.getBlock().setType(Material.AIR);
					        }else {
					        	if (location.getBlock().getType() == cportal.getMaterial() || location.getBlock().getType().equals(cportal.getGlassData())) {
					        	} else {
					        		if (cportal.getData()==-1) {
					        			if (location.getBlock().getType() == cportal.getMaterial()) {
					        			} else {break;}
					        		} else {break;}
					        	}
					        }
				        if (down.get(cportal) == 1) {
				        	if (location.getY() == ploc.getY()+1) {break;}
				        } else {
				        	if (location.getY() == ploc.getY()+down.get(cportal)) {break;}
				        }
				        if (location.getBlock().getType() == cportal.getMaterial()) {
				        	location.add(0, 0, -(south.get(cportal)+1));
				        	location.add(0, -1, 0);
				        }
				    }
				}
				  if (true) {
					    Location location = ploc.clone();
					    for (int blocks = 1; blocks <= rad; blocks++) {
					        location.add(0, 0, -1);
					        if (location.getBlock().getType().equals(Material.STAINED_GLASS_PANE)||location.getBlock().getType().equals(Material.AIR)) {
					        	location.getBlock().setType(Material.AIR);
						        }else {
						        	if (location.getBlock().getType() == cportal.getMaterial() || location.getBlock().getType().equals(cportal.getGlassData())) {
						        	} else {
						        		if (cportal.getData()==-1) {
						        			if (location.getBlock().getType() == cportal.getMaterial()) {	
						        			} else {break;}
						        		} else {break;}
						        	}
						        }
					        if (down.get(cportal) == 1) {
					        	if (location.getY() == ploc.getY()+1) {break;}
					        } else {
					        	if (location.getY() == ploc.getY()+down.get(cportal)) {break;}
					        }
					        if (location.getBlock().getType() == cportal.getMaterial()) {
					        	location.add(0, 0, -(north.get(cportal)-1));
					        	location.add(0, -1, 0);
					        }
					    }
				  }
			  }
			  
		//Here would go teleport delay
		@SuppressWarnings("deprecation")
		public void testPlayer(Player p) {
			 if (!us.contains(p)) {
					 for (String fl : portalList) {
						 CPortal cportal = new CPortal(fl.replace(".yml", ""), p.getLocation());
						 if (isInFullPortal(cportal)) {
							 if (canUsePortal(p, cportal, false, true)) {
									if (p.getGameMode().equals(GameMode.SURVIVAL)||p.getGameMode().equals(GameMode.ADVENTURE)) {
										if (!us.contains(p)) {
											p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 7*20, 200));
										}
									}
								 usePortal(p, cportal);
							 }
						 }
					 }
			 } else {
				 boolean aa = false;
				 CPortal cportal = getPortal(p.getLocation());
					if (cportal!=null) {
					if (isInPortal(cportal)) {
					if (isInFullPortal(cportal)) {
						if (!FrameClass.plo.containsKey(p)) {
							FrameClass.plo.put(p, p.getLocation());
							FrameClass.hide(p, cportal);
							p.sendBlockChange(p.getLocation(), Material.AIR, (byte) 0);
						}
						aa = true;
					}
					}
					}
					
					if (!aa) {
						if (FrameClass.plo.containsKey(p)) {
							cportal = getPortal(FrameClass.plo.get(p));
							if (cportal!=null) {
								if (isInPortal(cportal)) {
								FrameClass.show(p, cportal);
								p.sendBlockChange(FrameClass.plo.get(p), Material.STAINED_GLASS_PANE, (byte) cportal.getGlassData());
								}
							}
							FrameClass.plo.remove(p);
						}
						us.remove(p);
					}
		 }
		}
		
		
		public boolean isNotOnClaimedLand(Player p, CPortal cportal) {
			if (Main.getInstance().usingfac) {
				if (Main.getInstance().fpt.equalsIgnoreCase("MCF")) {
					if (Main.getInstance().flMCF.isNotOnClaimedLand(p, cportal, cportal.getTeleportLocation().getWorld())) {
						return true;
					}
				} else if (Main.getInstance().fpt.equalsIgnoreCase("LF")) {
					if (Main.getInstance().flLF.isNotOnClaimedLand(p, cportal, cportal.getTeleportLocation().getWorld())) {
						return true;
					}
				}
			} else {
				return true;
			}
			return false;
		}
		
		public String getClaimedLandType(Player p, CPortal cportal) {
			if (Main.getInstance().fpt.equalsIgnoreCase("MCF")) {
				return Main.getInstance().flMCF.isOnClaimedLandType(p, cportal, cportal.getTeleportLocation().getWorld());
			} else if (Main.getInstance().fpt.equalsIgnoreCase("LF")) {
				return Main.getInstance().flLF.isOnClaimedLandType(p, cportal, cportal.getTeleportLocation().getWorld());
			} else {
				return "�4�l�nError�7";
			}
		}
		
		public boolean isInRegion(Player p, CPortal cportal) {
//			if (Main.getInstance().usingwg) {
//				if (Main.getInstance().wgr.isInRegion(p, cportal)) {
//					return true;
//				} else {
//					return false;
//				}
//			}
			return false;
		}
		
		public String getRegionName(Player p, CPortal cportal) {
			return Main.getInstance().wgr.getRegionName(cportal);
		}
		
}

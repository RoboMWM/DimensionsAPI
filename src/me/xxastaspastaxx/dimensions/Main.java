package me.xxastaspastaxx.dimensions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.xxastaspastaxx.dimensions.events.DimensionsLoadEvent;
import me.xxastaspastaxx.dimensions.events.DimensionsUnloadEvent;
import me.xxastaspastaxx.dimensions.particles.Particles;
import me.xxastaspastaxx.dimensions.portal.PortalClass;

public class Main extends JavaPlugin implements Listener {
	
	ArrayList<String> aliases = new ArrayList<String>();
	
	/*Commands list // Change this. It shouldnt be that way */
	public static HashMap<Integer, ArrayList<String>> help = new HashMap<Integer, ArrayList<String>>();
	public static HashMap<Integer, ArrayList<String>> perms = new HashMap<Integer, ArrayList<String>>();
	
	public Listeners l;
	public PortalClass portalClass;
	public Particles pac;
	
	//This will be used when players are using a portal
	public boolean usingfac = false;
	public String fpt = "none";
	
	//This will be used when players are using a portal
	public boolean usingwg = false;
	
	public void onEnable() {

		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			//This is used to return players to the previous world
			File pdLp = new File("plugins/Dimensions/PlayerData/"+p.getName()+"/LastPortal.yml");
			YamlConfiguration pdlpf = YamlConfiguration.loadConfiguration(pdLp);
			
			//Portal name
	          List<String> lup = pdlpf.getStringList("LastUsedP");
	          pdlpf.set("LastUsedP", lup);

	        //Worlds name
	          List<String> luw = pdlpf.getStringList("LastUsedW");
	          pdlpf.set("LastUsedW", luw);

			try {
				pdlpf.save(pdLp);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// Create a file to save essence stuff if it doesnt exist
			File pef = new File("plugins/Dimensions/PlayerData/"+p.getName()+"/Essence.yml");
			YamlConfiguration peff = YamlConfiguration.loadConfiguration(pef);
			
			peff.addDefault("Amount", "0");
			
			 peff.options().copyDefaults(true);
			try {
				peff.save(pef);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// help // TODO
		ArrayList<String> hpg1 = new ArrayList<String>();
		hpg1.add("§7/Dm Help [page] §c-§7 Displays help.");
		hpg1.add("§7/Dm Reload §c-§7 Reload Config");
		hpg1.add("§7/Dm Remove [amo] [player] §c-§7 Remove portal essence");
		hpg1.add("§7/Dm Add [amo] [player] §c-§7 Add portal essence");
		help.put(1, hpg1);
		
		ArrayList<String> hpg2 = new ArrayList<String>();
		hpg2.add("§7/Dm Set <amo> [player] §c-§7 Set teleport essence");
		hpg2.add("§7/Dm Ess [player] §c-§7 Show your/player's portal essence");
		hpg2.add("§7/Dm SpawenEss §c-§7 Spawn portal essence");
		hpg2.add("§7/Dm Perms [command] §c-§7 Show permissions for commands.");
		help.put(2, hpg2);
		// help //
		
		// perms // TODO
		ArrayList<String> ppg1 = new ArrayList<String>();
		ppg1.add("§7/Dm Help [page] §c-§7 dm.help");
		ppg1.add("§7/Dm Reload §c-§7 dm.reload");
		ppg1.add("§7/Dm Remove [amo] [player] §c-§7 dm.remove");
		ppg1.add("§7/Dm Add [amo] [player] §c-§7 dm.add");
		perms.put(1, ppg1);
		
		ArrayList<String> ppg2 = new ArrayList<String>();
		ppg2.add("§7/Dm Set <amo> [player] §c-§7 dm.set");
		ppg2.add("§7/Dm Ess [player] §c-§7 dm.ess");
		ppg2.add("§7/Dm SpawenEss §c-§7 dm.spawness");
		ppg2.add("§7/Dm Perms §c-§7 dm.perms");
		perms.put(2, ppg2);
		// perms //
		
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		
		//Settings, Portals, etc
		File dms = new File("plugins/Dimensions/Settings.yml");
		YamlConfiguration dmsf = YamlConfiguration.loadConfiguration(dms);

		/*List<String> msgoncmd = pf.getStringList("RegisteredPortals");
        msgoncmd.add("TestPortal");
        msgoncmd.add("TestPortal2");
        pf.set("RegisteredPortals", msgoncmd);*/
	
		dmsf.addDefault("Enable", "true");
		//This is a bit buggy - I didnt make it smart enough
        dmsf.addDefault("MaxPortalRadius", "50");
        
        //In case the return algorythm fails
        dmsf.addDefault("MainWorld", "world");
        
        dmsf.addDefault("CustomParticles.MaxDistance", "10");
        
        dmsf.addDefault("SpawnEssences.Enable", "true");
        dmsf.addDefault("SpawnEssences.CollectChanceAmmount", "1-5");
        dmsf.addDefault("SpawnEssences.CollectMessage", "&7You collected &c{amount} &7portal essence.");
	    dmsf.options().copyDefaults(true);
  	
    	
		File lbfo = new File("plugins/Dimensions/Portals");
		File[] lbfi;
		ArrayList<String> portalList = new ArrayList<String>();
    if (lbfo.exists()) {
    	lbfi = lbfo.listFiles(new FilenameFilter() {
		      public boolean accept(File dir, String name) {
		    	  portalList.add(name);
		           return name.toLowerCase().endsWith(".yml");
		      }
		});

    	for (File fl : lbfi) {
    		YamlConfiguration pf = YamlConfiguration.loadConfiguration(fl);
	    	  pf.addDefault("Enable", "true");
	    	  pf.addDefault("DisplayName", "&6Test Portal");

	    	  pf.addDefault("Block", "wool;-1");
	    	  pf.addDefault("Frame", "15");
	    	  pf.addDefault("World", "world");
	    	  pf.addDefault("WorldType", "normal");
	    	  
	    	  pf.addDefault("MinWidth", "2");
	    	  pf.addDefault("MinHeight", "3");
	    	  
	    	  pf.addDefault("CustomParticles.Enable", "true");
	    	  pf.addDefault("CustomParticles.Color", "black");
	    	  
	    	  pf.addDefault("RandomLocation", "false");
	    	  pf.addDefault("BuildExitPortal", "true");
	    	  pf.addDefault("SpawnOnAir", "false");
	    	  
	    	  pf.addDefault("SendMessage.Enable", "true");
	    	  pf.addDefault("SendMessage.Message", "&6{player}, You have been teleported to &c{world}&6 thru &c{portal}&6.");
	    	  
	    	  pf.addDefault("WorldGuard.Enable", "false");
	    	  pf.addDefault("WorldGuard.DenyMessage", "&6Teleportation is disabled in this current location because a protected area is claimed({region})");
	      	  if (pf.getStringList("WorldGuard.DisabledRegions").isEmpty()) {
		          List<String> cmp1wg = pf.getStringList("WorldGuard.DisabledRegions");
		          /*cmp1wg.add("Spawn");
		          cmp1wg.add("Shop");*/
		          pf.set("WorldGuard.DisabledRegions", cmp1wg);
	  	  }
	    	  
	    	  pf.addDefault("FactionsTerritories.Enable", "false");
	    	  pf.addDefault("FactionsTerritories.Enemy", "false");
	    	  pf.addDefault("FactionsTerritories.Ally", "true");
	    	  pf.addDefault("FactionsTerritories.Neutral", "false");
	    	  pf.addDefault("FactionsTerritories.Truce", "true");
	    	  pf.addDefault("FactionsTerritories.Member", "true");
	    	  pf.addDefault("FactionsTerritories.DenyMessage", "&6You cannot use the portal at this location because a &c{type}&6 faction has claim land there.");
	    	  
	    	  pf.addDefault("Essence.Enable", "true");
	    	  pf.addDefault("Essence.Amount", "25");
	    	  pf.addDefault("Essence.NotEnoughEssenceMessage", "&6{player}&c, You do not have &6{amount}&c essence to use this portal.");
	    	  
	    	  pf.addDefault("Charge.Enable", "false");
	    	  pf.addDefault("Charge.Amount", "50");
	    	  pf.addDefault("Charge.NotEnoughMoneyMessage", "&6{player}&c, You do not have &6{amount}&c to use this portal.");

	    	  pf.addDefault("UsePermission.Enable", "true");
	    	  pf.addDefault("UsePermission.Permission", "dm.useportal.testportal");
	    	  pf.addDefault("UsePermission.NoPermissionMessage", "&6{player}&c, Sorry but you have no permission &4{permission}&c to enter &6{portal}&c.");
    	
	    	  pf.options().copyDefaults(true);
	    	  
	    	  try {
				pf.save(fl);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
	} else {
		File ms = new File("plugins/Dimensions/Portals/ExamplePortal.yml");
		YamlConfiguration pf = YamlConfiguration.loadConfiguration(ms);
  	  pf.addDefault("Enable", "true");
  	  pf.addDefault("DisplayName", "&6Test Portal");

  	  pf.addDefault("Block", "wool;-1");
  	  pf.addDefault("Frame", "15");
  	  pf.addDefault("World", "world");
  	  pf.addDefault("WorldType", "normal");
  	  
	  pf.addDefault("MinWidth", "2");
	  pf.addDefault("MinHeight", "3");
  	  
  	  pf.addDefault("CustomParticles.Enable", "true");
  	  pf.addDefault("CustomParticles.Color", "black");
  	  
  	  pf.addDefault("RandomLocation", "false");
  	  pf.addDefault("BuildExitPortal", "true");
  	  pf.addDefault("SpawnOnAir", "false");
  	  
  	  pf.addDefault("SendMessage.Enable", "true");
  	  pf.addDefault("SendMessage.Message", "&6{player}, You have been teleported to &c{world}&6 thru &c{portal}&6.");
  	  
  	  pf.addDefault("WorldGuard.Enable", "false");
  	  pf.addDefault("WorldGuard.DenyMessage", "&6Teleportation is disabled in this current location because a protected area is claimed({region})");
  	  if (pf.getStringList("WorldGuard.DisabledRegions").isEmpty()) {
	          List<String> cmp1wg = pf.getStringList("WorldGuard.DisabledRegions");
	          /*cmp1wg.add("Spawn");
	          cmp1wg.add("Shop");*/
	          pf.set("WorldGuard.DisabledRegions", cmp1wg);
  	  }
  	  
  	  pf.addDefault("FactionsTerritories.Enable", "false");
  	  pf.addDefault("FactionsTerritories.Enemy", "false");
  	  pf.addDefault("FactionsTerritories.Ally", "true");
  	  pf.addDefault("FactionsTerritories.Neutral", "false");
  	  pf.addDefault("FactionsTerritories.Truce", "true");
  	  pf.addDefault("FactionsTerritories.Member", "true");
  	  pf.addDefault("FactionsTerritories.DenyMessage", "&6You cannot use the portal at this location because a &c{type}&6 faction has claim land there.");
  	  
  	  pf.addDefault("Essence.Enable", "true");
  	  pf.addDefault("Essence.Amount", "25");
  	  pf.addDefault("Essence.NotEnoughEssenceMessage", "&6{player}&c, You do not have &6{amount}&c essence to use this portal.");
  	  
  	  pf.addDefault("Charge.Enable", "false");
  	  pf.addDefault("Charge.Amount", "50");
  	  pf.addDefault("Charge.NotEnoughMoneyMessage", "&6{player}&c, You do not have &6{amount}&c to use this portal.");

  	  pf.addDefault("UsePermission.Enable", "true");
  	  pf.addDefault("UsePermission.Permission", "dm.useportal.testportal");
  	  pf.addDefault("UsePermission.NoPermissionMessage", "&6{player}&c, Sorry but you have no permission &4{permission}&c to enter &6{portal}&c.");
	
  	  pf.options().copyDefaults(true);
  	  
  	  try {
			pf.save(ms);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		File ms = new File("plugins/Dimensions/Messages.yml");
		YamlConfiguration msf = YamlConfiguration.loadConfiguration(ms);
		
    	if (!ms.exists()) {
    		
    		msf.addDefault("Prefix", "&7[&cDimensions&7]");
	        msf.addDefault("NoPermission", "&7Sorry &c{player}&7, but you have no permission to do that.");

	        msf.options().copyDefaults(true);
  	}
    	
    	lbfi = lbfo.listFiles(new FilenameFilter() {
		      public boolean accept(File dir, String name) {
		           return name.toLowerCase().endsWith(".yml");
		      }
		});
    	
		try {
			dmsf.save(dms);
			msf.save(ms);
			} catch(IOException e) {
			  e.printStackTrace();
			}
		
		// World Generator //
		DimensionsLoadEvent event = new DimensionsLoadEvent(this, usingfac, fpt);
		 Bukkit.getServer().getPluginManager().callEvent(event);
		 if (!event.isCancelled()) {
		ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
		boolean isokoe = true;
		console.sendMessage("§7-=-=-=-=-=-=-=[§cDimensions§7]=-=-=-=-=-=-=-=-");
		if (usingfac) {
			String ft = "§4§l§nERROR!!!";
			if (fpt.equalsIgnoreCase("MCF")) {
			ft = "Massive Core Factions";	
			} else if (fpt.equalsIgnoreCase("LF")) {
				ft = "Legacy Factions";
			}
			console.sendMessage("§7Factions plugin is §aenabled§7: §6"+ft+"§7.");
		}
	    if (lbfi.length>=1) {
	    	for (File fl : lbfi) {
	    		YamlConfiguration pf = YamlConfiguration.loadConfiguration(fl);
	    		String m = fl.getAbsolutePath().split("/")[fl.getAbsolutePath().split("/").length-1].replace(".yml", "");
			if (!usingwg && pf.getString("WorldGuard.Enable").equalsIgnoreCase("true")) {
				isokoe = false;
				console.sendMessage("§7Portal §c\""+m+"\"§7 has WorldGuard region disabler §aEnabled§7, but §6WorldGuard§7 is missing. Plugin will be disabled.");
			}
			
			if (Bukkit.getVersion().contains("1.7")) {
				pf.set("Essence.Enable", "false");
			}
			
			if (pf.getString("WorldType").contains(":")) {
				if (Integer.parseInt(pf.getString("WorldType").split(":")[0])<Integer.parseInt(pf.getString("WorldType").split(":")[1])) {
					console.sendMessage("§7Portal world scale is set wrongly. Plugin will be disabled.");
					isokoe = false;
				}
			}
			
			if (!usingfac && pf.getString("FactionsTerritories.Enable").equalsIgnoreCase("true")) {
				isokoe = false;
				console.sendMessage("§7Portal §c\""+m+"\"§7 has FactionsTerritories §aEnabled§7, but §6Factions§7 plugin is missing. Plugin will be disabled.");
			}
		String ww = pf.getString("World");	
		if (ww != null) {
			if (ww != "") {
		World w = Bukkit.getServer().getWorld(ww);
		console.sendMessage("§7Portal §c\""+m+"\"§7 needs world §c\""+ww+"\"§7.");
		if (pf.getString("Enable").equalsIgnoreCase("true")) {
			if (Bukkit.getServer().getWorlds().contains(w)) {
				console.sendMessage("§7World §c\""+ww+"\"§7 already exists. No need to generate one.");
			} else {
				console.sendMessage("§7World §c\""+ww+"\"§7 does not exists. Generating one...");
				Bukkit.getServer().createWorld(new WorldCreator(ww));
				console.sendMessage("§7World §c\""+ww+"\"§7 Succesfully generated.");
			}
		} else {
			console.sendMessage("§7Portal §c\""+m+"\"§7 is not enabled. No need to generate §c\""+ww+"\"§7.");
		}
			} else {
				console.sendMessage("§7Portal §c\""+m+"\"§7 is registered but it cannot be found. Plugin will be disabled.");
				isokoe = false;
			}
		} else {
			console.sendMessage("§7Portal §c\""+m+"\"§7 is registered but it cannot be found. Plugin will be disabled.");
			isokoe = false;
		}
		}
	    }
		console.sendMessage("§7-=-=-=-=-=-=-=[§cDimensions§7]=-=-=-=-=-=-=-=-");
		// World Generator //
		if (!isokoe) {
			Bukkit.getServer().getPluginManager().disablePlugin(this);
		}
		if (Bukkit.getServer().getPluginManager().isPluginEnabled(this) && isokoe) {
			//register classes
			portalClass = new PortalClass(this);

			pac = new Particles(this);

			l = new Listeners(this);
			
			instance = this;
			
			//Check if any player is using a portal
			//Could cause some serious server "lag" if many players would be in the server and the server had many portals
			//Havent tested any other way so its up to you
		    Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
				public void run()
		          {
					for (Player p : Bukkit.getServer().getOnlinePlayers()) {
						if (dmsf.getString("Enable").equalsIgnoreCase("true")) {
							portalClass.testPlayer(p);
						}
					}
		          }
		        }, 5, 5);
		}
	}
	}
	public void onDisable() {
		instance = null;
		
		DimensionsUnloadEvent event = new DimensionsUnloadEvent(this);
		 Bukkit.getServer().getPluginManager().callEvent(event);
	}
	
	private static Main instance;
	public static Main getInstance() {
	  return instance;
	}
	
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		File dms = new File("plugins/Dimensions/Settings.yml");
		YamlConfiguration dmsf = YamlConfiguration.loadConfiguration(dms);
		File ms = new File("plugins/Dimensions/Messages.yml");
		YamlConfiguration msf = YamlConfiguration.loadConfiguration(ms);
		
		String prefix = msf.getString("Prefix").replace("&", "§")+" ";
		String noperm = msf.getString("NoPermission").replace("&", "§");
			
		 if ((cmd.getName().equalsIgnoreCase("dimensions")) && ((sender instanceof ConsoleCommandSender))) {
			 ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
	          if (args.length == 0) {
			        	  int pg = Integer.parseInt(1+"");
			        	  	console.sendMessage("§7§m---§7[§c"+pg+"/"+help.size()+"§7]§m---§7[§cDimensions§7]§m---§7[§c"+pg+"/"+help.size()+"§7]§m---");
			        	  	console.sendMessage("");
			            	for (String m : help.get(pg)) {
			            		console.sendMessage(m);
			            	}
			            	console.sendMessage("");
			            	console.sendMessage("§7§m---§7[§c"+pg+"/"+help.size()+"§7]§m---§7[§cDimensions§7]§m---§7[§c"+pg+"/"+help.size()+"§7]§m---");
	          } else {
	        	  boolean iscmd = false;
	        	  
	         		if (args[0].equalsIgnoreCase("reload")) {
	         			//This is not needed since i get the data directly from files instead of saving them
	         			iscmd = true;  
		            	if (args.length == 1) {
		            		try {
								dmsf.load(dms);
								msf.load(ms);
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							} catch (InvalidConfigurationException e) {
								e.printStackTrace();
							}
			            	console.sendMessage(prefix+"§aReload complete.");
		            	}
	         		}
     		
	         		if (args[0].equalsIgnoreCase("help")) {
	         			iscmd = true; 
	    	            	if (args.length == 1) {
	    			        	  int pg = Integer.parseInt(1+"");
	    			        	  	console.sendMessage("§7§m---§7[§c"+pg+"/"+help.size()+"§7]§m---§7[§cDimensions§7]§m---§7[§c"+pg+"/"+help.size()+"§7]§m---");
	    			        	  	console.sendMessage("");
	    			            	for (String m : help.get(pg)) {
	    			            		console.sendMessage(m);
	    			            	}
	    			            	console.sendMessage("");
	    			            	console.sendMessage("§7§m---§7[§c"+pg+"/"+help.size()+"§7]§m---§7[§cDimensions§7]§m---§7[§c"+pg+"/"+help.size()+"§7]§m---");
	    	            	}
	    	            	if (args.length == 2) {
	    	            		if (isInt(args[1])) {
	    		            		if (help.containsKey(Integer.parseInt(args[1]))) {
	    					        	  int pg = Integer.parseInt(args[1]);
	    					        	  	console.sendMessage("§7§m---§7[§c"+pg+"/"+help.size()+"§7]§m---§7[§cDimensions§7]§m---§7[§c"+pg+"/"+help.size()+"§7]§m---");
	    					        	  	console.sendMessage("");
	    					            	for (String m : help.get(pg)) {
	    					            		console.sendMessage(m);
	    					            	}
	    					            	console.sendMessage("");
	    					            	console.sendMessage("§7§m---§7[§c"+pg+"/"+help.size()+"§7]§m---§7[§cDimensions§7]§m---§7[§c"+pg+"/"+help.size()+"§7]§m---");
	    			            		} else {
	    						        	 String pg = args[1];
	    						        	  	console.sendMessage("§7§m---§7[§c"+pg+"/"+help.size()+"§7]§m---§7[§cDimensions§7]§m---§7[§c"+pg+"/"+help.size()+"§7]§m---");
	    						        	  	console.sendMessage("");
	    						        	  	console.sendMessage("§7There is no page §c"+args[1]+"§7. Please use pages §c1-"+help.size()+"§7.");
	    						            	console.sendMessage("");
	    						            	console.sendMessage("§7§m---§7[§c"+pg+"/"+help.size()+"§7]§m---§7[§cDimensions§7]§m---§7[§c"+pg+"/"+help.size()+"§7]§m---");
	    			            		}
	    	            		} else {
	    	            			if (isInHelp(args[1])) {
	    					        	 String pg = args[1];
	    					        	  	console.sendMessage("§7§m---§7[§c"+pg+"§7]§m---§7[§cDimensions§7]§m---§7[§c"+pg+"§7]§m---");
	    					        	  	console.sendMessage("");
	    					        	  	for (String n : GetInHelp(pg)) {
	    					        	  		console.sendMessage(n);	
	    					        	  	}
	    					            	console.sendMessage("");
	    					            	console.sendMessage("§7§m---§7[§c"+pg+"§7]§m---§7[§cDimensions§7]§m---§7[§c"+pg+"§7]§m---");
	    	            			} else {
	    					        	 String pg = args[1];
	    					        	  	console.sendMessage("§7§m---§7[§c"+pg+"§7]§m---§7[§cDimensions§7]§m---§7[§c"+pg+"§7]§m---");
	    					        	  	console.sendMessage("");
	    					        	  	console.sendMessage("§7There is no command §c"+args[1]+"§7. Please use §c/help§7 to list commands.");
	    					            	console.sendMessage("");
	    					            	console.sendMessage("§7§m---§7[§c"+pg+"§7]§m---§7[§cDimensions§7]§m---§7[§c"+pg+"§7]§m---");
	    	            			}
	    	            		}
	    	            	}
	         }
	         		if (args[0].equalsIgnoreCase("perms")) {
	         			iscmd = true;
	    	            	if (args.length == 1) {
	    			        	  int pg = Integer.parseInt(1+"");
	    			        	  	console.sendMessage("§7§m---§7[§c"+pg+"/"+perms.size()+"§7]§m---§7[§cDimensions§7]§m---§7[§c"+pg+"/"+perms.size()+"§7]§m---");
	    			        	  	console.sendMessage("");
	    			            	for (String m : perms.get(pg)) {
	    			            		console.sendMessage(m);
	    			            	}
	    			            	console.sendMessage("");
	    			            	console.sendMessage("§7§m---§7[§c"+pg+"/"+perms.size()+"§7]§m---§7[§cDimensions§7]§m---§7[§c"+pg+"/"+perms.size()+"§7]§m---");
	    	            	}
	    	            	if (args.length == 2) {
	    	            		if (isInt(args[1])) {
	    		            		if (perms.containsKey(Integer.parseInt(args[1]))) {
	    					        	  int pg = Integer.parseInt(args[1]);
	    					        	  	console.sendMessage("§7§m---§7[§c"+pg+"/"+perms.size()+"§7]§m---§7[§cDimensions§7]§m---§7[§c"+pg+"/"+perms.size()+"§7]§m---");
	    					        	  	console.sendMessage("");
	    					            	for (String m : perms.get(pg)) {
	    					            		console.sendMessage(m);
	    					            	}
	    					            	console.sendMessage("");
	    					            	console.sendMessage("§7§m---§7[§c"+pg+"/"+perms.size()+"§7]§m---§7[§cDimensions§7]§m---§7[§c"+pg+"/"+perms.size()+"§7]§m---");
	    			            		} else {
	    						        	 String pg = args[1];
	    						        	  	console.sendMessage("§7§m---§7[§c"+pg+"/"+perms.size()+"§7]§m---§7[§cDimensions§7]§m---§7[§c"+pg+"/"+perms.size()+"§7]§m---");
	    						        	  	console.sendMessage("");
	    						        	  	console.sendMessage("§7There is no page §c"+args[1]+"§7. Please use pages §c1-"+perms.size()+"§7.");
	    						            	console.sendMessage("");
	    						            	console.sendMessage("§7§m---§7[§c"+pg+"/"+perms.size()+"§7]§m---§7[§cDimensions§7]§m---§7[§c"+pg+"/"+perms.size()+"§7]§m---");
	    			            		}
	    	            		} else {
	    	            			if (PisInHelp(args[1])) {
	    					        	 String pg = args[1];
	    					        	  	console.sendMessage("§7§m---§7[§c"+pg+"§7]§m---§7[§cDimensions§7]§m---§7[§c"+pg+"§7]§m---");
	    					        	  	console.sendMessage("");
	    					        	  	for (String n : PGetInHelp(pg)) {
	    					        	  		console.sendMessage(n);	
	    					        	  	}
	    					            	console.sendMessage("");
	    					            	console.sendMessage("§7§m---§7[§c"+pg+"§7]§m---§7[§cDimensions§7]§m---§7[§c"+pg+"§7]§m---");
	    	            			} else {
	    					        	 String pg = args[1];
	    					        	  	console.sendMessage("§7§m---§7[§c"+pg+"§7]§m---§7[§cDimensions§7]§m---§7[§c"+pg+"§7]§m---");
	    					        	  	console.sendMessage("");
	    					        	  	console.sendMessage("§7There is no command §c"+args[1]+"§7. Please use §c/perms§7 to list permissions.");
	    					            	console.sendMessage("");
	    					            	console.sendMessage("§7§m---§7[§c"+pg+"§7]§m---§7[§cDimensions§7]§m---§7[§c"+pg+"§7]§m---");
	    	            			}
	    	            		}
	    	            	}
	         }
   		
   		// Commands \\
   		if (args[0].equalsIgnoreCase("ess")) {
   			iscmd = true;
	            	if(args.length == 2) {
	        			File pef = new File("plugins/Dimensions/PlayerData/"+args[1]+"/Essence.yml");
	        			YamlConfiguration peff = YamlConfiguration.loadConfiguration(pef);
	        			if (pef.exists()) {
			            	console.sendMessage(prefix+"§7Player §c"+args[1]+" §7have §c"+peff.getString("Amount")+"§7 teleport essence.");
	        			} else {
	        				String[] letter = args[1].split("");
	        					if (letter[(letter.length-1)].equalsIgnoreCase("s")) {
	        						console.sendMessage(prefix+"§7Could not find §c"+args[1]+"'§7 data. ");
	        					} else {
	        						console.sendMessage(prefix+"§7Could not find §c"+args[1]+"'s§7 data. ");
	        					}
	        			}
	            	} else {
	            		console.sendMessage(prefix+"§7Please use §c/dm ess [player]");
	            	}
   		}

   		//--------------------------------------------------\\
   		
   		if (!iscmd) {
   				console.sendMessage(prefix+GetInHelpS(args[0]));
   		}
   		
   		//--------------------------------------------------------\\
	        }
	      }
		
	    //-------------------------------------------------------------------------------------------------------------------------------\\
	      
		 if ((cmd.getName().equalsIgnoreCase("dimensions")) && ((sender instanceof Player))) {
	        	Player p = (Player) sender;
	        	noperm = noperm.replace("{player}", p.getName());
	          if (args.length == 0) {
		            if (p.hasPermission("dm.help")) {
			        	  int pg = Integer.parseInt(1+"");
			        	  	p.sendMessage("§7§m---§7[§c"+pg+"/"+help.size()+"§7]§m---§7[§cDimensions§7]§m---§7[§c"+pg+"/"+help.size()+"§7]§m---");
			        	  	p.sendMessage("");
			            	for (String m : help.get(pg)) {
			            		p.sendMessage(m);
			            	}
			            	p.sendMessage("");
			            	p.sendMessage("§7§m---§7[§c"+pg+"/"+help.size()+"§7]§m---§7[§cDimensions§7]§m---§7[§c"+pg+"/"+help.size()+"§7]§m---");
		            } else {
		            	p.sendMessage(prefix + noperm);
		            }
	          } else {
	        	  boolean iscmd = false;
	        	  
	         		if (args[0].equalsIgnoreCase("reload")) {
	         			iscmd = true;
			            if (p.hasPermission("dm.reload")) {  
		            	if (args.length == 1) {
		            		try {
								dmsf.load(dms);
								msf.load(ms);
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							} catch (InvalidConfigurationException e) {
								e.printStackTrace();
							}
			            	p.sendMessage(prefix+"§aReload complete.");
		            	}
			            } else {
			            	p.sendMessage(prefix + noperm);
			            }
	         		}
       		
	         		if (args[0].equalsIgnoreCase("help")) {
	         			iscmd = true;
	    	            if (p.hasPermission("dm.help")) {    
	    	            	if (args.length == 1) {
	    			        	  int pg = Integer.parseInt(1+"");
	    			        	  	p.sendMessage("§7§m---§7[§c"+pg+"/"+help.size()+"§7]§m---§7[§cDimensions§7]§m---§7[§c"+pg+"/"+help.size()+"§7]§m---");
	    			        	  	p.sendMessage("");
	    			            	for (String m : help.get(pg)) {
	    			            		p.sendMessage(m);
	    			            	}
	    			            	p.sendMessage("");
	    			            	p.sendMessage("§7§m---§7[§c"+pg+"/"+help.size()+"§7]§m---§7[§cDimensions§7]§m---§7[§c"+pg+"/"+help.size()+"§7]§m---");
	    	            	}
	    	            	if (args.length == 2) {
	    	            		if (isInt(args[1])) {
	    		            		if (help.containsKey(Integer.parseInt(args[1]))) {
	    					        	  int pg = Integer.parseInt(args[1]);
	    					        	  	p.sendMessage("§7§m---§7[§c"+pg+"/"+help.size()+"§7]§m---§7[§cDimensions§7]§m---§7[§c"+pg+"/"+help.size()+"§7]§m---");
	    					        	  	p.sendMessage("");
	    					            	for (String m : help.get(pg)) {
	    					            		p.sendMessage(m);
	    					            	}
	    					            	p.sendMessage("");
	    					            	p.sendMessage("§7§m---§7[§c"+pg+"/"+help.size()+"§7]§m---§7[§cDimensions§7]§m---§7[§c"+pg+"/"+help.size()+"§7]§m---");
	    			            		} else {
	    						        	 String pg = args[1];
	    						        	  	p.sendMessage("§7§m---§7[§c"+pg+"/"+help.size()+"§7]§m---§7[§cDimensions§7]§m---§7[§c"+pg+"/"+help.size()+"§7]§m---");
	    						        	  	p.sendMessage("");
	    						        	  	p.sendMessage("§7There is no page §c"+args[1]+"§7. Please use pages §c1-"+help.size()+"§7.");
	    						            	p.sendMessage("");
	    						            	p.sendMessage("§7§m---§7[§c"+pg+"/"+help.size()+"§7]§m---§7[§cDimensions§7]§m---§7[§c"+pg+"/"+help.size()+"§7]§m---");
	    			            		}
	    	            		} else {
	    	            			if (isInHelp(args[1])) {
	    					        	 String pg = args[1];
	    					        	  	p.sendMessage("§7§m---§7[§c"+pg+"§7]§m---§7[§cDimensions§7]§m---§7[§c"+pg+"§7]§m---");
	    					        	  	p.sendMessage("");
	    					        	  	for (String n : GetInHelp(pg)) {
	    					        	  		p.sendMessage(n);	
	    					        	  	}
	    					            	p.sendMessage("");
	    					            	p.sendMessage("§7§m---§7[§c"+pg+"§7]§m---§7[§cDimensions§7]§m---§7[§c"+pg+"§7]§m---");
	    	            			} else {
	    					        	 String pg = args[1];
	    					        	  	p.sendMessage("§7§m---§7[§c"+pg+"§7]§m---§7[§cDimensions§7]§m---§7[§c"+pg+"§7]§m---");
	    					        	  	p.sendMessage("");
	    					        	  	p.sendMessage("§7There is no command §c"+args[1]+"§7. Please use §c/help§7 to list commands.");
	    					            	p.sendMessage("");
	    					            	p.sendMessage("§7§m---§7[§c"+pg+"§7]§m---§7[§cDimensions§7]§m---§7[§c"+pg+"§7]§m---");
	    	            			}
	    	            		}
	    	            	}
	    	            } else {
	    	            	p.sendMessage(prefix + noperm);
	    	            }
	         }
	         		if (args[0].equalsIgnoreCase("perms")) {
	         			iscmd = true;
	    	            if (p.hasPermission("dm.perms")) {
	    	            	if (args.length == 1) {
	    			        	  int pg = Integer.parseInt(1+"");
	    			        	  	p.sendMessage("§7§m---§7[§c"+pg+"/"+perms.size()+"§7]§m---§7[§cDimensions§7]§m---§7[§c"+pg+"/"+perms.size()+"§7]§m---");
	    			        	  	p.sendMessage("");
	    			            	for (String m : perms.get(pg)) {
	    			            		p.sendMessage(m);
	    			            	}
	    			            	p.sendMessage("");
	    			            	p.sendMessage("§7§m---§7[§c"+pg+"/"+perms.size()+"§7]§m---§7[§cDimensions§7]§m---§7[§c"+pg+"/"+perms.size()+"§7]§m---");
	    	            	}
	    	            	if (args.length == 2) {
	    	            		if (isInt(args[1])) {
	    		            		if (perms.containsKey(Integer.parseInt(args[1]))) {
	    					        	  int pg = Integer.parseInt(args[1]);
	    					        	  	p.sendMessage("§7§m---§7[§c"+pg+"/"+perms.size()+"§7]§m---§7[§cDimensions§7]§m---§7[§c"+pg+"/"+perms.size()+"§7]§m---");
	    					        	  	p.sendMessage("");
	    					            	for (String m : perms.get(pg)) {
	    					            		p.sendMessage(m);
	    					            	}
	    					            	p.sendMessage("");
	    					            	p.sendMessage("§7§m---§7[§c"+pg+"/"+perms.size()+"§7]§m---§7[§cDimensions§7]§m---§7[§c"+pg+"/"+perms.size()+"§7]§m---");
	    			            		} else {
	    						        	 String pg = args[1];
	    						        	  	p.sendMessage("§7§m---§7[§c"+pg+"/"+perms.size()+"§7]§m---§7[§cDimensions§7]§m---§7[§c"+pg+"/"+perms.size()+"§7]§m---");
	    						        	  	p.sendMessage("");
	    						        	  	p.sendMessage("§7There is no page §c"+args[1]+"§7. Please use pages §c1-"+perms.size()+"§7.");
	    						            	p.sendMessage("");
	    						            	p.sendMessage("§7§m---§7[§c"+pg+"/"+perms.size()+"§7]§m---§7[§cDimensions§7]§m---§7[§c"+pg+"/"+perms.size()+"§7]§m---");
	    			            		}
	    	            		} else {
	    	            			if (PisInHelp(args[1])) {
	    					        	 String pg = args[1];
	    					        	  	p.sendMessage("§7§m---§7[§c"+pg+"§7]§m---§7[§cDimensions§7]§m---§7[§c"+pg+"§7]§m---");
	    					        	  	p.sendMessage("");
	    					        	  	for (String n : PGetInHelp(pg)) {
	    					        	  		p.sendMessage(n);	
	    					        	  	}
	    					            	p.sendMessage("");
	    					            	p.sendMessage("§7§m---§7[§c"+pg+"§7]§m---§7[§cDimensions§7]§m---§7[§c"+pg+"§7]§m---");
	    	            			} else {
	    					        	 String pg = args[1];
	    					        	  	p.sendMessage("§7§m---§7[§c"+pg+"§7]§m---§7[§cDimensions§7]§m---§7[§c"+pg+"§7]§m---");
	    					        	  	p.sendMessage("");
	    					        	  	p.sendMessage("§7There is no command §c"+args[1]+"§7. Please use §c/perms§7 to list permissions.");
	    					            	p.sendMessage("");
	    					            	p.sendMessage("§7§m---§7[§c"+pg+"§7]§m---§7[§cDimensions§7]§m---§7[§c"+pg+"§7]§m---");
	    	            			}
	    	            		}
	    	            	}
	    	            } else {
	    	            	p.sendMessage(prefix + noperm);
	    	            }
	         }
     		
     		// Commands \\
     		//--------------------------------------------------\\
     		
     		if (!iscmd) {
     				p.sendMessage(prefix+GetInHelpS(args[0]));
     		}
     		
     		//--------------------------------------------------------\\
	        }
	      }
	  
		return true;
  }
	
	public ArrayList<String> GetInHelp(String s) {
		Collection<ArrayList<String>> a = help.values();
		ArrayList<String> ali = new ArrayList<String>();
		for (ArrayList<String> b : a) {
			for (String m : b) {
				for (String c : m.split(" ")) {
					if (c.toUpperCase().contains(s.toUpperCase())) {
						if (c.toUpperCase().contains(s.toUpperCase())) {
							String[] hmess = m.split(" ");
							if (hmess[1].toUpperCase().startsWith(s.toUpperCase())) {
								if (!ali.contains(m)) {
									ali.add(m);
								}
							}
						}
					}
				}
			}
		}
		if (!ali.isEmpty()) {
			return ali;
		}
		ArrayList<String> hli = new ArrayList<String>();
		hli.add("§7There is no command §c"+s+"§7. Please use §c/help§7 to list commands.");
		return hli;
	}
	
	public String GetInHelpS(String s) {
		Collection<ArrayList<String>> a = help.values();
		for (ArrayList<String> b : a) {
			for (String m : b) {
				for (String c : m.split(" ")) {
					if (c.toUpperCase().contains(s.toUpperCase())) {
						String[] hmess = m.split(" ");
						if (hmess[1].toUpperCase().startsWith(s.toUpperCase())) {
							String[] mess = m.split(" §c-");
							return "§7Did you mean §c"+mess[0].replace("§7", "§c")+"§7.";
						}
					}
				}
			}
		}
		String mes = "§7Please use §c/dm help§7 for help";
		return mes;
	}
	
	public boolean isInHelp(String s) {
		Collection<ArrayList<String>> a = help.values();
		for (ArrayList<String> b : a) {
			
			for (String m : b) {
				
				for (String c : m.split(" ")) {
					
					if (c.toUpperCase().contains(s.toUpperCase())) {
						
						return true;
						
					}
					
				}
				
			}
			
		}
		
		return false;
	}
	
	public ArrayList<String> PGetInHelp(String s) {
		Collection<ArrayList<String>> a = perms.values();
		ArrayList<String> ali = new ArrayList<String>();
		for (ArrayList<String> b : a) {
			for (String m : b) {
				for (String c : m.split(" ")) {
					if (c.toUpperCase().contains(s.toUpperCase())) {
						if (c.toUpperCase().contains(s.toUpperCase())) {
							String[] hmess = m.split(" ");
							if (hmess[1].toUpperCase().startsWith(s.toUpperCase())) {
								if (!ali.contains(m)) {
									ali.add(m);
								}
							}
						}
					}
				}
			}
		}
		if (!ali.isEmpty()) {
			return ali;
		}
		ArrayList<String> hli = new ArrayList<String>();
		hli.add("§7There is no command §c"+s+"§7. Please use §c/help§7 to list commands.");
		return hli;
	}
	
	public boolean PisInHelp(String s) {
		Collection<ArrayList<String>> a = perms.values();
		for (ArrayList<String> b : a) {
			
			for (String m : b) {
				
				for (String c : m.split(" ")) {
					
					if (c.toUpperCase().contains(s.toUpperCase())) {
						
						return true;
						
					}
					
				}
				
			}
			
		}
		
		return false;
	}
	
    public int getRandom(int lower, int upper) {
        Random random = new Random();
        return random.nextInt((upper - lower) + 1) + lower;
    }

	
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
    	Player p = e.getPlayer();
    	
		File pdLp = new File("plugins/Dimensions/PlayerData/"+p.getName()+"/LastPortal.yml");
		YamlConfiguration pdlpf = YamlConfiguration.loadConfiguration(pdLp);

		
        List<String> lup = pdlpf.getStringList("LastUsedP");
        
        pdlpf.set("LastUsedP", lup);
        
        List<String> luw = pdlpf.getStringList("LastUsedW");
        
        pdlpf.set("LastUsedW", luw);
		


		try {
			pdlpf.save(pdLp);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }

	  public boolean isInt(String s)
	  {
	    try {
	      Integer.parseInt(s);
	    } catch (NumberFormatException nfe) {
	      return false;
	    }
	    return true;
	  }
	
}
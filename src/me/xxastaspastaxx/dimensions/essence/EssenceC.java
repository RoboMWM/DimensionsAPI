package me.xxastaspastaxx.dimensions.essence;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import me.xxastaspastaxx.dimensions.Main;
import me.xxastaspastaxx.dimensions.events.EssenceClaimEvent;
import me.xxastaspastaxx.dimensions.events.EssenceSpawnEvent;

public class EssenceC implements Listener {
	//THE WHOLE CLASS IS A MESS
	//delete it since nobody uses it
	  public EssenceC(Plugin pl) {
		  
			File dms = new File("plugins/Dimensions/Settings.yml");
			YamlConfiguration dmsf = YamlConfiguration.loadConfiguration(dms);
		  
		    Bukkit.getScheduler().scheduleSyncRepeatingTask(pl, new Runnable() {
		    	
				public void run()
		          {
					
					if (dmsf.getString("SpawnEssences").equalsIgnoreCase("true")) {
						
						for (World w : Bukkit.getWorlds()) {
							int max = Main.getInstance().getRandom(5, 10);
							for (int a=0;a<max;++a) {
								
					            int x = Main.getInstance().getRandom(-2000, 2000);
					            int y = Main.getInstance().getRandom(1, 250);
					            int z = Main.getInstance().getRandom(-2000, 2000);
								
					            Location sloc = new Location(w, x+0.5,y,z-0.5);
					            
					    		EssenceSpawnEvent event = new EssenceSpawnEvent(sloc);
					   		 	Bukkit.getServer().getPluginManager().callEvent(event);
					   		 	if (!event.isCancelled()) {
					            	SpawnEssence(event.getEssenceLocation());
					            }
								
							}
							
						}
								
						
					}
					
		          }
		        }, 900*20, 900*20);
		  
		  
		    Bukkit.getScheduler().scheduleSyncRepeatingTask(pl, new Runnable() {
		    	
				public void run()
		          {
						for (World w : Bukkit.getWorlds()) {
							for (Entity en : w.getEntities()) {
								if (en.getType().equals(EntityType.ARMOR_STAND)) {
									  ItemStack obsidian = new ItemStack(Material.STAINED_GLASS, 1, (byte) 2);
									  obsidian.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
									  obsidian.getItemMeta().setDisplayName("§6Uhhh.. Why?");
									  if (((ArmorStand) en).getHelmet().equals(obsidian)) {
									  if (en.getCustomName().equals("§6FloatingPortalEssence")) {
											//Location loc = new Location(w, en.getLocation().getX()+0.5,en.getLocation().getY()+1,en.getLocation().getZ()-0.5);
											en.getLocation().getWorld().playEffect(en.getLocation(), Effect.ENDER_SIGNAL, 59);
										
										  }
									  }
								}
							}
						}
		          }
		        }, 10, 10);
		    
		    Bukkit.getServer().getPluginManager().registerEvents(this, pl);
		  }

	public void SpawnEssence(Location sloc) {
		
		  
		  ArmorStand stand = (ArmorStand) sloc.getWorld().spawnEntity(sloc, EntityType.ARMOR_STAND);
		  
		  stand.setCustomName("§6FloatingPortalEssence");
		  ItemStack obsidian = new ItemStack(Material.STAINED_GLASS, 1, (byte) 2);
		  obsidian.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		  obsidian.getItemMeta().setDisplayName("§6Uhhh.. Why?");
		  stand.setHelmet(obsidian);
		  stand.setCustomNameVisible(false);
		  if (!Bukkit.getVersion().contains("1.8")) {
			  stand.setInvulnerable(false);
		  }
		  stand.setVisible(false);
		
	}
	  @EventHandler
	  public void onInteract(PlayerInteractAtEntityEvent e) {
		  
			File dms = new File("plugins/Dimensions/Settings.yml");
			YamlConfiguration dmsf = YamlConfiguration.loadConfiguration(dms);
		  
		  Player p = e.getPlayer();

		  if ((e.getRightClicked().getType().equals(EntityType.ARMOR_STAND))){
			  Entity en = e.getRightClicked();
			  ItemStack obsidian = new ItemStack(Material.STAINED_GLASS, 1, (byte) 2);
			  obsidian.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
			  obsidian.getItemMeta().setDisplayName("§6Uhhh.. Why?");
			  if (((ArmorStand) en).getHelmet().equals(obsidian)) {
			  if (en.getCustomName().equals("§6FloatingPortalEssence")) {
					e.setCancelled(true);
					if (((Damageable) e.getRightClicked()).getHealth() > 0) {
						((ArmorStand) en).setHealth(0);
						String[] em = dmsf.getString("SpawnEssences.CollectChanceAmmount").split("-");
						int min = Integer.parseInt(em[0]);
						int max = Integer.parseInt(em[1]);
						int eamo = Main.getInstance().getRandom(min, max);
						
				  		String m = dmsf.getString("SpawnEssences.CollectMessage");
				  		m = m.replace("{amount}", eamo+"").replace("{player}", p.getName()).replace("&", "§");
			    		EssenceClaimEvent event = new EssenceClaimEvent(p, en.getLocation(), eamo, m);
			   		 	Bukkit.getServer().getPluginManager().callEvent(event);
			   		 	if (!event.isCancelled()) {
						ES.addEssence(p, event.getEssenceAmount());
				  		m=event.getClaimMessage();
				  		p.sendMessage(m);
			   		 	}
					}
			  	}
			  }
		  }
		  
	  }

}

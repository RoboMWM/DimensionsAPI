package me.xxastaspastaxx.dimensions.particles;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

import me.xxastaspastaxx.dimensions.Main;
import me.xxastaspastaxx.dimensions.api.CPortal;

public class Particles implements Listener {
	//TODO A COMPLETE MESS
	
	//portals that players can see (and particles will appear)
	public static ArrayList<Location> ports = new ArrayList<Location>();
	
	public Particles(Plugin pl) {
		
	    Bukkit.getServer().getPluginManager().registerEvents(this, pl);
	 }
	
	File dms = new File("plugins/Dimensions/Settings.yml");
	YamlConfiguration dmsf = YamlConfiguration.loadConfiguration(dms);

	
	/**/

	
	  //----------------------------------------------------------------------------------------------------
	
	//Summon particles
	
	
	//What is does is summon particles for every glass pane in portal and keeps on spawning even if no players are nearby (should change that)
		public void Particler(Location loc) {
				 for (String fl : Main.getInstance().portalClass.portalList) {
					 CPortal cportal = new CPortal(fl.replace(".yml", ""), loc);
					 int fr = 0;
					 int fg = 0;
					 int fb = 0;
				
					String fc = "none";
					
					boolean usc = false;
					
					if (cportal.getParticlesColor().contains(";")) {
						String[] rgb = cportal.getParticlesColor().split(";");
						
						fr = Integer.parseInt(rgb[0])*1/255;
						fg = Integer.parseInt(rgb[1])*1/255;
						fb = Integer.parseInt(rgb[2])*1/255;
					} else {
						if (cportal.getParticlesColor().equalsIgnoreCase("rainbow")) {
							usc = true;
							fc = "r";
						} else if (cportal.getParticlesColor().equalsIgnoreCase("black")) {
							usc = true;
							fc = "b";
						}
					}
	
					
					int r = fr;
					int g = fg;
					int b = fb;
					
					String c = fc;
					
					boolean fusc = usc;
					
					Location location = loc.clone();
					
					  Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
							public void run()
					          {
								CPortal cuportal = cportal;
									if (Main.getInstance().portalClass.isInPortal(cuportal)) {
										if (Main.getInstance().portalClass.isInFullPortal(cuportal)) {
											Location loca = location.clone().add(Math.random(),Math.random(),Math.random());
											if (!fusc) {
												loc.getWorld().spawnParticle(Particle.REDSTONE, loca, 0, r, g, b, 1);
											} else {
												if (c.equalsIgnoreCase("r")) {
													loc.getWorld().spawnParticle(Particle.REDSTONE, loca, 1, r, g, b, 1);
												} else if (c.equalsIgnoreCase("b")) {
													if (!Bukkit.getVersion().contains("1.9")) {
														loc.getWorld().spawnParticle(Particle.FALLING_DUST, loca, 0, r, g, b, 1);
													}
												}
											}
										}
									}
					          }
					        }, 0, 1*10);
				
				 } 
		}
		
	//Check if a portal is around player
	  public void circle(Location loc, int radius){
		  
		  int cx = loc.getBlockX();
		  int cy = loc.getBlockY();
		  int cz = loc.getBlockZ();
				 for (String fl : Main.getInstance().portalClass.portalList) {
		  for(int x = cx - radius; x <= cx + radius; x++){
		  for (int z = cz - radius; z <= cz + radius; z++){
		  for(int y = (cy - radius); y < (cy + radius); y++){
		  double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + ((cy - y) * (cy - y));

		  if(dist < radius * radius){
		  Location l = new Location(loc.getWorld(), x, y + 2, z);
		  

				
				//(Block p, Block fire, Material bl, int id, int fid, String al)
		  		if (l.getBlock().getType()==Material.STAINED_GLASS_PANE) {
		  			if (!ports.contains(l)) {
		  				CPortal cportal = new CPortal(fl.replace(".yml", ""), l);
		  				if (Main.getInstance().portalClass.isInFullPortal(cportal)) {
			  				if (cportal.getParticlesEnabled()) {
			  					if (Main.getInstance().portalClass.isInPortal(cportal)) {
									Particler(l);
									ports.add(l);
								}
							}
		  				}
					}
				}
			}
		  }
		  }
		  }
		  }
		  return;
		  }
	  
	  @EventHandler
	  public void onMove(PlayerMoveEvent e) {
		  Player p = e.getPlayer();
		  
		  if (((int) e.getFrom().getX()!=(int) e.getTo().getX())||((int) e.getFrom().getY()!=(int) e.getTo().getY())||((int) e.getFrom().getZ()!=(int) e.getTo().getZ())) {
				File dms = new File("plugins/Dimensions/Settings.yml");
				YamlConfiguration dmsf = YamlConfiguration.loadConfiguration(dms);
				
			  int md = Integer.parseInt(dmsf.getString("CustomParticles.MaxDistance"));
			  
			  circle(p.getLocation(), md);
			  
		  }
		  
	  }
	  
	  
}

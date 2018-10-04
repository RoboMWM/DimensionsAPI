package me.xxastaspastaxx.dimensions.api;

import java.io.File;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

public class CPortal {

	private String portalName;
	private Location portalLocation;
	private Location teleportLocation;
	
	File fl;
	YamlConfiguration pf;
	
	public CPortal(String portalName, Location portalLocation) {
		this.portalName = portalName;
		this.portalLocation = portalLocation;
		
		fl = new File("plugins/Dimensions/Portals/"+portalName+".yml");
		pf = YamlConfiguration.loadConfiguration(fl);
		
		this.teleportLocation=getActuallTeleportLocation();
	}
	
	public boolean isEnabled() {
		return Boolean.parseBoolean(pf.getString("Enable"));
	}
	
	public String getName() {
		return portalName;
	}
	
	public String getDisplayName() {
		return pf.getString("DisplayName").replace("&", "§").replace("\\§", "&");
	}
	
	public Location getLocation() {
		return portalLocation;
	}
	
	public Location getActuallTeleportLocation() {
		Location atl = new Location(Bukkit.getWorld(pf.getString("World")), portalLocation.getX(), portalLocation.getY(), portalLocation.getZ());
		return atl;
	}
	
	public void setTeleportLocation(Location loc) {
		teleportLocation=loc;
	}
	
	public Location getTeleportLocation() {
		return teleportLocation;
	}
	
	public Material getMaterial() {
		return Material.matchMaterial(pf.getString("Block"));
	}
	
	public int getData(){
		return Integer.parseInt(pf.getString("Block").split(";")[1]);
	}
	
	public Material getGlassData() {
		return Material.matchMaterial(pf.getString("Frame"));
	}
	
	public int getMinWidth() {
		return Integer.parseInt(pf.getString("MinWidth"));
	}
	
	public int getMinHeight() {
		return Integer.parseInt(pf.getString("MinHeight"));
	}
	
	public boolean getPermissionEnabled() {
		return Boolean.parseBoolean(pf.getString("UsePermission.Enable"));
	}
	
	public String getPermissionNode() {
		return pf.getString("UsePermission.Permission");
	}
	
	public String getNoPermissionMessage() {
		return pf.getString("UsePermission.NoPermissionMessage").replace("&", "§").replace("\\§", "&").replace("{permission}", getPermissionNode()).replace("{portal}", getDisplayName());
	}
	
	public boolean getChargeEnabled() {
		return Boolean.parseBoolean(pf.getString("Charge.Enable"));
	}
	
	public int getChargeAmount() {
		return Integer.parseInt(pf.getString("Charge.Amount"));
	}
	
	public String getNotEnoughMoneyMessage() {
		return pf.getString("Charge.NotEnoughMoneyMessage").replace("&", "§").replace("\\§", "&").replace("{amount}", getChargeAmount()+"");
	}
	
	public boolean getEssenceEnabled() {
		return Boolean.parseBoolean(pf.getString("Essence.Enable"));
	}
	
	public int getEssenceAmount() {
		return Integer.parseInt(pf.getString("Essence.Amount"));
	}
	
	public String getNotEnoughEssenceMessage() {
		return pf.getString("Essence.NotEnoughEssenceMessage").replace("&", "§").replace("\\§", "&").replace("{amount}", getEssenceAmount()+"");
	}
	
	public boolean getSendMessageEnabled() {
		return Boolean.parseBoolean(pf.getString("SendMessage.Enable"));
	}
	
	public String getMessage() {
		return pf.getString("SendMessage.Message").replace("&", "§").replace("\\§", "&").replace("{portal}", getDisplayName()).replace("{world}", getTeleportLocation().getWorld().getName());
	}
	
	public World getWorld() {
		return Bukkit.getWorld(pf.getString("World"));
	}
	
	public boolean getParticlesEnabled() {
		return Boolean.parseBoolean(pf.getString("CustomParticles.Enable"));
	}
	
	public String getParticlesColor() {
		return pf.getString("CustomParticles.Color");
	}
	
	public String getWorldType() {
		return pf.getString("WorldType");
	}
	
	public boolean getRandomLocationEnabled() {
		return Boolean.parseBoolean(pf.getString("RandomLocation"));
	}
	
	public boolean getBuildExitPortalEnabled() {
		return Boolean.parseBoolean(pf.getString("BuildExitPortal"));
	}
	
	public boolean getSpawnOnAirEnabled() {
		return Boolean.parseBoolean(pf.getString("SpawnOnAir"));
	}
	
	public boolean getFactionTerritoriesEnabled() {
		return Boolean.parseBoolean(pf.getString("FactionsTerritories.Enable"));
	}
	
	public boolean getFactionTerritoriesEnemyAllowed() {
		return Boolean.parseBoolean(pf.getString("FactionsTerritories.Enemy"));
	}
	public boolean getFactionTerritoriesAllyAllowed() {
		return Boolean.parseBoolean(pf.getString("FactionsTerritories.Ally"));
	}
	public boolean getFactionTerritoriesNeutralAllowed() {
		return Boolean.parseBoolean(pf.getString("FactionsTerritories.Neutral"));
	}
	public boolean getFactionTerritoriesTruceAllowed() {
		return Boolean.parseBoolean(pf.getString("FactionsTerritories.Truce"));
	}
	public boolean getFactionTerritoriesMemberAllowed() {
		return Boolean.parseBoolean(pf.getString("FactionsTerritories.Member"));
	}
	
	public String getFactionTerritoriesDenyMessage() {
		return pf.getString("FactionsTerritories.DenyMessage").replace("&", "§").replace("\\§", "&");
	}
	
	public boolean getWorldGuardEnabled() {
		return Boolean.parseBoolean(pf.getString("WorldGuard.Enable"));
	}
	
	public List<String> getWorldGuardAreas() {
		return pf.getStringList("WorldGuard.DisabledRegions");
	}
	
	public String getWorldGuardDenyMessage() {
		return pf.getString("WorldGuard.DenyMessage").replace("&", "§").replace("\\§", "&");
	}
	
}

package me.xxastaspastaxx.dimensions.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.xxastaspastaxx.dimensions.api.CPortal;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

public final class CreateCPortalEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private Location portalLocation;
    private CPortal cportal;
    private int portalHeight;
    private int portalWidth;
    private boolean cancelled;

    public CreateCPortalEvent(Player player, Location portalLocation, CPortal cportal, int portalHeight, int portalWidth) {
    	this.player=player;
    	this.portalLocation=portalLocation;
    	this.cportal=cportal;
    	this.portalHeight=portalHeight;
    	this.portalWidth=portalWidth;
    }

    public Player getPlayer() {
    	return player;
    }
    
    public Location getLocation() {
    	return portalLocation;
    }
    
    public CPortal getPortal() {
    	return cportal;
    }
    
    public int getPortalHeight() {
    	return portalHeight;
    }
    
    public int getPortalWidth() {
    	return portalWidth;
    }
    
    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
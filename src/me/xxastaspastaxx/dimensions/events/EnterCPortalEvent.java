package me.xxastaspastaxx.dimensions.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.xxastaspastaxx.dimensions.api.CPortal;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

public final class EnterCPortalEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private Location portalLocation;
    private CPortal cportal;
    private boolean cancelled;

    public EnterCPortalEvent(Player player, Location portalLocation, CPortal cportal) {
    	this.player=player;
    	this.portalLocation=portalLocation;
    	this.cportal=cportal;
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
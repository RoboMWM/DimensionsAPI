package me.xxastaspastaxx.dimensions.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.Cancellable;

public final class DimensionsLoadEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private Plugin plugin;
    private boolean usingfac;
    private String facpl;
    private boolean cancelled;

    public DimensionsLoadEvent(Plugin plugin, boolean usingfac, String facpl) {
    	this.plugin = plugin;
    	this.usingfac = usingfac;
    	this.facpl = facpl;
    }

    public Plugin getPlugin() {
        return plugin;
    }
    
    public boolean isUsingFactions() {
        return usingfac;
    }

    public String getFactionsPlugin() {
    	return facpl;
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
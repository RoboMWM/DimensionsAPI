package me.xxastaspastaxx.dimensions.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.Location;
import org.bukkit.event.Cancellable;

public final class EssenceSpawnEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private Location essLocation;
    private boolean cancelled;

    public EssenceSpawnEvent(Location essLocation) {
    	this.essLocation = essLocation;
    }

    public Location getEssenceLocation() {
        return essLocation;
    }
    
    public void setEssenceLocation(Location loc) {
    	essLocation=loc;
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
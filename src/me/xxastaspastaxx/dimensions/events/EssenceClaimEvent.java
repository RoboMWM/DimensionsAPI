package me.xxastaspastaxx.dimensions.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

public final class EssenceClaimEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private Location essLocation;
    private Player p;
    private int essenceAmount;
    private String claimMessage;
    private boolean cancelled;

    public EssenceClaimEvent(Player p, Location essLocation, int essenceAmount, String claimMessage) {
    	this.essLocation = essLocation;
    	this.p=p;
    	this.essenceAmount=essenceAmount;
    	this.claimMessage=claimMessage;
    }

    public Location getEssenceLocation() {
        return essLocation;
    }
    
    public Player getPlayer() {
    	return p;
	}
    
    public int getEssenceAmount() {
    	return essenceAmount;
    }
    
    public void setEssenceAmount(int amount) {
    	essenceAmount=amount;
    }
    
    public String getClaimMessage() {
    	return claimMessage;
    }
    
    public void setClaimMessage(String message) {
    	claimMessage=message;
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
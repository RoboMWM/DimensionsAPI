package me.xxastaspastaxx.dimensions.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

public final class DimensionsUnloadEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Plugin plugin;

    public DimensionsUnloadEvent(Plugin plugin) {
    	this.plugin = plugin;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
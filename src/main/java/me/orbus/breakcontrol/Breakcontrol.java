package me.orbus.breakcontrol;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class Breakcontrol extends JavaPlugin {
    private final BlockBreakListener spawnerBreakListener = new BlockBreakListener(this);
    private String allowMessage;
    private String denyMessage;

    public Breakcontrol() {
    }

    public void onEnable() {
        this.saveDefaultConfig();
        this.allowMessage = this.getConfig().getString("allowMessage");
        this.denyMessage = this.getConfig().getString("denyMessage");
        this.getServer().getPluginManager().registerEvents(this.spawnerBreakListener, this);
    }

    public void onDisable() {
        HandlerList.unregisterAll(this.spawnerBreakListener);
    }

    public String getAllowMessage() {
        return this.allowMessage;
    }

    public String getDenyMessage() {
        return this.denyMessage;
    }
}

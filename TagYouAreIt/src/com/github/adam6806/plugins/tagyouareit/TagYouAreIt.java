package com.github.adam6806.plugins.tagyouareit;

import org.bukkit.plugin.java.JavaPlugin;

public final class TagYouAreIt extends JavaPlugin {

	@Override
    public void onEnable() {
		getLogger().info("TagYouAreIt has been enabled.");
		getServer().getPluginManager().registerEvents(new PlayerTagListener(), this);
    }
 
    @Override
    public void onDisable() {
    	getLogger().info("TagYouAreIt has been disabled.");
    }
}

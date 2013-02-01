package com.github.adam6806.plugins.tagyouareit;

import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class TagYouAreIt extends JavaPlugin {

	@Override
    public void onEnable() {
		getLogger().info("TagYouAreIt has been enabled.");
		Listener l = new PlayerTagListener();
		CommandExecutor t = new TagCommands();
		((TagCommands) t).setListener(l);
		((PlayerTagListener) l).setCmd(t);
		
		getServer().getPluginManager().registerEvents(l, this);
		getCommand("tag").setExecutor(t);
    }
 
    @Override
    public void onDisable() {
    	getLogger().info("TagYouAreIt has been disabled.");
    }
}

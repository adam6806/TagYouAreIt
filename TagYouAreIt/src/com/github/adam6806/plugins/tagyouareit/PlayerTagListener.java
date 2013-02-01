package com.github.adam6806.plugins.tagyouareit;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerTagListener implements Listener {

	@EventHandler
	public void onPlayerTag(PlayerInteractEntityEvent event) {
		
		Entity e = event.getRightClicked();
		Player p = event.getPlayer();
		if (e instanceof Player) {
			//If the Player p is the person chasing
			//and the Entity e is a player who is part
			//of the game of tag then record the tag
			//for use in determining the next "chaser"
			//generate a broadcast for the tag using something
			//like Entity e was tagged out by Player p
			Player p2 = ((Player) e).getPlayer();
			p.sendMessage("You tagged " + p2.getDisplayName());
			p2.sendMessage("You were tagged by " + p.getDisplayName());
			
		}
		
	}
}

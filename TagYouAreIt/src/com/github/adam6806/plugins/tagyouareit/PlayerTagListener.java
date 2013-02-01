package com.github.adam6806.plugins.tagyouareit;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.potion.*;

public class PlayerTagListener implements Listener {
	
	public Player chaser;
	public Player p2;
	public Player[] participants = new Player[20];
	public CommandExecutor cmd;

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
			p2 = ((Player) e).getPlayer();
			if (checkIsParticipant()) {
				if (p == chaser) {
					Bukkit.broadcastMessage(p2.getDisplayName() + " was tagged by " + p.getDisplayName() + ". Now " + p2.getDisplayName() + " is it!!");
					newChaser();
				} else p.sendMessage("You can't tag anyone because you're not it!");
			} else p.sendMessage("You're not playing. Use /tag join to join the game.");
			
			p.sendMessage("You tagged " + p2.getDisplayName());
			p2.sendMessage("You were tagged by " + p.getDisplayName());
			
		}
		
	}
	
	public boolean checkIsParticipant() {
		for (int i = 0;i<participants.length;i++) {
			if (participants[i].equals(p2)) return true;
		}
		return false;
	}
	
	public void newChaser() {
		chaser.removePotionEffect(PotionEffectType.JUMP);
		chaser.removePotionEffect(PotionEffectType.SPEED);
		chaser = p2;
		chaser.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,10000,5));
		chaser.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,10000,5));
	}
	
	public Player getChaser() {
		return chaser;
	}
	
	public void setCmd (CommandExecutor l) {
		cmd = l;
	}

	public void setChaser(Player c) {
		chaser = c;
	}

	public void setParticipants(Player[] p) {
		participants = p;
	}
}

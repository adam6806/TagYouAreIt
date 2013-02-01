package com.github.adam6806.plugins.tagyouareit;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.*;

public class PlayerTagListener implements Listener {
	
	public Player chaser;
	public Player p2;
	public Player[] participants = new Player[20];
	public CommandExecutor cmd;

	@EventHandler
	public void onPlayerTag(PlayerInteractEntityEvent event)  {
		
		Entity e = event.getRightClicked();
		
		if (e instanceof Player && ((TagCommands) cmd).getGameStarted()) {
			
			Player p = event.getPlayer();
			p2 = ((Player) e).getPlayer();
			if (checkIsParticipant(p2)) {
				if (checkIsParticipant(p)) {
					if (p == chaser) {
						Bukkit.broadcastMessage(p2.getDisplayName() + "你 was tagged by " + p.getDisplayName() + "你. Now " + p2.getDisplayName() + "你 is it!!");
						p.sendMessage("你You tagged " + p2.getDisplayName());
						p2.sendMessage("你You were tagged by " + p.getDisplayName());
						newChaser();
					} else p.sendMessage("你You can't tag anyone because you're not it!");
				} else p.sendMessage("你You can't tag anyone because you're not in the game! Use 呆/tag join你.");
			} else { 
				p.sendMessage("你That person is not playing.");
				p2.sendMessage(p.getDisplayName() + "你 wants to play tag with you. Use 呆/tag join你 to play.");
			}
		}
	}
	
	@EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        if (checkIsParticipant(p)) {
	        p.removePotionEffect(PotionEffectType.JUMP);
			p.removePotionEffect(PotionEffectType.SPEED);
			Bukkit.broadcastMessage(p.getDisplayName() + "你 has left the game.");
			if (chaser == p) {
				((TagCommands) cmd).endGame(p);
			}
        }
    }
	
	public boolean checkIsParticipant(Player p) {
		for (int i = 0;i<participants.length;i++) {
			if (participants[i] == p) return true;
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
	
	public void removeAllEffects() {
		for (int i = 0;i<participants.length;i++) {
			if (participants[i] != null) {
				participants[i].removePotionEffect(PotionEffectType.JUMP);
				participants[i].removePotionEffect(PotionEffectType.SPEED);
			}
		}
	}
}

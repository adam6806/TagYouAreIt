package com.github.adam6806.plugins.tagyouareit;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TagCommands implements CommandExecutor {
	
	public Player chaser;
	public Player[] participants = new Player[20];
	public boolean currentGame = false;
	public boolean gameStarted = false;
	public int counter = 0;
	public Listener tagListener;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel,
			String[] args) {
		if (commandLabel.equalsIgnoreCase("tag")) {
			if (args.length == 0) return false;
			if (args[0].equalsIgnoreCase("new")) {
				if (currentGame == false) {
					Bukkit.getServer().broadcastMessage("A new game of tag is starting! Use /tag join to join in.");
					chaser = (Player) sender;
					participants[counter] = chaser;
					counter++;
					currentGame = true;
					((PlayerTagListener) tagListener).setChaser(chaser);
					((PlayerTagListener) tagListener).setParticipants(participants);
				} else sender.sendMessage("There is already a game in progress. Use /tag join to join in.");
				
			} else if (args[0].equalsIgnoreCase("join")) {
				if (currentGame == false) sender.sendMessage("There is no current game in progress. Use /tag new to create one");
				else if (sender == chaser) sender.sendMessage("You can't join a game you created silly...");
				else if (counter < 20) {
					participants[counter] = (Player) sender;
					counter++;
					((PlayerTagListener) tagListener).setParticipants(participants);
					sender.sendMessage("You have joined the current game of tag");
					Bukkit.getServer().broadcastMessage(((Player) sender).getDisplayName() + " has joined the game!");
				} else sender.sendMessage("This game is full.");
				
			} else if (args[0].equalsIgnoreCase("start")) {
				if (sender == chaser) {
					gameStarted = true;
					Bukkit.getServer().broadcastMessage("A new game of tag has begun!");
					chaser.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,10000,5));
					chaser.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,10000,5));
					((PlayerTagListener) tagListener).setChaser(chaser);
					((PlayerTagListener) tagListener).setParticipants(participants);
				} else sender.sendMessage("Only the game creator can start the game.");
				
			} else if (args[0].equalsIgnoreCase("stop")) {
				if (!gameStarted) sender.sendMessage("The game has not yet begun.");
				else if (sender == ((PlayerTagListener) tagListener).getChaser()) {
					Bukkit.getServer().broadcastMessage("The game has been ended by " + ((PlayerTagListener) tagListener).getChaser().getDisplayName());
					chaser = null;
					participants = new Player[20];
					currentGame = false;
					counter = 0;
					((PlayerTagListener) tagListener).removeAllEffects();
				} else if (((PlayerTagListener) tagListener).checkIsParticipant((Player) sender)) {
					sender.sendMessage("You can only stop the game if you are it.");
				} else sender.sendMessage("You can only stop the game if you are in the game.");
				
			} else if (args[0].equalsIgnoreCase("quit")) {
				if (counter > 0) {
					if (((PlayerTagListener) tagListener).checkIsParticipant((Player) sender)) {
						if (sender == ((PlayerTagListener) tagListener).getChaser()) {
							Bukkit.getServer().broadcastMessage("The game has been ended by " + ((PlayerTagListener) tagListener).getChaser().getDisplayName());
							chaser = null;
							participants = new Player[20];
							currentGame = false;
							counter = 0;
							((PlayerTagListener) tagListener).removeAllEffects();
						} else {
							for (int i = 0;i<participants.length;i++) {
								if (participants[i] == sender) {
									participants[i] = null;
									sender.sendMessage("You have quit the game.");
									((PlayerTagListener) tagListener).setParticipants(participants);
								}
							}
						}
					} else sender.sendMessage("You can only quit the game if you are in the game.");
				} else sender.sendMessage("You can only quit the game if you are in the game.");
			} else return false;
			return true;
		}
		return false;
	}
	
	public void setListener (Listener l) {
		tagListener = l;
	}
	
	public boolean getGameStarted() {
		return gameStarted;
	}
}

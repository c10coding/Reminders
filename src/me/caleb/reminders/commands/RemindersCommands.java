package me.caleb.reminders.commands;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;


import me.caleb.reminders.Main;
import me.caleb.reminders.utils.Utils;

public class RemindersCommands implements CommandExecutor{
	
	private Main plugin;
	
	public RemindersCommands(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("reminders").setExecutor(this);
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
		Player p = (Player) sender;
		
		if(p.hasPermission("reminders.use")) {
			if(args[0].equalsIgnoreCase("add")) {
				
				String[] addArr = Arrays.copyOfRange(args, 1, args.length);
				String add = String.join(" ", addArr);
				List<String> list = plugin.getConfig().getStringList("Reminders.Strings");
				list.add(add);
				plugin.getConfig().set("Reminders.Strings",list);
				sender.sendMessage(Utils.chat("&7[&bReminder&7]" + "&b You have added a reminder!"));
				plugin.saveConfig();
				plugin.reloadConfig();

				return true;
			}else if(args[0].isEmpty()) {
				sender.sendMessage(Utils.chat("&7[&bReminder&7]" + "&c You can either do /reminders add or /reminders remove"));
				return true;
			}else if(args[0].equalsIgnoreCase("list")) {
				List<String> list = plugin.getConfig().getStringList("Reminders.Strings");
				int count = 1;
				sender.sendMessage(Utils.chat("&7[&bReminder&7]" + "&c List of reminders:"));
				for(String e : list) {
					sender.sendMessage(Utils.chat("[#" + count + "]" + " &b" + e));
					count++;
				}
				
				return true;	
			}else if(args[0].equalsIgnoreCase("remove")) {
				List<String> list = plugin.getConfig().getStringList("Reminders.Strings");
				// 0 1 2 3 4 
				int num = Integer.parseInt(args[1]);
				num--;
				list.remove(num);
				sender.sendMessage(Utils.chat("&7[&bReminder&7]" + "&b You have removed an element from the array!"));
				plugin.getConfig().set("Reminders.Strings",list);
				plugin.saveConfig();
				plugin.reloadConfig();
			}else if(args[0].equalsIgnoreCase("edit")) {
				List<String> list = plugin.getConfig().getStringList("Reminders.Strings");
				int index = Integer.parseInt(args[1]);
				index--;
				
				String[] editArr = Arrays.copyOfRange(args, 2, args.length);
				String edit = String.join(" ", editArr);
				
				list.set(index, edit);
				plugin.getConfig().set("Reminders.Strings",list);
				sender.sendMessage(Utils.chat("&7[&bReminder&7]" + "&b You have edited an element from the array!"));
			    plugin.saveConfig();
			    plugin.reloadConfig();
			}else if(args[0].equalsIgnoreCase("setTime")) {
				
				//long time = plugin.getConfig().getLong("Reminders.Time");
				long newTime = Long.parseLong(args[1]);
				
				plugin.getConfig().set("Time", newTime);
				sender.sendMessage(Utils.chat("&7[&bReminder&7]" + "&b You have edited the time between each reminder to " + newTime + " Make sure you do &6/plugman reload Reminders &bfor it to take effect!"));
				plugin.saveConfig();
				plugin.reloadConfig();
				
			}else if(args[0].equalsIgnoreCase("help")) {
				List<String> list = plugin.getConfig().getStringList("Help");
				sender.sendMessage(Utils.chat("&7[&bReminder&7]" + "&c List of commands:"));
				for(String e : list) {
					sender.sendMessage(Utils.chat("&a " + e));
				}
			}
		}else {
			sender.sendMessage(Utils.chat("&7[&bReminder&7]" + " You do not have permission to use this command!"));
		}
		
		
		return false;
	}
	public static List<String> getList(Main plugin) {
		List<String> list = plugin.getConfig().getStringList("Reminders.Strings");
		return list;
	}
}
	
	


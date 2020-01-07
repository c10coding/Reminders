package me.caleb.reminders;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import me.caleb.reminders.commands.RemindersCommands;
import me.caleb.reminders.utils.Utils;

public class Main extends JavaPlugin{
	
	private Main plugin = this;
	long time = this.getConfig().getLong("Time");
	FileConfiguration config = plugin.getConfig();
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		new RemindersCommands(this);
		
		BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
            	List<String> list = getConfig().getStringList("Reminders.Strings");
            	Random rand = new Random();	
            	int randomIndex = 0;
            	final String PREFIX = config.getString("Prefix");
            	
            	if(list.size() == 0) {
            		Bukkit.getConsoleSender().sendMessage(Utils.chat("&7[&bReminder&7]" + " There are no reminders create. Ignoring..."));
            		return;
            	}else {
            		randomIndex = rand.nextInt(list.size());
            	}
                
                int prev = plugin.getConfig().getInt("Prev");    
                int size = (list.size()-1);
                
                //Only executes if there's one reminder
                if(list.size() != 1) {
                	 if(randomIndex == prev) {
                     	if(randomIndex == 0) {
                     		randomIndex++;
                     	}else if(randomIndex == size) {
                     		randomIndex--;
                     	}else {
                     		randomIndex++;
                     	}
                	 }
                }
               
                config.set("Prev",randomIndex);
                plugin.saveConfig();
                plugin.reloadConfig();
                
                String randomElement = list.get(randomIndex);
                Bukkit.broadcastMessage(Utils.chat(PREFIX + " &c" + randomElement));
                
            }
        }, 0L, time);
		
	}
	
}

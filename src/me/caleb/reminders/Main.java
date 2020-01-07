package me.caleb.reminders;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import me.caleb.reminders.commands.RemindersCommands;
import me.caleb.reminders.utils.Utils;

public class Main extends JavaPlugin{
	
	private Main plugin = this;
	long time = this.getConfig().getLong("Time");
	
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
                int randomIndex = rand.nextInt(list.size());
                int prev = plugin.getConfig().getInt("Prev");    
                int size = (list.size()-1);
                 
                if(randomIndex == prev) {
                	if(randomIndex == 0) {
                		randomIndex++;
                	}else if(randomIndex == size) {
                		randomIndex--;
                	}else {
                		randomIndex++;
                	}
                }
                
                plugin.getConfig().set("Prev",randomIndex);
                plugin.saveConfig();
                plugin.reloadConfig();
                
                String randomElement = list.get(randomIndex);
                Bukkit.broadcastMessage(Utils.chat(plugin.getConfig().getString("Prefix") + " &c" + randomElement));
                
            }
        }, 0L, time);
		
	}
	
}

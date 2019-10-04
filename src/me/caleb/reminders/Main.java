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
                	//Bukkit.broadcastMessage(String.valueOf("They are equal!"));
                	if(randomIndex == 0) {
                		//Bukkit.broadcastMessage(String.valueOf("It equaled 0! : " + randomIndex));
                		randomIndex++;
                	}else if(randomIndex == size) {
                		//Bukkit.broadcastMessage("RandomIndex should be 1! " + randomIndex);
                		randomIndex--;
                	}else {
                		randomIndex++;
                	}
                }
                
                //Bukkit.broadcastMessage(Utils.chat("&bRandomIndex: &r" + randomIndex));
                //Bukkit.broadcastMessage(Utils.chat("&bPrevious: &r" + prev));
                
                
                plugin.getConfig().set("Prev",randomIndex);
                plugin.saveConfig();
                plugin.reloadConfig();
                
                String randomElement = list.get(randomIndex);
                Bukkit.broadcastMessage(Utils.chat("&7[&bReminder&7]" + " &c" + randomElement));
                
            }
        }, 0L, time);
		
	}
	
}

package me.caleb.reminders;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import me.caleb.reminders.commands.RemindersCommands;
import me.caleb.reminders.utils.Utils;

public class Main extends JavaPlugin{
	
	private Main plugin;
	long time = this.getConfig().getLong("Time");
	int prev = this.getConfig().getInt("Prev");
	
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
                //if(randomIndex != 0) {
                	//randomIndex -= 1;
                //}
                String randomElement = list.get(randomIndex);
                Bukkit.broadcastMessage(Utils.chat("&7[&bReminder&7]" + " &c" + randomElement));
            }
        }, 0L, time);
		
	}
	
}

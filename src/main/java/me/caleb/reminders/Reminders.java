package me.caleb.reminders;

import me.c10coding.coreapi.CoreAPI;
import me.caleb.reminders.command.Command;
import me.caleb.reminders.files.ConfigManager;
import me.caleb.reminders.runnables.ReminderSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class Reminders extends JavaPlugin {

    private ConfigManager cm;

    private final String PREFIX = "&l[&b&l" + this.getName() + "&r&l]";
    private CoreAPI api = new CoreAPI();
    //private final Logger logger = this.getLogger();

    @Override
    public void onEnable(){
        cm = new ConfigManager(this);
        cm.validateConfig();
        double minutes = cm.getTime();
        this.getCommand("reminders").setExecutor(new Command(this));
        new ReminderSender(this).runTaskTimer(this, 0L, minutesToTicks(minutes));
    }

    @Override
    public void onDisable(){ }

    public String getPrefix(){
        return PREFIX;
    }

    public CoreAPI getApi(){
        return api;
    }

    public long minutesToTicks(double minutes){
        return (long) (minutes * 60) * (20);
    }

    public ConfigManager getConfigManager(){
        return cm;
    }

}

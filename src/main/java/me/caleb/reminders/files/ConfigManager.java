package me.caleb.reminders.files;

import me.caleb.reminders.Reminders;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ConfigManager {

    private Reminders plugin;
    private File configFile;
    private FileConfiguration config;

    public ConfigManager(Reminders plugin){
        this.plugin = plugin;
        configFile = new File(plugin.getDataFolder(), "config.yml");
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void validateConfig(){
        File f = new File(plugin.getDataFolder(), "config.yml");
        if(!f.exists()){
            plugin.saveResource("config.yml", false);
        }
    }

    public void addReminder(String s){
        List<String> remindersList = getRemindersList();
        remindersList.add(s);
        config.set("Reminders", remindersList);
        saveConfig();
    }

    public void removeReminder(int numReminder){
        List<String> remindersList = getRemindersList();
        remindersList.remove(numReminder-1);
        config.set("Reminders", remindersList);
        saveConfig();
    }

    public void editReminder(String newReminder, int numReminder){
        List<String> remindersList = getRemindersList();
        remindersList.set(numReminder-1, newReminder);
        config.set("Reminders", remindersList);
        saveConfig();
    }

    public List<String> getHelpMessages(){
        return config.getStringList("Help");
    }

    public boolean sendRandomMessages(){
        return config.getBoolean("RandomMessages");
    }

    public int getCurrentIndex(){
        return config.getInt("CurrentIndex");
    }

    public void increaseIndex(int currentIndex){
        config.set("CurrentIndex", (currentIndex + 1));
        saveConfig();
    }

    public void resetIndex(){
        config.set("CurrentIndex", 0);
        saveConfig();
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public double getTime(){
        return config.getDouble("Time");
    }

    public void setTime(int minutes){
        config.set("Time", minutes);
        saveConfig();
    }

    public boolean isGreaterThanRemindersSize(int index){
        return getRemindersList().size() < index;
    }

    public String getPrefix(){
        return config.getString("Prefix");
    }

    public List<String> getRemindersList(){
        return config.getStringList("Reminders");
    }

}

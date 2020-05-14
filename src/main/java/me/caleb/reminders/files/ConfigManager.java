package me.caleb.reminders.files;

import com.google.inject.Inject;
import me.caleb.reminders.Reminders;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.List;

public class ConfigManager {

    @Inject
    private Reminders plugin;
    private FileConfiguration config = plugin.getConfig();

    public void validateConfig(Reminders plugin){
        File f = new File(plugin.getDataFolder(), "config.yml");
        if(!f.exists()){
            plugin.saveResource("config.yml", false);
        }
    }

    public void addReminder(String s){
        List<String> remindersList = getRemindersList();
        remindersList.add(s);
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

    public void saveConfig(){
        plugin.saveConfig();
    }

    public int getTime(){
        return config.getInt("Time");
    }

    public void setTime(int minutes){
        config.set("Time", minutes);
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

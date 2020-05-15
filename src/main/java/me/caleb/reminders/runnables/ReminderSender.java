package me.caleb.reminders.runnables;

import me.caleb.reminders.Reminders;
import me.caleb.reminders.files.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Random;

public class ReminderSender extends BukkitRunnable {

    private ConfigManager cm;
    private Reminders plugin;

    public ReminderSender(Reminders plugin){
        this.plugin = plugin;
        cm = new ConfigManager(plugin);
    }

    @Override
    public void run() {

        cm.reloadConfig();
        List<String> messages = cm.getRemindersList();
        boolean sendRandomMessages = cm.sendRandomMessages();
        String msg;

        if(!messages.isEmpty()){
            if(sendRandomMessages){
                Random rnd = new Random();
                int randNum = rnd.nextInt(messages.size());
                msg = messages.get(randNum);
            }else{
                int currentIndexMessage = cm.getCurrentIndex();
                if(messages.size() != 1){
                    if(currentIndexMessage == messages.size()){
                        cm.resetIndex();
                        currentIndexMessage = 0;
                    }
                    msg = messages.get(currentIndexMessage);
                    cm.increaseIndex(currentIndexMessage);
                }else{
                    cm.resetIndex();
                    msg = messages.get(0);
                }

            }
            for(Player p : Bukkit.getOnlinePlayers()){
                plugin.getApi().getChatFactory().sendPlayerMessage(msg, true, p, cm.getPrefix());
            }
        }else{
            plugin.getLogger().info("There are no reminders in the config...");
        }

    }
}

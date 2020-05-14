package me.caleb.reminders.runnables;

import com.google.inject.Inject;
import me.caleb.reminders.Reminders;
import me.caleb.reminders.files.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Random;

public class ReminderSender extends BukkitRunnable {

    @Inject private ConfigManager cm;
    @Inject private Reminders plugin;

    @Override
    public void run() {
        List<String> messages = cm.getRemindersList();
        String msg;
        boolean sendRandomMessages = cm.sendRandomMessages();
        int currentIndexMessage = 0;

        if(sendRandomMessages){
            Random rnd = new Random();
            int randNum = rnd.nextInt(messages.size()-1);
            msg = messages.get(randNum);
        }else{
            msg = messages.get(currentIndexMessage);
            currentIndexMessage++;
        }

        for(Player p : Bukkit.getOnlinePlayers()){
            plugin.getApi().getChatFactory().sendPlayerMessage(msg, true, p, cm.getPrefix());
        }
    }
}

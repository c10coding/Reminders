package me.caleb.reminders.command;

import com.google.inject.Inject;
import me.c10coding.coreapi.chat.Chat;
import me.caleb.reminders.Reminders;
import me.caleb.reminders.files.ConfigManager;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class Command implements CommandExecutor {

    @Inject private Reminders plugin;
    @Inject private ConfigManager cm;
    private Chat chatFactory = plugin.getApi().getChatFactory();

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] args) {

        if(args[0].equalsIgnoreCase("add") && args.length == 2){
            String reminder = args[1];
            cm.addReminder(reminder);
            chatFactory.sendPlayerMessage("You have created a new reminder!",true , commandSender, plugin.getPrefix());
        }else if(args[0].equalsIgnoreCase("remove") && args.length == 2){

            int index;
            try{
                index = Integer.parseInt(args[1]);
            }catch(NumberFormatException e){
                chatFactory.sendPlayerMessage("That is not a valid number!",true , commandSender, plugin.getPrefix());
                return false;
            }

            try{
                cm.removeReminder(index);
            }catch(ArrayIndexOutOfBoundsException e){
                chatFactory.sendPlayerMessage("That is not a valid number!",true , commandSender, plugin.getPrefix());
                return false;
            }
        }else if(args[0].equalsIgnoreCase("edit") && args.length == 3) {

            int index;
            String newReminder = args[1];
            try {
                index = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                chatFactory.sendPlayerMessage("That is not a valid number!", true, commandSender, plugin.getPrefix());
                return false;
            }

            if (!cm.isGreaterThanRemindersSize(index) && index > 0) {
                cm.editReminder(newReminder, index);
                chatFactory.sendPlayerMessage("You have edited a reminder!", true, commandSender, plugin.getPrefix());
            } else {
                chatFactory.sendPlayerMessage("This is not a valid reminder index!", true, commandSender, plugin.getPrefix());
            }
        }else if(args[0].equalsIgnoreCase("settime") && args.length == 2) {
            int minutes;
            try {
                minutes = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                chatFactory.sendPlayerMessage("That is not a valid number!", true, commandSender, plugin.getPrefix());
                return false;
            }
            cm.setTime(minutes);
        }else if(args[0].equalsIgnoreCase("list") && args.length == 1){
            for(String m : cm.getRemindersList()){
                chatFactory.sendPlayerMessage(m, true, commandSender, plugin.getPrefix());
            }
        }else{
            sendHelp(commandSender);
        }
        return false;
    }

    public void sendHelp(CommandSender sender){
        List<String> messages = cm.getHelpMessages();
        for(String m : messages){
            chatFactory.sendPlayerMessage(m, true, sender, plugin.getPrefix());
        }
    }
}

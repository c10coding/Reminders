package me.caleb.reminders.command;

import com.google.inject.Inject;
import me.c10coding.coreapi.chat.Chat;
import me.caleb.reminders.Reminders;
import me.caleb.reminders.files.ConfigManager;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

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

            }
        }else if(args[0].equalsIgnoreCase("edit") && args.length == 3){

            int index;
            try{
                index = Integer.parseInt(args[1]);
            }catch(NumberFormatException e){
                chatFactory.sendPlayerMessage("That is not a valid number!",true , commandSender, plugin.getPrefix());
                return false;
            }

            if(!cm.isGreaterThanRemindersSize(index)){

            }
        }

        return false;
    }
}

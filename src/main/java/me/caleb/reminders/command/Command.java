package me.caleb.reminders.command;

import me.c10coding.coreapi.chat.Chat;
import me.caleb.reminders.Reminders;
import me.caleb.reminders.files.ConfigManager;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class Command implements CommandExecutor {

    private Reminders plugin;
    private Chat chatFactory;
    private ConfigManager cm;
    private String prefix;

    public Command(Reminders plugin){
        this.plugin = plugin;
        this.chatFactory = plugin.getApi().getChatFactory();
        this.cm = new ConfigManager(plugin);
        this.prefix = plugin.getPrefix();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] args) {

        if(args.length != 0){
            int index;
            if(args[0].equalsIgnoreCase("add") && args.length == 2){
                String reminder = args[1];
                cm.addReminder(reminder);
                chatFactory.sendPlayerMessage("You have created a new reminder!",true , commandSender, prefix);
            }else if(args[0].equalsIgnoreCase("remove") && args.length == 2){

                if(NumberUtils.isNumber(args[1])){
                    index = Integer.parseInt(args[1]);
                    if (!cm.isGreaterThanRemindersSize(index) && index > 0) {
                        cm.removeReminder(index);
                        chatFactory.sendPlayerMessage("You have removed a reminder!", true, commandSender, prefix);
                    }else{
                        chatFactory.sendPlayerMessage("That is not a valid index!",true , commandSender, prefix);
                        return false;
                    }
                }else{
                    chatFactory.sendPlayerMessage("That is not a valid number!", true, commandSender, prefix);
                    return false;
                }

            }else if(args[0].equalsIgnoreCase("edit") && args.length == 3) {

                String newReminder = args[2];

                if(NumberUtils.isNumber(args[1])){

                    index = Integer.parseInt(args[1]);

                    if (!cm.isGreaterThanRemindersSize(index) && index > 0) {
                        cm.editReminder(newReminder, index);
                        chatFactory.sendPlayerMessage("You have edited a reminder!", true, commandSender, prefix);
                    } else {
                        chatFactory.sendPlayerMessage("That is not a valid index!",true , commandSender, prefix);
                    }

                }else{
                    chatFactory.sendPlayerMessage("That is not a valid number!", true, commandSender, prefix);
                    return false;
                }

            }else if(args[0].equalsIgnoreCase("settime") && args.length == 2) {
                int minutes;

                if(NumberUtils.isNumber(args[1])){
                    //Valid number
                    minutes = Integer.parseInt(args[1]);
                    cm.setTime(minutes);

                    chatFactory.sendPlayerMessage("A reminder will now be sent every &b&l" + minutes + "&r minutes!", true, commandSender, prefix);
                    chatFactory.sendPlayerMessage("Make sure you reload the plugin or restart your server for it to take effect!", true, commandSender, prefix);

                }else{
                    chatFactory.sendPlayerMessage("That is not a valid number!", true, commandSender, prefix);
                }

            }else if(args[0].equalsIgnoreCase("list") && args.length == 1) {


                int x = 1;
                List<String> reminders = cm.getRemindersList();

                if(reminders.isEmpty()){
                    chatFactory.sendPlayerMessage("There aren't any reminders right now!", true, commandSender, prefix);
                }else{

                    chatFactory.sendPlayerMessage("Here are the list of reminders:", true, commandSender, prefix);

                    for (String m : cm.getRemindersList()) {
                        chatFactory.sendPlayerMessage("&b&l" + x + ".)&r&l " + m, false, commandSender, prefix);
                        x++;
                    }

                }

            }else if(args[0].equalsIgnoreCase("help") && args.length == 1) {
                sendHelp(commandSender);
            /*}else if(args[0].equalsIgnoreCase("reload") && args.length == 1){
                cm.reloadConfig();
                chatFactory.sendPlayerMessage("The config has been reloaded!", true, commandSender, prefix);*/
            }else{
                sendHelp(commandSender);
            }
        }
        return false;
    }

    public void sendHelp(CommandSender sender){
        List<String> messages = cm.getHelpMessages();
        chatFactory.sendPlayerMessage("Commands: ", true, sender, prefix);
        for(String m : messages){
            chatFactory.sendPlayerMessage(m, false, sender, prefix);
        }
    }
}

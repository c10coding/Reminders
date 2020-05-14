package me.caleb.reminders;

import com.google.inject.Inject;
import com.google.inject.Injector;
import me.c10coding.coreapi.CoreAPI;
import me.c10coding.coreapi.binder.Binder;
import me.caleb.reminders.command.Command;
import me.caleb.reminders.files.ConfigManager;
import me.caleb.reminders.runnables.ReminderSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class Reminders extends JavaPlugin {

    private CoreAPI api = new CoreAPI();
    @Inject private ConfigManager cm;
    //Injects command so that it can use the plugin variable within it
    @Inject private Command command;
    private final String PREFIX = "&l[&b&l" + this.getName() + "&r&l] ";

    @Override
    public void onEnable(){

        Binder b = new Binder(this);
        Injector injector = b.createInjector();
        injector.injectMembers(this);

        int minutes = cm.getTime();

        this.getCommand("reminders").setExecutor(this.command);
        new ReminderSender().runTaskTimer(this, 0L, minutesToTicks(minutes));
    }

    @Override
    public void onDisable(){

    }

    public String getPrefix(){
        return PREFIX;
    }

    public CoreAPI getApi(){
        return api;
    }

    public long minutesToTicks(int minutes){
        return (long) (minutes * 60) * (20);
    }

}

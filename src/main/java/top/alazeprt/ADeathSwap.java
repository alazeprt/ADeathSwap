package top.alazeprt;

import org.bukkit.plugin.java.JavaPlugin;
import top.alazeprt.command.AdminCommand;
import top.alazeprt.event.DeathEvent;
import top.alazeprt.game.GameThread;

public class ADeathSwap extends JavaPlugin {
    @Override
    public void onEnable() {
        getCommand("deathswap").setExecutor(new AdminCommand());
        getServer().getPluginManager().registerEvents(new DeathEvent(), this);
    }

    @Override
    public void onDisable() {
        if(GameThread.thread != null && !GameThread.thread.isInterrupted()) {
            GameThread.thread.interrupt();
        }
    }
}

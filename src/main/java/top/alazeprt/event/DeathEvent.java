package top.alazeprt.event;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import top.alazeprt.game.GameThread;

public class DeathEvent implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if(GameThread.thread == null || !GameThread.thread.isAlive()) return;
        GameThread.survivedPlayers.remove(event.getEntity());
        Bukkit.broadcastMessage(ChatColor.AQUA + event.getEntity().getName() + " died!");
        if(GameThread.survivedPlayers.size() == 1) {
            Bukkit.broadcastMessage(ChatColor.GREEN + "The winner is " + GameThread.survivedPlayers.get(0).getName() + "!");
            GameThread.thread.interrupt();
            GameThread.thread = null;
        }
    }
}

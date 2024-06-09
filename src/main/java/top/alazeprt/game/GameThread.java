package top.alazeprt.game;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import top.alazeprt.ADeathSwap;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameThread {

    public static Thread thread;

    public static List<Player> survivedPlayers;

    public static void start(long period) {
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.setFoodLevel(20);
            player.setHealth(20);
            player.setGameMode(GameMode.SURVIVAL);
            player.sendTitle(ChatColor.RED + "Death Swap", ChatColor.GREEN + "Protect yourself, make the other person die when exchange");
            player.sendMessage(ChatColor.RED + "Death Swap");
            player.sendMessage(ChatColor.GREEN + "Protect yourself, make the other person die when exchange");
            player.sendMessage(ChatColor.YELLOW + "You have " + period + " seconds to prepare!");
        }
        survivedPlayers.addAll(Bukkit.getOnlinePlayers());
        thread = new Thread(() -> {
            while(true) {
                try {
                    Thread.sleep((period - 30) * 1000);
                } catch (InterruptedException e) {
                    return;
                }
                for(Player player : Bukkit.getOnlinePlayers()) {
                    player.sendMessage(ChatColor.YELLOW + "30 seconds left!");
                }
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    return;
                }
                for(int i = 10; i > 0; i--) {
                    for(Player player : Bukkit.getOnlinePlayers()) {
                        player.sendMessage(ChatColor.RED +
                                String.valueOf(i) + " seconds left!");
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        return;
                    }
                }
                List<? extends Player> onlinePlayers = (List<? extends Player>)
                        Bukkit.getOnlinePlayers();
                Collections.shuffle(onlinePlayers);
                Location location = null;
                for(int i = 0; i < onlinePlayers.size(); i++) {
                    Player player = onlinePlayers.get(i);
                    if(i%2==0) {
                        location = player.getLocation();
                    } else {
                        Player lastPlayer = onlinePlayers.get(i - 1);
                        player.teleport(lastPlayer.getLocation());
                        lastPlayer.teleport(location);
                    }
                }
                if(onlinePlayers.size()%2==0) {
                    Player targetPlayer = onlinePlayers.get(Math.abs(new Random().nextInt())%(onlinePlayers.size()-1));
                    Player player = onlinePlayers.get(onlinePlayers.size()-1);
                    player.teleport(targetPlayer.getLocation());
                    targetPlayer.teleport(location);
                }
            }
        });
        thread.start();
    }
}

package top.alazeprt.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import top.alazeprt.game.GameThread;

public class AdminCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!commandSender.hasPermission("adeathswap.admin")) {
            commandSender.sendMessage(ChatColor.RED + "You don't have permission to use this top.alazeprt.command!");
            return true;
        }
        if(strings.length != 1) {
            commandSender.sendMessage("Usage: /deathswap <period>");
            return true;
        }
        Long period = Long.parseLong(strings[0]);
        if(period < 10) {
            commandSender.sendMessage(ChatColor.RED + "Period cannot be less than 10 seconds!");
            return true;
        }
        GameThread.start(period);
        return false;
    }
}

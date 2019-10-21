package me.jaymesss.donkey.command;

import me.jaymesss.donkey.Donkey;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

public class SpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        Location location = new Location(Bukkit.getWorld("world"), 0.5, 82, 0.5);
        if (!commandSender.hasPermission("donkey.player.admin")) {
            Donkey.getInstance().getTimerManager().getSpawnTimer().teleport(player, location, 5000L, ChatColor.RED + "You are being teleported to spawn. Please wait " + ChatColor.YELLOW + 5 + ChatColor.RED + " seconds without moving or taking damage.", PlayerTeleportEvent.TeleportCause.COMMAND);

        } else {
            player.teleport(location);
            commandSender.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + ChatColor.ITALIC.toString() + "boop boop you're opped so you go speedy fast");
        }
        return false;
    }
}

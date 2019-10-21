package me.jaymesss.donkey.command;

import me.jaymesss.donkey.DonkeyAPI;
import me.jaymesss.donkey.utils.DurationFormatter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;

import java.lang.management.ManagementFactory;

public class LagCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!commandSender.hasPermission("donkey.player.admin")) {
            commandSender.sendMessage(ChatColor.RED + "No permission.");
            return true;
        }
        if (args.length < 1) {
            commandSender.sendMessage(ChatColor.BLUE.toString() + ChatColor.STRIKETHROUGH + "-----------------------------------");
            commandSender.sendMessage(ChatColor.RED + "/lag clear");
            commandSender.sendMessage(ChatColor.RED + "/lag gc");
            commandSender.sendMessage(ChatColor.RED + "/lag show");
            commandSender.sendMessage(ChatColor.BLUE.toString() + ChatColor.STRIKETHROUGH + "-----------------------------------");
            return true;
        }
        if (args[0].equalsIgnoreCase("clear")) {
            commandSender.sendMessage(ChatColor.GREEN + "Clearing "
                    + DonkeyAPI.clearAllEntities(
                    EntityType.PAINTING, EntityType.ITEM_FRAME, EntityType.ENDER_PEARL, EntityType.MINECART, EntityType.SPLASH_POTION, EntityType.VILLAGER)
                    + " entities in all worlds.");
            return true;
        } else if (args[0].equalsIgnoreCase("gc")) {
            commandSender.sendMessage(ChatColor.GREEN + "Running system garbage collector.");
            System.gc();
            return true;
        } else if (args[0].equalsIgnoreCase("show")) {
            long max = Runtime.getRuntime().maxMemory() / 1024 / 1024;
            long free = Runtime.getRuntime().freeMemory() / 1024 / 1024;
            long used = max - free;
            commandSender.sendMessage(ChatColor.GOLD + "TPS from last 1m, 5m, 15m: " + DonkeyAPI.getFormattedTps(Bukkit.spigot().getTPS()[0]) + ChatColor.GOLD + ", " + DonkeyAPI.getFormattedTps(Bukkit.spigot().getTPS()[1]) + ChatColor.GOLD + ", " + DonkeyAPI.getFormattedTps(Bukkit.spigot().getTPS()[2]));
            commandSender.sendMessage(ChatColor.GOLD + "Server Performance: " + ChatColor.GREEN + DonkeyAPI.getFormattedLag());
            commandSender.sendMessage(ChatColor.GOLD + "Free Memory: " + ChatColor.GREEN + free + "MB" + '/' + max + "MB");
            commandSender.sendMessage(ChatColor.GOLD + "Used Memory: " + ChatColor.GREEN + used + "MB");
            commandSender.sendMessage(ChatColor.GOLD + "Online Players: " + ChatColor.GREEN + Bukkit.getOnlinePlayers().size() + '/' + Bukkit.getMaxPlayers());
            commandSender.sendMessage(String.format(ChatColor.GOLD + "Uptime: " + ChatColor.GREEN + DurationFormatter.getRemaining(System.currentTimeMillis() - ManagementFactory.getRuntimeMXBean().getStartTime(), true, true)));
            commandSender.sendMessage(ChatColor.GOLD + "Entities: " + ChatColor.GREEN + DonkeyAPI.getTotalEntities());
            commandSender.sendMessage(ChatColor.GOLD + "Average Ping: " + ChatColor.GREEN + DonkeyAPI.getAveragePing());
        }
        return true;
    }
}

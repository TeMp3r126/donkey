package me.jaymesss.donkey.command.social;

import me.jaymesss.donkey.Donkey;
import me.jaymesss.donkey.user.DonkeyUser;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class IgnoreCommand implements CommandExecutor {

    private String getIgnored(DonkeyUser survivalUser) {
        String message = "";
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (survivalUser.getIgnored().contains(player.getName())) {
                message = message + ChatColor.WHITE + player.getName() + ChatColor.WHITE + ", " + ChatColor.WHITE;
            }
        }
        if (message.length() > 1) {
            message = message.substring(0, message.length() - 2);
        }
        if (message.length() == 0) {
            message = ChatColor.WHITE + "Nonene";
        }
        return message;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length < 1) {
            commandSender.sendMessage(ChatColor.BLUE.toString() + ChatColor.STRIKETHROUGH + "-----------------------------------");
            commandSender.sendMessage(ChatColor.RED + "/ignore <player>");
            commandSender.sendMessage(ChatColor.RED + "/ignore remove <player>");
            commandSender.sendMessage(ChatColor.RED + "/ignore list");
            commandSender.sendMessage(ChatColor.BLUE.toString() + ChatColor.STRIKETHROUGH + "-----------------------------------");
            return true;
        }
        Player player = (Player) commandSender;
        DonkeyUser survivalUser = Donkey.getInstance().getUserManager().getUser(player.getUniqueId());
        if (args[0].equalsIgnoreCase("list")) {
            commandSender.sendMessage(ChatColor.GOLD + "Ignored: " + getIgnored(survivalUser).substring(0, getIgnored(survivalUser).length() - 2));
            return true;
        }
        if (args[0].equalsIgnoreCase("remove")) {
            if (args.length == 1) {
                commandSender.sendMessage(ChatColor.RED + "Usage: /ignore remove <player>");
                return true;
            }
            if (survivalUser.getIgnored().contains(args[1])) {
                commandSender.sendMessage(ChatColor.YELLOW + "You have un-ignored " + ChatColor.LIGHT_PURPLE + args[1] + ChatColor.YELLOW + '.');
                survivalUser.getIgnored().remove(args[1]);
                return true;
            } else {
                commandSender.sendMessage(ChatColor.YELLOW + "You do not have " + ChatColor.LIGHT_PURPLE + args[1] + ChatColor.YELLOW + "ignored.");
                return true;
            }
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (!target.hasPlayedBefore()) {
            commandSender.sendMessage(ChatColor.RED + args[0] + " has never joined the server.");
            return true;
        }
        if (survivalUser.getIgnored().contains(args[0])) {
            commandSender.sendMessage(ChatColor.YELLOW + "You already have " + ChatColor.LIGHT_PURPLE + args[0] + ChatColor.YELLOW + " ignored.");
            return true;
        } else {
            commandSender.sendMessage(ChatColor.YELLOW + "You have ignored " + ChatColor.LIGHT_PURPLE + args[0] + ChatColor.YELLOW + '.');
            survivalUser.getIgnored().add(args[0]);
            return true;
        }
    }
}

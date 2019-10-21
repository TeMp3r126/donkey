package me.jaymesss.donkey.command;

import me.jaymesss.donkey.Donkey;
import me.jaymesss.donkey.DonkeyAPI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TntCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length < 1) {
            commandSender.sendMessage(ChatColor.RED + "Usage: /tnt toggle");
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "toggle":
                if (Donkey.getInstance().getConfig().getString("tnt").equals("enabled")) {
                    commandSender.sendMessage(ChatColor.YELLOW + "You have " + ChatColor.RED + "disabled" + ChatColor.YELLOW + " tnt.");
                    DonkeyAPI.playerPerformFormat(commandSender, "Disabled tnt", "donkey.player.admin");
                    Donkey.getInstance().getConfig().set("tnt", "disabled");
                    return true;
                } else {
                    commandSender.sendMessage(ChatColor.YELLOW + "You have " + ChatColor.GREEN + "enabled" + ChatColor.YELLOW + " tnt.");
                    DonkeyAPI.playerPerformFormat(commandSender, "Enabled tnt", "donkey.player.admin");
                    Donkey.getInstance().getConfig().set("tnt", "enabled");
                    return true;
                }
            default:
                commandSender.sendMessage(ChatColor.RED + "Usage: /tnt toggle");
                return true;
        }
    }
}

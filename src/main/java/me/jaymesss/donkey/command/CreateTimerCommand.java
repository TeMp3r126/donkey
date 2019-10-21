package me.jaymesss.donkey.command;

import me.jaymesss.donkey.Donkey;
import me.jaymesss.donkey.DonkeyAPI;
import me.jaymesss.donkey.timer.CustomTimer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CreateTimerCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!commandSender.hasPermission("donkey.player.admin")) {
            commandSender.sendMessage(ChatColor.RED + "No permission.");
            return true;
        }
        if (args.length < 2) {
            commandSender.sendMessage(ChatColor.RED + "Usage: /createtimer <timerName> <displayName>");
            return true;
        }
        String timerName = args[0];
        String displayName = args[1];
        Donkey.getInstance().getTimerManager().registerCustomTimer(new CustomTimer(timerName, displayName));
        DonkeyAPI.playerPerformFormat(commandSender, "Created timer " + args[0], "donkey.player.admin");
        commandSender.sendMessage(ChatColor.GREEN + "You have created the timer " + ChatColor.translateAlternateColorCodes('&', args[1]).replace('_', ' '));
        return false;
    }
}
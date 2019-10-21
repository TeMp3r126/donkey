package me.jaymesss.donkey.command;

import me.jaymesss.donkey.DonkeyAPI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PingCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player player = (Player) commandSender;
        if (args.length < 1) {
            commandSender.sendMessage(ChatColor.YELLOW + "Your ping is " + DonkeyAPI.getColoredPing(player) + ChatColor.YELLOW + '.');
            return true;
        }
        if (args[0].equalsIgnoreCase("multiplier") && commandSender.hasPermission("donkey.player.admin")) {
            commandSender.sendMessage(ChatColor.GREEN + "You set the ping spoofing multiplier to " + args[1] + "x.");
            DonkeyAPI.setPingMultiplier(Double.parseDouble(args[1]));
            return true;
        } else {
            commandSender.sendMessage(ChatColor.YELLOW + "Your ping is " + DonkeyAPI.getColoredPing(player) + ChatColor.YELLOW + '.');
            return true;
        }
    }

}

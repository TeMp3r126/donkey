package me.jaymesss.donkey.command;

import me.jaymesss.donkey.Donkey;
import me.jaymesss.donkey.user.DonkeyUser;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NickCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!commandSender.hasPermission("donkey.command.nick")) {
            commandSender.sendMessage(ChatColor.RED + "No permission.");
            return true;
        }
        if (args.length < 1) {
            commandSender.sendMessage(ChatColor.RED + "Usage: /" + s + " <nickName>");
            return true;
        }
        String nickName = args[0];
        if (!nickName.toLowerCase().matches("[a-zA-Z0-9_\\-]+")) {
            commandSender.sendMessage(ChatColor.RED + "Your nickname must be alphanumeric!");
            return true;
        }
        if (nickName.length() < 3) {
            commandSender.sendMessage(ChatColor.RED + "Your nickname cannot be shorter than 3 characters!");
            return true;
        }
        for (DonkeyUser survivalUser : Donkey.getInstance().getUserManager().getUsers().values()) {
            if (nickName.equalsIgnoreCase(survivalUser.getName()) && survivalUser.getName() != commandSender.getName()) {
                commandSender.sendMessage(ChatColor.RED + "Your nickname cannot be another player's name!");
                return true;
            }
            if (nickName.equalsIgnoreCase(survivalUser.getNickName())) {
                commandSender.sendMessage(ChatColor.RED + "That nickname is already taken!");
                return true;
            }
        }
        Player player = (Player) commandSender;
        DonkeyUser survivalUser = Donkey.getInstance().getUserManager().getUser(player.getUniqueId());
        if (args[0].equalsIgnoreCase("off")) {
            commandSender.sendMessage(ChatColor.GREEN + "You have took off your nickname!");
            survivalUser.setNickName(null);
            return true;
        }
        survivalUser.setNickName(nickName);
        commandSender.sendMessage(ChatColor.GREEN + "Your nickname has been set to " + ChatColor.YELLOW + nickName + '.');
        commandSender.sendMessage(ChatColor.GRAY.toString() + ChatColor.ITALIC + "If you ever want to remove your nickname, at any time use /" + s + " off.");

        return false;
    }
}

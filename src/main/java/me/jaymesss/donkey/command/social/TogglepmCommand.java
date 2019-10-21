package me.jaymesss.donkey.command.social;

import me.jaymesss.donkey.Donkey;
import me.jaymesss.donkey.user.DonkeyUser;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TogglepmCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player player = (Player) commandSender;
        DonkeyUser survivalUser = Donkey.getInstance().getUserManager().getUser(player.getUniqueId());
        if (survivalUser.isPrivateMessages()) {
            commandSender.sendMessage(ChatColor.YELLOW + "You can " + ChatColor.RED + "no longer" + ChatColor.YELLOW + " receive private messages!");
            survivalUser.setPrivateMessages(false);
            return true;
        } else {
            commandSender.sendMessage(ChatColor.YELLOW + "You can " + ChatColor.GREEN + "now" + ChatColor.YELLOW + " receive private messages!");
            survivalUser.setPrivateMessages(true);
            return true;
        }
    }
}

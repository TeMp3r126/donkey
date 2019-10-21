package me.jaymesss.donkey.command.social;

import me.jaymesss.donkey.Donkey;
import me.jaymesss.donkey.user.DonkeyUser;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SocialSpyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!commandSender.hasPermission("donkey.player.admin")) {
            commandSender.sendMessage(ChatColor.RED + "No permission.");
            return true;
        }
        Player player = (Player) commandSender;
        DonkeyUser survivalUser = Donkey.getInstance().getUserManager().getUser(player.getUniqueId());
        if (survivalUser.isSocialSpy()) {
            survivalUser.setSocialSpy(false);
            commandSender.sendMessage(ChatColor.RED + "You are no longer in social spy!");
            return true;
        } else {
            survivalUser.setSocialSpy(true);
            commandSender.sendMessage(ChatColor.GREEN + "You are now in social spy!");
            return true;
        }
    }
}

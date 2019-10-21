package me.jaymesss.donkey.command.social;

import me.jaymesss.donkey.Donkey;
import me.jaymesss.donkey.user.DonkeyUser;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleSoundsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player player = (Player) commandSender;
        DonkeyUser survivalUser = Donkey.getInstance().getUserManager().getUser(player.getUniqueId());
        if (survivalUser.isSounds()) {
            commandSender.sendMessage(ChatColor.YELLOW + "You can " + ChatColor.RED + "no longer" + ChatColor.YELLOW + " hear message noises!");
            survivalUser.setSounds(false);
            return true;
        } else {
            commandSender.sendMessage(ChatColor.YELLOW + "You can " + ChatColor.GREEN + "now" + ChatColor.YELLOW + " hear message noises!");
            survivalUser.setSounds(true);
            return true;
        }
    }
}

package me.jaymesss.donkey.command.social;

import me.jaymesss.donkey.Donkey;
import me.jaymesss.donkey.DonkeyAPI;
import me.jaymesss.donkey.user.DonkeyUser;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length < 2) {
            commandSender.sendMessage(ChatColor.RED + "Usage: /" + s + " <player> <message>");
            return true;
        }
        Player player = (Player) commandSender;
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
        String message = StringUtils.join(args, ' ', 1, args.length);
        if (offlinePlayer.isOnline()) {
            Player target = (Player) offlinePlayer;
            DonkeyUser survivalUser = Donkey.getInstance().getUserManager().getUser(player.getUniqueId());
            DonkeyUser survivalUser1 = Donkey.getInstance().getUserManager().getUser(target.getUniqueId());
            if (survivalUser.getIgnored().contains(target.getName())) {
                commandSender.sendMessage(ChatColor.RED + "You cannot message someone you have ignored!");
            }
            if (!survivalUser1.isPrivateMessages() || survivalUser1.getIgnored().contains(commandSender.getName())) {
                commandSender.sendMessage(ChatColor.RED + target.getName() + " has private messages toggled off.");
                return true;
            }
            DonkeyAPI.messageFormat(player, target, survivalUser, survivalUser1, message);
            return true;
        } else {
            commandSender.sendMessage(ChatColor.RED + args[0] + " is not online.");
            return true;
        }
    }
}

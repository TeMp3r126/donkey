package me.jaymesss.donkey.command.social;

import me.jaymesss.donkey.Donkey;
import me.jaymesss.donkey.DonkeyAPI;
import me.jaymesss.donkey.user.DonkeyUser;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReplyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length < 1) {
            commandSender.sendMessage(ChatColor.RED + "Usage: /" + s + " <message>");
            return true;
        }
        Player player = (Player) commandSender;
        DonkeyUser survivalUser = Donkey.getInstance().getUserManager().getUser(player.getUniqueId());
        if (survivalUser.getReply() == null) {
            commandSender.sendMessage(ChatColor.RED + "You have no one to reply to.");
            return true;
        } else {
            Player reply = Bukkit.getPlayer(survivalUser.getReply());
            DonkeyUser survivalUser1 = Donkey.getInstance().getUserManager().getUser(reply.getUniqueId());
            String message = StringUtils.join(args, ' ', 0, args.length);
            DonkeyAPI.messageFormat(player, reply, survivalUser, survivalUser1, message);
            return true;
        }
    }
}

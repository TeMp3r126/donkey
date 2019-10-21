package me.jaymesss.donkey.command;

import me.jaymesss.donkey.DonkeyAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class VoteCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        for (String string : DonkeyAPI.getStringList("messages.vote")) {
            commandSender.sendMessage(string);
        }
        return false;
    }
}

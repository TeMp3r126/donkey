package me.jaymesss.donkey.command;

import me.jaymesss.donkey.Donkey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LogoutCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        Donkey.getInstance().getTimerManager().getLogoutTimer().run(player);
        return false;
    }
}

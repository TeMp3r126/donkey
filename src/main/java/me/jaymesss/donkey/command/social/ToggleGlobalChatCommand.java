package me.jaymesss.donkey.command.social;

import me.jaymesss.donkey.Donkey;
import me.jaymesss.donkey.user.DonkeyUser;
import me.jaymesss.donkey.utils.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleGlobalChatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player player = (Player) commandSender;
        DonkeyUser survivalUser = Donkey.getInstance().getUserManager().getUser(player.getUniqueId());
        if (survivalUser.isShowGlobalChat()) {
            player.sendMessage(Color.translate("&eYou can &cno longer &esee &eglobal chat!"));
            survivalUser.setShowGlobalChat(false);
        } else {
            player.sendMessage(Color.translate("&eYou can &anow &esee &eglobal chat!"));
            survivalUser.setShowGlobalChat(true);

        }
        return false;
    }
}

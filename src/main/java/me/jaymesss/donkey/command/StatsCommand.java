package me.jaymesss.donkey.command;

import me.jaymesss.donkey.Donkey;
import me.jaymesss.donkey.DonkeyAPI;
import me.jaymesss.donkey.user.DonkeyUser;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length < 1) {
            Player player = (Player) commandSender;
            DonkeyUser donkeyUser = Donkey.getInstance().getUserManager().getUser(player.getUniqueId());
            commandSender.sendMessage(ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "---------------------------------------");
            commandSender.sendMessage(DonkeyAPI.getDisplayName(player) + "'s Statistics:");
            commandSender.sendMessage("");
            commandSender.sendMessage(DonkeyAPI.getColor2() + "Kills: " + ChatColor.WHITE + player.getStatistic(Statistic.PLAYER_KILLS));
            commandSender.sendMessage(DonkeyAPI.getColor2() + "Deaths: " + ChatColor.WHITE + player.getStatistic(Statistic.DEATHS));
            commandSender.sendMessage(DonkeyAPI.getColor2() + "Duels Won: " + ChatColor.WHITE + donkeyUser.getDuelWins());
            commandSender.sendMessage(DonkeyAPI.getColor2() + "Duels Lost: " + ChatColor.WHITE + donkeyUser.getDuelLosses());
            commandSender.sendMessage(ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "---------------------------------------");
            return true;
        }
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
        if (offlinePlayer.hasPlayedBefore()) {
            Player target = offlinePlayer.getPlayer();
            DonkeyUser donkeyUser = Donkey.getInstance().getUserManager().getUser(target.getUniqueId());
            commandSender.sendMessage(ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "---------------------------------------");
            commandSender.sendMessage(DonkeyAPI.getDisplayName(target) + "'s Offline Statistics:");
            commandSender.sendMessage("");
            commandSender.sendMessage(DonkeyAPI.getColor2() + "Duels Won: " + ChatColor.WHITE + donkeyUser.getDuelWins());
            commandSender.sendMessage(DonkeyAPI.getColor2() + "Duels Lost: " + ChatColor.WHITE + donkeyUser.getDuelLosses());
            commandSender.sendMessage("");
            commandSender.sendMessage(ChatColor.GRAY.toString() + ChatColor.ITALIC + "The player you are trying to lookup is offline, so we cannot get you their Kills & Deaths.");
            commandSender.sendMessage(ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "---------------------------------------");
            return true;
        }
        return false;
    }
}

package me.jaymesss.donkey.command;

import me.jaymesss.donkey.Donkey;
import me.jaymesss.donkey.DonkeyAPI;
import me.jaymesss.donkey.listener.DuelListener;
import me.jaymesss.donkey.user.DonkeyUser;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DuelCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length < 1) {
            commandSender.sendMessage(ChatColor.BLUE.toString() + ChatColor.STRIKETHROUGH + "-----------------------------------");
            commandSender.sendMessage(ChatColor.RED + "/duel <player>");
            commandSender.sendMessage(ChatColor.RED + "/duel accept <player>");
            commandSender.sendMessage(ChatColor.RED + "/duel deny <player>");
            commandSender.sendMessage(ChatColor.BLUE.toString() + ChatColor.STRIKETHROUGH + "-----------------------------------");
            return true;
        }
        Player player = (Player) commandSender;
        if (args[0].toLowerCase().equalsIgnoreCase("accept")) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
            if (offlinePlayer.isOnline()) {
                Player target = offlinePlayer.getPlayer();
                DonkeyUser donkeyUser = Donkey.getInstance().getUserManager().getUser(target.getUniqueId());
                donkeyUser.getDuelRequests().remove(commandSender.getName());
                DuelListener.startDuel(player, target);
                commandSender.sendMessage(ChatColor.GREEN + "You have accepted " + args[1] + "'s duel!");
                target.sendMessage(ChatColor.GREEN + commandSender.getName() + " has accepted your duel!");
            }
            return true;
        } else if (args[0].toLowerCase().equalsIgnoreCase("deny")) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
            if (offlinePlayer.isOnline()) {
                DonkeyUser donkeyUser = Donkey.getInstance().getUserManager().getUser(player.getUniqueId());
                if (!donkeyUser.getDuelRequests().contains(args[1])) {
                    commandSender.sendMessage(ChatColor.RED + "You do not have a duel request from " + args[1] + '.');
                    return true;
                }
                Player target = offlinePlayer.getPlayer();
                target.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + commandSender.getName() + " has denied your duel request.");
                commandSender.sendMessage(ChatColor.GREEN + "You have denied " + args[1] + "'s duel request!");
                return true;
            } else {
                commandSender.sendMessage(ChatColor.RED + "Player not found.");
            }
        }
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
        if (offlinePlayer.isOnline()) {
            Player target = offlinePlayer.getPlayer();
            DonkeyUser donkeyUser = Donkey.getInstance().getUserManager().getUser(target.getUniqueId());
            if (donkeyUser.getDuelRequests().contains(commandSender.getName())) {
                commandSender.sendMessage(ChatColor.RED + "You have already sent " + args[0] + " a duel request.");
                return true;
            }
            commandSender.sendMessage(ChatColor.GREEN + "Your duel request to " + args[0] + " has been sent!");
            target.sendMessage(DonkeyAPI.getDisplayName(player) + ChatColor.YELLOW + " has sent you a duel request! Type " + ChatColor.LIGHT_PURPLE + "/duel accept " + commandSender.getName() + ChatColor.YELLOW + " to accept it!");
            donkeyUser.getDuelRequests().add(commandSender.getName());
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!target.getKiller().getName().equalsIgnoreCase(commandSender.getName())) {
                        commandSender.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "Your duel request to " + args[0] + " has expired.");
                        target.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + commandSender.getName() + "'s duel request to you has expired.");
                        donkeyUser.getDuelRequests().remove(commandSender.getName());
                    }
                }
            }.runTaskLater(Donkey.getInstance(), 1800L);
        } else {
            commandSender.sendMessage(ChatColor.RED + "Player not found.");
            return true;
        }
        return true;
    }
}

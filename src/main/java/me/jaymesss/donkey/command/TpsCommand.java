package me.jaymesss.donkey.command;

import me.jaymesss.donkey.DonkeyAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TpsCommand implements CommandExecutor {
    private boolean spoofed;
    private double spoofedTps;

    public TpsCommand() {
        this.spoofedTps = 0.0;
        this.spoofed = false;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length < 1) {
            if (spoofed == true) {
                commandSender.sendMessage(ChatColor.GOLD + "Server performance: " + ChatColor.GREEN + spoofedTps + ChatColor.GOLD + "/20.0 [" + ChatColor.GREEN + "||||||||||||||||||||" + ChatColor.GOLD + ']');
                return true;
            } else {
                final double tps2 = Bukkit.spigot().getTPS()[1];
                final StringBuilder tpsBuilder = new StringBuilder();
                tpsBuilder.append(ChatColor.GOLD).append("Server performance: ");
                tpsBuilder.append(DonkeyAPI.formatBasicTps(tps2)).append(ChatColor.GOLD).append("/20.0");
                tpsBuilder.append(" [").append((tps2 > 18.0) ? ChatColor.GREEN : ((tps2 > 16.0) ? ChatColor.YELLOW : ChatColor.RED));
                int j;
                for (j = 0; j < Math.round(tps2); ++j) {
                    tpsBuilder.append("|");
                }
                tpsBuilder.append(ChatColor.DARK_GRAY);
                while (j < 20) {
                    tpsBuilder.append("|");
                    ++j;
                }
                tpsBuilder.append(ChatColor.GOLD).append("]");
                commandSender.sendMessage(tpsBuilder.toString());
                return true;
            }
        }
        if (args[0].equalsIgnoreCase("set") && commandSender.hasPermission("creative.admin")) {
            commandSender.sendMessage(ChatColor.GREEN + "You have set the tps to " + args[1]);
            spoofedTps = Double.parseDouble(args[1]);
            spoofed = true;
            return true;
        } else {
            if (spoofed == true) {
                commandSender.sendMessage(ChatColor.GOLD + "Server performance: " + ChatColor.GREEN + spoofedTps + ChatColor.GOLD + "/20.0 [" + ChatColor.GREEN + "||||||||||||||||||||" + ChatColor.GOLD + ']');
                return true;
            } else {
                final double tps2 = Bukkit.spigot().getTPS()[1];
                final StringBuilder tpsBuilder = new StringBuilder();
                tpsBuilder.append(ChatColor.GOLD).append("Server performance: ");
                tpsBuilder.append(DonkeyAPI.formatBasicTps(tps2)).append(ChatColor.GOLD).append("/20.0");
                tpsBuilder.append(" [").append((tps2 > 18.0) ? ChatColor.GREEN : ((tps2 > 16.0) ? ChatColor.YELLOW : ChatColor.RED));
                int j;
                for (j = 0; j < Math.round(tps2); ++j) {
                    tpsBuilder.append("|");
                }
                tpsBuilder.append(ChatColor.DARK_GRAY);
                while (j < 20) {
                    tpsBuilder.append("|");
                    ++j;
                }
                tpsBuilder.append(ChatColor.GOLD).append("]");
                commandSender.sendMessage(tpsBuilder.toString());
                return true;
            }
        }
    }


}

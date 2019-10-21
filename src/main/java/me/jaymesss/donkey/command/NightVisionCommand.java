package me.jaymesss.donkey.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class NightVisionCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        if (!player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
            player.sendMessage(ChatColor.YELLOW + "You have " + ChatColor.GREEN + "enabled" + ChatColor.YELLOW + " night vision!");
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 1, true, false));
        } else {
            player.sendMessage(ChatColor.YELLOW + "You have " + ChatColor.RED + "disabled" + ChatColor.YELLOW + " night vision!");
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
        }
        return false;
    }
}

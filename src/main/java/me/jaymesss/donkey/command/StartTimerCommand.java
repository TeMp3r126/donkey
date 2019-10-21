package me.jaymesss.donkey.command;

import me.jaymesss.donkey.Donkey;
import me.jaymesss.donkey.DonkeyAPI;
import me.jaymesss.donkey.timer.CustomTimer;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.regex.Pattern;

public class StartTimerCommand implements CommandExecutor {
    private static final Pattern WHITESPACE_TRIMMER;

    static {
        WHITESPACE_TRIMMER = Pattern.compile("\\s");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!commandSender.hasPermission("donkey.player.admin")) {
            commandSender.sendMessage(ChatColor.RED + "No permission.");
        }
        if (args.length < 2) {
            commandSender.sendMessage(ChatColor.RED + "Usage: /starttimer <timerName> <remaining>");
            return true;
        }
        long duration = DonkeyAPI.parse(args[1]);
        if (duration <= 0) {
            commandSender.sendMessage(ChatColor.RED + "Invalid duration, use the correct format 6h6m6s.");
            return true;
        }
        String timerName = args[0];
        CustomTimer customTimer = null;
        for (CustomTimer customTimers : Donkey.getInstance().getTimerManager().getCustomTimers()) {
            if (WHITESPACE_TRIMMER.matcher(customTimers.getName()).replaceAll("").equalsIgnoreCase(timerName)) {
                customTimer = customTimers;
                break;
            }
        }
        if (customTimer == null) {
            commandSender.sendMessage(ChatColor.RED + "The timer " + args[0] + " was not found.");
            return true;
        }
        customTimer.start(duration);
        DonkeyAPI.playerPerformFormat(commandSender, "Started timer " + args[0], "donkey.player.admin");
        commandSender.sendMessage(ChatColor.GREEN + "You started timer " + customTimer.getDisplayName() + ChatColor.GREEN + " for " + ChatColor.BOLD + DurationFormatUtils.formatDurationWords(duration, true, true) + ChatColor.GREEN + '.');
        return false;
    }
}

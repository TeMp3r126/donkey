package me.jaymesss.donkey.sidebar;

import com.bizarrealex.aether.scoreboard.Board;
import com.bizarrealex.aether.scoreboard.BoardAdapter;
import com.bizarrealex.aether.scoreboard.cooldown.BoardCooldown;
import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import me.jaymesss.donkey.Donkey;
import me.jaymesss.donkey.DonkeyAPI;
import me.jaymesss.donkey.timer.CustomTimer;
import me.jaymesss.donkey.timer.Timer;
import me.jaymesss.donkey.timer.type.EnderPearlTimer;
import me.jaymesss.donkey.timer.type.SpawnTimer;
import me.jaymesss.donkey.utils.Color;
import me.jaymesss.donkey.utils.DurationFormatter;
import me.jaymesss.donkey.utils.JavaUtils;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class DonkeySidebar implements BoardAdapter, Listener {


//    public String getVanish(Player player) {
//        world.yeehaw.essentials.Essentials essentials = world.yeehaw.essentials.Essentials.getInstance();
//        if (essentials.getStaffModeListener().isVanished(player)) {
//            return ChatColor.GREEN + "Enabled";
//        } else {
//            return ChatColor.RED + "Disabled";
//        }
//    }
//
//    public String getChat(Player player) {
//        world.yeehaw.essentials.Essentials essentials = world.yeehaw.essentials.Essentials.getInstance();
//        if (essentials.getStaffModeListener().isStaffChatActive(player)) {
//            return ChatColor.AQUA + "Staff";
//        } else {
//            return ChatColor.WHITE + "Public";
//        }
//    }

    @Override
    public String getTitle(Player player) {
        return ChatColor.BLUE.toString() + ChatColor.BOLD + "VyriseMC";
    }

    @Override
    public List<String> getScoreboard(Player player, Board board, Set<BoardCooldown> cooldowns) {
        List<String> strings = new ArrayList<>();
        strings.add(ChatColor.translateAlternateColorCodes('&', "&7&m----------------------"));
        strings.add(Color.translate("&9&lPersonal"));
        strings.add(Color.translate(" &f» &7User: " + DonkeyAPI.getDisplayName(player)));
        RegisteredServiceProvider<Chat> chatProvider = Donkey.getInstance().getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);

        strings.add(Color.translate(" &f» &7Rank: ") + chatProvider.getProvider().getPrimaryGroup(player));
        Essentials ess = (Essentials) Donkey.getInstance().getServer().getPluginManager().getPlugin("Essentials");
        User user = ess.getUser(player.getUniqueId());
        strings.add(Color.translate(" &f» &7Balance: &a$&f") + user.getMoney());
//        if (player.hasPermission("utilities.player.staff")) {
//            strings.add(Color.translate(" &f» &7Vanish: ") + getVanish(player));
//            strings.add(Color.translate(" &f» &7Chat: ") + getChat(player));
//        }
        FPlayer factionPlayer = FPlayers.getInstance().getByPlayer(player);
        Faction faction = factionPlayer.getFaction();
        if (!faction.isNone()) {
            strings.add("");
            strings.add(Color.translate("&9&lFaction Info"));
            strings.add(Color.translate(" &f» &7Faction: &a" + faction.getTag()));
            String power = JavaUtils.format(faction.getPower(), 0);
            strings.add(Color.translate(" &f» &7Power: &f" + power));
            strings.add(Color.translate(" &f» &7Members: &f" + faction.getOnlinePlayers().size() + '/' + faction.getFPlayers().size()));
        }
        final Collection<Timer> timers = Donkey.getInstance().getTimerManager().getTimers();
        for (final Timer timer : timers) {
            if (timer instanceof EnderPearlTimer) {
                final EnderPearlTimer playerTimer = (EnderPearlTimer) timer;
                final long remaining4 = playerTimer.getRemaining(player);
                if (remaining4 <= 0L) {
                    continue;
                }
                String timerName = playerTimer.getName();
                if (timerName.length() > 14) {
                    timerName = timerName.substring(0, timerName.length());
                }
                strings.add(playerTimer.getScoreboardPrefix() + timerName + ChatColor.GRAY + ": " + ChatColor.RED + DurationFormatter.getRemaining(remaining4, true));
            }
            if (timer instanceof SpawnTimer) {
                final SpawnTimer playerTimer = (SpawnTimer) timer;
                final long remaining4 = playerTimer.getRemaining(player);
                if (remaining4 <= 0L) {
                    continue;
                }
                String timerName = playerTimer.getName();
                if (timerName.length() > 14) {
                    timerName = timerName.substring(0, timerName.length());
                }
                strings.add(playerTimer.getScoreboardPrefix() + timerName + ChatColor.GRAY + ": " + ChatColor.RED + DurationFormatter.getRemaining(remaining4, true));
            }
        }
        Collection<CustomTimer> customTimers = Donkey.getInstance().getTimerManager().getCustomTimers();
        for (CustomTimer timer : customTimers) {
            if (timer.getCustomTimerRunnable() == null) continue;
            long remaining = timer.getRemaining();
            if (remaining <= 0) continue;
            String timerName = timer.getScoreboardPrefix();
            if (timerName.length() > 14) timerName = timerName.substring(0, timerName.length());

            strings.add("" + timerName + ChatColor.GRAY + ": " + ChatColor.RED + DurationFormatter.getRemaining(remaining, true).replace('_', ' '));
        }
        strings.add(ChatColor.translateAlternateColorCodes('&', "&7&m----------------------"));


        if (strings.size() == 2) {
            return null;
        }

        return strings;
    }
}

package me.jaymesss.donkey.listener;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import me.jaymesss.donkey.Donkey;
import me.jaymesss.donkey.DonkeyAPI;
import me.jaymesss.donkey.user.DonkeyUser;
import me.jaymesss.donkey.utils.Color;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.RegisteredServiceProvider;

public class ChatListener implements Listener {

    private String getPlayerName(Player player) {
        DonkeyUser survivalUser = Donkey.getInstance().getUserManager().getUser(player.getUniqueId());
        if (survivalUser.getNickName() != null) {
            return survivalUser.getNickName();
        } else {
            return player.getName();
        }
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);
        Player player = event.getPlayer();
        String message = event.getMessage();
        ConsoleCommandSender console = Bukkit.getConsoleSender();
        console.sendMessage(player.getName() + ": " + message);
        if (GgListener.isGgTrain()) {
            DonkeyUser survivalUser = Donkey.getInstance().getUserManager().getUser(player.getUniqueId());
            if (!survivalUser.isGged() && event.getMessage().equalsIgnoreCase("gg")) {
                Bukkit.getServer().dispatchCommand(console, DonkeyAPI.getString("reward-command".replace("{player}", player.getName())));
                survivalUser.setGged(true);
            }
        }

        for (Player recipient : event.getRecipients()) {
//            world.yeehaw.essentials.Essentials essentials = world.yeehaw.essentials.Essentials.getInstance();
//
//            if (!essentials.getStaffModeListener().isStaffChatActive(player)) {
                DonkeyUser donkeyUser = Donkey.getInstance().getUserManager().getUser(recipient.getUniqueId());
                if (donkeyUser.isShowGlobalChat()) {
                    recipient.sendMessage(this.getFormattedMessage(player, message, recipient));
//                }

            }
        }
    }

    private String getFormattedMessage(Player player, String message, CommandSender viewers) {
        Player viewer = (Player) viewers;
        FPlayer vPlayer = FPlayers.getInstance().getByPlayer(viewer);
        FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);
        Faction faction = fPlayer.getFaction();

        RegisteredServiceProvider<Chat> chatProvider = Donkey.getInstance().getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        String prefix = Color.translate(chatProvider.getProvider().getPlayerPrefix(player));

//        String name = "";
//        String tag = "";
//        if (!Essentials.getInstance().playerdata.getString(player.getName() + ".tag").equals("None")) {
//            name = Essentials.getInstance().playerdata.getString(player.getName() + ".tag").replaceAll("&", "§");
//            tag = " " + Essentials.getInstance().tags.getString(name + ".name").replaceAll("&", "§");
//        }
//        String colorName = "";
//        String color = "";
//        if (!Essentials.getInstance().playerdata.getString(player.getName() + ".chatcolor").equals("None")) {
//            colorName = Essentials.getInstance().playerdata.getString(player.getName() + ".chatcolor").replaceAll("&", "§");
//            color = Essentials.getInstance().colors.getString(colorName + ".name").replaceAll("&", "§");
//        }
        if (!faction.isNone()) {
            return ChatColor.GOLD + "[" + DonkeyAPI.getRelationColor(vPlayer, faction) + faction.getTag() + ChatColor.GOLD + "] " + ChatColor.WHITE + prefix + getPlayerName(player) + ChatColor.WHITE + ": " + message;
        } else {
            return prefix + getPlayerName(player) + ChatColor.WHITE + ": " + message;
        }
    }
}

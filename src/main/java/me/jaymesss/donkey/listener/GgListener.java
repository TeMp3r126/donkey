package me.jaymesss.donkey.listener;

import lombok.Getter;
import me.jaymesss.donkey.Donkey;
import me.jaymesss.donkey.user.DonkeyUser;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class GgListener implements Listener {

    @Getter
    private static boolean ggTrain = false;

    public static void startGgTrain() {
        ggTrain = true;
        for (Player player : Bukkit.getOnlinePlayers()) {
            DonkeyUser survivalUser = Donkey.getInstance().getUserManager().getUser(player.getUniqueId());
            survivalUser.setGged(false);
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.broadcastMessage(ChatColor.RED.toString() + ChatColor.BOLD + "The GG train is over!");
                ggTrain = false;
            }
        }.runTaskLater(Donkey.getInstance(), 1200L);
    }


}

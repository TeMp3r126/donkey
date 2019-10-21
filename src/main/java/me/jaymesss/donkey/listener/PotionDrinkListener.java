package me.jaymesss.donkey.listener;

import me.jaymesss.donkey.Donkey;
import me.jaymesss.donkey.timer.PlayerTimer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;

public class PotionDrinkListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            Player player = event.getPlayer();
            if (player.getItemInHand() != null && player.getItemInHand().getType().equals(Material.POTION)) {
                Potion potion = Potion.fromItemStack(player.getItemInHand());
                if (!potion.isSplash()) {
                    for (PotionEffect potionEffect : potion.getEffects()) {
                        PlayerTimer playerTimer = Donkey.getInstance().getTimerManager().getCombatTimer();
                        long remaining = playerTimer.getRemaining(player);
                        if (!(remaining > 0L)) {
                            player.addPotionEffect(potionEffect, true);
                            player.playSound(player.getLocation(), Sound.DRINK, 1.0F, 1.0F);
                            player.sendMessage(ChatColor.GREEN + "Since you weren't combat tagged, we cut down the time for you to use your potion!");
                            player.setItemInHand(null);
                            event.setCancelled(true);
                        }
                    }
                }
            }
        }
    }
}

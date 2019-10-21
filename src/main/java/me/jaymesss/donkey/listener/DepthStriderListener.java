package me.jaymesss.donkey.listener;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;

public class DepthStriderListener implements Listener {

    @EventHandler
    public void onItemEnchant(EnchantItemEvent event) {
        if (event.getEnchantsToAdd().containsValue(Enchantment.DEPTH_STRIDER)) {
            event.setCancelled(true);
            event.getEnchanter().sendMessage(ChatColor.RED + "You would of enchanted depth strider, but as of now it is disabled.");
        }
    }
}

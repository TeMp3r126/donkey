package me.jaymesss.donkey.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

public class AntiCraftListener implements Listener {

    @EventHandler
    public void onItemCraft(PrepareItemCraftEvent event) {
        Material itemType = event.getRecipe().getResult().getType();
        Byte itemData = event.getRecipe().getResult().getData().getData();
        if (itemType == Material.HOPPER) {
            event.getInventory().setResult(new ItemStack(Material.AIR));
        }
        if (itemType == Material.GOLDEN_APPLE) {
            event.getInventory().setResult(new ItemStack(Material.AIR));
        }
        if (itemType == Material.BOAT) {
            event.getInventory().setResult(new ItemStack(Material.AIR));
        }
    }
}

package me.jaymesss.donkey.timer.type;

import com.google.common.base.Predicate;
import me.jaymesss.donkey.timer.PlayerTimer;
import me.jaymesss.donkey.utils.DurationFormatter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.concurrent.TimeUnit;

public class EnderPearlTimer extends PlayerTimer implements Listener {

    public EnderPearlTimer() {
        super(ChatColor.YELLOW.toString() + ChatColor.BOLD + "Enderpearl", TimeUnit.SECONDS.toMillis(15L));
    }

    public String getScoreboardPrefix() {
        return ChatColor.DARK_AQUA.toString();
    }

    public void refund(Player player) {
        player.getInventory().addItem(new ItemStack[]{new ItemStack(Material.ENDER_PEARL, 1)});
        EnderPearlTimer playerTimer = null;
        setCooldown(player, player.getUniqueId(), 5L, true, null);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlayerConsume(ProjectileLaunchEvent event) {
        if (event.getEntity() instanceof EnderPearl) {
            final Player player = (Player) event.getEntity().getShooter();
            if (!this.setCooldown(player, player.getUniqueId(), this.defaultCooldown, false, (Predicate<Long>) new Predicate<Long>() {
                public boolean apply(@Nullable final Long value) {
                    return false;
                }

            })) {
                event.setCancelled(true);
                player.getInventory().addItem(new ItemStack[]{new ItemStack(Material.ENDER_PEARL, 1)});
                player.sendMessage(ChatColor.RED + "You are cannot use this for another " + DurationFormatter.getRemaining(this.getRemaining(player), true, false) + ChatColor.RED + '.');
            }
        }
    }
}

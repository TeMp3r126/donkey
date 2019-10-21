package me.jaymesss.donkey.timer.type;

import com.google.common.base.Optional;
import me.jaymesss.donkey.timer.PlayerTimer;
import me.jaymesss.donkey.timer.event.TimerClearEvent;
import me.jaymesss.donkey.timer.event.TimerStartEvent;
import me.jaymesss.donkey.utils.BukkitUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class CombatTimer extends PlayerTimer implements Listener {

    public CombatTimer() {
        super("Combat Tag", TimeUnit.SECONDS.toMillis(30L));
    }

    public String getScoreboardPrefix() {
        return String.valueOf(ChatColor.RED.toString()) + ChatColor.BOLD;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onTimerStop(final TimerClearEvent event) {
        if (event.getTimer() == this) {
            final Optional<UUID> optionalUserUUID = event.getUserUUID();
            if (optionalUserUUID.isPresent()) {
                this.onExpire((UUID) optionalUserUUID.get());
            }
        }
    }

    @Override
    public void onExpire(final UUID userUUID) {
        final Player player = Bukkit.getPlayer(userUUID);
        if (player != null) {
        }
    }


    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onEntityDamageByEntity(final EntityDamageByEntityEvent event) {
        final Player attacker = BukkitUtils.getFinalAttacker((EntityDamageEvent) event, true);
        final Entity entity;
        if (attacker != null && (entity = event.getEntity()) instanceof Player) {
            final Player attacked = (Player) entity;
            this.setCooldown(attacker, attacker.getUniqueId(), this.defaultCooldown, false);
            this.setCooldown(attacked, attacked.getUniqueId(), this.defaultCooldown, false);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onCommandPreProcess(PlayerCommandPreprocessEvent event) {
        if (getRemaining(event.getPlayer()) > 0L) {
            if (event.getMessage().contains("home") || event.getMessage().contains("tpa") || event.getMessage().contains("spawn")) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatColor.YELLOW + "You cannot do this while combat tagged!");
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onTimerStart(TimerStartEvent event) {
        if (event.getTimer() == this) {
            final Optional<Player> optional = event.getPlayer();
            if (optional.isPresent()) {
                final Player player = (Player) optional.get();
                player.sendMessage(ChatColor.YELLOW + "You have been combat-taggged for " + ChatColor.RED + "30" + ChatColor.YELLOW + " seconds!");
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlayerRespawn(final PlayerRespawnEvent event) {
        this.clearCooldown(event.getPlayer().getUniqueId());
    }


}

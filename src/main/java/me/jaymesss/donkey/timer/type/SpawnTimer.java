package me.jaymesss.donkey.timer.type;

import me.jaymesss.donkey.timer.PlayerTimer;
import me.jaymesss.donkey.timer.TimerCooldown;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class SpawnTimer extends PlayerTimer implements Listener {
    private final Map<UUID, Location> destinationMap;

    public SpawnTimer() {
        super("Spawn", TimeUnit.SECONDS.toMillis(5L), false);
        this.destinationMap = new HashMap<UUID, Location>();
    }

    public Location getDestination(final Player player) {
        return this.destinationMap.get(player.getUniqueId());
    }

    public String getScoreboardPrefix() {
        return String.valueOf(ChatColor.GREEN.toString()) + ChatColor.BOLD;
    }

    @Override
    public TimerCooldown clearCooldown(final UUID uuid) {
        final TimerCooldown runnable = super.clearCooldown(uuid);
        if (runnable != null) {
            this.destinationMap.remove(uuid);
            return runnable;
        }
        return null;
    }


    public boolean teleport(final Player player, final Location location, final long millis, final String warmupMessage, final PlayerTeleportEvent.TeleportCause cause) {
        this.cancelTeleport(player, null);
        boolean result;
        if (millis <= 0L) {
            result = player.teleport(location, cause);
            this.clearCooldown(player.getUniqueId());
        } else {
            final UUID uuid = player.getUniqueId();
            player.sendMessage(warmupMessage);
            this.destinationMap.put(uuid, location.clone());
            this.setCooldown(player, uuid, millis, true, null);
            result = true;
        }
        return result;
    }

    public void cancelTeleport(final Player player, final String reason) {
        final UUID uuid = player.getUniqueId();
        if (this.getRemaining(uuid) > 0L) {
            this.clearCooldown(uuid);
            if (reason != null && !reason.isEmpty()) {
                player.sendMessage(reason);
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlayerMove(final PlayerMoveEvent event) {
        final Location from = event.getFrom();
        final Location to = event.getTo();
        if (from.getBlockX() == to.getBlockX() && from.getBlockY() == to.getBlockY() && from.getBlockZ() == to.getBlockZ()) {
            return;
        }
        this.cancelTeleport(event.getPlayer(), ChatColor.YELLOW + "You moved a block, therefore cancelling your teleport.");
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlayerDamage(final EntityDamageEvent event) {
        final Entity entity = event.getEntity();
        if (entity instanceof Player) {
            this.cancelTeleport((Player) entity, ChatColor.YELLOW + "You took damage, therefore cancelling your teleport.");
        }
    }

    @Override
    public void onExpire(final UUID userUUID) {
        final Player player = Bukkit.getPlayer(userUUID);
        if (player == null) {
            return;
        }
        final Location destination = this.destinationMap.remove(userUUID);
        if (destination != null) {
            destination.getChunk();
            player.teleport(destination, PlayerTeleportEvent.TeleportCause.COMMAND);
        }
    }
}

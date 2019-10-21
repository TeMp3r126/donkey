package me.jaymesss.donkey.timer;

import me.jaymesss.donkey.Donkey;
import me.jaymesss.donkey.timer.event.TimerExpireEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;

public class TimerCooldown {
    private final Timer timer;
    private final UUID owner;
    private BukkitTask eventNotificationTask;
    private long expiryMillis;
    private long pauseMillis;

    public TimerCooldown(final Timer timer, final long duration) {
        this.owner = null;
        this.timer = timer;
        this.setRemaining(duration);
    }

    public TimerCooldown(final Timer timer, final UUID playerUUID, final long duration) {
        this.timer = timer;
        this.owner = playerUUID;
        this.setRemaining(duration);
    }

    public Timer getTimer() {
        return this.timer;
    }

    public long getRemaining() {
        return this.getRemaining(false);
    }

    public void setRemaining(final long remaining) {
        this.setExpiryMillis(remaining);
    }

    public long getRemaining(final boolean ignorePaused) {
        if (!ignorePaused && this.pauseMillis != 0L) {
            return this.pauseMillis;
        }
        return this.expiryMillis - System.currentTimeMillis();
    }

    public long getExpiryMillis() {
        return this.expiryMillis;
    }

    private void setExpiryMillis(final long remainingMillis) {
        final long expiryMillis = System.currentTimeMillis() + remainingMillis;
        if (expiryMillis == this.expiryMillis) {
            return;
        }
        this.expiryMillis = expiryMillis;
        if (remainingMillis > 0L) {
            if (this.eventNotificationTask != null) {
                this.eventNotificationTask.cancel();
            }
            this.eventNotificationTask = new BukkitRunnable() {
                public void run() {
                    Bukkit.getPluginManager().callEvent((Event) new TimerExpireEvent(TimerCooldown.this.owner, TimerCooldown.this.timer));
                }
            }.runTaskLater((Plugin) Donkey.getInstance(), remainingMillis / 50L);
        }
    }

    public long getPauseMillis() {
        return this.pauseMillis;
    }

    public void setPauseMillis(final long pauseMillis) {
        this.pauseMillis = pauseMillis;
    }

    public boolean isPaused() {
        return this.pauseMillis != 0L;
    }

    public void setPaused(final boolean paused) {
        if (paused != this.isPaused()) {
            if (paused) {
                this.pauseMillis = this.getRemaining(true);
                this.cancel();
            } else {
                this.setExpiryMillis(this.pauseMillis);
                this.pauseMillis = 0L;
            }
        }
    }

    public boolean cancel() {
        if (this.eventNotificationTask != null) {
            this.eventNotificationTask.cancel();
            return true;
        }
        return false;
    }
}

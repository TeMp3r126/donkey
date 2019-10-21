package me.jaymesss.donkey.timer;

import me.jaymesss.donkey.Donkey;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class CustomTimer extends GlobalTimer {
    private String prefix;
    private CustomTimerRunnable customTimerRunnable;

    public CustomTimer(final String name, final String displayName) {
        super(name, 10L);
        this.prefix = ChatColor.translateAlternateColorCodes('&', displayName);
    }

    static /* synthetic */ void access$1(final CustomTimer customTimer, final CustomTimerRunnable customTimerRunnable) {
        customTimer.customTimerRunnable = customTimerRunnable;
    }

    @Override
    public String getScoreboardPrefix() {
        return this.prefix.replace('_', ' ');
    }

    public boolean cancel() {
        if (this.customTimerRunnable != null) {
            this.customTimerRunnable.cancel();
            this.customTimerRunnable = null;
            return true;
        }
        return false;
    }

    public void start(final long millis) {
        (this.customTimerRunnable = new CustomTimerRunnable(this, millis)).runTaskLater((Plugin) Donkey.getInstance(), millis / 50L);
    }

    public CustomTimerRunnable getCustomTimerRunnable() {
        return this.customTimerRunnable;
    }

    public static class CustomTimerRunnable extends BukkitRunnable {
        private CustomTimer customTimer;
        private long startMillis;
        private long endMillis;

        public CustomTimerRunnable(final CustomTimer customTimer, final long duration) {
            this.customTimer = customTimer;
            this.startMillis = System.currentTimeMillis();
            customTimer.setRemaining(duration, true);
            this.endMillis = this.startMillis + duration;
        }

        public long getRemaining() {
            return this.endMillis - System.currentTimeMillis();
        }

        public void run() {
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&7 "));
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&cThe " + this.customTimer.prefix + " &chas ended.").replace('_', ' '));
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&7 "));
            this.cancel();
            CustomTimer.access$1(this.customTimer, null);
        }
    }
}

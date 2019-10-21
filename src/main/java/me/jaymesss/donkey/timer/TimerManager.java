package me.jaymesss.donkey.timer;

import lombok.Getter;
import me.jaymesss.donkey.Donkey;
import me.jaymesss.donkey.timer.type.CombatTimer;
import me.jaymesss.donkey.timer.type.EnderPearlTimer;
import me.jaymesss.donkey.timer.type.LogoutTimer;
import me.jaymesss.donkey.timer.type.SpawnTimer;
import me.jaymesss.donkey.utils.Config;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;


public class TimerManager implements Listener {
    @Getter
    public CombatTimer combatTimer;
    @Getter
    public LogoutTimer logoutTimer;
    @Getter
    public EnderPearlTimer enderPearlTimer;
    @Getter
    public SpawnTimer spawnTimer;
    private Set<Timer> timers;
    private Collection<CustomTimer> customTimers;
    private JavaPlugin plugin;
    private Config config;

    public TimerManager(final Donkey plugin) {
        this.timers = new LinkedHashSet<Timer>();
        this.customTimers = new LinkedHashSet<CustomTimer>();
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents((Listener) this, (Plugin) plugin);
        this.registerTimer(this.enderPearlTimer = new EnderPearlTimer());
        this.registerTimer(this.spawnTimer = new SpawnTimer());
        this.registerTimer(this.logoutTimer = new LogoutTimer());
        this.registerTimer(this.combatTimer = new CombatTimer());
        this.reloadTimerData();
    }

    public void registerTimer(final Timer timer) {
        this.timers.add(timer);
        if (timer instanceof Listener) {
            this.plugin.getServer().getPluginManager().registerEvents((Listener) timer, (Plugin) this.plugin);
        }
    }

    public void registerCustomTimer(final CustomTimer timer) {
        this.customTimers.add(timer);
    }

    public void unregisterTimer(final Timer timer) {
        this.timers.remove(timer);
    }

    public void reloadTimerData() {
        this.config = new Config(this.plugin, "timers");
        for (final Timer timer : this.timers) {
            timer.load(this.config);
        }
    }

    public void saveTimerData() {
        for (final Timer timer : this.timers) {
            timer.onDisable(this.config);
        }
        this.config.save();
    }

    public Set<Timer> getTimers() {
        return this.timers;
    }

    public void setTimers(final Set<Timer> timers) {
        this.timers = timers;
    }

    public Collection<CustomTimer> getCustomTimers() {
        return this.customTimers;
    }

    public void setCustomTimers(final Collection<CustomTimer> customTimers) {
        this.customTimers = customTimers;
    }

    public JavaPlugin getPlugin() {
        return this.plugin;
    }

    public void setPlugin(final JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public Config getConfig() {
        return this.config;
    }

    public void setConfig(final Config config) {
        this.config = config;
    }

    public void setCombatTimer(final CombatTimer combatTimer) {
        this.combatTimer = combatTimer;
    }

    public void setLogoutTimer(final LogoutTimer logoutTimer) {
        this.logoutTimer = logoutTimer;
    }

    public void setEnderPearlTimer(final EnderPearlTimer enderPearlTimer) {
        this.enderPearlTimer = enderPearlTimer;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TimerManager)) {
            return false;
        }
        final TimerManager other = (TimerManager) o;
        if (!other.canEqual(this)) {
            return false;
        }
        final Object this$combatTimer = this.getCombatTimer();
        final Object other$combatTimer = other.getCombatTimer();
        Label_0065:
        {
            if (this$combatTimer == null) {
                if (other$combatTimer == null) {
                    break Label_0065;
                }
            } else if (this$combatTimer.equals(other$combatTimer)) {
                break Label_0065;
            }
            return false;
        }
        final Object this$logoutTimer = this.getLogoutTimer();
        final Object other$logoutTimer = other.getLogoutTimer();
        Label_0102:
        {
            if (this$logoutTimer == null) {
                if (other$logoutTimer == null) {
                    break Label_0102;
                }
            } else if (this$logoutTimer.equals(other$logoutTimer)) {
                break Label_0102;
            }
            return false;
        }
        final Object this$enderPearlTimer = this.getEnderPearlTimer();
        final Object other$enderPearlTimer = other.getEnderPearlTimer();
        Label_0139:
        {
            if (this$enderPearlTimer == null) {
                if (other$enderPearlTimer == null) {
                    break Label_0139;
                }
            } else if (this$enderPearlTimer.equals(other$enderPearlTimer)) {
                break Label_0139;
            }
            return false;
        }
        final Object this$timers = this.getTimers();
        final Object other$timers = other.getTimers();
        Label_0472:
        {
            if (this$timers == null) {
                if (other$timers == null) {
                    break Label_0472;
                }
            } else if (this$timers.equals(other$timers)) {
                break Label_0472;
            }
            return false;
        }
        final Object this$customTimers = this.getCustomTimers();
        final Object other$customTimers = other.getCustomTimers();
        Label_0509:
        {
            if (this$customTimers == null) {
                if (other$customTimers == null) {
                    break Label_0509;
                }
            } else if (this$customTimers.equals(other$customTimers)) {
                break Label_0509;
            }
            return false;
        }
        final Object this$plugin = this.getPlugin();
        final Object other$plugin = other.getPlugin();
        Label_0546:
        {
            if (this$plugin == null) {
                if (other$plugin == null) {
                    break Label_0546;
                }
            } else if (this$plugin.equals(other$plugin)) {
                break Label_0546;
            }
            return false;
        }
        final Object this$config = this.getConfig();
        final Object other$config = other.getConfig();
        if (this$config == null) {
            if (other$config == null) {
                return true;
            }
        } else if (this$config.equals(other$config)) {
            return true;
        }
        return false;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof TimerManager;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $combatTimer = this.getCombatTimer();
        result = result * 59 + (($combatTimer == null) ? 43 : $combatTimer.hashCode());
        final Object $logoutTimer = this.getLogoutTimer();
        result = result * 59 + (($logoutTimer == null) ? 43 : $logoutTimer.hashCode());
        final Object $enderPearlTimer = this.getEnderPearlTimer();
        result = result * 59 + (($enderPearlTimer == null) ? 43 : $enderPearlTimer.hashCode());
        final Object $timers = this.getTimers();
        result = result * 59 + (($timers == null) ? 43 : $timers.hashCode());
        final Object $customTimers = this.getCustomTimers();
        result = result * 59 + (($customTimers == null) ? 43 : $customTimers.hashCode());
        final Object $plugin = this.getPlugin();
        result = result * 59 + (($plugin == null) ? 43 : $plugin.hashCode());
        final Object $config = this.getConfig();
        result = result * 59 + (($config == null) ? 43 : $config.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "TimerManager(combatTimer=" + this.getCombatTimer() + ", logoutTimer=" + this.getLogoutTimer() + ", enderPearlTimer=" + this.getEnderPearlTimer() + ", timers=" + this.getTimers() + ", customTimers=" + this.getCustomTimers() + ", plugin=" + this.getPlugin() + ", config=" + this.getConfig() + ")";
    }
}

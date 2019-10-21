package me.jaymesss.donkey.timer.event;

import com.google.common.base.Optional;
import me.jaymesss.donkey.timer.Timer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class TimerPauseEvent extends Event implements Cancellable {
    private static final HandlerList handlers;

    static {
        handlers = new HandlerList();
    }

    private final boolean paused;
    private final Optional<UUID> userUUID;
    private final Timer timer;
    private boolean cancelled;

    public TimerPauseEvent(final Timer timer, final boolean paused) {
        this.userUUID = (Optional) Optional.absent();
        this.timer = timer;
        this.paused = paused;
    }

    public TimerPauseEvent(final UUID userUUID, final Timer timer, final boolean paused) {
        this.userUUID = (Optional<UUID>) Optional.fromNullable(userUUID);
        this.timer = timer;
        this.paused = paused;
    }

    public static HandlerList getHandlerList() {
        return TimerPauseEvent.handlers;
    }

    public Optional<UUID> getUserUUID() {
        return this.userUUID;
    }

    public Timer getTimer() {
        return this.timer;
    }

    public boolean isPaused() {
        return this.paused;
    }

    public HandlerList getHandlers() {
        return TimerPauseEvent.handlers;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
}

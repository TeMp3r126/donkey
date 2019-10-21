package me.jaymesss.donkey.timer.event;

import com.google.common.base.Optional;
import me.jaymesss.donkey.timer.Timer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class TimerClearEvent extends Event {
    private static final HandlerList handlers;

    static {
        handlers = new HandlerList();
    }

    private final Optional<UUID> userUUID;
    private final Timer timer;

    public TimerClearEvent(final Timer timer) {
        this.userUUID = Optional.absent();
        this.timer = timer;
    }

    public TimerClearEvent(final UUID userUUID, final Timer timer) {
        this.userUUID = (Optional<UUID>) Optional.of(userUUID);
        this.timer = timer;
    }

    public static HandlerList getHandlerList() {
        return TimerClearEvent.handlers;
    }

    public Optional<UUID> getUserUUID() {
        return this.userUUID;
    }

    public Timer getTimer() {
        return this.timer;
    }

    public HandlerList getHandlers() {
        return TimerClearEvent.handlers;
    }
}

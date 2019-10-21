package me.jaymesss.donkey.timer.event;

import com.google.common.base.Optional;
import me.jaymesss.donkey.timer.Timer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class TimerExpireEvent extends Event {
    private static final HandlerList handlers;

    static {
        handlers = new HandlerList();
    }

    private final Optional<UUID> userUUID;
    private final Timer timer;

    public TimerExpireEvent(final Timer timer) {
        this.userUUID = Optional.absent();
        this.timer = timer;
    }

    public TimerExpireEvent(final UUID userUUID, final Timer timer) {
        this.userUUID = (Optional<UUID>) Optional.fromNullable(userUUID);
        this.timer = timer;
    }

    public static HandlerList getHandlerList() {
        return TimerExpireEvent.handlers;
    }

    public Optional<UUID> getUserUUID() {
        return this.userUUID;
    }

    public Timer getTimer() {
        return this.timer;
    }

    public HandlerList getHandlers() {
        return TimerExpireEvent.handlers;
    }
}

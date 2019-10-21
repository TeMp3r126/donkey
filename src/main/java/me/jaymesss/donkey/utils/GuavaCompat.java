package me.jaymesss.donkey.utils;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

import javax.annotation.Nullable;

public class GuavaCompat {
    public static <T extends Enum<T>> Optional<T> getIfPresent(final Class<T> enumClass, final String value) {
        Preconditions.checkNotNull((Object) enumClass);
        Preconditions.checkNotNull((Object) value);
        try {
            return Optional.of(Enum.valueOf(enumClass, value));
        } catch (IllegalArgumentException iae) {
            return Optional.absent();
        }
    }

    public static <T> T firstNonNull(@Nullable final T first, @Nullable final T second) {
        return (first != null) ? first : Preconditions.checkNotNull(second);
    }
}

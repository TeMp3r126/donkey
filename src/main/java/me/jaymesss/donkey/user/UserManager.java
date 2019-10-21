package me.jaymesss.donkey.user;

import me.jaymesss.donkey.Donkey;
import me.jaymesss.donkey.utils.Config;
import me.jaymesss.donkey.utils.GuavaCompat;
import org.bukkit.Bukkit;
import org.bukkit.configuration.MemorySection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.*;
import java.util.regex.Pattern;

public class UserManager implements Listener {

    private static final Pattern USERNAME_REGEX;

    static {
        USERNAME_REGEX = Pattern.compile("^[a-zA-Z0-9_]{2,16}$");
    }

    protected final Map<String, UUID> uuidCache;
    private final Donkey plugin;
    private final Map<UUID, DonkeyUser> users = new HashMap<>();
    private Config userConfig;

    public UserManager(Donkey plugin) {
        this.plugin = plugin;
        this.uuidCache = Collections.synchronizedMap(new TreeMap<String, UUID>(String.CASE_INSENSITIVE_ORDER));
        this.reloadUserData();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        users.putIfAbsent(uuid, new DonkeyUser(uuid));
    }

    /**
     * Gets a map of {@link DonkeyUser} this manager holds.
     *
     * @return map of user UUID strings to their corresponding {@link DonkeyUser}.
     */
    public Map<UUID, DonkeyUser> getUsers() {
        return users;
    }

    /**
     * Gets a {@link DonkeyUser} by their {@link UUID} asynchronously.
     *
     * @param {@link UUID} to get from
     * @return the {@link DonkeyUser} with the {@link UUID}
     */


    public UUID fetchUUID(final String username) {
        final Player player = Bukkit.getPlayer(username);
        if (player != null) {
            return player.getUniqueId();
        }
        if (UserManager.USERNAME_REGEX.matcher(username).matches()) {
            return this.uuidCache.get(username);
        }
        return null;
    }

    public DonkeyUser getUserAsync(UUID uuid) {
        synchronized (users) {
            DonkeyUser revert;
            DonkeyUser user = users.putIfAbsent(uuid, revert = new DonkeyUser(uuid));
            return GuavaCompat.firstNonNull(user, revert);
        }
    }

    /**
     * Gets a {@link DonkeyUser} by their {@link UUID}.
     *
     * @param uuid the {@link UUID} to get from
     * @return the {@link DonkeyUser} with the {@link UUID}
     */
    public DonkeyUser getUser(UUID uuid) {
        DonkeyUser revert;
        DonkeyUser user = users.putIfAbsent(uuid, revert = new DonkeyUser(uuid));
        return GuavaCompat.firstNonNull(user, revert);
    }

    /**
     * Loads the user data from storage.
     */
    public void reloadUserData() {
        this.userConfig = new Config(plugin, "donkey-users");

        Object object = userConfig.get("users");
        if (object instanceof MemorySection) {
            MemorySection section = (MemorySection) object;
            Collection<String> keys = section.getKeys(false);
            for (String id : keys) {
                users.put(UUID.fromString(id), (DonkeyUser) userConfig.get(section.getCurrentPath() + '.' + id));
            }
        }
    }

    /**
     * Saves the user data to storage.
     */
    public void saveUserData() {
        Set<Map.Entry<UUID, DonkeyUser>> entrySet = users.entrySet();
        Map<String, DonkeyUser> saveMap = new LinkedHashMap<>(entrySet.size());
        for (Map.Entry<UUID, DonkeyUser> entry : entrySet) {
            saveMap.put(entry.getKey().toString(), entry.getValue());
        }

        userConfig.set("users", saveMap);
        userConfig.save();
    }
}

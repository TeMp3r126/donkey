package me.jaymesss.donkey.user;

import lombok.Getter;
import lombok.Setter;
import me.jaymesss.donkey.utils.GenericUtils;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@Getter
@Setter
public class DonkeyUser implements ConfigurationSerializable {

    protected final Set<String> ignored = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
    protected final Set<String> duelRequests = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
    private final UUID userUUID;
    private String name = null;
    private String nickName = null;
    private int duelWins = 0;
    private int duelLosses = 0;
    private boolean showGlobalChat = true;
    private boolean privateMessages = true;
    private boolean sounds = true;
    private boolean commandSpy = false;
    private boolean socialSpy = false;
    private boolean gged = true;
    private String reply = null;
    private ItemStack[] beforeDuelItems = null;
    private ItemStack[] beforeDuelArmor = null;
    private Location beforeDuelLocation = null;

    public DonkeyUser(UUID userUUID) {
        this.userUUID = userUUID;
    }

    public DonkeyUser(Map<String, Object> map) {
        this.userUUID = UUID.fromString((String) map.get("userUUID"));
        this.name = (String) map.getOrDefault("name", null);
        this.nickName = (String) map.getOrDefault("nickname", null);
        this.showGlobalChat = (Boolean) map.getOrDefault("showGlobalChat", true);
        this.beforeDuelItems = (ItemStack[]) map.getOrDefault("beforeDuelItems", null);
        this.beforeDuelArmor = (ItemStack[]) map.getOrDefault("beforeDuelArmor", null);
        this.beforeDuelLocation = (Location) map.getOrDefault("beforeDuelLocation", null);
        this.privateMessages = (Boolean) map.getOrDefault("privateMessages", true);
        this.commandSpy = (Boolean) map.getOrDefault("commandSpy", false);
        this.gged = (Boolean) map.getOrDefault("gged", false);
        this.socialSpy = (Boolean) map.getOrDefault("socialSpy", false);
        this.sounds = (Boolean) map.getOrDefault("sounds", true);
        this.ignored.addAll(GenericUtils.createList(map.get("ignored"), String.class));
        this.duelRequests.addAll(GenericUtils.createList(map.get("duelRequests"), String.class));
        this.reply = (String) map.getOrDefault("reply", null);
        this.duelWins = (int) map.getOrDefault("duelWins", 0);
        this.duelLosses = (int) map.getOrDefault("duelLosses", 0);

    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("userUUID", userUUID.toString());
        map.put("name", this.name);
        map.put("nickName", this.nickName);
        map.put("ignored", new ArrayList<>(ignored));
        map.put("duelRequests", new ArrayList<>(duelRequests));
        map.put("showGlobalChat", this.showGlobalChat);
        map.put("privateMessages", this.privateMessages);
        map.put("commandSpy", this.commandSpy);
        map.put("beforeDuelItems", this.beforeDuelItems);
        map.put("beforeDuelArmor", this.beforeDuelArmor);
        map.put("beforeDuelLocation", this.beforeDuelLocation);
        map.put("socialSpy", this.socialSpy);
        map.put("sounds", this.sounds);
        map.put("gged", this.gged);
        map.put("reply", this.reply);
        map.put("duelWins", this.duelWins);
        map.put("duelLosses", this.duelLosses);

        return map;
    }
}

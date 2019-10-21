package me.jaymesss.donkey;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.struct.Relation;
import lombok.Getter;
import lombok.Setter;
import me.jaymesss.donkey.user.DonkeyUser;
import me.jaymesss.donkey.utils.Color;
import me.jaymesss.donkey.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class DonkeyAPI {

    private static Method getHandleMethod;
    private static Field pingField;
    @Getter
    @Setter
    private static double pingMultiplier;

    public DonkeyAPI() {
        pingMultiplier = 1.85;
    }

    public static double getLag() {
        return (Bukkit.spigot().getTPS()[0] / 20 * 100 > 100 ? 100 : Bukkit.spigot().getTPS()[0] / 20 * 100);
    }

    public static int clearAllEntities(EntityType... excluded) {
        int removed = 0;
        for (World world : Bukkit.getServer().getWorlds()) {
            entityLoop:
            for (Entity entity : world.getEntities()) {
                for (EntityType type : excluded) {
                    if (entity.getType() == EntityType.PLAYER) {
                        continue entityLoop;
                    }

                    if (entity.getType() == type) {
                        continue entityLoop;
                    }
                }

                removed++;
                entity.remove();
            }
        }

        return removed;
    }

    public static String formatBasicTps(final double tps) {
        return ((tps > 18.0) ? ChatColor.GREEN : ((tps > 16.0) ? ChatColor.YELLOW : ChatColor.RED)).toString() + Math.min(Math.round(tps * 10.0) / 10.0, 20.0);
    }

    public static String getFormattedLag() {
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        return decimalFormat.format(DonkeyAPI.getLag()) + '%';
    }

    public static int getPing(Player player) {
        try {
            if (getHandleMethod == null) {
                getHandleMethod = player.getClass().getDeclaredMethod("getHandle");
                getHandleMethod.setAccessible(true);
            }
            Object entityPlayer = getHandleMethod.invoke(player);
            if (pingField == null) {
                pingField = entityPlayer.getClass().getDeclaredField("ping");
                pingField.setAccessible(true);
            }
            int ping = (int) (pingField.getInt(entityPlayer) / pingMultiplier);

            return ping > 0 ? ping : 0;
        } catch (Exception e) {
            return 1;
        }
    }

    public static double getAveragePing() {
        int averagePing = 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            averagePing += getPing(player);
        }
        return averagePing / Bukkit.getOnlinePlayers().size();
    }

    public static String getColoredPing(Player player) {
        if (getPing(player) > 130) {
            return "" + ChatColor.DARK_RED + getPing(player);
        }
        if (getPing(player) > 80) {
            return "" + ChatColor.GOLD + getPing(player);
        } else {
            return "" + ChatColor.GREEN + getPing(player);
        }
    }

    public static String getFormattedTps(double tps) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        if (tps > 20) {
            return ChatColor.DARK_GREEN + "*20";
        } else if (tps <= 20.0 && tps > 15) {
            return ChatColor.GREEN.toString() + decimalFormat.format(tps);
        } else if (tps <= 15 && tps > 10) {
            return ChatColor.YELLOW.toString() + decimalFormat.format(tps);
        } else if (tps <= 10 && tps > 5) {
            return ChatColor.RED.toString() + decimalFormat.format(tps);
        } else if (tps <= 5) {
            return ChatColor.DARK_RED.toString() + decimalFormat.format(tps);
        } else {
            return "0";
        }
    }

    public static int getTotalEntities() {
        Integer total = 0;
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                total += 1;
            }
        }
        return total;
    }

    public static long parse(final String input) {
        if (input == null || input.isEmpty()) {
            return -1L;
        }
        long result = 0L;
        StringBuilder number = new StringBuilder();
        for (int i = 0; i < input.length(); ++i) {
            final char c = input.charAt(i);
            if (Character.isDigit(c)) {
                number.append(c);
            } else {
                final String str;
                if (Character.isLetter(c) && !(str = number.toString()).isEmpty()) {
                    result += convert(Integer.parseInt(str), c);
                    number = new StringBuilder();
                }
            }
        }
        return result;
    }

    private static long convert(final int value, final char unit) {
        switch (unit) {
            case 'y': {
                return value * TimeUnit.DAYS.toMillis(365L);
            }
            case 'M': {
                return value * TimeUnit.DAYS.toMillis(30L);
            }
            case 'd': {
                return value * TimeUnit.DAYS.toMillis(1L);
            }
            case 'h': {
                return value * TimeUnit.HOURS.toMillis(1L);
            }
            case 'm': {
                return value * TimeUnit.MINUTES.toMillis(1L);
            }
            case 's': {
                return value * TimeUnit.SECONDS.toMillis(1L);
            }
            default: {
                return -1L;
            }
        }
    }

    public static Player getPlayerFromUUID(UUID uuid) {
        return Bukkit.getPlayer(uuid);
    }

    public static String getDisplayName(Player player) {
        return player.getDisplayName();
    }

//    public static String getRank(Player player) {
//        String prefix = new ColorUtils().translateFromString("");
//        final PermissionUser permissionUser = PermissionsEx.getUser(player);
//        String[] groupNames;
//        for (int length = (groupNames = permissionUser.getGroupNames()).length, i = 0; i < length; ++i) {
//            final String ranks;
//            final String s = ranks = groupNames[i];
//            final String s2;
//            switch (s2 = s) {
//                case "Peasant": {
//                    prefix = "&fPeasant";
//                    continue;
//                }
//                case "Squire": {
//                    prefix = "&8Squire";
//                    continue;
//                }
//                case "Knight": {
//                    prefix = "&bKnight";
//                    continue;
//                }
//                case "Paladin": {
//                    prefix = "&ePaladin";
//                    continue;
//                }
//                case "King": {
//                    prefix = "&cKing";
//                    continue;
//                }
//                case "Lord": {
//                    prefix = "&4Lord";
//                    continue;
//                }
//                case "Trial-Mod": {
//                    prefix = "&5Trial-Mod";
//                    continue;
//                }
//                case "Mod": {
//                    prefix = "&5Mod";
//                    continue;
//                }
//                case "SrMod": {
//                    prefix = "&5Mod+";
//                    continue;
//                }
//                case "Admin": {
//                    prefix = "&cAdmin";
//                    continue;
//                }
//                case "SrAdmin": {
//                    prefix = "&cSr Admin";
//                    continue;
//                }
//                case "Dev": {
//                    prefix = "&bDeveloper";
//                    continue;
//                }
//                case "Manager": {
//                    prefix = "&4Manager";
//                    continue;
//                }
//                case "Owner": {
//                    prefix = "&4Owner";
//                    continue;
//                }
//                case "Media": {
//                    prefix = "&cMedia";
//                    continue;
//                }
//                default:
//                    break;
//            }
//            prefix = "&c&nError";
//        }
//        return new ColorUtils().translateFromString(prefix);
//    }

    public static void messageFormat(Player messager, Player messaged, DonkeyUser messagerUser, DonkeyUser messagedUser, String message) {
        messager.sendMessage(ChatColor.GRAY + "(To " + getDisplayName(messaged) + ChatColor.GRAY + ") " + message);
        messaged.sendMessage(ChatColor.GRAY + "(From " + getDisplayName(messager) + ChatColor.GRAY + ") " + message);
        if (messagedUser.isSounds()) {
            messaged.playSound(messaged.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
        }
        messagedUser.setReply(messager.getName());
        messagerUser.setReply(messaged.getName());
        for (Player player : Bukkit.getOnlinePlayers()) {
            DonkeyUser SurvivalUser = Donkey.getInstance().getUserManager().getUser(player.getUniqueId());
            if (SurvivalUser.isSocialSpy()) {
                player.sendMessage(ChatColor.GRAY.toString() + ChatColor.ITALIC + "[" + messager.getName() + ": Messaged " + messaged.getName() + ChatColor.UNDERLINE + message + ']');
            }
        }
    }


//    public static String getRankColor(Player player) {
//        String color = new ColorUtils().translateFromString("");
//        final PermissionUser permissionUser = PermissionsEx.getUser(player);
//        String[] groupNames;
//        for (int length = (groupNames = permissionUser.getGroupNames()).length, i = 0; i < length; ++i) {
//            final String ranks;
//            final String s = ranks = groupNames[i];
//            final String s2;
//            switch (s2 = s) {
//                case "Peasant": {
//                    color = "&f";
//                    continue;
//                }
//                case "Squire": {
//                    color = "&8";
//                    continue;
//                }
//                case "Knight": {
//                    color = "&b";
//                    continue;
//                }
//                case "Paladin": {
//                    color = "&e";
//                    continue;
//                }
//                case "King": {
//                    color = "&c";
//                    continue;
//                }
//                case "Lord": {
//                    color = "&4";
//                    continue;
//                }
//                case "Trial-Mod": {
//                    color = "&5";
//                    continue;
//                }
//                case "Mod": {
//                    color = "&5";
//                    continue;
//                }
//                case "SrMod": {
//                    color = "&5&o";
//                    continue;
//                }
//                case "Admin": {
//                    color = "&c";
//                    continue;
//                }
//                case "SrAdmin": {
//                    color = "&c&o";
//                    continue;
//                }
//                case "Dev": {
//                    color = "&b&o";
//                    continue;
//                }
//                case "Manager": {
//                    color = "&4";
//                    continue;
//                }
//                case "Owner": {
//                    color = "&4";
//                    continue;
//                }
//                case "Media": {
//                    color = "&c";
//                    continue;
//                }
//                default:
//                    break;
//            }
//            color = "&c&nError";
//        }
//        return new ColorUtils().translateFromString(color);
//    }

    public static ChatColor getRelationColor(FPlayer fplayer, Faction faction) {
        if (fplayer.getRelationTo(faction).equals(Relation.ALLY)) {
            return ChatColor.LIGHT_PURPLE;
        } else if (fplayer.getRelationTo(faction).equals(Relation.ENEMY)) {
            return ChatColor.RED;
        } else if (fplayer.getRelationTo(faction).equals(Relation.TRUCE)) {
            return ChatColor.BLUE;
        } else if (fplayer.getRelationTo(faction).equals(Relation.MEMBER)) {
            return ChatColor.GREEN;
        } else if (fplayer.getRelationTo(faction).equals(Relation.NEUTRAL)) {
            return ChatColor.YELLOW;
        } else {
            return ChatColor.WHITE;
        }
    }

    public static String getColor() {
        return Color.translate(Donkey.getInstance().getConfig().getString("server-color1"));
    }

    public static String getColor2() {
        return Color.translate(Donkey.getInstance().getConfig().getString("server-color2"));
    }

    public static String getServerName() {
        return Color.translate(Donkey.getInstance().getConfig().getString("server-name"));
    }

    public static String getWebstore() {
        return Color.translate(Donkey.getInstance().getConfig().getString("webstore"));
    }

    public static void playerPerformFormat(Player performer, String action, String permission) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName() != performer.getName() && player.hasPermission(permission)) {
                player.sendMessage(ChatColor.GRAY.toString() + ChatColor.ITALIC + '[' + performer.getName() + ": " + action + ']');
            }
        }
    }

    public static void playerPerformFormat(CommandSender performer, String action, String permission) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName() != performer.getName() && player.hasPermission(permission)) {
                player.sendMessage(ChatColor.GRAY.toString() + ChatColor.ITALIC + '[' + performer.getName() + ": " + action + ']');
            }
        }
    }

    public static String getString(String path) {
        if (Donkey.getInstance().getConfig().contains(path)) {
            return new ColorUtils().translateFromString(Donkey.getInstance().getConfig().getString(path.replace("{color1}", DonkeyAPI.getColor().replace("{color2}", DonkeyAPI.getColor2().replace("{server-name}", DonkeyAPI.getServerName())))));
        }
        return new ColorUtils().translateFromString("&cString not found: '" + path + "'");
    }

    public static List<String> getStringList(String path) {
        if (Donkey.getInstance().getConfig().contains(path)) {
            ArrayList<String> lines = new ArrayList<String>();
            for (String text : Donkey.getInstance().getConfig().getStringList(path)) {
                lines.add(new ColorUtils().translateFromString(text).replace("{color1}", DonkeyAPI.getColor().replace("{color2}", DonkeyAPI.getColor2().replace("{server-name}", DonkeyAPI.getServerName()))));
            }
            return lines;
        }
        return Arrays.<String>asList(new ColorUtils().translateFromString("&cString list not found: '" + path + "'"));
    }

}

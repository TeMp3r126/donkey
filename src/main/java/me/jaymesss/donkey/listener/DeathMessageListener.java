package me.jaymesss.donkey.listener;

import com.google.common.base.Preconditions;
import net.minecraft.server.v1_8_R3.EntityLiving;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;


public class DeathMessageListener implements Listener {


    public static String replaceLast(String text, String regex, String replacement) {
        return text.replaceFirst("(?s)" + regex + "(?!.*?" + regex + ')', replacement);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerDeathEvent event) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(getDeathMessage(event.getDeathMessage(), event.getEntity(), getKiller(event)));
        }
        event.setDeathMessage(null);
    }

    private CraftEntity getKiller(PlayerDeathEvent event) {
        EntityLiving lastAttacker = ((CraftPlayer) event.getEntity()).getHandle().lastDamager;
        return lastAttacker == null ? null : lastAttacker.getBukkitEntity();
    }

    private String getDeathMessage(String input, Entity entity, Entity killer) {
        input = input.replaceFirst("\\[", ChatColor.DARK_RED + "[");
        input = replaceLast(input, "]", ChatColor.DARK_RED + "]");

        if (entity != null) {
            input = input.replaceFirst("(?i)" + getEntityName(entity), ChatColor.RED + getDisplayName(entity) + ChatColor.YELLOW);
        }

        if (killer != null && (!killer.equals(entity))) {
            input = input.replaceFirst("(?i)" + getEntityName(killer), ChatColor.RED + getDisplayName(killer) + ChatColor.YELLOW);
        }

        return input;
    }

    private String getEntityName(Entity entity) {
        Preconditions.checkNotNull(entity, "Entity cannot be null");
        return entity instanceof Player ? ((Player) entity).getName() : ((CraftEntity) entity).getHandle().getName();
    }

    private String getDisplayName(Entity entity) {
        Preconditions.checkNotNull(entity, "Entity cannot be null");
        if (entity instanceof Player) {
            Player player = (Player) entity;
            return player.getName() + ChatColor.DARK_RED + '[' + player.getStatistic(Statistic.PLAYER_KILLS) + ']';
        } else {
            return WordUtils.capitalizeFully(entity.getType().name().replace('_', ' '));
        }
    }
}
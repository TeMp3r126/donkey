package me.jaymesss.donkey.listener;

import me.jaymesss.donkey.Donkey;
import me.jaymesss.donkey.DonkeyAPI;
import me.jaymesss.donkey.user.DonkeyUser;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class DuelListener implements Listener {

    private static ArrayList<String> duelers = new ArrayList<String>();

    private static void giveLoadout(Player player) {
        ItemStack diamondhelmet = new ItemStack(Material.DIAMOND_HELMET, 1);
        diamondhelmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        diamondhelmet.addEnchantment(Enchantment.DURABILITY, 3);
        ItemStack diamondchestplate = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
        diamondchestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        diamondchestplate.addEnchantment(Enchantment.DURABILITY, 3);
        ItemStack diamondleggings = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
        diamondleggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        diamondleggings.addEnchantment(Enchantment.DURABILITY, 3);
        ItemStack diamondboots = new ItemStack(Material.DIAMOND_BOOTS, 1);
        diamondboots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        diamondboots.addEnchantment(Enchantment.DURABILITY, 3);
        diamondboots.addEnchantment(Enchantment.PROTECTION_FALL, 4);
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD, 1);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        sword.addEnchantment(Enchantment.DURABILITY, 3);
        PlayerInventory pi = player.getInventory();
        pi.clear();
        pi.setItem(0, sword);
        pi.setItem(1, new ItemStack(Material.ENDER_PEARL, 16));
        pi.setItem(8, new ItemStack(Material.GOLDEN_APPLE, 64));
        pi.setBoots(diamondboots);
        pi.setLeggings(diamondleggings);
        pi.setChestplate(diamondchestplate);
        pi.setHelmet(diamondhelmet);
        player.updateInventory();
    }

    public static void startDuel(Player player, Player player2) {
        DonkeyUser donkeyUser = Donkey.getInstance().getUserManager().getUser(player.getUniqueId());
        DonkeyUser donkeyUser2 = Donkey.getInstance().getUserManager().getUser(player2.getUniqueId());
        donkeyUser.setBeforeDuelItems(player.getInventory().getContents());
        donkeyUser2.setBeforeDuelItems(player2.getInventory().getContents());
        donkeyUser.setBeforeDuelArmor(player.getInventory().getArmorContents());
        donkeyUser2.setBeforeDuelArmor(player2.getInventory().getArmorContents());
        donkeyUser.setBeforeDuelLocation(player.getLocation());
        donkeyUser2.setBeforeDuelLocation(player2.getLocation());
        Location location = new Location(Bukkit.getWorld(DonkeyAPI.getString("location1.world")), Donkey.getInstance().getConfig().getInt("location1.x"), Donkey.getInstance().getConfig().getInt("location1.y"), Donkey.getInstance().getConfig().getInt("location1.z"));
        Location location2 = new Location(Bukkit.getWorld(DonkeyAPI.getString("location2.world")), Donkey.getInstance().getConfig().getInt("location2.x"), Donkey.getInstance().getConfig().getInt("location2.y"), Donkey.getInstance().getConfig().getInt("location2.z"));
        player.teleport(location);
        player2.teleport(location2);
        giveLoadout(player);
        giveLoadout(player2);
        duelers.add(player.getName());
        duelers.add(player2.getName());
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (!players.getName().equalsIgnoreCase(player2.getName())) {
                player.hidePlayer(players);
            }
            if (!players.getName().equalsIgnoreCase(player.getName())) {
                player2.hidePlayer(players);
            }
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        DonkeyUser donkeyUser = Donkey.getInstance().getUserManager().getUser(player.getUniqueId());
        if (duelers.contains(player.getName())) {
            event.setRespawnLocation(donkeyUser.getBeforeDuelLocation());
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.teleport(donkeyUser.getBeforeDuelLocation());

                }
            }.runTaskLater(Donkey.getInstance(), 40L);
            player.getInventory().setContents(donkeyUser.getBeforeDuelItems());
            player.getInventory().setArmorContents(donkeyUser.getBeforeDuelArmor());
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = event.getEntity().getKiller();
        event.setDeathMessage(null);
        int deaths = player.getStatistic(Statistic.DEATHS) - 1;
        int kills = killer.getStatistic(Statistic.PLAYER_KILLS) - 1;
        player.setStatistic(Statistic.DEATHS, deaths);
        killer.setStatistic(Statistic.PLAYER_KILLS, kills);
        if (duelers.contains(player.getName())) {
            DonkeyUser donkeyUser = Donkey.getInstance().getUserManager().getUser(player.getUniqueId());
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.teleport(donkeyUser.getBeforeDuelLocation());

                }
            }.runTaskLater(Donkey.getInstance(), 40L);
            player.getInventory().setContents(donkeyUser.getBeforeDuelItems());
            player.getInventory().setArmorContents(donkeyUser.getBeforeDuelArmor());
        }
        if (duelers.contains(killer.getName())) {
            DonkeyUser donkeyUser = Donkey.getInstance().getUserManager().getUser(killer.getUniqueId());
            DonkeyUser donkeyUser2 = Donkey.getInstance().getUserManager().getUser(player.getUniqueId());
            int duelWins = donkeyUser.getDuelWins() + 1;
            int duelLosses = donkeyUser.getDuelLosses() + 1;
            donkeyUser.setDuelWins(duelWins);
            donkeyUser2.setDuelLosses(duelLosses);
            Bukkit.broadcastMessage(ChatColor.AQUA + "[DUELS] " + DonkeyAPI.getDisplayName(killer) + ChatColor.WHITE + " has beat " + DonkeyAPI.getDisplayName(player) + ChatColor.WHITE + " in a 1v1!");
            player.sendMessage(ChatColor.RED + "You have lost the duel against " + killer.getName() + '!');
            killer.sendMessage(ChatColor.GREEN + "You have won the duel against " + player.getName() + '!');
            killer.teleport(donkeyUser.getBeforeDuelLocation());
            killer.getInventory().setContents(donkeyUser.getBeforeDuelItems());
            killer.getInventory().setArmorContents(donkeyUser.getBeforeDuelArmor());
            duelers.remove(killer.getName());
            duelers.remove(player.getName());
        }
        for (Player players : Bukkit.getOnlinePlayers()) {
//            Essentials essentials = Essentials.getInstance();
//            if (!essentials.getStaffModeListener().isVanished(players)) {
                killer.showPlayer(players);
                player.showPlayer(players);
//            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (duelers.contains(event.getPlayer().getName())) {
            event.getPlayer().sendMessage(ChatColor.RED + "You cannot do this in a duel!");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (duelers.contains(event.getPlayer().getName())) {
            event.getPlayer().sendMessage(ChatColor.RED + "You cannot do this in a duel!");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerPreproccessCommand(PlayerCommandPreprocessEvent event) {
        if (duelers.contains(event.getPlayer().getName())) {
            if (event.getMessage().startsWith("/")) {
                event.getPlayer().sendMessage(ChatColor.RED + "You cannot do this in a duel!");
                event.setCancelled(true);
            }
        }
    }
}

package me.jaymesss.donkey.listener;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import me.jaymesss.donkey.Donkey;
import me.jaymesss.donkey.user.DonkeyUser;
import me.jaymesss.donkey.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

public class DonkeyListener implements Listener {

    @EventHandler
    public void onPlayerPreprocessCommand(PlayerCommandPreprocessEvent event) {
        Player commandSender = event.getPlayer();
        if (event.getMessage().equalsIgnoreCase("/vote")) {
            commandSender.sendMessage(Color.translate("&8&m-------------------------------------"));
            commandSender.sendMessage(ChatColor.BLUE.toString() + ChatColor.BOLD + "VyriseMC " + ChatColor.WHITE + "Voting");
            commandSender.sendMessage(ChatColor.GRAY + " - " + ChatColor.AQUA + "https://bit.ly/vvote-1");
            commandSender.sendMessage(ChatColor.GRAY + " - " + ChatColor.AQUA + "https://bit.ly/vvote-2");
            commandSender.sendMessage(ChatColor.GRAY + " - " + ChatColor.AQUA + "https://bit.ly/vvote-3");
            commandSender.sendMessage(ChatColor.GRAY + " - " + ChatColor.AQUA + "https://bit.ly/vvote-4");
            commandSender.sendMessage("");
            commandSender.sendMessage(ChatColor.GRAY.toString() + ChatColor.ITALIC + "Thanks for supporting VyriseMC!");
            commandSender.sendMessage(Color.translate("&8&m-------------------------------------"));
        }
        if (event.getMessage().startsWith("/pex") || event.getMessage().startsWith("/promote") || event.getMessage().startsWith("/demote")) {
            commandSender.sendMessage("Unknown command. Type " + '"' + "/help" + '"' + " for help.");
        }
    }

    @EventHandler
    public void onInventoryInteract(InventoryClickEvent event) {
        if (event.getInventory().getTitle().contains("Thanks for shopping")) {
            event.setCancelled(true);
            if (event.getCurrentItem().equals(Material.WOOL)) {
                event.getWhoClicked().closeInventory();
                event.getWhoClicked().sendMessage(ChatColor.GREEN + "Thanks for shopping with VyriseMC!");
            }
        }
    }

    @EventHandler
    public void onPlayerSpawn(PlayerSpawnLocationEvent event) {
        Location location = new Location(Bukkit.getWorld("world"), 0.5, 82, 0.5);
        if (!event.getPlayer().hasPlayedBefore()) {
            event.setSpawnLocation(location);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Location location = new Location(Bukkit.getWorld("world"), 0.5, 82, 0.5);

        Essentials ess = (Essentials) Donkey.getInstance().getServer().getPluginManager().getPlugin("Essentials");

        if (player.hasPermission("donkey.player.admin")) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    User user = ess.getUser(player.getUniqueId());
                    user.setVanished(false);
                }
            }.runTaskLater(Donkey.getInstance(), 20L);
        }
        if (!player.hasPlayedBefore()) {
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage(ChatColor.AQUA.toString() + ChatColor.BOLD + "Welcome!");
            Bukkit.broadcastMessage(ChatColor.DARK_AQUA + player.getName() + ChatColor.WHITE + " has just joined for the first time.");
            Bukkit.broadcastMessage("");
            DonkeyUser donkeyUser = Donkey.getInstance().getUserManager().getUser(player.getUniqueId());
            donkeyUser.setName(player.getName());
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.teleport(location);
                }
            }.runTaskLater(Donkey.getInstance(), 40L);
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        event.setRespawnLocation(Bukkit.getWorld("world").getSpawnLocation());
        player.teleport(Bukkit.getWorld("world").getSpawnLocation());
    }


}

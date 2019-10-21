package me.jaymesss.donkey;

import com.bizarrealex.aether.Aether;
import lombok.Getter;
import lombok.Setter;
import me.jaymesss.donkey.command.*;
import me.jaymesss.donkey.command.social.*;
import me.jaymesss.donkey.listener.*;
import me.jaymesss.donkey.sidebar.DonkeySidebar;
import me.jaymesss.donkey.timer.TimerManager;
import me.jaymesss.donkey.user.UserManager;
import me.jaymesss.donkey.utils.DurationFormatter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.lang.management.ManagementFactory;

public class Donkey extends JavaPlugin {

    @Getter
    @Setter
    private static Donkey instance;
    @Getter
    @Setter
    private UserManager userManager;
    @Getter
    @Setter
    private TimerManager timerManager;

    public void onEnable() {
        instance = this;
        setupConfig();
        registerManagers();
        registerListeners();
        registerCommands();
        runTimers();
        new Aether(this, new DonkeySidebar());
        DonkeyAPI.setPingMultiplier(1.00);

    }

    public void onDisable() {
        instance = null;
    }

    public void setupConfig() {
        File file = new File(this.getDataFolder().getAbsolutePath(), "config.yml");
        if (!file.exists()) {
            getFileConfigurationOptions().copyDefaults(true);
            saveConfig();
            Bukkit.getServer().getConsoleSender().sendMessage("[Donkey] File config.yml not found, creaating now.");
        }
        else {
            Bukkit.getServer().getConsoleSender().sendMessage("[Donkey] File config.yml found and loaded.");

        }
    }

    public void saveData() {
        userManager.saveUserData();
        timerManager.saveTimerData();
    }

    public void registerManagers() {
        userManager = new UserManager(this);
        timerManager = new TimerManager(this);
    }

    public void registerCommands() {
        getCommand("createtimer").setExecutor(new CreateTimerCommand());
        getCommand("logout").setExecutor(new LogoutCommand());
        getCommand("nightvision").setExecutor(new NightVisionCommand());
        getCommand("savedata").setExecutor(new SaveDataCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("starttimer").setExecutor(new StartTimerCommand());
        getCommand("tnt").setExecutor(new TntCommand());
        getCommand("lag").setExecutor(new LagCommand());
        getCommand("nick").setExecutor(new NickCommand());
        getCommand("realname").setExecutor(new RealNameCommand());
        getCommand("tps").setExecutor(new TpsCommand());
        getCommand("logout").setExecutor(new LogoutCommand());
        getCommand("ping").setExecutor(new PingCommand());
        getCommand("ignore").setExecutor(new IgnoreCommand());
        getCommand("message").setExecutor(new MessageCommand());
        getCommand("reply").setExecutor(new ReplyCommand());
        getCommand("socialspy").setExecutor(new SocialSpyCommand());
        getCommand("toggleglobalchat").setExecutor(new ToggleGlobalChatCommand());
        getCommand("togglepm").setExecutor(new TogglepmCommand());
        getCommand("togglesounds").setExecutor(new ToggleSoundsCommand());
        getCommand("sendpurchase").setExecutor(new SendPurchaseCommand());
        getCommand("vote").setExecutor(new VoteCommand());
        getCommand("duel").setExecutor(new DuelCommand());
        getCommand("stats").setExecutor(new StatsCommand());

    }

    public void registerListeners() {
        getServer().getPluginManager().registerEvents(new AntiCraftListener(), this);
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new DeathMessageListener(), this);
        getServer().getPluginManager().registerEvents(new DepthStriderListener(), this);
        getServer().getPluginManager().registerEvents(new DonkeyListener(), this);
        getServer().getPluginManager().registerEvents(new EntityLimiterListener(), this);
        getServer().getPluginManager().registerEvents(new HungerFixListener(), this);
        getServer().getPluginManager().registerEvents(new PotionDrinkListener(), this);
        getServer().getPluginManager().registerEvents(new TntListener(), this);
        getServer().getPluginManager().registerEvents(new WeatherListener(), this);
        getServer().getPluginManager().registerEvents(new DuelListener(), this);
    }

    public void runTimers() {
//        new BukkitRunnable() {
//            @Override
//            public void run() {
//                if (FreezeAllListener.serverFreeze == true) {
//                    Bukkit.broadcastMessage("");
//                    Bukkit.broadcastMessage(ChatColor.RED.toString() + ChatColor.BOLD + "The server is currently FROZEN!");
//                    Bukkit.broadcastMessage(ChatColor.YELLOW + "Do not be scared, this is probably simply just due to the fact that the server is lagging, or the admins need to do something ASAP with no interruptions. We are sorry for the inconvience.");
//                    Bukkit.broadcastMessage("");
//                }
//            }
//        }.runTaskTimer(this, 0L, 1200L);
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    long max = Runtime.getRuntime().maxMemory() / 1024 / 1024;
                    long free = Runtime.getRuntime().freeMemory() / 1024 / 1024;
                    long used = max - free;
                    if (player.hasPermission("donkey.player.admin")) {
                        player.sendMessage("");
                        player.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "Server Status");
                        player.sendMessage(ChatColor.YELLOW + "TPS from last 1m, 5m, 15m: " + DonkeyAPI.getFormattedTps(Bukkit.spigot().getTPS()[0]) + ChatColor.YELLOW + ", " + DonkeyAPI.getFormattedTps(Bukkit.spigot().getTPS()[1]) + ChatColor.YELLOW + ", " + DonkeyAPI.getFormattedTps(Bukkit.spigot().getTPS()[2]));
                        player.sendMessage(ChatColor.YELLOW + "Server Performance: " + ChatColor.GREEN + DonkeyAPI.getFormattedLag());
                        player.sendMessage(ChatColor.YELLOW + "Free Memory: " + ChatColor.GREEN + free + "MB" + '/' + max + "MB");
                        player.sendMessage(ChatColor.YELLOW + "Used Memory: " + ChatColor.GREEN + used + "MB");

                        player.sendMessage(ChatColor.YELLOW + "Online Players: " + ChatColor.GREEN + Bukkit.getOnlinePlayers().size() + '/' + Bukkit.getMaxPlayers());
                        player.sendMessage(String.format(ChatColor.YELLOW + "Uptime: " + ChatColor.GREEN + DurationFormatter.getRemaining(System.currentTimeMillis() - ManagementFactory.getRuntimeMXBean().getStartTime(), true, true)));
                        player.sendMessage(ChatColor.YELLOW + "Entities: " + ChatColor.GREEN + DonkeyAPI.getTotalEntities());
                        player.sendMessage("");
                        player.sendMessage(ChatColor.GRAY.toString() + ChatColor.ITALIC + "Saved " + getUserManager().getUsers().values().size() + " players' data!");
                        player.sendMessage("");
                        saveData();
                    }
                }
            }
        }.runTaskTimer(this, 0L, 6000L);
    }

    public FileConfigurationOptions getFileConfigurationOptions() {
        return this.getConfig().options();
    }

}

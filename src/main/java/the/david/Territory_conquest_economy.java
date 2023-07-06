package the.david;

import me.angeschossen.lands.api.LandsIntegration;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.logging.Logger;

public final class Territory_conquest_economy extends JavaPlugin implements @NotNull Listener {
    public static LandsIntegration landsAPI;
    private Plugin myPlugin;
    public static JavaPlugin instance;
    private static final Logger log = Logger.getLogger("Minecraft");
    private static Economy econ = null;
    private static Permission perms = null;
    private static Chat chat = null;
    public static long oldDayTime;
    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        Bukkit.getPluginManager().registerEvents(new eventListener(), this);
        Bukkit.getPluginManager().registerEvents(new quest(), this);
        if (!setupEconomy() ) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        setupPermissions();
        if (Bukkit.getPluginCommand("tce") != null) {
            Bukkit.getPluginCommand("tce").setExecutor(new commandHandler());
        }
        Objects.requireNonNull(Bukkit.getPluginCommand("tce")).setTabCompleter(new commandHandler());
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            /*
             * We register the EventListener here, when PlaceholderAPI is installed.
             * Since all events are in the main class (this class), we simply use "this"
             */
            new placeholderRegister().register();
            Bukkit.getPluginManager().registerEvents(this, this);
        } else {
            /*
             * We inform about the fact that PlaceholderAPI isn't installed and then
             * disable this plugin to prevent issues.
             */
            getLogger().info("Could not find PlaceholderAPI! This plugin is required.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                points.calculatePointsMaxMoney();
                questPoints.calculateQuestPointsMaxMoney();
                if(Bukkit.getWorld("world").getTime() < oldDayTime){
                    points.calculatePoint();
                    questPoints.calculatePoint();
                    quest.randomQuest();
                }
                if(Objects.equals(quest.todayQuest,null)){
                    quest.randomQuest();
                }
                oldDayTime = Bukkit.getWorld("world").getTime();
            }
        }.runTaskTimer(this,0,20);
    }

    @Override
    public void onLoad() {
        this.myPlugin = this;
        this.landsAPI = getLandsAPI();
    }
    @Override
    public void onDisable() {
        new placeholderRegister().unregister();
        // Plugin shutdown logic
    }
    private LandsIntegration getLandsAPI(){
        LandsIntegration landsAPI;
        landsAPI = apiGetter(this);

        if(landsAPI == null){
            getServer().getPluginManager().disablePlugin(this);
        }

        Bukkit.getLogger().info("Lands API successfully initialized!");
        return landsAPI;
    }
    public LandsIntegration apiGetter(Plugin plugin){
        try {
            return LandsIntegration.of(this);
        }
        catch (NullPointerException nullPointerException){
            plugin.getLogger().severe("[Landlord] Failed to initialize the LandsAPI!");
            return null;
        }
    }
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }
    public static Economy getEconomy() {
        return econ;
    }

    public static Permission getPermissions() {
        return perms;
    }

    public static Chat getChat() {
        return chat;
    }
}

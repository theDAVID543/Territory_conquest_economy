package the.david;

import fr.maxlego08.zauctionhouse.api.AuctionManager;
import fr.maxlego08.zauctionhouse.api.ConvertManager;
import fr.maxlego08.zauctionhouse.api.blacklist.IBlacklistManager;
import fr.maxlego08.zauctionhouse.api.category.CategoryManager;
import fr.maxlego08.zauctionhouse.api.filter.FilterManager;
import fr.maxlego08.zauctionhouse.api.inventory.InventoryManager;
import fr.maxlego08.zauctionhouse.api.transaction.TransactionManager;
import me.angeschossen.lands.api.LandsIntegration;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Territory_conquest_economy extends JavaPlugin {
    public static LandsIntegration landsAPI;
    private Plugin myPlugin;
    public static JavaPlugin instance;
    private static final Logger log = Logger.getLogger("Minecraft");
    private static Economy econ = null;
    private static Permission perms = null;
    private static Chat chat = null;
    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        Bukkit.getPluginManager().registerEvents(new eventListener(), this);
        ConfigReader.createCustomConfig();
        if (!setupEconomy() ) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        setupPermissions();
        setupChat();
    }
    @Override
    public void onLoad() {
        this.myPlugin = this;
        this.landsAPI = getLandsAPI();
    }
    @Override
    public void onDisable() {
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

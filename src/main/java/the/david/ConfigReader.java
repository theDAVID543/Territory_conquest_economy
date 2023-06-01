package the.david;

import me.angeschossen.lands.api.land.Land;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class ConfigReader {
    public static Integer playerExpGot(UUID uuid) {
        if(dataConfig.getString("player." + uuid) == null){
            return null;
        }else{
            return Integer.valueOf(dataConfig.getString("player." + uuid));
        }
    }
    public static Integer landExpGot(Integer id) {
        if(dataConfig.getString("land." + id) == null){
            return null;
        }else{
            return Integer.valueOf(dataConfig.getString("land." + id));
        }
    }

    public static void setPlayerExpConfig(UUID uuid, Integer exp) {
        dataConfig.set("player." + uuid,exp);
        Territory_conquest_economy.instance.saveConfig();
        try {
            dataConfig.save(dataConfigFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void setLandExpConfig(Integer id, Integer exp) {
        dataConfig.set("land." + id,exp);
        Territory_conquest_economy.instance.saveConfig();
        try {
            dataConfig.save(dataConfigFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static File dataConfigFile;
    private static FileConfiguration dataConfig;

    public static void createCustomConfig() {
        dataConfigFile = new File(Territory_conquest_economy.instance.getDataFolder(), "data/expGot.yml");
        if (!dataConfigFile.exists()) {
            dataConfigFile.getParentFile().mkdirs();
            Territory_conquest_economy.instance.saveResource("data/expGot.yml", false);
        }

        dataConfig = new YamlConfiguration();
        try {
            dataConfig.load(dataConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}

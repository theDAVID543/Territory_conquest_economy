package the.david;

import me.angeschossen.lands.api.LandsIntegration;
import me.angeschossen.lands.api.land.Land;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static java.lang.Math.pow;

public final class points {
    private static final Economy economy = Territory_conquest_economy.getEconomy();
    private static final LandsIntegration landsAPI = Territory_conquest_economy.landsAPI;
    private static final Map<UUID,Float> activity = new HashMap<>();
    private static final Map<UUID,Double> playerPoints = new HashMap<>();
    private static Double maxMoney = 0.0;
    public static Double getPoint(UUID uuid){
        if(!Objects.equals(playerPoints.get(uuid),null)){
            return playerPoints.get(uuid);
        } else{
            return 0.0;
        }
    }
    public static void addPoint(UUID uuid, Double points){
        playerPoints.putIfAbsent(uuid, 0.0d);
        playerPoints.put(uuid, playerPoints.get(uuid) + points);
    }
    public void removePoint(UUID uuid, Double points){
        playerPoints.putIfAbsent(uuid, 0.0);
        playerPoints.put(uuid, playerPoints.get(uuid) - points);
    }
    public static void clearPoint(UUID uuid){
        playerPoints.putIfAbsent(uuid, 0.0);
        playerPoints.remove(uuid);
    }
    public static Float getActivity(UUID uuid){
        if(!Objects.equals(activity.get(uuid),null)){
            return activity.get(uuid);
        }else{
            return 1f;
        }
    }
    public static void addActivity(UUID uuid, Float d){
        activity.putIfAbsent(uuid,1f);
        activity.put(uuid, activity.get(uuid) + d);
        if(activity.get(uuid) > 2){
            activity.put(uuid,2f);
        }
    }
    public static void removeActivity(UUID uuid, Float d){
        activity.putIfAbsent(uuid,1f);
        activity.put(uuid, activity.get(uuid) - d);
        if(activity.get(uuid) < 0.9){
            activity.put(uuid,0.9f);
        }
    }

    public static void calculateMaxMoney(){
        double moneys = 0.0;
        serverTotalPoints = 0.0;
        for(Player player : Bukkit.getServer().getOnlinePlayers()){
            if(!Objects.equals(landsAPI.getLandPlayer(player.getUniqueId()),null)){
                int totalLandChunks = 0;
                for(Land land : landsAPI.getLandPlayer(player.getUniqueId()).getLands()){
                    totalLandChunks += land.getChunksAmount();
                }
                moneys += totalLandChunks * 6 * getActivity(player.getUniqueId());
            }
        }
        if(moneys >= maxMoney){
            maxMoney = moneys;
        }
    }
    public static Double serverTotalPoints = 0.0;
    public static void calculatePoint(){
        serverTotalPoints = 0.0;
        Bukkit.getLogger().info("moneys: " + maxMoney);
        playerPoints.forEach((k,v) -> {
            Bukkit.getLogger().info(Bukkit.getOfflinePlayer(k) + "serverTotalPoints added " + pow(v, 0.5));
            serverTotalPoints += pow(v, 0.5);
        });
        playerPoints.forEach((k,v) -> {
            Bukkit.getLogger().info(Bukkit.getOfflinePlayer(k) + " added " + (pow(v, 0.5) / serverTotalPoints) * maxMoney);
            economy.depositPlayer(Bukkit.getOfflinePlayer(k), (pow(v, 0.5) / serverTotalPoints) * maxMoney);
        });
        Bukkit.getLogger().info("serverTotalPoints: " + serverTotalPoints);
        playerPoints.clear();
//        Set<UUID> ketSet = playerPoints.keySet();
//        for(UUID uuid : ketSet){
//            serverTotalPoints += pow(getPoint(uuid),0.5);
//        }
//        for(UUID uuid : ketSet){
//            economy.depositPlayer(Bukkit.getOfflinePlayer(uuid), (serverTotalPoints / pow(getPoint(uuid),0.5)) * moneys);
//            clearPoint(uuid);
//        }
    }
}

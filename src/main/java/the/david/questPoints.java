package the.david;

import me.angeschossen.lands.api.LandsIntegration;
import me.angeschossen.lands.api.land.Land;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static java.lang.Math.pow;

public final class questPoints {
    private static final Economy economy = Territory_conquest_economy.getEconomy();
    private static final LandsIntegration landsAPI = Territory_conquest_economy.landsAPI;
    private static final Map<UUID,Float> activity = new HashMap<>();
    public static final Map<UUID,Double> playerQuestPoints = new HashMap<>();
    public static Double maxMoney = 0.0;
    private static Integer rantimes = 0;
    public static Double getPoint(UUID uuid){
        if(!Objects.equals(playerQuestPoints.get(uuid),null)){
            return playerQuestPoints.get(uuid);
        } else{
            return 0.0;
        }
    }
    public static void addPoint(UUID uuid, Double questPoints){
        playerQuestPoints.putIfAbsent(uuid, 0.0d);
        playerQuestPoints.put(uuid, playerQuestPoints.get(uuid) + questPoints);
    }
    public static void setPoint(UUID uuid, Double points){
        playerQuestPoints.put(uuid, points);
    }
    public static void removePoint(UUID uuid, Double questPoints){
        playerQuestPoints.putIfAbsent(uuid, 0.0);
        playerQuestPoints.put(uuid, playerQuestPoints.get(uuid) - questPoints);
        if(playerQuestPoints.get(uuid) < 0){
            playerQuestPoints.put(uuid, 0d);
        }
    }
    public static void clearPoint(UUID uuid){
        playerQuestPoints.putIfAbsent(uuid, 0.0);
        playerQuestPoints.remove(uuid);
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
    public static void calculateQuestPointsMaxMoney(){
        double moneys = 0.0;
        for(Player player : Bukkit.getServer().getOnlinePlayers()){
            if(!Objects.equals(landsAPI.getLandPlayer(player.getUniqueId()),null) && !player.isOp()){
                int totalLandChunks = 0;
                for(Land land : landsAPI.getLandPlayer(player.getUniqueId()).getLands()){
                    if(land.getOwnerUID().equals(player.getUniqueId())){
                        totalLandChunks += land.getChunksAmount();
                    }
                }
                moneys += totalLandChunks * 3 * getActivity(player.getUniqueId());
                moneys += 20 * getActivity(player.getUniqueId());
            }
        }
        if(moneys >= maxMoney){
            maxMoney = moneys;
        }
    }

    public static Double serverTotalQuestPoints = 0.0;
    public static void calculatePoint(){
        serverTotalQuestPoints = 0.0;
        Bukkit.getLogger().info("moneys: " + maxMoney);
        playerQuestPoints.forEach((k,v) -> {
            if(Objects.equals(v,null) || Bukkit.getOfflinePlayer(k).isOp() || Objects.equals(v, 0d)){
                return;
            }
            serverTotalQuestPoints += pow(v, 0.5);
        });
        playerQuestPoints.forEach((k,v) -> {
            if(Objects.equals(v,null) || Bukkit.getOfflinePlayer(k).isOp() || Objects.equals(v, 0d)){
                return;
            }
            Double moneyToAdd = 0d;
            DecimalFormat df = new DecimalFormat("#.##");
            moneyToAdd = (pow(v, 0.5) / serverTotalQuestPoints) * maxMoney;
            moneyToAdd = Double.valueOf(df.format(moneyToAdd));
            Bukkit.getLogger().info(Bukkit.getOfflinePlayer(k).getName() + " has " + v + " questPoints, added: " + moneyToAdd + " $ ");
            if(!Objects.equals(Bukkit.getPlayer(k),null)){
                Bukkit.getPlayer(k).sendMessage(
                        Component.text()
                                .append(Component.text("已獲得本日任務點數獎勵: ").color(NamedTextColor.YELLOW))
                                .append(Component.text(moneyToAdd + " $").color(NamedTextColor.GOLD))
                );
            }
            economy.depositPlayer(Bukkit.getOfflinePlayer(k), moneyToAdd);
        });
        Bukkit.getLogger().info("serverTotalQuestPoints: " + serverTotalQuestPoints);
        if(playerQuestPoints.size() >= 10){
            Stream<Map.Entry<UUID,Double>> sorted =
                    playerQuestPoints.entrySet().stream()
                            .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()));
            rantimes = 0;
            sorted.forEach((entry) ->{
                if(rantimes < playerQuestPoints.size()/2){
                    addActivity(entry.getKey(), 0.05F);
                    Bukkit.getLogger().info("addedActivity: " + Bukkit.getPlayer(entry.getKey()));
                }
                rantimes ++;
            });
        }
        playerQuestPoints.clear();
        maxMoney = 0d;
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

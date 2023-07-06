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
    public static Double maxMoney = 0.0;
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
    public static void setPoint(UUID uuid, Double points){
        playerPoints.put(uuid, points);
    }
    public static void removePoint(UUID uuid, Double points){
        playerPoints.putIfAbsent(uuid, 0.0);
        playerPoints.put(uuid, playerPoints.get(uuid) - points);
        if(playerPoints.get(uuid) < 0){
            playerPoints.put(uuid, 0d);
        }
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

    public static void calculatePointsMaxMoney(){
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
                moneys += 10;
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
            if(Objects.equals(v,null) || Bukkit.getPlayer(k).isOp()){
                return;
            }
            serverTotalPoints += pow(v, 0.5);
        });
        playerPoints.forEach((k,v) -> {
            if(Objects.equals(v,null) || Bukkit.getPlayer(k).isOp()){
                return;
            }
            Double moneyToAdd = 0d;
            DecimalFormat df = new DecimalFormat("#.##");
            moneyToAdd = (pow(v, 0.5) / serverTotalPoints) * maxMoney;
            moneyToAdd = Double.valueOf(df.format(moneyToAdd));
            Bukkit.getLogger().info(Bukkit.getOfflinePlayer(k).getName() + " has " + v  + " points, added " + moneyToAdd);
            if(!Objects.equals(Bukkit.getPlayer(k),null)){
                Bukkit.getPlayer(k).sendMessage(
                        Component.text()
                                .append(Component.text("已獲得本日經驗點數獎勵: ").color(NamedTextColor.YELLOW))
                                .append(Component.text(moneyToAdd + " $").color(NamedTextColor.GOLD))
                );
            }
            economy.depositPlayer(Bukkit.getOfflinePlayer(k), (pow(v, 0.5) / serverTotalPoints) * maxMoney);
        });
        Bukkit.getLogger().info("serverTotalPoints: " + serverTotalPoints);
        playerPoints.clear();
        new BukkitRunnable() {
            @Override
            public void run() {
                maxMoney = 0d;
            }

        }.runTaskLater(Territory_conquest_economy.instance, 1);
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

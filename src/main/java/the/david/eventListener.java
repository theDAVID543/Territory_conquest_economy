package the.david;

import com.destroystokyo.paper.event.player.PlayerPickupExperienceEvent;
import me.angeschossen.lands.api.events.memberholder.MemberHolderUpkeepEvent;
import me.angeschossen.lands.api.events.player.PlayerTaxEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.util.Objects;
import java.util.UUID;

public class eventListener implements Listener {
    @EventHandler
    public void onPickExpOrb(PlayerPickupExperienceEvent e){
        if(e.getExperienceOrb().getExperience() > 0 && !e.isCancelled()){
            for(int exps = e.getExperienceOrb().getExperience(); exps > 0; exps--){
                points.addPoint(e.getPlayer().getUniqueId(), 1d);
            }
        }
    }
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        if(e.getPlayer().getKiller() != null){
            Double giveMoney = Territory_conquest_economy.getEconomy().getBalance(e.getPlayer()) * 0.01;
            Bukkit.getLogger().info(e.getPlayer().getName() + " gave " + e.getPlayer().getKiller().getName() + " " + giveMoney + " $");
            DecimalFormat format = new DecimalFormat("0.00");
            e.getPlayer().sendMessage(Component.text("您已被玩家 ")
                    .append(Component.text(e.getPlayer().getKiller().getName(), NamedTextColor.RED))
                    .append(Component.text(" 殺害,已扣除 "))
                    .append(Component.text(format.format(giveMoney) + " $", NamedTextColor.GOLD)));
            e.getPlayer().getKiller().sendMessage(Component.text("您已殺害玩家 ")
                    .append(Component.text(e.getPlayer().getName(), NamedTextColor.GREEN))
                    .append(Component.text(" ,已獲得 "))
                    .append(Component.text(format.format(giveMoney* 0.9925) + " $", NamedTextColor.GOLD)));
            Territory_conquest_economy.getEconomy().withdrawPlayer(e.getPlayer(), giveMoney);
            Territory_conquest_economy.getEconomy().depositPlayer(e.getPlayer().getKiller(), giveMoney* 0.9925);
            points.addPoint(e.getPlayer().getKiller().getUniqueId(), points.getPoint(e.getPlayer().getUniqueId()));
            questPoints.addPoint(e.getPlayer().getKiller().getUniqueId(), questPoints.getPoint(e.getPlayer().getUniqueId()));
        }
        points.clearPoint(e.getPlayer().getUniqueId());
        questPoints.clearPoint(e.getPlayer().getUniqueId());
    }
//    @EventHandler
//    public void onPlayerJoin(PlayerJoinEvent e){
//        Player player = e.getPlayer();
//        new BukkitRunnable(){
//            @Override
//            public void run() {
//                if(!Objects.equals(questPoints.playerCanCalculate.get(player.getUniqueId()), false)){
//                    questPoints.calculateLandCanAddMoney(player);
//                    points.calculateLandCanAddMoney(player);
//                    Bukkit.getLogger().info("calculating");
//                }
//            }
//        }.runTaskLater(Territory_conquest_economy.instance, 10L);
//
//    }
    @EventHandler
    public void onLandsUpkeep(MemberHolderUpkeepEvent e){
        UUID uuid = e.getMemberHolder().getOwnerUID();
        points.playerCanAddLandMoney.putIfAbsent(uuid, 0d);
        questPoints.playerCanAddLandMoney.putIfAbsent(uuid, 0d);
        points.playerCanAddLandMoney.put(uuid, points.playerCanAddLandMoney.get(uuid) + e.getUpkeep());
        questPoints.playerCanAddLandMoney.put(uuid, points.playerCanAddLandMoney.get(uuid) + e.getUpkeep());
        Bukkit.getLogger().info(points.playerCanAddLandMoney + " " + questPoints.playerCanAddLandMoney);
    }
}

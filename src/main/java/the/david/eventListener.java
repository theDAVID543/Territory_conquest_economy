package the.david;

import com.destroystokyo.paper.event.player.PlayerPickupExperienceEvent;
import com.google.common.base.Objects;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.text.DecimalFormat;

public class eventListener implements Listener {
    @EventHandler
    public void onPickExpOrb(PlayerPickupExperienceEvent e){
        if(e.getExperienceOrb().getExperience() > 0 && !e.isCancelled()){
            if(!Objects.equal(ConfigReader.playerExpGot(e.getPlayer().getUniqueId()), null)){
                ConfigReader.setPlayerExpConfig(e.getPlayer().getUniqueId(), e.getExperienceOrb().getExperience() + ConfigReader.playerExpGot(e.getPlayer().getUniqueId()));
            }else{
                ConfigReader.setPlayerExpConfig(e.getPlayer().getUniqueId(), e.getExperienceOrb().getExperience());
            }
            while(!Objects.equal(ConfigReader.playerExpGot(e.getPlayer().getUniqueId()), null) &&  ConfigReader.playerExpGot(e.getPlayer().getUniqueId()) >= 5){
//                Territory_conquest_economy.getEconomy().depositPlayer(e.getPlayer(),0.01);
                points.addPoint(e.getPlayer().getUniqueId(), 0.01d);
                ConfigReader.setPlayerExpConfig(e.getPlayer().getUniqueId(), ConfigReader.playerExpGot(e.getPlayer().getUniqueId()) - 5);
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
        }
        points.clearPoint(e.getPlayer().getUniqueId());
    }
}

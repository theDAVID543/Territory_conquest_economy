package the.david;

import com.destroystokyo.paper.event.player.PlayerPickupExperienceEvent;
import com.google.common.base.Objects;
import io.papermc.paper.event.block.BlockBreakBlockEvent;
import me.angeschossen.lands.api.LandsIntegration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Arrays;
import java.util.List;

public class eventListener implements Listener {
    private List<Material> crops = Arrays.asList(new Material[]{Material.WHEAT, Material.COCOA, Material.BEETROOTS, Material.CARROTS, Material.POTATOES, Material.SWEET_BERRY_BUSH});
    private LandsIntegration landsAPI = Territory_conquest_economy.landsAPI;
    @EventHandler
    public void onPickExpOrb(PlayerPickupExperienceEvent e){
        if(!Objects.equal(ConfigReader.playerExpGot(e.getPlayer().getUniqueId()), null)){
            ConfigReader.setPlayerExpConfig(e.getPlayer().getUniqueId(), e.getExperienceOrb().getExperience() + ConfigReader.playerExpGot(e.getPlayer().getUniqueId()));
        }else{
            ConfigReader.setPlayerExpConfig(e.getPlayer().getUniqueId(), e.getExperienceOrb().getExperience());
        }
        if(!Objects.equal(landsAPI.getArea(e.getPlayer().getLocation()),null)){
            if(!Objects.equal(ConfigReader.landExpGot(landsAPI.getArea(e.getPlayer().getLocation()).getLand().getId()),null)){
                ConfigReader.setLandExpConfig(landsAPI.getArea(e.getPlayer().getLocation()).getLand().getId(), e.getExperienceOrb().getExperience() + ConfigReader.landExpGot(landsAPI.getArea(e.getPlayer().getLocation()).getLand().getId()));
            }else{
                ConfigReader.setLandExpConfig(landsAPI.getArea(e.getPlayer().getLocation()).getLand().getId(), e.getExperienceOrb().getExperience());
            }
        }
        while(!Objects.equal(ConfigReader.playerExpGot(e.getPlayer().getUniqueId()), null) && ConfigReader.playerExpGot(e.getPlayer().getUniqueId()) >= 1){
            Territory_conquest_economy.getEconomy().depositPlayer(e.getPlayer(),0.01);
            ConfigReader.setPlayerExpConfig(e.getPlayer().getUniqueId(), ConfigReader.playerExpGot(e.getPlayer().getUniqueId()) - 1);
        }
        while(!Objects.equal(ConfigReader.landExpGot(landsAPI.getArea(e.getPlayer().getLocation()).getLand().getId()), null) && ConfigReader.landExpGot(landsAPI.getArea(e.getPlayer().getLocation()).getLand().getId()) >= 25 - ((landsAPI.getArea(e.getPlayer().getLocation()).getLand().getLevel().getPosition() - 1) * 2 )){
            landsAPI.getArea(e.getPlayer().getLocation()).getLand().addBalance(0.01);
            ConfigReader.setLandExpConfig(landsAPI.getArea(e.getPlayer().getLocation()).getLand().getId(), ConfigReader.landExpGot(landsAPI.getArea(e.getPlayer().getLocation()).getLand().getId()) - 25 - ((landsAPI.getArea(e.getPlayer().getLocation()).getLand().getLevel().getPosition() - 1) * 2 ) );
            landsAPI.getArea(e.getPlayer().getLocation()).getLand();
        }
    }
    @EventHandler
    public void onBlockBreakBlock(BlockBreakBlockEvent e){
        if(crops.contains(e.getBlock().getType())){
            Ageable crop = (Ageable) e.getBlock().getBlockData();
            if(crop.getAge() == crop.getMaximumAge()){
                Bukkit.getLogger().info("grown");
            }
        }
    }
    @EventHandler
    public void onBreakBlock(BlockBreakEvent e){
        if(crops.contains(e.getBlock().getType())){
            Ageable crop = (Ageable) e.getBlock().getBlockData();
            if(crop.getAge() == crop.getMaximumAge()){
                Bukkit.getLogger().info("grown");
            }
        }
    }
}

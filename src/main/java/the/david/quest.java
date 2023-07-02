package the.david;

import io.papermc.paper.event.player.PlayerTradeEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.*;

public class quest implements Listener {
    public static List<EntityType> entityTypes = Arrays.asList(
            EntityType.ALLAY,
            EntityType.AXOLOTL,
            EntityType.BAT,
            EntityType.BEE,
            EntityType.BLAZE,
            EntityType.CAT,
            EntityType.COD,
            EntityType.COW,
            EntityType.CAVE_SPIDER,
            EntityType.CHICKEN,
            EntityType.CREEPER,
            EntityType.CAMEL,
            EntityType.DOLPHIN,
            EntityType.DONKEY,
            EntityType.DROWNED,
            EntityType.ELDER_GUARDIAN,
            EntityType.ENDERMAN,
            EntityType.ENDERMITE,
            EntityType.EVOKER,
            EntityType.FOX,
            EntityType.FROG,
            EntityType.GHAST,
            EntityType.GOAT,
            EntityType.GUARDIAN,
            EntityType.HOGLIN,
            EntityType.HUSK,
            EntityType.HORSE,
            EntityType.IRON_GOLEM,
            EntityType.LLAMA,
            EntityType.MAGMA_CUBE,
            EntityType.MULE,
            EntityType.MUSHROOM_COW,
            EntityType.OCELOT,
            EntityType.PANDA,
            EntityType.PIG,
            EntityType.PARROT,
            EntityType.PHANTOM,
            EntityType.PIGLIN,
            EntityType.PIGLIN_BRUTE,
            EntityType.PILLAGER,
            EntityType.POLAR_BEAR,
            EntityType.PUFFERFISH,
            EntityType.RABBIT,
            EntityType.RAVAGER,
            EntityType.SALMON,
            EntityType.SHEEP,
            EntityType.SHULKER,
            EntityType.SILVERFISH,
            EntityType.SKELETON_HORSE,
            EntityType.SLIME,
            EntityType.SPIDER,
            EntityType.STRAY,
            EntityType.SNIFFER,
            EntityType.SQUID,
            EntityType.STRIDER,
            EntityType.TADPOLE,
            EntityType.TURTLE,
            EntityType.TROPICAL_FISH,
            EntityType.VEX,
            EntityType.VILLAGER,
            EntityType.VINDICATOR,
            EntityType.WARDEN,
            EntityType.WITCH,
            EntityType.WOLF,
            EntityType.WITHER,
            EntityType.WITHER_SKELETON,
            EntityType.ZOGLIN,
            EntityType.ZOMBIE,
            EntityType.ZOMBIE_VILLAGER,
            EntityType.ZOMBIFIED_PIGLIN
    );
    public static List<String> quests = Arrays.asList(
            "killEntity",
            "trade",
            "mineOre"
    );
    public static List<Material> ores = Arrays.asList(
            Material.COAL_ORE,
            Material.COPPER_ORE,
            Material.DIAMOND_ORE,
            Material.EMERALD_ORE,
            Material.GOLD_ORE,
            Material.IRON_ORE,
            Material.LAPIS_ORE,
            Material.REDSTONE_ORE,
            Material.DEEPSLATE_COAL_ORE,
            Material.DEEPSLATE_COPPER_ORE,
            Material.DEEPSLATE_DIAMOND_ORE,
            Material.DEEPSLATE_EMERALD_ORE,
            Material.DEEPSLATE_GOLD_ORE,
            Material.DEEPSLATE_IRON_ORE,
            Material.DEEPSLATE_LAPIS_ORE,
            Material.DEEPSLATE_REDSTONE_ORE,
            Material.NETHER_GOLD_ORE,
            Material.NETHER_QUARTZ_ORE
            );
    public static List<Villager.Profession> professions = Arrays.asList(
            Villager.Profession.ARMORER,
            Villager.Profession.BUTCHER,
            Villager.Profession.CLERIC,
            Villager.Profession.CARTOGRAPHER,
            Villager.Profession.FARMER,
            Villager.Profession.FISHERMAN,
            Villager.Profession.FLETCHER,
            Villager.Profession.LEATHERWORKER,
            Villager.Profession.LIBRARIAN,
            Villager.Profession.MASON,
            Villager.Profession.SHEPHERD,
            Villager.Profession.TOOLSMITH,
            Villager.Profession.WEAPONSMITH
    );
    public static EntityType randomEntityType(){
        Random rand = new Random();
        return entityTypes.get(rand.nextInt(entityTypes.size()));
    }
    public static String todayQuest;
    public static EntityType todayType;
    public static Villager.Profession todayVillagerProfession;
    public static String randomQuestType(){
        Random rand = new Random();
        return quests.get(rand.nextInt(quests.size()));
    }
    public static Villager.Profession randomProfession(){
        Random rand = new Random();
        return professions.get(rand.nextInt(professions.size()));
    }

    public static void randomQuest(){
        todayQuest = randomQuestType();
        if(todayQuest.equals("killEntity")){
            todayType = randomEntityType();
            Bukkit.getServer().sendMessage(
                    Component.text()
                            .append(Component.text("今日任務: ").color(NamedTextColor.GOLD))
                            .append(Component.text("殺死生物 ").color(NamedTextColor.DARK_RED))
                            .append(Component.text(placeholderRegister.translation.get(todayType.translationKey())).color(NamedTextColor.RED))
            );
            todayVillagerProfession = null;
        }else if(todayQuest.equals("trade")){
            todayVillagerProfession = randomProfession();
            Bukkit.getServer().sendMessage(
                    Component.text()
                            .append(Component.text("今日任務: ").color(NamedTextColor.GOLD))
                            .append(Component.text("與村民 ").color(NamedTextColor.GREEN))
                            .append(Component.text(placeholderRegister.translation.get(todayVillagerProfession.translationKey())).color(TextColor.color(0x85590E)))
                            .append(Component.text(" 交易").color(NamedTextColor.GREEN))
            );
            todayType = null;
        }else if(todayQuest.equals("mineOre")){
            Bukkit.getServer().sendMessage(
                    Component.text()
                            .append(Component.text("今日任務: ").color(NamedTextColor.GOLD))
                            .append(Component.text("挖掘礦物").color(NamedTextColor.DARK_GRAY))
            );
        }
    }
    @EventHandler
    public void onEntityDeath(EntityDeathEvent e){

    }
    @EventHandler
    public void onBlockDropExp(BlockExpEvent e){
        if(ores.contains(e.getBlock().getType())){

        }
    }
    @EventHandler
    public void onTrade(PlayerTradeEvent e){
        Villager villager = (Villager) e.getVillager();
        Bukkit.getLogger().info(String.valueOf(villager.getProfession()));
    }
}

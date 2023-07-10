package the.david;

import com.destroystokyo.paper.event.player.PlayerElytraBoostEvent;
import io.papermc.paper.event.player.PlayerTradeEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerRiptideEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class quest implements Listener {
    public static final List<EntityType> entityTypes = Arrays.asList(
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
    public static final List<String> quests = Arrays.asList(
            "killEntity",
            "killEntity",
            "killEntity",
            "killEntity",
            "killEntity",
            "killEntity",
            "killEntity",
            "killEntity",
            "trade",
            "trade",
            "trade",
            "mineOre",
            "mineOre",
            "mineOre",
            "exploreBiome",
            "exploreBiome"
    );
    public static final List<Material> ores = Arrays.asList(
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
    public static final List<Villager.Profession> professions = Arrays.asList(
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
    public static Villager.Profession todayVillagerProfession;
    public static String randomQuestType(){
        Random rand = new Random();
        return quests.get(rand.nextInt(quests.size()));
    }
    public static Villager.Profession randomProfession(){
        Random rand = new Random();
        return professions.get(rand.nextInt(professions.size()));
    }
    public static String todayQuest;
    public static EntityType todayType;

    public static void randomQuest(){
        todayQuest = randomQuestType();
        switch (todayQuest) {
            case "killEntity":
                todayType = randomEntityType();
                Bukkit.getServer().sendMessage(
                        Component.text()
                                .append(Component.text("今日任務: ").color(NamedTextColor.GOLD))
                                .append(Component.text("殺死生物 ").color(NamedTextColor.DARK_RED))
                                .append(Component.text(placeholderRegister.translation.get(todayType.translationKey())).color(NamedTextColor.RED))
                );
                todayVillagerProfession = null;
                break;
            case "trade":
                todayVillagerProfession = randomProfession();
                Bukkit.getServer().sendMessage(
                        Component.text()
                                .append(Component.text("今日任務: ").color(NamedTextColor.GOLD))
                                .append(Component.text("與村民 ").color(NamedTextColor.GREEN))
                                .append(Component.text(placeholderRegister.translation.get(todayVillagerProfession.translationKey())).color(TextColor.color(0x85590E)))
                                .append(Component.text(" 交易").color(NamedTextColor.GREEN))
                );
                todayType = null;
                break;
            case "mineOre":
                Bukkit.getServer().sendMessage(
                        Component.text()
                                .append(Component.text("今日任務: ").color(NamedTextColor.GOLD))
                                .append(Component.text("挖掘礦物").color(NamedTextColor.DARK_GRAY))
                );
                break;
            case "exploreBiome":
                Bukkit.getServer().sendMessage(
                        Component.text()
                                .append(Component.text("今日任務: ").color(NamedTextColor.GOLD))
                                .append(Component.text("探索生態域").color(NamedTextColor.YELLOW))
                );
                countBiome();
                break;
        }
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDeath(EntityDeathEvent e){
        if(Objects.equals(todayQuest, "killEntity") && e.getEntity().getType().equals(todayType) && !Objects.equals(e.getEntity().getKiller(), null)){
            new BukkitRunnable(){
                public void run(){
                    EntityType type = e.getEntityType();
                    if(e.getDroppedExp() > 0 || type.equals(EntityType.ALLAY) || type.equals(EntityType.VILLAGER) || type.equals(EntityType.BAT) || type.equals(EntityType.TADPOLE)){
                        questPoints.addPoint(Objects.requireNonNull(e.getEntity().getKiller()).getUniqueId(),1d);
                    }
                }
            }.runTaskLater(Territory_conquest_economy.instance, 1);
        }
    }
    public static final NamespacedKey keyPlacedOre = new NamespacedKey(Territory_conquest_economy.instance, "placedOre");
    @EventHandler
    public void onBlockBreak(BlockDropItemEvent e){
        if(ores.contains(e.getBlockState().getType())){
            if(e.getBlockState().getChunk().getPersistentDataContainer().has(keyPlacedOre) && e.getBlockState().getChunk().getPersistentDataContainer().get(keyPlacedOre, PersistentDataType.INTEGER) > 0) {
                int placedOresBef = e.getBlockState().getChunk().getPersistentDataContainer().get(keyPlacedOre, PersistentDataType.INTEGER);
                e.getBlockState().getChunk().getPersistentDataContainer().set(keyPlacedOre, PersistentDataType.INTEGER, placedOresBef - 1);
                return;
            }
        }
        if(todayQuest.equals("mineOre") && ores.contains(e.getBlockState().getType())){
            if(e.getItems().isEmpty()){
                return;
            }
            Set<Material> oreDrops = Set.of(
                    Material.COAL,
                    Material.RAW_IRON,
                    Material.RAW_GOLD,
                    Material.RAW_COPPER,
                    Material.DIAMOND,
                    Material.QUARTZ,
                    Material.REDSTONE,
                    Material.EMERALD,
                    Material.LAPIS_LAZULI
            );
            Set<Material> items = new HashSet<>();
            for(Item item : e.getItems()){
                items.add(item.getItemStack().getType());
            }
            if(oreDrops.containsAll(items)){
                questPoints.addPoint(e.getPlayer().getUniqueId(), 1d);
            }
        }
    }
    @EventHandler
    public void onPistonExtend(BlockPistonExtendEvent e){
        for(Block block : e.getBlocks()){
            if(ores.contains(block.getType())){
                if(block.getChunk().getPersistentDataContainer().has(keyPlacedOre) && block.getChunk().getPersistentDataContainer().get(keyPlacedOre, PersistentDataType.INTEGER) > 0){
                    int placedOresBef = block.getChunk().getPersistentDataContainer().get(keyPlacedOre, PersistentDataType.INTEGER);
                    block.getChunk().getPersistentDataContainer().set(keyPlacedOre, PersistentDataType.INTEGER, placedOresBef - 1);
                }
                int x = 0;
                int z = 0;
                if(e.getDirection().equals(BlockFace.EAST)){
                    x = 1;
                }else if(e.getDirection().equals(BlockFace.WEST)){
                    x = -1;
                }else if(e.getDirection().equals(BlockFace.SOUTH)){
                    z = 1;
                }else if(e.getDirection().equals(BlockFace.NORTH)){
                    z = -1;
                }
                Location newLoc = block.getLocation().add(x,0,z);
                if(newLoc.getChunk().getPersistentDataContainer().has(keyPlacedOre)){
                    int placedOresBef = newLoc.getChunk().getPersistentDataContainer().get(keyPlacedOre, PersistentDataType.INTEGER);
                    newLoc.getChunk().getPersistentDataContainer().set(keyPlacedOre, PersistentDataType.INTEGER, placedOresBef + 1);
                }else{
                    newLoc.getChunk().getPersistentDataContainer().set(keyPlacedOre, PersistentDataType.INTEGER, 1);
                }
            }
        }
    }
    @EventHandler
    public void onPistonRetract(BlockPistonRetractEvent e){
        for(Block block : e.getBlocks()){
            if(ores.contains(block.getType())){
                if(block.getChunk().getPersistentDataContainer().has(keyPlacedOre) && block.getChunk().getPersistentDataContainer().get(keyPlacedOre, PersistentDataType.INTEGER) > 0){
                    int placedOresBef = block.getChunk().getPersistentDataContainer().get(keyPlacedOre, PersistentDataType.INTEGER);
                    block.getChunk().getPersistentDataContainer().set(keyPlacedOre, PersistentDataType.INTEGER, placedOresBef - 1);
                }
                int x = 0;
                int z = 0;
                if(e.getDirection().equals(BlockFace.EAST)){
                    x = 1;
                }else if(e.getDirection().equals(BlockFace.WEST)){
                    x = -1;
                }else if(e.getDirection().equals(BlockFace.SOUTH)){
                    z = 1;
                }else if(e.getDirection().equals(BlockFace.NORTH)){
                    z = -1;
                }
                Location newLoc = block.getLocation().add(x,0,z);
                if(newLoc.getChunk().getPersistentDataContainer().has(keyPlacedOre)){
                    int placedOresBef = newLoc.getChunk().getPersistentDataContainer().get(keyPlacedOre, PersistentDataType.INTEGER);
                    newLoc.getChunk().getPersistentDataContainer().set(keyPlacedOre, PersistentDataType.INTEGER, placedOresBef + 1);
                }else{
                    newLoc.getChunk().getPersistentDataContainer().set(keyPlacedOre, PersistentDataType.INTEGER, 1);
                }
            }
        }
    }
    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent e){
        if(ores.contains(e.getBlock().getType())){
            if(e.getBlock().getChunk().getPersistentDataContainer().has(keyPlacedOre)){
                int placedOresBef = e.getBlock().getChunk().getPersistentDataContainer().get(keyPlacedOre, PersistentDataType.INTEGER);
                e.getBlock().getChunk().getPersistentDataContainer().set(keyPlacedOre, PersistentDataType.INTEGER, placedOresBef + 1);
            }else{
                e.getBlock().getChunk().getPersistentDataContainer().set(keyPlacedOre, PersistentDataType.INTEGER, 1);
            }
        }
    }
    @EventHandler
    public void onTrade(PlayerTradeEvent e){
        Villager villager = (Villager) e.getVillager();
        if(todayQuest.equals("trade") && villager.getProfession().equals(todayVillagerProfession)){
            questPoints.addPoint(e.getPlayer().getUniqueId(),1d);
        }
    }
    @EventHandler
    public void onElytraBoost(PlayerElytraBoostEvent e){
        if(todayQuest.equals("exploreBiome")){
            questPoints.removePoint(e.getPlayer().getUniqueId(),1d);
        }
    }
    @EventHandler
    public void onRiptide(PlayerRiptideEvent e){
        if(todayQuest.equals("exploreBiome")){
            questPoints.removePoint(e.getPlayer().getUniqueId(),1d);
        }
    }
    public static void countBiome(){
        Map<UUID, Set<Biome>> playerBiomes = new HashMap<>();
        if(todayQuest.equals("exploreBiome")){
            new BukkitRunnable() {
                @Override
                public void run() {
                    if(!todayQuest.equals("exploreBiome")){
                        playerBiomes.clear();
                        this.cancel();
                    }
                    for(Player player : Bukkit.getOnlinePlayers()){
                        UUID uuid = player.getUniqueId();
                        Set<Biome> biomes = new HashSet<>();
                        if(Objects.equals(playerBiomes.get(uuid),null)) {
                            questPoints.addPoint(uuid, 1d);
                        }else if(!playerBiomes.get(uuid).isEmpty()){
                            biomes = playerBiomes.get(uuid);
                            if(!biomes.contains(player.getLocation().getBlock().getBiome())){
                                questPoints.addPoint(uuid, 1d);
                            }
                        }
                        biomes.add(player.getLocation().getBlock().getBiome());
                        playerBiomes.put(uuid, biomes);
                    }
                }
            }.runTaskTimer(Territory_conquest_economy.instance,0,20*5);
        }
    }
}

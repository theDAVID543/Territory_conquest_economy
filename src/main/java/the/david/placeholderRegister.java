package the.david;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.kyori.adventure.translation.TranslationRegistry;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.util.Map.entry;

public class placeholderRegister extends PlaceholderExpansion implements Listener {
    public static final Map<String,String> translation = Map.<String, String>ofEntries(
            entry("entity.minecraft.allay", "悅靈"),
            entry("entity.minecraft.area_effect_cloud", "藥水效果雲"),
            entry("entity.minecraft.armor_stand", "盔甲座"),
            entry("entity.minecraft.arrow", "箭矢"),
            entry("entity.minecraft.axolotl", "六角恐龍"),
            entry("entity.minecraft.bat", "蝙蝠"),
            entry("entity.minecraft.bee", "蜜蜂"),
            entry("entity.minecraft.blaze", "烈焰使者"),
            entry("entity.minecraft.block_display", "方塊展示實體"),
            entry("entity.minecraft.boat", "船"),
            entry("entity.minecraft.camel", "駱駝"),
            entry("entity.minecraft.cat", "貓"),
            entry("entity.minecraft.cave_spider", "洞穴蜘蛛"),
            entry("entity.minecraft.chest_boat", "儲物箱船"),
            entry("entity.minecraft.chest_minecart", "儲物箱礦車"),
            entry("entity.minecraft.chicken", "雞"),
            entry("entity.minecraft.cod", "鱈魚"),
            entry("entity.minecraft.command_block_minecart", "指令方塊礦車"),
            entry("entity.minecraft.cow", "牛"),
            entry("entity.minecraft.creeper", "苦力怕"),
            entry("entity.minecraft.dolphin", "海豚"),
            entry("entity.minecraft.donkey", "驢子"),
            entry("entity.minecraft.dragon_fireball", "龍炎彈"),
            entry("entity.minecraft.drowned", "沉屍"),
            entry("entity.minecraft.egg", "拋出的雞蛋"),
            entry("entity.minecraft.elder_guardian", "遠古深海守衛"),
            entry("entity.minecraft.end_crystal", "終界水晶"),
            entry("entity.minecraft.ender_dragon", "終界龍"),
            entry("entity.minecraft.ender_pearl", "拋出的終界珍珠"),
            entry("entity.minecraft.enderman", "終界使者"),
            entry("entity.minecraft.endermite", "終界蟎"),
            entry("entity.minecraft.evoker", "喚魔者"),
            entry("entity.minecraft.evoker_fangs", "喚魔者尖牙"),
            entry("entity.minecraft.experience_bottle", "拋出的經驗瓶"),
            entry("entity.minecraft.experience_orb", "經驗球"),
            entry("entity.minecraft.eye_of_ender", "終界之眼"),
            entry("entity.minecraft.falling_block", "掉落的方塊"),
            entry("entity.minecraft.falling_block_type", "掉落的 %s"),
            entry("entity.minecraft.fireball", "火球"),
            entry("entity.minecraft.firework_rocket", "煙火"),
            entry("entity.minecraft.fishing_bobber", "浮標"),
            entry("entity.minecraft.fox", "狐狸"),
            entry("entity.minecraft.frog", "青蛙"),
            entry("entity.minecraft.furnace_minecart", "熔爐礦車"),
            entry("entity.minecraft.ghast", "地獄幽靈"),
            entry("entity.minecraft.giant", "巨人"),
            entry("entity.minecraft.glow_item_frame", "螢光物品展示框"),
            entry("entity.minecraft.glow_squid", "螢光魷魚"),
            entry("entity.minecraft.goat", "山羊"),
            entry("entity.minecraft.guardian", "深海守衛"),
            entry("entity.minecraft.hoglin", "豬布獸"),
            entry("entity.minecraft.hopper_minecart", "漏斗礦車"),
            entry("entity.minecraft.horse", "馬"),
            entry("entity.minecraft.husk", "屍殼"),
            entry("entity.minecraft.illusioner", "幻術師"),
            entry("entity.minecraft.interaction", "互動實體"),
            entry("entity.minecraft.iron_golem", "鐵魔像"),
            entry("entity.minecraft.item", "物品"),
            entry("entity.minecraft.item_display", "物品展示實體"),
            entry("entity.minecraft.item_frame", "物品展示框"),
            entry("entity.minecraft.killer_bunny", "殺手兔"),
            entry("entity.minecraft.leash_knot", "拴繩"),
            entry("entity.minecraft.lightning_bolt", "閃電電流"),
            entry("entity.minecraft.llama", "駱馬"),
            entry("entity.minecraft.llama_spit", "駱馬唾液"),
            entry("entity.minecraft.magma_cube", "岩漿立方怪"),
            entry("entity.minecraft.marker", "標記"),
            entry("entity.minecraft.minecart", "礦車"),
            entry("entity.minecraft.mooshroom", "哞菇"),
            entry("entity.minecraft.mule", "騾子"),
            entry("entity.minecraft.ocelot", "山貓"),
            entry("entity.minecraft.painting", "繪畫"),
            entry("entity.minecraft.panda", "貓熊"),
            entry("entity.minecraft.parrot", "鸚鵡"),
            entry("entity.minecraft.phantom", "夜魅"),
            entry("entity.minecraft.pig", "豬"),
            entry("entity.minecraft.piglin", "豬布林"),
            entry("entity.minecraft.piglin_brute", "豬布林蠻兵"),
            entry("entity.minecraft.pillager", "掠奪者"),
            entry("entity.minecraft.player", "玩家"),
            entry("entity.minecraft.polar_bear", "北極熊"),
            entry("entity.minecraft.potion", "藥水"),
            entry("entity.minecraft.pufferfish", "河豚"),
            entry("entity.minecraft.rabbit", "兔子"),
            entry("entity.minecraft.ravager", "劫毀獸"),
            entry("entity.minecraft.salmon", "鮭魚"),
            entry("entity.minecraft.sheep", "綿羊"),
            entry("entity.minecraft.shulker", "界伏蚌"),
            entry("entity.minecraft.shulker_bullet", "界伏彈"),
            entry("entity.minecraft.silverfish", "蠹魚"),
            entry("entity.minecraft.skeleton", "骷髏"),
            entry("entity.minecraft.skeleton_horse", "骷髏馬"),
            entry("entity.minecraft.slime", "史萊姆"),
            entry("entity.minecraft.small_fireball", "小火球"),
            entry("entity.minecraft.sniffer", "嗅探獸"),
            entry("entity.minecraft.snow_golem", "雪人"),
            entry("entity.minecraft.snowball", "雪球"),
            entry("entity.minecraft.spawner_minecart", "生怪磚礦車"),
            entry("entity.minecraft.spectral_arrow", "追跡之箭"),
            entry("entity.minecraft.spider", "蜘蛛"),
            entry("entity.minecraft.squid", "魷魚"),
            entry("entity.minecraft.stray", "流髑"),
            entry("entity.minecraft.strider", "熾足獸"),
            entry("entity.minecraft.tadpole", "蝌蚪"),
            entry("entity.minecraft.text_display", "文字展示實體"),
            entry("entity.minecraft.tnt", "點燃的 TNT"),
            entry("entity.minecraft.tnt_minecart", "TNT 礦車"),
            entry("entity.minecraft.trader_llama", "商駝"),
            entry("entity.minecraft.trident", "三叉戟"),
            entry("entity.minecraft.tropical_fish", "熱帶魚"),
            entry("entity.minecraft.tropical_fish.predefined.0", "海葵魚"),
            entry("entity.minecraft.tropical_fish.predefined.1", "長鼻高鰭刺尾魚"),
            entry("entity.minecraft.tropical_fish.predefined.10", "角蝶魚"),
            entry("entity.minecraft.tropical_fish.predefined.11", "華麗蝴蝶魚"),
            entry("entity.minecraft.tropical_fish.predefined.12", "鸚哥魚"),
            entry("entity.minecraft.tropical_fish.predefined.13", "額斑刺蝶魚"),
            entry("entity.minecraft.tropical_fish.predefined.14", "赤慈鯛"),
            entry("entity.minecraft.tropical_fish.predefined.15", "紅唇真蛇䲁"),
            entry("entity.minecraft.tropical_fish.predefined.16", "西大西洋笛鯛"),
            entry("entity.minecraft.tropical_fish.predefined.17", "馬鮁"),
            entry("entity.minecraft.tropical_fish.predefined.18", "白條雙鋸魚"),
            entry("entity.minecraft.tropical_fish.predefined.19", "鱗魨"),
            entry("entity.minecraft.tropical_fish.predefined.2", "擬刺尾鯛"),
            entry("entity.minecraft.tropical_fish.predefined.20", "高鰭鸚哥魚"),
            entry("entity.minecraft.tropical_fish.predefined.21", "黃高鰭刺尾魚"),
            entry("entity.minecraft.tropical_fish.predefined.3", "蝴蝶魚"),
            entry("entity.minecraft.tropical_fish.predefined.4", "慈鯛"),
            entry("entity.minecraft.tropical_fish.predefined.5", "小丑魚"),
            entry("entity.minecraft.tropical_fish.predefined.6", "五彩搏魚"),
            entry("entity.minecraft.tropical_fish.predefined.7", "紅黃繡雀鯛"),
            entry("entity.minecraft.tropical_fish.predefined.8", "川紋笛鯛"),
            entry("entity.minecraft.tropical_fish.predefined.9", "鬚鯛"),
            entry("entity.minecraft.tropical_fish.type.betty", "貝蒂魚"),
            entry("entity.minecraft.tropical_fish.type.blockfish", "方塊魚"),
            entry("entity.minecraft.tropical_fish.type.brinely", "鹽水魚"),
            entry("entity.minecraft.tropical_fish.type.clayfish", "黏土魚"),
            entry("entity.minecraft.tropical_fish.type.dasher", "迅泳魚"),
            entry("entity.minecraft.tropical_fish.type.flopper", "翅翼魚"),
            entry("entity.minecraft.tropical_fish.type.glitter", "閃魚"),
            entry("entity.minecraft.tropical_fish.type.kob", "赤羚魚"),
            entry("entity.minecraft.tropical_fish.type.snooper", "藏礁魚"),
            entry("entity.minecraft.tropical_fish.type.spotty", "斑點魚"),
            entry("entity.minecraft.tropical_fish.type.stripey", "條紋魚"),
            entry("entity.minecraft.tropical_fish.type.sunstreak", "日紋魚"),
            entry("entity.minecraft.turtle", "海龜"),
            entry("entity.minecraft.vex", "惱鬼"),
            entry("entity.minecraft.villager", "村民"),
            entry("entity.minecraft.villager.armorer", "製甲師"),
            entry("entity.minecraft.villager.butcher", "屠夫"),
            entry("entity.minecraft.villager.cartographer", "製圖師"),
            entry("entity.minecraft.villager.cleric", "神職人員"),
            entry("entity.minecraft.villager.farmer", "農夫"),
            entry("entity.minecraft.villager.fisherman", "漁夫"),
            entry("entity.minecraft.villager.fletcher", "製箭師"),
            entry("entity.minecraft.villager.leatherworker", "皮匠"),
            entry("entity.minecraft.villager.librarian", "圖書管理員"),
            entry("entity.minecraft.villager.mason", "石匠"),
            entry("entity.minecraft.villager.nitwit", "傻子"),
            entry("entity.minecraft.villager.none", "村民"),
            entry("entity.minecraft.villager.shepherd", "牧羊人"),
            entry("entity.minecraft.villager.toolsmith", "工具匠"),
            entry("entity.minecraft.villager.weaponsmith", "武器匠"),
            entry("entity.minecraft.vindicator", "衛道士"),
            entry("entity.minecraft.wandering_trader", "流浪商人"),
            entry("entity.minecraft.warden", "伏守者"),
            entry("entity.minecraft.witch", "女巫"),
            entry("entity.minecraft.wither", "凋零怪"),
            entry("entity.minecraft.wither_skeleton", "凋零骷髏"),
            entry("entity.minecraft.wither_skull", "凋零頭顱"),
            entry("entity.minecraft.wolf", "狼"),
            entry("entity.minecraft.zoglin", "豬屍獸"),
            entry("entity.minecraft.zombie", "殭屍"),
            entry("entity.minecraft.zombie_horse", "殭屍馬"),
            entry("entity.minecraft.zombie_villager", "殭屍村民"),
            entry("entity.minecraft.zombified_piglin", "殭屍化豬布林")
    );
    @Override
    public @NotNull String getIdentifier() {
        return "tce";
    }

    @Override
    public @NotNull String getAuthor() {
        return "tce";
    }

    @Override
    public @NotNull String getVersion() {
        return "0.0.1";
    }
    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }
    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if(params.equalsIgnoreCase("points")){
            DecimalFormat df = new DecimalFormat("#.##");
            return df.format(points.getPoint(player.getUniqueId()));
        }
        if(params.equalsIgnoreCase("activity")){
            DecimalFormat df = new DecimalFormat("#.##");
            return df.format(points.getActivity(player.getUniqueId()));
        }
        if(params.equalsIgnoreCase("quest")){
            if(!Objects.equals(quest.todayQuest,null)){
                if(quest.todayQuest.equals("killEntity")){
                    return "&4殺死生物: &c" + translation.get(quest.todayType.translationKey());
                }else if(quest.todayQuest.equals("trade")){
                    return "&a與村民 &2" + translation.get(quest.todayVillagerProfession.translationKey()) + " &a交易";
                }else if(quest.todayQuest.equals("mineOre")){
                    return "&8&l挖掘礦物";
                }
            }else{
                return null;
            }
        }
        if(params.equalsIgnoreCase("questEntity")){
            if(!Objects.equals(quest.todayQuest,null)){
                return translation.get(quest.todayType.translationKey());
            }
            return null;
        }

        return null; // Placeholder is unknown by the Expansion
    }
}

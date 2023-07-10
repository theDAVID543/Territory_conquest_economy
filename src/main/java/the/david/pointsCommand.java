package the.david;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static java.lang.Math.pow;

public class pointsCommand implements CommandExecutor {
    private static Double serverTotalPoints = 0.0;
    private static Double pointsMaxMoney = 0.0;
    private static Map<UUID, Double> playerPoints;
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        serverTotalPoints = 0.0;
        pointsMaxMoney = points.maxMoney;
        playerPoints = points.playerPoints;
        if(playerPoints.size() > 0){
            playerPoints.forEach((k,v) -> {
                if(Objects.equals(v,null) || Bukkit.getOfflinePlayer(k).isOp() || Objects.equals(v, 0d)){
                    return;
                }
                serverTotalPoints += pow(v, 0.5);
            });
            sender.sendMessage(
                    Component.text()
                            .append(Component.text("                       ").decoration(TextDecoration.STRIKETHROUGH,true))
                            .append(Component.text("經驗點數"))
                            .append(Component.text("                       ").decoration(TextDecoration.STRIKETHROUGH,true))
            );
            playerPoints.forEach((k,v) -> {
                if(Objects.equals(v,null) || Bukkit.getOfflinePlayer(k).isOp() || Objects.equals(v, 0d)){
                    return;
                }
                Double moneyToAdd = 0d;
                DecimalFormat df = new DecimalFormat("#.##");
                moneyToAdd = (pow(v, 0.5) / serverTotalPoints) * pointsMaxMoney;
                moneyToAdd = Double.valueOf(df.format(moneyToAdd));
                sender.sendMessage(
                        Component.text()
                                .append(Component.text(Bukkit.getOfflinePlayer(k).getName() + "擁有 " + playerPoints.get(k) + " 點, 將獲得 " + moneyToAdd))
                                .append(Component.text(" $").color(NamedTextColor.GOLD))
                );
            });
            Double onePointMoney = 0d;
            DecimalFormat df = new DecimalFormat("#.##");
            onePointMoney = (pow(1, 0.5) / serverTotalPoints) * pointsMaxMoney;
            onePointMoney = Double.valueOf(df.format(onePointMoney));
            sender.sendMessage(
                    Component.text()
                            .append(Component.text("* "))
                            .append(Component.text("目前一點經驗點數可獲得 ").color(TextColor.color(0x4287f5)))
                            .append(Component.text(onePointMoney))
                            .append(Component.text(" $").color(NamedTextColor.GOLD))
            );
            sender.sendMessage(
                    Component.text("                                                       ").decoration(TextDecoration.STRIKETHROUGH,true)
            );
        }else {
            sender.sendMessage(
                    Component.text()
                            .append(Component.text("* 目前沒有玩家已獲得經驗點數").color(NamedTextColor.RED))
            );
            Double onePointMoney = 0d;
            DecimalFormat df = new DecimalFormat("#.##");
            onePointMoney = (pow(1, 0.5)) * pointsMaxMoney;
            onePointMoney = Double.valueOf(df.format(onePointMoney));
            sender.sendMessage(
                    Component.text()
                            .append(Component.text("* "))
                            .append(Component.text("目前一點經驗點數可獲得 ").color(TextColor.color(0x4287f5)))
                            .append(Component.text(onePointMoney))
                            .append(Component.text(" $").color(NamedTextColor.GOLD))
            );
        }
        serverTotalPoints = 0.0;
        pointsMaxMoney = questPoints.maxMoney;
        playerPoints = questPoints.playerQuestPoints;
        if(playerPoints.size() > 0){
            playerPoints.forEach((k,v) -> {
                if(Objects.equals(v,null) || Bukkit.getOfflinePlayer(k).isOp() || Objects.equals(v, 0d)){
                    return;
                }
                serverTotalPoints += pow(v, 0.5);
            });
            sender.sendMessage(
                    Component.text()
                            .append(Component.text("                       ").decoration(TextDecoration.STRIKETHROUGH,true))
                            .append(Component.text("任務點數"))
                            .append(Component.text("                       ").decoration(TextDecoration.STRIKETHROUGH,true))
            );
            playerPoints.forEach((k,v) -> {
                if(Objects.equals(v,null) || Bukkit.getOfflinePlayer(k).isOp() || Objects.equals(v, 0d)){
                    return;
                }
                Double moneyToAdd = 0d;
                DecimalFormat df = new DecimalFormat("#.##");
                moneyToAdd = (pow(v, 0.5) / serverTotalPoints) * pointsMaxMoney;
                moneyToAdd = Double.valueOf(df.format(moneyToAdd));
                sender.sendMessage(
                        Component.text()
                                .append(Component.text(Bukkit.getOfflinePlayer(k).getName() + "擁有 " + playerPoints.get(k) + " 點, 將獲得 " + moneyToAdd))
                                .append(Component.text(" $").color(NamedTextColor.GOLD))
                );
            });
            Double onePointMoney = 0d;
            DecimalFormat df = new DecimalFormat("#.##");
            onePointMoney = (pow(1, 0.5) / serverTotalPoints) * pointsMaxMoney;
            onePointMoney = Double.valueOf(df.format(onePointMoney));
            sender.sendMessage(
                    Component.text()
                            .append(Component.text("* "))
                            .append(Component.text("目前一點任務點數可獲得 ").color(TextColor.color(0x4287f5)))
                            .append(Component.text(onePointMoney))
                            .append(Component.text(" $").color(NamedTextColor.GOLD))
            );
            sender.sendMessage(
                    Component.text("                                                       ").decoration(TextDecoration.STRIKETHROUGH,true)
            );
        }else {
            sender.sendMessage(
                    Component.text()
                            .append(Component.text("目前沒有玩家已獲得任務點數").color(NamedTextColor.RED))
            );
            Double onePointMoney = 0d;
            DecimalFormat df = new DecimalFormat("#.##");
            onePointMoney = (pow(1, 0.5)) * pointsMaxMoney;
            onePointMoney = Double.valueOf(df.format(onePointMoney));
            sender.sendMessage(
                    Component.text()
                            .append(Component.text("* "))
                            .append(Component.text("目前一點任務點數可獲得 ").color(TextColor.color(0x4287f5)))
                            .append(Component.text(onePointMoney))
                            .append(Component.text(" $").color(NamedTextColor.GOLD))
            );
        }
        return true;
    }
}

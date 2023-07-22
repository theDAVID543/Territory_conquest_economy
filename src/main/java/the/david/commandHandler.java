package the.david;

import com.google.common.base.Objects;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class commandHandler implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(args.length == 0){
            return false;
        }
        switch (args[0]) {
            case "points":
                if (args.length > 1) {
                    if (args[1].equals("give")) {
                        double d = Double.parseDouble(args[3]);
                        if (!Objects.equal(d, Double.NaN)) {
                            OfflinePlayer player = Bukkit.getOfflinePlayer(args[2]);
                            points.addPoint(player.getUniqueId(), d);
                            return true;
                        }
                    } else if (args[1].equals("clear")) {
                        double d = Double.parseDouble(args[3]);
                        if (!Objects.equal(d, Double.NaN)) {
                            OfflinePlayer player = Bukkit.getOfflinePlayer(args[2]);
                            points.clearPoint(player.getUniqueId());
                            return true;
                        }
                    } else if (args[1].equals("get")) {
                        OfflinePlayer player = Bukkit.getOfflinePlayer(args[2]);
                        sender.sendMessage(String.valueOf(points.getPoint(player.getUniqueId())));
                        return true;
                    } else if (args[1].equals("calculate")) {
                        points.calculatePoint();
                        return true;
                    }
                } else {
                    sender.sendMessage(String.valueOf(points.getPoint(Bukkit.getPlayer(sender.getName()).getUniqueId())));
                    return true;
                }
                break;
            case "activity":
                if (args.length > 1) {
                    if (args[1].equals("give")) {
                        float f = Float.parseFloat(args[3]);
                        if (!Objects.equal(f, Float.NaN)) {
                            OfflinePlayer player = Bukkit.getOfflinePlayer(args[2]);
                            points.addActivity(player.getUniqueId(), f);
                            return true;
                        }
                    }
                    if (args[1].equals("remove")) {
                        float f = Float.parseFloat(args[3]);
                        if (!Objects.equal(f, Float.NaN)) {
                            OfflinePlayer player = Bukkit.getOfflinePlayer(args[2]);
                            points.removeActivity(player.getUniqueId(), f);
                            return true;
                        }
                    }
                } else {
                    sender.sendMessage(String.valueOf(points.getActivity(Bukkit.getPlayer(sender.getName()).getUniqueId())));
                    return true;
                }
                break;
            case "quest":
                quest.randomQuest();
                return true;
            case "questpoints":
                if (args.length > 1) {
                    if (args[1].equals("give")) {
                        double d = Double.parseDouble(args[3]);
                        if (!Objects.equal(d, Double.NaN)) {
                            OfflinePlayer player = Bukkit.getOfflinePlayer(args[2]);
                            questPoints.addPoint(player.getUniqueId(), d);
                            return true;
                        }
                    } else if (args[1].equals("clear")) {
                        double d = Double.parseDouble(args[3]);
                        if (!Objects.equal(d, Double.NaN)) {
                            OfflinePlayer player = Bukkit.getOfflinePlayer(args[2]);
                            questPoints.clearPoint(player.getUniqueId());
                            return true;
                        }
                    } else if (args[1].equals("get")) {
                        OfflinePlayer player = Bukkit.getOfflinePlayer(args[2]);
                        sender.sendMessage(String.valueOf(questPoints.getPoint(player.getUniqueId())));
                        return true;
                    } else if (args[1].equals("calculate")) {
                        questPoints.calculatePoint();
                        return true;
                    }
                } else {
                    sender.sendMessage(String.valueOf(questPoints.getPoint(Bukkit.getPlayer(sender.getName()).getUniqueId())));
                    return true;
                }
                break;
            case "placedores":
                if (args.length == 1) {
                    Bukkit.getPlayer(sender.getName()).getLocation().getChunk();
                    sender.sendMessage(String.valueOf(Bukkit.getPlayer(sender.getName()).getLocation().getChunk().getPersistentDataContainer().get(quest.keyPlacedOre, PersistentDataType.INTEGER)));
                    return true;
                }else if(args.length > 1){
                    if (args[1].equals("reset")) {
                        Bukkit.getPlayer(sender.getName()).getLocation().getChunk().getPersistentDataContainer().remove(quest.keyPlacedOre);
                    }
                }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            return null;
        }
        if (args.length == 0 || args.length == 1) {
            List<String> completer = new ArrayList<>();
            completer.add("points");
            completer.add("activity");
            completer.add("quest");
            completer.add("questpoints");
            completer.add("placedores");
            completer.add("calculateLandCanAddMoney");
            return completer;
        }
        switch (args[0]) {
            case "points":
                if (args.length == 1 || args.length == 2) {
                    List<String> completer = new ArrayList<>();
                    completer.add("give");
                    completer.add("clear");
                    completer.add("get");
                    completer.add("calculate");
                    return completer;
                }
                break;
            case "activity":
                if (args.length == 1 || args.length == 2) {
                    List<String> completer = new ArrayList<>();
                    completer.add("give");
                    completer.add("remove");
                    return completer;
                }
                break;
            case "questpoints":
                if (args.length == 1 || args.length == 2) {
                    List<String> completer = new ArrayList<>();
                    completer.add("give");
                    completer.add("clear");
                    completer.add("get");
                    completer.add("calculate");
                    return completer;
                }
                break;
            case "placedores":
                if (args.length == 1 || args.length == 2) {
                    List<String> completer = new ArrayList<>();
                    completer.add("reset");
                    return completer;
                }
        }
        return null;
    }
}

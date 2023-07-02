package the.david;

import com.google.common.base.Objects;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class commandHandler implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(args.length == 0){
            return false;
        }
        if(args[0].equals("points")){
            if(args.length > 1){
                if(args[1].equals("give")){
                    double d = Double.parseDouble(args[3]);
                    if(!Objects.equal(d,Double.NaN)){
                        OfflinePlayer player = Bukkit.getOfflinePlayer(args[2]);
                        points.addPoint(player.getUniqueId(), d);
                        return true;
                    }
                }else if(args[1].equals("clear")){
                    double d = Double.parseDouble(args[3]);
                    if(!Objects.equal(d,Double.NaN)){
                        OfflinePlayer player = Bukkit.getOfflinePlayer(args[2]);
                        points.clearPoint(player.getUniqueId());
                        return true;
                    }
                }else if(args[1].equals("get")){
                    OfflinePlayer player = Bukkit.getOfflinePlayer(args[2]);
                    sender.sendMessage(String.valueOf(points.getPoint(player.getUniqueId())));
                    return true;
                }else if(args[1].equals("calculate")){
                    points.calculatePoint();
                    return true;
                }
            }else{
                sender.sendMessage(String.valueOf(points.getPoint(Bukkit.getPlayer(sender.getName()).getUniqueId())));
                return true;
            }
        }
        if(args[0].equals("activity")){
            if(args.length > 1){
                if(args[1].equals("give")){
                    float f = Float.parseFloat(args[3]);
                    if(!Objects.equal(f,Float.NaN)){
                        OfflinePlayer player = Bukkit.getOfflinePlayer(args[2]);
                        points.addActivity(player.getUniqueId(), f);
                        return true;
                    }
                }
                if(args[1].equals("remove")){
                    float f = Float.parseFloat(args[3]);
                    if(!Objects.equal(f,Float.NaN)){
                        OfflinePlayer player = Bukkit.getOfflinePlayer(args[2]);
                        points.removeActivity(player.getUniqueId(), f);
                        return true;
                    }
                }
            }else{
                sender.sendMessage(String.valueOf(points.getActivity(Bukkit.getPlayer(sender.getName()).getUniqueId())));
                return true;
            }
        }
        if(args[0].equals("quest")){
            quest.randomQuest();
            return true;
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
            return completer;
        }
        if (args[0].equals("points")) {
            if(args.length == 1 || args.length == 2){
                List<String> completer = new ArrayList<>();
                completer.add("give");
                completer.add("clear");
                completer.add("get");
                completer.add("calculate");
                return completer;
            }
        }
        if (args[0].equals("activity")) {
            if(args.length == 1 || args.length == 2){
                List<String> completer = new ArrayList<>();
                completer.add("give");
                completer.add("remove");
                return completer;
            }
        }
        return null;
    }
}

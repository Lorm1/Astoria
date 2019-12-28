package me.george.astoria.game.command.mode;

import me.george.astoria.Astoria;
import me.george.astoria.game.command.BaseCommand;
import me.george.astoria.game.player.APlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

import static me.george.astoria.game.player.APlayer.getInstanceOfPlayer;

public class CommandVanish extends BaseCommand {

    public CommandVanish() {
        super("vanish","/vanish or /vanish <player>", "Vanishes yourself or a player.", Arrays.asList("v", "invisible", "invis"));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player)) return true;
            Player p = (Player) sender;
            APlayer player = getInstanceOfPlayer(p);

            if (!player.isStaff()) return true;
            if (Astoria._hiddenPlayers.contains(p)) {
                Astoria._hiddenPlayers.add(p);
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    pl.hidePlayer(Astoria.getInstance(), p);
                    p.setPlayerListName(null);
                }
                p.sendMessage(ChatColor.AQUA + "Vanish - " + ChatColor.GREEN.toString() + ChatColor.BOLD + "ENABLED");
            } else {
                Astoria._hiddenPlayers.remove(p);
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    pl.showPlayer(Astoria.getInstance(), p);
                    p.setPlayerListName(p.getDisplayName());
                }
                p.sendMessage(ChatColor.AQUA + "Vanish - " + ChatColor.RED.toString() + ChatColor.BOLD + "DISABLED");
            }
        } else if (args.length == 1) {
            if (sender instanceof ConsoleCommandSender || (sender instanceof Player && getInstanceOfPlayer((Player) sender).isStaff())) {
                Player target = Bukkit.getPlayer(args[0]);
                if (!target.isOnline() || target == null) {
                    sender.sendMessage(ChatColor.RED + "That player is not online.");
                    return true;
                }

                if (!Astoria._hiddenPlayers.contains(target)) {
                    Astoria._hiddenPlayers.add(target);
                    sender.sendMessage(ChatColor.GREEN + "Vanished player " + ChatColor.YELLOW + getInstanceOfPlayer(target).getDisplayName());
                    target.sendMessage(ChatColor.GREEN + "You were vanishe.");

                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        pl.hidePlayer(Astoria.getInstance(), target);
                        target.setPlayerListName(null);
                    }
                } else {
                    Astoria._hiddenPlayers.remove(target);
                    sender.sendMessage(ChatColor.RED + "Unvanished player " + ChatColor.YELLOW + getInstanceOfPlayer(target).getDisplayName());
                    target.sendMessage(ChatColor.RED + "You were unvanished.");

                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        pl.showPlayer(Astoria.getInstance(), target);
                        target.setPlayerListName(getInstanceOfPlayer(target).getDisplayName());
                    }
                }
            }
        }
        return true;
    }
}

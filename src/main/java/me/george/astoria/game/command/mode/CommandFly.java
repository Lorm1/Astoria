package me.george.astoria.game.command.mode;

import me.george.astoria.game.Rank;
import me.george.astoria.game.command.BaseCommand;
import me.george.astoria.game.player.APlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import static me.george.astoria.game.player.APlayer.getInstanceOfPlayer;

public class CommandFly extends BaseCommand {
    public CommandFly() {
        super("fly", "/fly or /fly <player> (optional)", "Enables flying for yourself or a player.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender || (sender instanceof Player && getInstanceOfPlayer((Player) sender).getRank().equals(Rank.ADMIN))) {

            if (args.length == 0) {
                if (sender instanceof ConsoleCommandSender) {
                    sender.sendMessage("The console cannot execute this command.");
                    return true;
                }

                Player p = (Player) sender;
                if (!p.getAllowFlight()) {
                    p.setAllowFlight(true);
                    p.sendMessage(ChatColor.DARK_AQUA + "Flight - " + ChatColor.GREEN + "Enabled");
                    return true;
                } else {
                    p.setAllowFlight(false);
                    p.sendMessage(ChatColor.DARK_AQUA + "Flight - " + ChatColor.RED + "Disabled");
                }
            } else if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(ChatColor.RED + "That player is not online.");
                    return true;
                }

                if (!target.getAllowFlight()) {
                    target.setAllowFlight(true);
                    sender.sendMessage(ChatColor.GREEN + "Enabled flying for " + APlayer.getInstanceOfPlayer(target).getRank().getChatPrefix() + APlayer.getInstanceOfPlayer(target).getDisplayName());
                    target.sendMessage(ChatColor.DARK_AQUA + "Flight - " + ChatColor.GREEN + "Enabled" + ChatColor.DARK_AQUA + " by " + (sender instanceof ConsoleCommandSender ?
                            ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "CONSOLE" : APlayer.getInstanceOfPlayer(target).getRank().getChatPrefix() + APlayer.getInstanceOfPlayer(target).getDisplayName()));
                } else {
                    target.setAllowFlight(false);
                    sender.sendMessage(ChatColor.RED + "Disabled flying for " + APlayer.getInstanceOfPlayer(target).getRank().getChatPrefix() + APlayer.getInstanceOfPlayer(target).getDisplayName());
                    target.sendMessage(ChatColor.DARK_AQUA + "Flight - " + ChatColor.RED + "Disabled");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Invalid Usage.");
                return false;
            }
        }
        return true;
    }
}

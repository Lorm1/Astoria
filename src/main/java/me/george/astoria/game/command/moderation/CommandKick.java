package me.george.astoria.game.command.moderation;

import me.george.astoria.game.command.BaseCommand;
import me.george.astoria.game.player.APlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import static me.george.astoria.game.player.APlayer.getInstanceOfPlayer;

public class CommandKick extends BaseCommand {

    public CommandKick() {
        super("kick", "/kick <player> <reason>", "Kicks a player with a specified reason.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof ConsoleCommandSender) || (sender instanceof Player) && !(getInstanceOfPlayer((Player) sender).isStaff())) return true;

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + usage);
            return true;
        } else if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (!target.isOnline() || target == null) {
                sender.sendMessage(ChatColor.RED + "That player is not online.");
                return true;
            }

            target.kickPlayer(ChatColor.RED + "You have been kicked.");

            for (Player pl : Bukkit.getOnlinePlayers()) {
                APlayer pla = getInstanceOfPlayer(pl);
                if (pla.isStaff()) {
                    pla.getPlayer().sendMessage(ChatColor.DARK_RED + sender.getName() + ChatColor.RED + " has kicked the player " + ChatColor.YELLOW + target.getName()
                            + ChatColor.GOLD.toString() + ChatColor.UNDERLINE + " Reason"
                            + ChatColor.GOLD + ": " + ChatColor.RED + "Unspecified");
                }
            }
        } else if (args.length > 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(ChatColor.RED + "That player is not online.");
                return true;
            }

            String reason = "";

            for (int i = 2; i < args.length; i++)
                reason += args[i] + " ";

            target.kickPlayer(ChatColor.RED + reason);

            for (Player pl : Bukkit.getOnlinePlayers()) {
                APlayer pla = getInstanceOfPlayer(pl);
                if (pla.isStaff()) {
                    pla.getPlayer().sendMessage(ChatColor.DARK_RED + sender.getName() + ChatColor.RED + " has kicked the player " + ChatColor.YELLOW + target.getName()
                            + ChatColor.GOLD.toString() + ChatColor.UNDERLINE + " Reason"
                            + ChatColor.GOLD + ": " + ChatColor.RED + reason);
                }
            }
        } else
            sender.sendMessage(ChatColor.RED + usage);
        return true;
    }
}

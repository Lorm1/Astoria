package me.george.astoria.game.command.moderation;

import me.george.astoria.game.Rank;
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

public class CommandSetRank extends BaseCommand {

    public CommandSetRank() {
        super("setrank", "/setrank <player> <rank>", "Sets a player's rank.", Arrays.asList("sr"));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender || (sender instanceof Player && getInstanceOfPlayer((Player) sender).getRank().equals(Rank.ADMIN))) {
            if (args.length != 2) {
                sender.sendMessage(ChatColor.RED + usage);
                return true;
            }

            String targetName = args[0];
            if (Bukkit.getPlayer(targetName) != null) {
                APlayer targetPlayer = getInstanceOfPlayer(Bukkit.getPlayer(targetName));
                if ((targetPlayer.getRank().equals(Rank.ADMIN) && !(sender instanceof ConsoleCommandSender)) && targetPlayer.getPlayer() != sender) {
                    sender.sendMessage(ChatColor.RED + "You cannot change this player's rank.");
                    return true;
                }

                if (Rank.isValidRank(args[1].toUpperCase())) {
                    targetPlayer.setRank(Rank.valueOf(args[1].toUpperCase()));
                    sender.sendMessage(ChatColor.GREEN + "You have set " + targetPlayer.getDisplayName() + ChatColor.GREEN + "'s rank to " + targetPlayer.getRank().getChatColor().toString() + ChatColor.BOLD + targetPlayer.getRank().getInternalName().toUpperCase() + ChatColor.GREEN + ".");
                    targetPlayer.sendMessage(ChatColor.DARK_AQUA + "Your rank has been set to " + targetPlayer.getRank().getChatColor().toString() + ChatColor.BOLD + targetPlayer.getRank().getInternalName().toUpperCase() + ChatColor.DARK_AQUA + " by " + ChatColor.YELLOW + sender.getName() + ChatColor.DARK_AQUA + ".");
                } else
                    sender.sendMessage(ChatColor.RED + "Invalid Rank.");
            } else
                sender.sendMessage(ChatColor.RED + "That player is not online.");
        }
        return true;
    }
}

package me.george.astoria.game.command.moderation;

import me.george.astoria.game.Rank;
import me.george.astoria.game.command.BaseCommand;
import me.george.astoria.game.nation.Nation;
import me.george.astoria.game.player.APlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

import static me.george.astoria.game.player.APlayer.getInstanceOfPlayer;

public class CommandSetNation extends BaseCommand {

    public CommandSetNation() {
        super("setnation", "/setnation <player> <nation>", "Sets a player's nation.", Arrays.asList("setn", "snation", "sn"));
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
                if (Nation.isValidNation(args[1].toUpperCase())) {
                    targetPlayer.setNation(Nation.valueOf(args[1].toUpperCase()));
                    sender.sendMessage(ChatColor.GREEN + "You have set " + targetPlayer.getDisplayName() + ChatColor.GREEN + "'s nation to " + targetPlayer.getNation().getColor() + targetPlayer.getNation().getName() + ChatColor.GREEN + ".");
                    targetPlayer.sendMessage(ChatColor.DARK_AQUA + "Your nation has been changed to " + targetPlayer.getNation().getColor() + targetPlayer.getNation().getName() + ChatColor.DARK_AQUA + " by " + ChatColor.YELLOW + sender.getName() + ChatColor.DARK_AQUA + ".");
                } else
                    sender.sendMessage(ChatColor.RED + "Invalid Nation.");
            } else
                sender.sendMessage(ChatColor.RED + "That player is not online.");
        }
        return true;
    }
}

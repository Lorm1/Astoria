package me.george.astoria.game.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import static me.george.astoria.game.player.APlayer.getInstanceOfPlayer;

public class CommandFeed extends BaseCommand {

    public CommandFeed() {
        super("feed", "/feed <player>", "Completely saturates your hunger (or a player's).");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof ConsoleCommandSender) || (sender instanceof Player) && !(getInstanceOfPlayer((Player) sender).isStaff())) return true;

        if (args.length == 0) {
            if (!(sender instanceof Player)) return true;
            ((Player) sender).setFoodLevel(20);
            sender.sendMessage(ChatColor.GREEN + "Your apetite has been saturated.");
        } else if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                sender.sendMessage(ChatColor.RED + "That player is offline.");
                return true;
            }
            target.setFoodLevel(20);
            target.sendMessage(ChatColor.GREEN + "Your apetite has been saturated.");

            sender.sendMessage(ChatColor.GREEN + "Fed player " + ChatColor.YELLOW + target.getName());
        } else
            sender.sendMessage(ChatColor.RED + usage);
        return true;
    }
}

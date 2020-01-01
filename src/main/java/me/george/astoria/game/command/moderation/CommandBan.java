package me.george.astoria.game.command.moderation;

import me.george.astoria.game.Rank;
import me.george.astoria.game.command.BaseCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import static me.george.astoria.game.player.APlayer.getInstanceOfPlayer;

public class CommandBan extends BaseCommand {

    public CommandBan() {
        super("ban", "/ban <player> <reason> <duration>", "Bans a player with a specified reason and duration.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof ConsoleCommandSender) || (sender instanceof Player) && !(getInstanceOfPlayer((Player) sender).getRank().equals(Rank.ADMIN))) return true;

        if (args.length < 1) { // ban
            sender.sendMessage(ChatColor.RED + usage);
            return true;
        }
        return true;
    }
}

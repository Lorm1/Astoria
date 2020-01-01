package me.george.astoria.game.command.moderation;

import me.george.astoria.game.Rank;
import me.george.astoria.game.command.BaseCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import static me.george.astoria.game.player.APlayer.getInstanceOfPlayer;

public class CommandUnban extends BaseCommand {

    public CommandUnban() {
        super("unban", "/unban <player>", "Unbans a banned player.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof ConsoleCommandSender) || (sender instanceof Player) && !(getInstanceOfPlayer((Player) sender).getRank().equals(Rank.ADMIN))) return true;

        if (args.length < 1) { // unban
            sender.sendMessage(ChatColor.RED + usage);
            return true;
        }
        return true;
    }
}

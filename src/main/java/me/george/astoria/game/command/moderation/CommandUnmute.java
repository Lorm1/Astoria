package me.george.astoria.game.command.moderation;

import me.george.astoria.game.command.BaseCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import static me.george.astoria.game.player.APlayer.getInstanceOfPlayer;

public class CommandUnmute extends BaseCommand {

    public CommandUnmute() {
        super("unmute", "/unmute <player>", "Unmutes a muted player.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof ConsoleCommandSender) || (sender instanceof Player) && !(getInstanceOfPlayer((Player) sender).isStaff())) return true;

        if (args.length < 1) { // unmute
            sender.sendMessage(ChatColor.RED + usage);
            return true;
        }
        return true;
    }
}

package me.george.astoria.game.command.chat;

import me.george.astoria.game.Rank;
import me.george.astoria.game.command.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

import static me.george.astoria.game.player.APlayer.getInstanceOfPlayer;

public class CommandAlert extends BaseCommand {

    public CommandAlert() {
        super("alert", "/alert <message>", "Alerts online users with a specific message.", Arrays.asList("alertall"));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof ConsoleCommandSender) || (sender instanceof Player && !(getInstanceOfPlayer((Player) sender)).getRank().equals(Rank.ADMIN))) return true;

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + usage);
            return true;
        } else {
            String message = "";
            for (int i = 0; i < args.length; i++) {
                message += args[i] + " ";
            }

            message.trim();
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 5, 5);
                p.sendTitle(ChatColor.GRAY + "[" + ChatColor.RED + "ALERT" + ChatColor.GRAY + "]",
                        ChatColor.GOLD + ChatColor.translateAlternateColorCodes('&', message), 15, 75, 25);
                p.sendActionBar(ChatColor.RED + "ALERT");
            }
        }
        return true;
    }
}

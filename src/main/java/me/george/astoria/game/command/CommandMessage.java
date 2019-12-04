package me.george.astoria.game.command;

import me.george.astoria.game.player.APlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

import static me.george.astoria.game.player.APlayer.getInstanceOfPlayer;

public class CommandMessage extends BaseCommand {

    public CommandMessage() {
        super("message", "/message <player> <message>", "Message a player.", Arrays.asList("msg", "whisper", "w"));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + usage);
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "That player is offline.");
            return true;
        }
        APlayer t = getInstanceOfPlayer(target);

        StringBuilder str = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            str.append(args[i] + " ");
        }

        sender.sendMessage(ChatColor.GRAY.toString() + ChatColor.ITALIC + "Me"+ ChatColor.BLUE + " -> " + t.getRank().getChatPrefix() + t.getDisplayName() + ChatColor.RESET + ": " + ChatColor.GRAY.toString() + ChatColor.ITALIC + str.toString().trim());

        target.sendMessage(sender instanceof Player ? (getInstanceOfPlayer((Player) sender)).getRank().getChatPrefix() + (getInstanceOfPlayer((Player) sender).getDisplayName() + ChatColor.BLUE + " -> "
                + ChatColor.GRAY.toString() + ChatColor.ITALIC + "me: " + ChatColor.GRAY.toString() + ChatColor.ITALIC + str.toString().trim()) :
                ChatColor.DARK_AQUA.toString() + ChatColor.BOLD.toString() + ChatColor.ITALIC + "CONSOLE" + ChatColor.BLUE + " -> " + ChatColor.GRAY.toString() + ChatColor.ITALIC + str.toString().trim());
        target.playSound(target.getLocation(), Sound.ENTITY_CHICKEN_EGG, 5, 5);
        return true;
    }
}

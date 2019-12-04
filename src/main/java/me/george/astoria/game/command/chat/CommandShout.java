package me.george.astoria.game.command.chat;

import me.george.astoria.game.command.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class CommandShout extends BaseCommand {

    public CommandShout() {
        super("shout", "/shout <message>", "Broadcasts a server-wide message.", Arrays.asList("say", "sayall", "broadcast", "announce"));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + usage);
            return true;
        } else {
            String message = "";
            for (int i = 0; i < args.length; i++) {
                message += args[i] + " ";
            }
            message.trim();

            Bukkit.broadcastMessage(ChatColor.AQUA.toString() + ChatColor.BOLD + ">> " + ChatColor.AQUA + message);
            Bukkit.getOnlinePlayers().forEach(p -> p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 15, 15));
        }
        return true;
    }
}

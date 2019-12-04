package me.george.astoria.game.command.chat;

import me.george.astoria.game.command.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

import static me.george.astoria.game.player.APlayer.getInstanceOfPlayer;

public class CommandClearChat extends BaseCommand {

    public CommandClearChat() {
        super("clearchat", "/clearchat", "Clears the chat.", Arrays.asList("chat", "clearc", "chatclear", "chatc"));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender || (sender instanceof Player && getInstanceOfPlayer((Player) sender).isStaff())) {
            for (Player pl : Bukkit.getOnlinePlayers()) {
                if (!getInstanceOfPlayer(pl).isStaff()) { // dont spam staff
                    for (int i = 0; i < 100; i++)
                        pl.sendMessage(" ");
                }
            }
            Bukkit.broadcastMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + "The chat has been cleared.");
        }
        return true;
    }
}

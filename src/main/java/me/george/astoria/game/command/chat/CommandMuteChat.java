package me.george.astoria.game.command.chat;

import me.george.astoria.game.chat.Chat;
import me.george.astoria.game.command.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

import static me.george.astoria.game.player.APlayer.getInstanceOfPlayer;

public class CommandMuteChat extends BaseCommand {

    public CommandMuteChat() {
        super("mutechat", "/mutechat", "Mutes the chat.", Arrays.asList("mutec", "mutech", "mchat"));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender || (sender instanceof Player && getInstanceOfPlayer((Player) sender).isStaff())) {
            // Invert the chatEnabled boolean to the opposite
            Chat.chatEnabled = !Chat.chatEnabled;
            // See if the chatEnabled boolean is true if it is print the string 'Unmuted the chat' if not then 'Muted the chat'
            sender.sendMessage((Chat.chatEnabled ? ChatColor.GREEN + "Unmuted the chat" : ChatColor.RED + "Muted the chat"));
            Bukkit.broadcastMessage((Chat.chatEnabled ? ChatColor.GREEN + "The chat has been unmuted." : ChatColor.RED + "The chat has been muted."));
            Bukkit.getLogger().warning("[CHAT] The Chat has been muted by " + sender.getName());
        }
        return true;
    }
}

package me.george.astoria.utils;

import me.george.astoria.game.player.APlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import static me.george.astoria.game.player.APlayer.getInstanceOfPlayer;

public class MessageUtils {

    public static void sendStaffMessage(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            APlayer pl = getInstanceOfPlayer(player);
            if (pl.isStaff())
                pl.sendMessage(ChatColor.DARK_RED.toString() + ChatColor.BOLD + ">> "+ ChatColor.RESET + message);
        }
    }
}

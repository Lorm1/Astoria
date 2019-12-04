package me.george.astoria.game.server;

import me.george.astoria.Constants;
import me.george.astoria.utils.MessageUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerListPingEvent;

public class Setup implements Listener {

    public static boolean MAINTENANCE_MODE = false;

    @EventHandler
    public void onPing(ServerListPingEvent event) {
        if (!MAINTENANCE_MODE)
            event.setMotd(ChatColor.translateAlternateColorCodes('&', Constants.MOTD));
        else
            event.setMotd(ChatColor.translateAlternateColorCodes('&', Constants.MAINTENANCE_MOTD));
        event.setMaxPlayers(Constants.PLAYER_SLOTS);
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player p = event.getPlayer();
        String command = event.getMessage();

        MessageUtils.sendStaffMessage(ChatColor.RED.toString() + ChatColor.BOLD + p.getName() + ": " + ChatColor.RED + command);
    }
}

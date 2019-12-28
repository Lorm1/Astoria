package me.george.astoria.game.player;

import me.george.astoria.game.Rank;
import me.george.astoria.networking.database.DatabaseAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

import static me.george.astoria.game.player.APlayer.getInstanceOfPlayer;

public class PlayerConnection implements Listener {

    @EventHandler
    public void onAsyncPreJoin(AsyncPlayerPreLoginEvent event) {
        Player player = Bukkit.getPlayer(event.getName());
        UUID uuid = event.getUniqueId();

        DatabaseAPI.loadPlayer(uuid, player);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player pl = event.getPlayer();
        APlayer player = getInstanceOfPlayer(pl);

        // TODO: Database access on-join ?
        player.setRank(Rank.ADMIN); // DEBUG
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player pl = event.getPlayer();
        APlayer player = getInstanceOfPlayer(pl);

        // TODO: Database save player data.
        APlayer.getAstoriaPlayers().remove(pl.getUniqueId(), player);
    }
}

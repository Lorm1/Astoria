package me.george.astoria.game.player;

import me.george.astoria.game.Rank;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static me.george.astoria.game.player.APlayer.getInstanceOfPlayer;

public class PlayerConnection implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player pl = event.getPlayer();
        APlayer player = getInstanceOfPlayer(pl);

        player.setRank(Rank.ADMIN);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player pl = event.getPlayer();
        APlayer player = getInstanceOfPlayer(pl);

        APlayer.getAstoriaPlayers().remove(pl.getUniqueId(), player);
    }
}

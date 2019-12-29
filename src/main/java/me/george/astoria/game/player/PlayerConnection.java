package me.george.astoria.game.player;

import me.george.astoria.Astoria;
import me.george.astoria.game.Rank;
import me.george.astoria.game.nation.Nation;
import me.george.astoria.networking.database.DatabaseAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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

        event.setJoinMessage(null);

        if (player.getRank().equals(Rank.ADMIN)) {
            Astoria._hiddenPlayers.add(player.getPlayer());
            player.getPlayer().setGameMode(GameMode.CREATIVE);
            player.getPlayer().setInvulnerable(true);

            for (Player pla : Bukkit.getOnlinePlayers()) {
                if (getInstanceOfPlayer(pla).isStaff())
                    pla.sendMessage(player.getDisplayName() + ChatColor.GREEN + " has joined.");
            }
        }

        for (Player p : Astoria._hiddenPlayers) {
            pl.hidePlayer(p);
            p.setPlayerListName(null);
        }

        if (!pl.hasPlayedBefore()) {
            pl.sendMessage("welcome");
            pl.sendActionBar("welcome"); // debug

            pl.teleport(Nation.HUMANS.getSpawnLocation());
            pl.playSound(pl.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 5);
            pl.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 5, 50));
            pl.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 3, 35));
        }

        // TODO: Database access on-join ?
        player.setRank(Rank.ADMIN); // DEBUG
        player.setNation(Nation.ORCS); // DEBUG

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player pl = event.getPlayer();
        APlayer player = getInstanceOfPlayer(pl);

        // TODO: Database save player data.
        Astoria._hiddenPlayers.remove(pl);
        APlayer.getAstoriaPlayers().remove(pl.getUniqueId(), player);
    }
}

package me.george.astoria.game.player;

import me.george.astoria.Astoria;
import me.george.astoria.Constants;
import me.george.astoria.game.Rank;
import me.george.astoria.game.nation.Nation;
import me.george.astoria.game.server.Setup;
import me.george.astoria.networking.database.DatabaseAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static me.george.astoria.game.player.APlayer.getInstanceOfPlayer;

public class PlayerConnection implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        DatabaseAPI.loadPlayer(event.getPlayer().getUniqueId(), event.getPlayer());
        APlayer player = getInstanceOfPlayer(event.getPlayer());

        String banMessage = ChatColor.BLUE.toString() + ChatColor.BOLD + "     Myths of Astoria     \n\n\n" + ChatColor.RED.toString() + ChatColor.UNDERLINE
                + "You have been banned.\n\n" + ChatColor.BLUE.toString() + ChatColor.UNDERLINE + "Reason" + ChatColor.BLUE + ": " + ChatColor.GRAY + player.getBanReason()
                + "\n\n" + ChatColor.GOLD.toString() + ChatColor.UNDERLINE + "Expires" + ChatColor.GOLD + ": " + ChatColor.DARK_RED + player.getBanDuration()
                + "\n\n\n" + ChatColor.GRAY + "Find out more at: " + ChatColor.BLUE.toString() + ChatColor.UNDERLINE + Constants.SITE_NAME;

        String maintenanceMessage = ChatColor.BLUE.toString() + ChatColor.BOLD.toString()
                + ChatColor.UNDERLINE + "Myths of Astoria\n\n\n"
                + ChatColor.RED.toString() + ChatColor.UNDERLINE + "Maintenance Mode\n\n\n"
                + ChatColor.GRAY + "Find out more at: " + ChatColor.BLUE.toString() + ChatColor.UNDERLINE + Constants.SITE_NAME;

        if (player.isBanned) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, banMessage);
            return;
        }

        if (Setup.MAINTENANCE_MODE) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, maintenanceMessage);
            return;
        }

        if (event.getResult() == PlayerLoginEvent.Result.KICK_FULL) {
            if (player.isDonator() || player.isStaff()) {
                event.allow();
                player.sendMessage(ChatColor.GREEN + "You have bypassed the slot limit as you are a(n) " + player.getRank().getPrefix());
            }
        }
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

            Bukkit.getOnlinePlayers().stream().filter(pla -> getInstanceOfPlayer(pla).isStaff()).forEach(pla -> pla.sendMessage(player.getDisplayName() + ChatColor.DARK_AQUA + " has joined."));
        }

        Astoria._hiddenPlayers.forEach(p -> {
            pl.hidePlayer(Astoria.getInstance(), p);
            p.setPlayerListName(null);
        });

        if (!pl.hasPlayedBefore()) {
            pl.teleport(Nation.HUMANS.getSpawnLocation());

            pl.sendTitle(ChatColor.WHITE.toString() + ChatColor.BOLD + "Welcome to", ChatColor.BLUE.toString() + ChatColor.BOLD + "Myths of Astoria", 2, 3, 3);
            pl.sendActionBar(ChatColor.BLUE + "Welcome!");

            pl.sendMessage(ChatColor.STRIKETHROUGH.toString() + ChatColor.GRAY + "--------------------------");
            pl.sendMessage(ChatColor.BLUE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE + "     Myths of Astoria     \n");
            pl.sendMessage(ChatColor.GRAY.toString() + ChatColor.ITALIC + "Welcome to Myths of Astoria, adventurer.");
            pl.sendMessage(ChatColor.GRAY.toString() + ChatColor.ITALIC + "A huge adventure awaits you ahead.");
            pl.sendMessage(ChatColor.GRAY.toString() + ChatColor.ITALIC + "If you have any questions, make sure to ask around or visit our website.\n");
            pl.sendMessage(ChatColor.GRAY.toString() + ChatColor.UNDERLINE + "     " + Constants.SITE_NAME + "     ");
            pl.sendMessage(ChatColor.STRIKETHROUGH.toString() + ChatColor.GRAY + "--------------------------");

            pl.playSound(pl.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 5);
            pl.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 5, 50));
            pl.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 3, 35));
        } else {
            pl.playSound(pl.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 5, 5);

            pl.sendMessage(ChatColor.STRIKETHROUGH.toString() + ChatColor.GRAY + "--------------------------");
            pl.sendMessage(ChatColor.BLUE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE + "     Myths of Astoria     \n");
            pl.sendMessage(ChatColor.GRAY.toString() + ChatColor.ITALIC + "Welcome back to Myths of Astoria.");
            pl.sendMessage(ChatColor.GRAY.toString() + ChatColor.ITALIC + "If you have any questions, make sure to ask around or visit our website.\n");
            pl.sendMessage(ChatColor.GRAY.toString() + ChatColor.UNDERLINE + "     " + Constants.SITE_NAME + "     ");
            pl.sendMessage(ChatColor.STRIKETHROUGH.toString() + ChatColor.GRAY + "--------------------------");

            pl.sendActionBar(ChatColor.GRAY + "Welcome back.");

            pl.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 1, 50));
            pl.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 1, 50));
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player pl = event.getPlayer();
        APlayer player = getInstanceOfPlayer(pl);

        DatabaseAPI.savePlayer(pl);
        Bukkit.getLogger().info("[Database] Saved player data for " + pl.getName() + ".");

        Astoria._hiddenPlayers.remove(pl);
        APlayer.getAstoriaPlayers().remove(pl.getUniqueId(), player);
    }
}

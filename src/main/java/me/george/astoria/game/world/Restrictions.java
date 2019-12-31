package me.george.astoria.game.world;

import me.george.astoria.game.Rank;
import me.george.astoria.game.player.APlayer;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.InventoryHolder;

import static me.george.astoria.game.player.APlayer.getInstanceOfPlayer;

public class Restrictions implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void chat(PlayerChatTabCompleteEvent event) {
        Player p = event.getPlayer();
        APlayer player = getInstanceOfPlayer(p);

        String msg = event.getChatMessage();

        if (!player.getRank().equals(Rank.ADMIN)) {
            if (msg.contains("/"))
                event.getTabCompletions().clear();
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        APlayer player = getInstanceOfPlayer(p);

        if ((!p.isOp() || player.getRank().equals(Rank.ADMIN)) && !p.getGameMode().equals(GameMode.CREATIVE))  // only admins in creative can modify the world.
            e.setCancelled(true);
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.ENDER_PEARL))
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        APlayer player = getInstanceOfPlayer(p);

        Action action = e.getAction();
        Block block = e.getClickedBlock();

        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            if (p.getInventory().getItemInMainHand().getType() == null || p.getInventory().getItemInMainHand() == null) return;

            if (!player.getRank().equals(Rank.ADMIN) && !p.getGameMode().equals(GameMode.CREATIVE)) {
                if (block.getState() instanceof InventoryHolder)
                    e.setCancelled(true);

                if (p.getInventory().getItemInMainHand().getType() == Material.SHEARS)
                    e.setCancelled(true);

                if (p.getInventory().getItemInMainHand().getType() == Material.ENDER_PEARL)
                    e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onItemSwap(PlayerSwapHandItemsEvent e) {
        Player p = e.getPlayer();

        if (e.getMainHandItem() == null || e.getMainHandItem().getType() == null || e.getMainHandItem().getType() == Material.AIR
                || p.getInventory().getItemInMainHand() == null || p.getInventory().getItemInMainHand().getType() == null)
            return;
    }

    @EventHandler
    public void onPortal(PlayerPortalEvent e) {
        Player p = e.getPlayer();
        APlayer player = getInstanceOfPlayer(p);

        if (!player.getRank().equals(Rank.ADMIN))
            e.setCancelled(true);
    }

    @EventHandler
    public void onBlockFade(BlockFadeEvent e) {
        e.setCancelled(true); // stops block from fading = stop soiled dirt turning into normal dirt (by jumping on it etc...)
    }
}

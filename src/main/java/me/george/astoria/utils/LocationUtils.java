package me.george.astoria.utils;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class LocationUtils {

    public static List<Entity> getNearbyEntities(Location where, int range) {
        List<Entity> found = new ArrayList<Entity>();

        for (Entity entity : where.getWorld().getEntities()) {
            if (isInBorder(where, entity.getLocation(), range)) {
                found.add(entity);
            }
        }
        return found;
    }

    public static boolean isInBorder(Location center, Location notCenter, int range) {
        int x = center.getBlockX(), z = center.getBlockZ();
        int x1 = notCenter.getBlockX(), z1 = notCenter.getBlockZ();

        if (x1 >= (x + range) || z1 >= (z + range) || x1 <= (x - range) || z1 <= (z - range)) {
            return false;
        }
        return true;
    }

    public static double distanceSquared(Location first, Location second){
        if(first.getWorld() != second.getWorld())return Integer.MAX_VALUE;
        return first.distanceSquared(second);
    }

    public static List<Player> getNearbyPlayers(Location location, int radius) {
        return getNearbyPlayers(location, radius, false);
    }

    public static List<Player> getNearbyPlayers(Entity entity, int radius) {
        return getNearbyPlayers(entity, radius, false);
    }

    public static List<Player> getNearbyPlayers(Location location, int radius, boolean ignoreVanish) {
        return location.getWorld().getPlayers().stream().filter(player -> !ignoreVanish).filter(player -> location.distanceSquared(player.getLocation()) <= radius * radius).collect(Collectors.toList());
    }

    public static volatile Set<Player> asyncTracker = new ConcurrentSet<>();

    public static Set<Player> getNearbyPlayersAsync(Location location, int radius) {
        return asyncTracker.stream().filter(OfflinePlayer::isOnline).filter(player -> player.getWorld().equals(location.getWorld()) && location.distanceSquared(player.getLocation()) <= radius * radius).collect(Collectors.toSet());
    }

    public static List<Player> getNearbyPlayers(Entity entity, int radius, boolean ignoreVanish) {
        List<Player> playersNearby = new ArrayList<>();

        entity.getNearbyEntities(radius, radius, radius).stream().filter((ent) -> ent instanceof Player).forEach((pl) -> {
            Player player = (Player) pl;

            playersNearby.add(player);
        });
        return playersNearby;
    }
}

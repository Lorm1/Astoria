package me.george.astoria.game.nation;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum Nation {
    ORCS("Orcs", ChatColor.RED, new Location(Bukkit.getWorld("world"), 0, 0, 0)),
    VIKINGS("Vikings", ChatColor.BLUE, new Location(Bukkit.getWorld("world"), 1, 1, 1)),
    ELVES("Elves", ChatColor.DARK_GREEN, new Location(Bukkit.getWorld("world"), 2, 2, 2)),
    HUMANS("Humans", ChatColor.GRAY, new Location(Bukkit.getWorld("world"), 3, 3, 3));

    @Getter private String name;
    @Getter private ChatColor color;
    @Getter private Location spawnLocation;

    Nation(String name, ChatColor color, Location spawnLocation) {
        this.color = color;
        this.spawnLocation = spawnLocation;
        this.name = name;
    }

    public static boolean isValidNation(final String nation) {
        return Arrays.stream(Nation.values())
                .map(Nation::name)
                .collect(Collectors.toSet())
                .contains(nation);
    }
}

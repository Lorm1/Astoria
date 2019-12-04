package me.george.astoria.game;

import lombok.Getter;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum Rank {
    DEV("DEV", ChatColor.DARK_AQUA),
    ADMIN("ADMIN", ChatColor.DARK_RED),
    MOD("MOD", ChatColor.DARK_PURPLE),
    TESTER("TESTER", ChatColor.WHITE),
    DONATOR("DONATOR", ChatColor.GREEN),
    DEFAULT("", ChatColor.GRAY);

    private String prefix;
    @Getter private ChatColor chatColor;

    Rank(String prefix, ChatColor color) {
        this.prefix = prefix;
        this.chatColor = color;
    }

    public String getInternalName() {
        return name().toLowerCase();
    }

    public String getChatPrefix() {
        return this.prefix.length() > 0 ? getPrefix() + " " : "";
    } // not default, therefore has a prefix because default is an empty string

    public String getPrefix() {
        return getChatColor().toString() + ChatColor.BOLD + this.prefix + ChatColor.RESET;
    }

    public static Rank getFromInternalName(String name) {
        if(name == null) return null;
        return Arrays.stream(values()).filter(rank -> rank.getInternalName().equals(name.toLowerCase())).findFirst().orElse(null);
    }

    public boolean isDonator() {
        return isAtLeast(Rank.DONATOR);
    }

    public boolean isStaff() {
        return isAtLeast(Rank.MOD);
    }

    public int getRank() {
        return ordinal();
    }

    public boolean isAtLeast(Rank rank){
        return getRank() >= rank.getRank();
    }

    public static boolean isValidRank(final String rank) {
        return Arrays.stream(Rank.values())
                .map(Rank::name)
                .collect(Collectors.toSet())
                .contains(rank);
    }
}

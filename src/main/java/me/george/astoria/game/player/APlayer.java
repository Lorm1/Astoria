package me.george.astoria.game.player;

import lombok.Getter;
import lombok.Setter;
import me.george.astoria.game.Rank;
import me.george.astoria.game.nation.Nation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class APlayer {

    @Getter private static Map<UUID, APlayer> astoriaPlayers = new ConcurrentHashMap<>();

    Player player;
    UUID uuid;

    @Getter @Setter
    int gold, ecash;

    @Getter @Setter
    int mobKills, playerKills, deaths;

    @Getter @Setter
    int level;

    @Getter @Setter
    double health, experience;

    @Getter @Setter
    Rank rank;

    @Getter @Setter
    Nation nation;

    @Getter @Setter
    Location playerLocation;

    @Getter @Setter
    String bannedBy, mutedBy;

    @Getter @Setter
    String muteReason, banReason;

    @Getter @Setter
    Boolean isBanned;

    @Getter @Setter
    Timestamp joinDate, lastLogin, lastLogout;

    @Getter @Setter
    Time banDuration;


    public void addGold(int gold) {
        assert gold >= 0;
        setGold(getGold() + gold);
    }

    public void subtractGold(int gold) {
        assert gold >= 0;
        setGold(getGold() - gold);
    }

    public void addEcash(int ecash) {
        assert ecash >= 0;
        setEcash(getEcash() + ecash);
    }

    public void subtractEcash(int ecash) {
        assert ecash >= 0;
        setEcash(getEcash() - ecash);
    }

    public APlayer(UUID uuid) {
        this.uuid = uuid;
        this.player = Bukkit.getPlayer(uuid);
        astoriaPlayers.put(uuid, this);
    }

    public APlayer(Player player) {
        this.player = player;
        this.uuid = player.getUniqueId();
        astoriaPlayers.put(player.getUniqueId(), this);
    }

    public APlayer(OfflinePlayer player) { this.uuid = player.getUniqueId(); }

    public static APlayer getInstanceOfPlayer(Player player) {
        if(!astoriaPlayers.containsKey(player.getUniqueId()))
            return new APlayer(player);
        else if(astoriaPlayers.containsKey(player.getUniqueId()))
            return astoriaPlayers.get(player.getUniqueId());
        return null;
    }

    public boolean isStaff() {
        return getRank().equals(Rank.MOD) || getRank().equals(Rank.ADMIN) || getRank().equals(Rank.DEV) || getPlayer().isOp();
    }

    public boolean isDonator() {
        return getRank().equals(Rank.DONATOR);
    }

    public String getName() {
        return getPlayer().getName();
    }

    public String getDisplayName() {
        Rank rank = getRank();
        Nation nation = getNation();

        ChatColor nameColor = isStaff() ? rank.getChatColor() : nation.getColor(); /*ChatColor.GRAY*/; // Display rank color for staff and nation color for everyone else.
        return nameColor + (isOnline() ? getPlayer().getName() : getName());
    }

    public void sendMessage(String message) {
        this.getPlayer().sendMessage(message);
    }

    public Player getPlayer() {
        return this.player;
    }

    public boolean isOnline() {
        return getPlayer() != null && getPlayer().isOnline();
    }
}

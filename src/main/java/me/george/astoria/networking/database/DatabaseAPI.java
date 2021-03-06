package me.george.astoria.networking.database;

import me.george.astoria.game.Rank;
import me.george.astoria.game.nation.Nation;
import me.george.astoria.game.player.APlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.UUID;

import static me.george.astoria.game.player.APlayer.getInstanceOfPlayer;

public class DatabaseAPI {

    public static PreparedStatement prepareStatement(String query) {
        PreparedStatement ps = null;

        try {
            ps = Database.getConnection().prepareStatement(query);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return ps;
    }

    public static boolean playerExists(UUID uuid) {
        try {
            PreparedStatement statement = prepareStatement("SELECT * FROM player_info WHERE UUID = ?");
            statement.setString(1, uuid.toString());

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean playerExists(String name) {
        try {
            PreparedStatement statement = prepareStatement("SELECT * FROM player_info WHERE NAME = ?");
            statement.setString(1, name);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return true;
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static UUID getPlayerUUID(String playerName) {
        if (!playerExists(playerName)) throw new NullPointerException("Player: " + playerName + " has never played before.");

        PreparedStatement sts;
        try {
            sts = prepareStatement("SELECT UUID FROM player_info WHERE NAME = ?");
            sts.setString(1, playerName);

            ResultSet rs = sts.executeQuery();

            if (rs.next()) {
                return UUID.fromString(rs.getString("UUID"));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("Player: " + playerName + " has never played before.");
    }

    public static void loadPlayer(final UUID uuid, Player player) {
        try {
            if (!playerExists(uuid)) {
                PreparedStatement ps = prepareStatement("INSERT INTO player_info(UUID, NAME, RANK, NATION, GOLD, ECASH, JOIN_DATE, LAST_LOGIN, IS_BANNED, BAN_DURATION, BAN_REASON, BANNED_BY) VALUES ('" + uuid.toString() + "'," + "'" + player.getName() + "', DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT);");
                ps.executeUpdate();
                ps.close();

                Bukkit.getLogger().info("[Database] Created Player " + player.getName() + " and added to the Database.");
            }

            PreparedStatement statement = prepareStatement("SELECT * FROM player_info WHERE UUID = ?");
            statement.setString(1, uuid.toString());

            ResultSet rs = statement.executeQuery();
            rs.next();

            APlayer pl = getInstanceOfPlayer(player);

            String playerName = rs.getString("NAME");
            String rank = rs.getString("RANK");
            String nation = rs.getString("NATION");

            int gold = rs.getInt("GOLD");
            int ecash = rs.getInt("ECASH");

            Timestamp joinDate = rs.getTimestamp("JOIN_DATE");
            Timestamp LAST_LOGIN = new Timestamp(System.currentTimeMillis());

            Boolean isPlayerBanned = rs.getBoolean("IS_BANNED");

            Time BAN_DURATION = rs.getTime("BAN_DURATION");

            String BAN_REASON = rs.getString("BAN_REASON");
            String BANNED_BY = rs.getString("BANNED_BY");

            // Load player data
            pl.setRank(Rank.valueOf(rank));
            pl.setNation(Nation.valueOf(nation));

            pl.setGold(gold);
            pl.setEcash(ecash);

            pl.setJoinDate(joinDate);
            pl.setLastLogin(LAST_LOGIN);

            pl.setIsBanned(isPlayerBanned);

            if (isPlayerBanned) {
                pl.setBanDuration(BAN_DURATION);
                pl.setBanReason(BAN_REASON);
                pl.setBannedBy(BANNED_BY);
            }

            Bukkit.getLogger().info("[Database] Loaded player " + player.getName());

            statement.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void savePlayer(Player player) {
        try {
            if (!playerExists(player.getUniqueId())) {
                Bukkit.getLogger().info("[Database] Could not save player " + player.getName() + " as they do not exist in the database.");
                return;
            }
            APlayer pl = getInstanceOfPlayer(player);

            PreparedStatement ps = prepareStatement("UPDATE player_info SET RANK = ?, NATION = ?, GOLD = ?, ECASH = ?, LAST_LOGIN = ?, IS_BANNED = ? WHERE UUID = '" + player.getUniqueId() + "';");

            ps.setString(1, pl.getRank().name().toUpperCase());
            ps.setString(2, pl.getNation().name().toUpperCase());
            ps.setInt(3, pl.getGold());
            ps.setInt(4, pl.getEcash());
            ps.setTimestamp(5, pl.getLastLogin());
            ps.setBoolean(6, pl.getIsBanned());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();;
        }
    }

    public void getRank(final UUID uuid) {
        try {
            PreparedStatement ps = prepareStatement("SELECT RANK FROM player_info WHERE UUID = ?");
            ps.setString(1, uuid.toString());

            ResultSet rs = ps.executeQuery();
            rs.next();

            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getNation(final UUID uuid) {
        try {
            PreparedStatement ps = prepareStatement("SELECT NATION FROM player_info WHERE UUID = ?");
            ps.setString(1, uuid.toString());

            ResultSet rs = ps.executeQuery();
            rs.next();

            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getGold(final UUID uuid) {
        try {
            PreparedStatement ps = prepareStatement("SELECT GOLD FROM player_info WHERE UUID = ?");
            ps.setString(1, uuid.toString());

            ResultSet rs = ps.executeQuery();
            rs.next();

            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getEcash(final UUID uuid) {
        try {
            PreparedStatement ps = prepareStatement("SELECT ECASH FROM player_info WHERE UUID = ?");
            ps.setString(1, uuid.toString());

            ResultSet rs = ps.executeQuery();
            rs.next();

            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setGold(final UUID uuid, int gold) {
        try {
            PreparedStatement statement = prepareStatement("UPDATE player_info SET GOLD = ? WHERE UUID = ?");
            statement.setInt(1, gold);
            statement.setString(2, uuid.toString());

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setEcash(final UUID uuid, int ecash) {
        try {
            PreparedStatement statement = prepareStatement("UPDATE player_info SET ECASH = ? WHERE UUID = ?");
            statement.setInt(1, ecash);
            statement.setString(2, uuid.toString());

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setRank(final UUID uuid, Rank rank) {
        try {
            PreparedStatement statement = prepareStatement("UPDATE player_info SET RANK = ? WHERE UUID = ?");
            statement.setString(1, String.valueOf(rank));
            statement.setString(2, uuid.toString());

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setLastLogin(final UUID uuid, Timestamp timestamp) {
        try {
            PreparedStatement statement = prepareStatement("UPDATE player_info SET LAST_LOGIN = ? WHERE UUID = ?");
            statement.setTimestamp(1, timestamp);
            statement.setString(2, uuid.toString());

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getLastLogin(final UUID uuid) {
        try {
            PreparedStatement ps = prepareStatement("SELECT LAST_LOGIN FROM player_info WHERE UUID = ?");
            ps.setString(1, uuid.toString());

            ResultSet rs = ps.executeQuery();
            rs.next();

            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setLastLogout(final UUID uuid, Timestamp timestamp) {
        try {
            PreparedStatement statement = prepareStatement("UPDATE player_info SET LAST_LOGOUT = ? WHERE UUID = ?");
            statement.setTimestamp(1, timestamp);
            statement.setString(2, uuid.toString());

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getLastLogout(final UUID uuid) {
        try {
            PreparedStatement ps = prepareStatement("SELECT LAST_LOGOUT FROM player_info WHERE UUID = ?");
            ps.setString(1, uuid.toString());

            ResultSet rs = ps.executeQuery();
            rs.next();

            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void setBanned(final UUID uuid, boolean l) {
        try {
            PreparedStatement statement = prepareStatement("UPDATE player_info SET IS_BANNED = ? WHERE UUID = ?");
            statement.setBoolean(1, l);
            statement.setString(2, uuid.toString());

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getIsBanned(final UUID uuid) {
        try {
            PreparedStatement ps = prepareStatement("SELECT IS_BANNED FROM player_info WHERE UUID = ?");
            ps.setString(1, uuid.toString());

            ResultSet rs = ps.executeQuery();
            rs.next();

            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setBanDuration(final UUID uuid, Time time) {
        try {
            PreparedStatement statement = prepareStatement("UPDATE player_info SET BAN_DURATION = ? WHERE UUID = ?");
            statement.setTime(1, time);
            statement.setString(2, uuid.toString());

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getBanDuration(final UUID uuid) {
        try {
            PreparedStatement ps = prepareStatement("SELECT BAN_DURATION FROM player_info WHERE UUID = ?");
            ps.setString(1, uuid.toString());

            ResultSet rs = ps.executeQuery();
            rs.next();

            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setBanReason(final UUID uuid, String reason) {
        try {
            PreparedStatement statement = prepareStatement("UPDATE player_info SET BAN_REASON = ? WHERE UUID = ?");
            statement.setString(1, reason);
            statement.setString(2, uuid.toString());

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getBanReason(final UUID uuid) {
        try {
            PreparedStatement ps = prepareStatement("SELECT BAN_REASON FROM player_info WHERE UUID = ?");
            ps.setString(1, uuid.toString());

            ResultSet rs = ps.executeQuery();
            rs.next();

            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setBannedBy(final UUID uuid, String playerName) {
        try {
            PreparedStatement statement = prepareStatement("UPDATE player_info SET BANNED_BY = ? WHERE UUID = ?");
            statement.setString(1, playerName);
            statement.setString(2, uuid.toString());

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getBannedBy(final UUID uuid) {
        try {
            PreparedStatement ps = prepareStatement("SELECT BANNED_BY FROM player_info WHERE UUID = ?");
            ps.setString(1, uuid.toString());

            ResultSet rs = ps.executeQuery();
            rs.next();

            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

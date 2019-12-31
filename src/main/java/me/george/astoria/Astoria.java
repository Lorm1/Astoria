package me.george.astoria;

import lombok.Getter;
import me.george.astoria.game.chat.Chat;
import me.george.astoria.game.command.CommandHelp;
import me.george.astoria.game.command.CommandManager;
import me.george.astoria.game.command.CommandMessage;
import me.george.astoria.game.command.chat.CommandAlert;
import me.george.astoria.game.command.chat.CommandClearChat;
import me.george.astoria.game.command.chat.CommandMuteChat;
import me.george.astoria.game.command.chat.CommandShout;
import me.george.astoria.game.command.item.CommandGive;
import me.george.astoria.game.command.mode.*;
import me.george.astoria.game.command.moderation.CommandSetNation;
import me.george.astoria.game.command.moderation.CommandSetRank;
import me.george.astoria.game.mechanic.TestMechanic;
import me.george.astoria.game.mechanic.template.MechanicManager;
import me.george.astoria.game.player.PlayerConnection;
import me.george.astoria.game.server.Setup;
import me.george.astoria.game.world.Restrictions;
import me.george.astoria.networking.database.Database;
import me.george.astoria.networking.database.DatabaseAPI;
import me.george.astoria.utils.ConcurrentSet;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Astoria extends JavaPlugin {

    private static Astoria instance = null;

    public static Astoria getInstance() {
        return instance;
    }

    private Database database;

    @Getter
    public List<String> commands = new ArrayList<>();

    public static Set<Player> _hiddenPlayers = new ConcurrentSet<>();

    @Override
    public void onEnable() {
        instance = this;
        database.getInstance().connect();

        Bukkit.getLogger().info("Enabling Astoria v." + Constants.SERVER_VERSION);

        setup();
    }

    @Override
    public void onDisable() {
        shutdown();
        instance = null;
        database.getInstance().disconnect(); // We may not want to disable the database.

        Bukkit.getLogger().info("Disabling Astoria v." + Constants.SERVER_VERSION);

    }

    private void setup() {
        registerMechanics();
        registerCommands();
        registerEvents();

        executeTasks();
    }

    private void shutdown() {
        Bukkit.getScheduler().runTask(this, () -> {
            int counter = 0;
            for (Player player : Bukkit.getOnlinePlayers()) {
                DatabaseAPI.savePlayer(player);
                counter++;
            }
            Bukkit.getLogger().info("[Database] Saving player data for " + counter + " players...");
        });

        MechanicManager.stopMechanics();
        _hiddenPlayers.clear();
    }

    private void registerMechanics() {
        MechanicManager.registerMechanic(new TestMechanic());

        MechanicManager.loadMechanics();
    }

    private void registerEvents() {
        PluginManager m = getServer().getPluginManager();

        m.registerEvents(new Setup(), this);
        m.registerEvents(new PlayerConnection(), this);
        m.registerEvents(new Chat(), this);
        m.registerEvents(new Restrictions(), this);
    }

    private void registerCommands() {
        this.getDescription().getCommands().keySet().forEach(command -> commands.add(command));

        CommandManager cm = new CommandManager();

        cm.registerCommand(new CommandHelp());
        cm.registerCommand(new CommandSetRank());
        cm.registerCommand(new CommandSetNation());
        cm.registerCommand(new CommandMessage());
        cm.registerCommand(new CommandFly());
        cm.registerCommand(new CommandSpeed());
        cm.registerCommand(new CommandGameMode());
        cm.registerCommand(new CommandGod());
        cm.registerCommand(new CommandGive());
        cm.registerCommand(new CommandMuteChat());
        cm.registerCommand(new CommandClearChat());
        cm.registerCommand(new CommandAlert());
        cm.registerCommand(new CommandShout());
        cm.registerCommand(new CommandVanish());
        // cm.registerCommand(new CommandGUIHelp());
    }

    private void executeTasks() {
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, () -> Bukkit.getOnlinePlayers().forEach(p -> DatabaseAPI.savePlayer(p)), 20 * 5, 20 * 10);
    }
}

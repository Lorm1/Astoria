package me.george.astoria;

import me.george.astoria.game.chat.Chat;
import me.george.astoria.game.command.CommandManager;
import me.george.astoria.game.command.CommandMessage;
import me.george.astoria.game.command.CommandSetRank;
import me.george.astoria.game.command.chat.CommandAlert;
import me.george.astoria.game.command.chat.CommandClearChat;
import me.george.astoria.game.command.chat.CommandMuteChat;
import me.george.astoria.game.command.chat.CommandShout;
import me.george.astoria.game.command.item.CommandGive;
import me.george.astoria.game.command.mode.*;
import me.george.astoria.game.mechanic.TestMechanic;
import me.george.astoria.game.mechanic.profession.Profession;
import me.george.astoria.game.mechanic.template.MechanicManager;
import me.george.astoria.game.player.PlayerConnection;
import me.george.astoria.game.server.Setup;
import me.george.astoria.utils.ConcurrentSet;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

public class Astoria extends JavaPlugin {

    private static Astoria instance = null;
    public static Astoria getInstance() {
        return instance;
    }

    public static Set<Player> _hiddenPlayers = new ConcurrentSet<>();

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getLogger().info("Enabling Astoria v." + Constants.SERVER_VERSION);

        setup();
    }

    @Override
    public void onDisable() {
        instance = null;
        Bukkit.getLogger().info("Disabling Astoria v." + Constants.SERVER_VERSION);

        shutdown();
    }

    private void setup() {
        registerMechanics();
        registerCommands();
        registerEvents();
    }

    private void shutdown() {
        MechanicManager.stopMechanics();
    }

    private void registerMechanics() {
        MechanicManager.registerMechanic(new Profession());
        MechanicManager.registerMechanic(new TestMechanic());

        MechanicManager.loadMechanics();
    }

    private void registerEvents() {
        PluginManager m = getServer().getPluginManager();

        m.registerEvents(new Setup(), this);
        m.registerEvents(new PlayerConnection(), this);
        m.registerEvents(new Chat(), this);
    }

    private void registerCommands() {
        CommandManager cm = new CommandManager();

        cm.registerCommand(new CommandSetRank());
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
    }
}

package me.george.astoria.game.mechanic;

import me.george.astoria.game.mechanic.template.Mechanic;
import me.george.astoria.game.mechanic.template.MechanicPriority;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TestMechanic implements Mechanic {

    private static TestMechanic instance = null;

    public static TestMechanic getInstance() {
        if (instance == null) {
            instance = new TestMechanic();
        }
        return instance;
    }

    @Override
    public MechanicPriority startPriority() {
        return MechanicPriority.NORMAL;
    }

    @Override
    public void startInitialization() {
        Bukkit.getLogger().info("Test Mechanic initialization!");
    }

    @Override
    public void stopInvocation() {
    }

    public void doSomething(Player p) {
        p.sendMessage(ChatColor.GREEN + "TestMechanic!");
    }
}

package me.george.astoria.game.mechanic.profession;

import me.george.astoria.game.mechanic.template.Mechanic;
import me.george.astoria.game.mechanic.template.MechanicPriority;
import org.bukkit.Bukkit;

public class Profession implements Mechanic {

    private ProfessionItem professionItem;

    @Override
    public MechanicPriority startPriority() {
        return MechanicPriority.HIGH;
    }

    @Override
    public void startInitialization() {
        Bukkit.getLogger().info("Profession Mechanic registered!");
        // Do mining stuff
        // Do fishing stuff
        // Do farming stuff
    }

    @Override
    public void stopInvocation() {
        // Do mining stuff
        // Do fishing stuff
        // Do farming stuff
    }
}

package me.george.astoria.game.mechanic.profession.professions;

import me.george.astoria.game.mechanic.profession.ProfessionItem;
import me.george.astoria.game.mechanic.template.Mechanic;
import me.george.astoria.game.mechanic.template.MechanicPriority;

public class Mining implements Mechanic {

    private ProfessionItem pickaxe;

    @Override
    public MechanicPriority startPriority() {
        return MechanicPriority.HIGH;
    }

    @Override
    public void startInitialization() {
        // setup all minable ores.
    }

    @Override
    public void stopInvocation() {

    }
}

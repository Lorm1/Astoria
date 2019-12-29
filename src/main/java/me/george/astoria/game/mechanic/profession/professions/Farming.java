package me.george.astoria.game.mechanic.profession.professions;

import me.george.astoria.game.mechanic.profession.ProfessionItem;
import me.george.astoria.game.mechanic.template.Mechanic;
import me.george.astoria.game.mechanic.template.MechanicPriority;

public class Farming implements Mechanic {

    private ProfessionItem hoe;

    @Override
    public MechanicPriority startPriority() {
        return MechanicPriority.HIGH;
    }

    @Override
    public void startInitialization() {
        // setup all Farming locations/blocks.
    }

    @Override
    public void stopInvocation() {
    }
}

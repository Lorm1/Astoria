package me.george.astoria.game.mechanic.profession.professions;

import me.george.astoria.game.mechanic.profession.ProfessionItem;
import me.george.astoria.game.mechanic.template.Mechanic;
import me.george.astoria.game.mechanic.template.MechanicPriority;

public class Fishing implements Mechanic {

    private ProfessionItem rod;

    @Override
    public MechanicPriority startPriority() {
        return MechanicPriority.HIGH;
    }

    @Override
    public void startInitialization() {
        // setup all fishing locations/spots.
    }

    @Override
    public void stopInvocation() {
    }
}

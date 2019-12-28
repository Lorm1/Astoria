package me.george.astoria.game.mechanic.template;

import java.util.ArrayList;
import java.util.List;

public class MechanicManager {

    private static List<Mechanic> mechanics = new ArrayList<>();

    public static void registerMechanic(Mechanic mechanic) {
        mechanics.add(mechanic);
    }

    /**
     * Load all of mechanics.
     */
    public static void loadMechanics() {
        for (MechanicPriority ep : MechanicPriority.values())
            if (ep != MechanicPriority.NO_STARTUP)
                mechanics.stream().filter(gm -> gm.startPriority() == ep).forEach(Mechanic::startInitialization);
    }

    /**
     * Shutdown all of mechanics.
     */
    public static void stopMechanics() {
        for (MechanicPriority ep : MechanicPriority.values())
            mechanics.stream().filter(gm -> gm.startPriority() == ep).forEach(Mechanic::stopInvocation);
        mechanics.clear(); // We don't need to track these anymore, and it prevents the shutdown from being called twice.
    }
}

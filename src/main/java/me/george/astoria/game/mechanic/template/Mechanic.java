package me.george.astoria.game.mechanic.template;

public interface Mechanic {

    MechanicPriority startPriority();

    void startInitialization();

    void stopInvocation();
}

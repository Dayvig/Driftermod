package DrifterMod.interfaces;

import basemod.interfaces.ISubscriber;


public interface hasOverdrawTrigger
        extends ISubscriber
{
    default void atStartOfTurnPostOverdraw() { }
}
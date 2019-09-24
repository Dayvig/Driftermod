package DrifterMod.Patches;

import DrifterMod.interfaces.hasOverdrawTrigger;
import DrifterMod.powers.TempMaxHandSizeInc;
import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.audio.TempMusic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;


public class OverdrawPatch {
    @SpirePatch(
            clz = DrawCardAction.class,
            method = "update")

    public static class Overdraw {
        public static void Prefix(DrawCardAction _instance) {
            if (_instance.amount + AbstractDungeon.player.hand.size() > BaseMod.MAX_HAND_SIZE){
                int k = (_instance.amount + AbstractDungeon.player.hand.size()) - BaseMod.MAX_HAND_SIZE;
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new TempMaxHandSizeInc(AbstractDungeon.player, AbstractDungeon.player, k), k));
                for (AbstractPower p : AbstractDungeon.player.powers){
                    if (p instanceof hasOverdrawTrigger){
                        System.out.println("test2");
                        ((hasOverdrawTrigger) p).atStartOfTurnPostOverdraw();
                    }
                }
            }
        }
    }
}


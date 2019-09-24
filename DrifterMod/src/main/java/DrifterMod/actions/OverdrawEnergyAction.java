package DrifterMod.actions;

import DrifterMod.DrifterMod;
import DrifterMod.powers.TempMaxHandSizeInc;
import com.badlogic.gdx.audio.Music;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.audio.MainMusic;
import com.megacrit.cardcrawl.audio.TempMusic;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;


public class OverdrawEnergyAction extends AbstractGameAction {
    private String key;

    public OverdrawEnergyAction (int amnt){
        amount = amnt;
    }

    public void update(){
        if (AbstractDungeon.player.hasPower(TempMaxHandSizeInc.POWER_ID)){
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(amount));
            this.isDone = true;
        }
    }
}


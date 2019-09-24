package DrifterMod.actions;

import DrifterMod.DrifterMod;
import com.badlogic.gdx.audio.Music;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.audio.MainMusic;
import com.megacrit.cardcrawl.audio.TempMusic;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;


public class EurobeatAction extends AbstractGameAction {
    private String key;
    public EurobeatAction (String k){
        key = k;
    }

    public void update(){
        AbstractDungeon.getCurrRoom().playBGM(key);
        this.isDone = true;
    }
}


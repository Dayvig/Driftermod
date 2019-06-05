package QueenMod.actions;

import QueenMod.cards.*;
import QueenMod.powers.ReinforcementsPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

public class ReinforcementAction extends AbstractGameAction {
    AbstractMonster monster;
    int numCards;
    int numTimes;
    AbstractCard[] upgradeMatrix;
    boolean isPlayer;
    AbstractCreature th;
    String Text = "My draw pile is empty!";
    String Text2 = "I have no soldiers in my draw pile!";

    public ReinforcementAction(AbstractCreature t, int n){
        numTimes = n;
        th = t;
        isPlayer = t.equals(AbstractDungeon.player);
    }

    public void update(){
        if (!isPlayer){
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(th, th, ReinforcementsPower.POWER_ID));
            this.isDone = true;
            return;
        }
        CardGroup p = AbstractDungeon.player.drawPile;
        if (p.group.isEmpty()){
            AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, Text2, true));
            this.isDone = true;
            return;
        }
        upgradeMatrix = new AbstractCard[p.size()];
        for (int i = 0;i<numTimes;i++) {
            numCards = 0;
            for (AbstractCard c : p.group) {
                if (c.cardID.equals(Hornet.ID) ||
                        c.cardID.equals(BumbleBee.ID) ||
                        c.cardID.equals(HoneyBee.ID) ||
                        c.cardID.equals(HornetCommander.ID) ||
                        c.cardID.equals(BumbleBeeCommander.ID) ||
                        c.cardID.equals(HoneyBeeCommander.ID)) {
                    upgradeMatrix[numCards] = c;
                    numCards++;
                }
            }
            if (numCards == 0) {
                AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, Text, true));
                this.isDone = true;
                return;
            }
            AbstractCard c1 = upgradeMatrix[AbstractDungeon.cardRandomRng.random(numCards-1)];
            c1.freeToPlayOnce = true;
            c1.applyPowers();
            AbstractDungeon.player.limbo.group.add(c1);
            if (c1.cardID.equals(Hornet.ID)){
                Hornet tmp = (Hornet) c1;
                tmp.playedBySwarm = true;
                AbstractDungeon.actionManager.addToTop(new QueueCardAction(c1, AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng)));
            }
            else if (c1.cardID.equals(HornetCommander.ID)){
                HornetCommander tmp = (HornetCommander) c1;
                tmp.playedBySwarm = true;
                AbstractDungeon.actionManager.addToTop(new QueueCardAction(c1, AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng)));
            }
            else {
                AbstractDungeon.actionManager.addToTop(new QueueCardAction(c1, null));
            }
        }
        for (AbstractCard c : AbstractDungeon.player.limbo.group){
            AbstractDungeon.actionManager.addToBottom(new UnlimboAction(c));
        }
        this.isDone = true;
    }
}


package QueenMod.cards;

import QueenMod.QueenMod;
import QueenMod.actions.DrawToHandAction;
import QueenMod.actions.RecruitAction;
import QueenMod.characters.TheQueen;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static QueenMod.QueenMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
public class Anticipate extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = QueenMod.makeID(Anticipate.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;

    private static final int COST = 1;  // COST = ${COST}
    private static final int BLOCK = 5;
    private static final int UPGRADE_BLOCK = 3;
    public AbstractMonster hoverTarget = null;
    // /STAT DECLARATION/


    public Anticipate() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = block = BLOCK;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p,p,this.block));
        for (AbstractCard c : AbstractDungeon.player.drawPile.group){
            if (c.cardID.equals(Drone.ID)) {
                AbstractDungeon.actionManager.addToBottom(new DrawToHandAction(new Drone()));
                break;
            }
        }
        if (m.intent.equals(AbstractMonster.Intent.ATTACK) ||
                m.intent.equals(AbstractMonster.Intent.ATTACK_BUFF) ||
                m.intent.equals(AbstractMonster.Intent.ATTACK_DEBUFF) ||
                m.intent.equals(AbstractMonster.Intent.ATTACK_DEFEND)
        )
        {
            AbstractDungeon.actionManager.addToBottom(new RecruitAction(new BumbleBee(), 1));
        }
        else if (m.intent.equals(AbstractMonster.Intent.BUFF)|| m.intent.equals(AbstractMonster.Intent.DEFEND_BUFF)){
            AbstractDungeon.actionManager.addToBottom(new RecruitAction(new Hornet(), 1));
        }
        else if (m.intent.equals(AbstractMonster.Intent.DEBUFF) || m.intent.equals(AbstractMonster.Intent.DEFEND_DEBUFF)){
            AbstractDungeon.actionManager.addToBottom(new RecruitAction(new HoneyBee(), 1));
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new RecruitAction(new HoneyBee(), 1));
        }
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
    }

    @Override
    public void update() {
        super.update();
        if (hoverTarget == null){
            return;
        }
        if (!hoverTarget.hb.hovered){
            this.rawDescription = DESCRIPTION;
        }
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (AbstractDungeon.player.isDraggingCard) {
                hoverTarget = m;
                if (m.intent.equals(AbstractMonster.Intent.ATTACK) ||
                        m.intent.equals(AbstractMonster.Intent.ATTACK_BUFF) ||
                        m.intent.equals(AbstractMonster.Intent.ATTACK_DEBUFF) ||
                        m.intent.equals(AbstractMonster.Intent.ATTACK_DEFEND)
                )
                {
                    this.rawDescription = EXTENDED_DESCRIPTION[0];
                    initializeDescription();
                }
                else if (m.intent.equals(AbstractMonster.Intent.BUFF)|| m.intent.equals(AbstractMonster.Intent.DEFEND_BUFF)){
                    this.rawDescription = EXTENDED_DESCRIPTION[1];
                    initializeDescription();
                }
                else if (m.intent.equals(AbstractMonster.Intent.DEBUFF) || m.intent.equals(AbstractMonster.Intent.DEFEND_DEBUFF)){
                    this.rawDescription = EXTENDED_DESCRIPTION[2];
                    initializeDescription();
                }
                else {
                    this.rawDescription = EXTENDED_DESCRIPTION[3];
                    initializeDescription();
                }
            }
        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
            initializeDescription();
        }
    }
}

package DrifterMod.cards;

import DrifterMod.DrifterMod;
import DrifterMod.characters.TheDrifter;
import DrifterMod.powers.DrawDownPower;
import DrifterMod.powers.DriftPower;
import DrifterMod.powers.DriftingPower;
import DrifterMod.powers.ThornsDownPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;

import static DrifterMod.DrifterMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
public class SpinOut extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DrifterMod.makeID(SpinOut.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheDrifter.Enums.COLOR_YELLOW;

    private static final int COST = 2;  // COST = ${COST}
    private static final int MAGIC = 3;
    private static final int UPGRADE_MAGIC = 1;
    // /STAT DECLARATION/


    public SpinOut() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(DriftPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ThornsPower(p, magicNumber * p.getPower(DriftPower.POWER_ID).amount), magicNumber * p.getPower(DriftPower.POWER_ID).amount));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ThornsDownPower(p, p, magicNumber * p.getPower(DriftPower.POWER_ID).amount), magicNumber * p.getPower(DriftPower.POWER_ID).amount));
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, DriftPower.POWER_ID));
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, DriftingPower.POWER_ID));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DrawDownPower(p, p, p.getPower(DriftPower.POWER_ID).amount), p.getPower(DriftPower.POWER_ID).amount));
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m){
        super.canUse(p, m);
        return p.hasPower(DriftPower.POWER_ID);
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.upgradeMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }
}

package QueenMod.cards;

import QueenMod.QueenMod;
import QueenMod.characters.TheQueen;
import QueenMod.powers.AttackNextTurnPower;
import QueenMod.powers.SkillNextTurnPower;
import basemod.helpers.ModalChoice;
import basemod.helpers.ModalChoiceBuilder;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static QueenMod.QueenMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
public class PlanAhead extends AbstractDynamicCard implements ModalChoice.Callback {

    // TEXT DECLARATION

    public static final String ID = QueenMod.makeID(PlanAhead.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.NONE;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheQueen.Enums.COLOR_YELLOW;

    private static final int COST = 0;  // COST = ${COST}
    private ModalChoice modal;
    // /STAT DECLARATION/


    public PlanAhead() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = magicNumber = 2;
        modal = new ModalChoiceBuilder()
                .setCallback(this) // Sets callback of all the below options to this
                .setColor(CardColor.RED) // Sets color of any following cards to colorless
                .addOption("Add " + magicNumber + " attacks to the top of your draw pile at the end of the turn.", CardTarget.NONE)
                .setColor(CardColor.GREEN) // Sets color of any following cards to red
                .addOption("Add " + magicNumber + " non-Drone skills to the top of your draw pile at the end of the turn.", CardTarget.NONE)
                .create();
        modal.generateTooltips();
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        modal.open();
    }

    // This is called when one of the option cards us chosen
    @Override
    public void optionSelected(AbstractPlayer p, AbstractMonster m, int i) {
        CardColor color;
        switch (i) {
            case 0:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new AttackNextTurnPower(p, p, this.magicNumber), this.magicNumber));
                break;
            case 1:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SkillNextTurnPower(p, p, this.magicNumber), this.magicNumber));
                break;
            default:
                break;
        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            modal = new ModalChoiceBuilder()
                    .setCallback(this) // Sets callback of all the below options to this
                    .setColor(CardColor.RED) // Sets color of any following cards to colorless
                    .addOption("Add " + magicNumber + " attacks to the top of your draw pile at the end of the turn.", CardTarget.ENEMY)
                    .setColor(CardColor.GREEN) // Sets color of any following cards to red
                    .addOption("Add " + magicNumber + " skills to the top of your draw pile at the end of the turn.", CardTarget.ENEMY)
                    .create();
            modal.generateTooltips();
            initializeDescription();
        }
    }
}

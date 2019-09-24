package DrifterMod.powers;

import DrifterMod.DrifterMod;
import DrifterMod.actions.OverdrawEnergyAction;
import DrifterMod.interfaces.hasOverdrawTrigger;
import DrifterMod.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.RetainCardsAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static DrifterMod.DrifterMod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class BalancePower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;
    public int balanced;
    public static final String POWER_ID = DrifterMod.makeID("BalancePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84f = TextureLoader.getTexture(makePowerPath("full84.png"));
    private static final Texture tex32f = TextureLoader.getTexture(makePowerPath("full32.png"));
    private static final Texture tex84s = TextureLoader.getTexture(makePowerPath("spill84.png"));
    private static final Texture tex32s = TextureLoader.getTexture(makePowerPath("spill32.png"));

    public BalancePower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84f, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32f, 0, 0, 32, 32);
        balanced = 0;
        updateDescription();
    }

    @Override
    public void atStartOfTurn(){
        if (balanced == 0){
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, this.amount));
        }
        else {
            this.region128 = new TextureAtlas.AtlasRegion(tex84f, 0, 0, 84, 84);
            this.region48 = new TextureAtlas.AtlasRegion(tex32f, 0, 0, 32, 32);
        }
        balanced = 0;
    }


    @Override
    public void onAfterUseCard(AbstractCard c, UseCardAction a) {
        super.onAfterUseCard(c, a);
        if (AbstractDungeon.player.cardsPlayedThisTurn%2 == 0){
            this.region128 = new TextureAtlas.AtlasRegion(tex84f, 0, 0, 84, 84);
            this.region48 = new TextureAtlas.AtlasRegion(tex32f, 0, 0, 32, 32);
        }
        else {
            this.region128 = new TextureAtlas.AtlasRegion(tex84s, 0, 0, 84, 84);
            this.region48 = new TextureAtlas.AtlasRegion(tex32s, 0, 0, 32, 32);
        }
        balanced = AbstractDungeon.player.cardsPlayedThisTurn%2;
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else if (amount > 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new ThermalPower(owner, source, amount);
    }
}

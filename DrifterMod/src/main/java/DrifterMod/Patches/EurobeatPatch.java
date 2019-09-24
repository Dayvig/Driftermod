package DrifterMod.Patches;

import DrifterMod.DrifterMod;
import com.badlogic.gdx.audio.Music;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.audio.MainMusic;
import com.megacrit.cardcrawl.audio.TempMusic;

@SpirePatch(
        clz = TempMusic.class,
        method = "getSong")
public class EurobeatPatch {

    @SpirePostfixPatch
    public static SpireReturn<Music> Prefix(TempMusic __instance, String key) {
        DrifterMod.logger.info("Music patch Temp hit");
        switch (key) {
            case "Gas": {
                return SpireReturn.Return(MainMusic.newMusic("DrifterModResources/audio/music/Gas.ogg"));
            }
            case "NightFire": {
                return SpireReturn.Return(MainMusic.newMusic("DrifterModResources/audio/music/NightFire.ogg"));
            }
            case "Dejavu": {
                return SpireReturn.Return(MainMusic.newMusic("DrifterModResources/audio/music/Dejavu.ogg"));
            }
            default: {
                return SpireReturn.Continue();
            }
        }
    }
}

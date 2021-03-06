package me.nokko.bexment.common.registry;

import me.nokko.bexment.common.Besmirchment;
import io.github.ytg1234.recipeconditions.api.RecipeConds;
import io.github.ytg1234.recipeconditions.api.condition.util.RecipeCondsUtil;
import vazkii.patchouli.api.PatchouliAPI;

@SuppressWarnings("ConstantConditions")
public class BSMConditions {
    public static void init(){
        PatchouliAPI.get().setConfigFlag("bsm_final_broom", Besmirchment.config.enableFinalBroom);
        PatchouliAPI.get().setConfigFlag("bsm_witchy_dye", Besmirchment.config.enableWitchyDye);
        PatchouliAPI.get().setConfigFlag("bsm_elite_coffin", Besmirchment.config.enableEliteCoffin);
        PatchouliAPI.get().setConfigFlag("bsm_love_potion", Besmirchment.config.enableLovePotion);
        PatchouliAPI.get().setConfigFlag("bsm_universal_familiars", Besmirchment.config.universalFamiliars.enable);
        PatchouliAPI.get().setConfigFlag("bsm_werepyres_spawn", Besmirchment.config.mobs.werepyreWeight > 0);
        PatchouliAPI.get().setConfigFlag("bsm_werepyrism", Besmirchment.config.enableWerepyrism);
        PatchouliAPI.get().setConfigFlag("bsm_beelzebub", Besmirchment.config.mobs.enableBeelzebub);
        PatchouliAPI.get().setConfigFlag("bsm_tamable_Demons", Besmirchment.config.enableTamableDemons);
        PatchouliAPI.get().setConfigFlag("bsm_sunscreen", Besmirchment.config.enableSunscreen);
        PatchouliAPI.get().setConfigFlag("bsm_lichdom", Besmirchment.config.enableLichdom);
        BSMUtil.register(RecipeConds.RECIPE_CONDITION, "bsm_config", RecipeCondsUtil.stringParam(BSMConditions::getOption));
    }

    public static boolean getOption(String key){
        switch (key) {
            case "final_broom":
                return Besmirchment.config.enableFinalBroom;
            case "witchy_dye":
                return Besmirchment.config.enableWitchyDye;
            case "elite_coffin":
                return Besmirchment.config.enableEliteCoffin;
            case "love_potion":
                return Besmirchment.config.enableLovePotion;
            case "universal_familiars":
                return Besmirchment.config.universalFamiliars.enable;
            case "sunscreen":
                return Besmirchment.config.enableSunscreen;
            case "lichdom":
                return Besmirchment.config.enableLichdom;
            case "beelzebub":
                return Besmirchment.config.mobs.enableBeelzebub;
        }
        return false;
    }
}

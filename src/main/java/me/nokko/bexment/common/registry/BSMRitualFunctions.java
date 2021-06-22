package me.nokko.bexment.common.registry;

import me.nokko.bexment.common.ritualfunction.ColorRitualFunction;
import me.nokko.bexment.common.ritualfunction.ReachLichdomRitualFunction;
import moriyashiine.bewitchment.api.registry.RitualFunction;
import moriyashiine.bewitchment.common.registry.BWRegistries;

public class BSMRitualFunctions {
    public static final RitualFunction COLOR = new ColorRitualFunction();
    public static final RitualFunction REACH_LICHDOM = new ReachLichdomRitualFunction();

    public static void init(){
        BSMUtil.register(BWRegistries.RITUAL_FUNCTIONS, "color", COLOR);
        BSMUtil.register(BWRegistries.RITUAL_FUNCTIONS, "reach_lichdom", REACH_LICHDOM);
    }
}

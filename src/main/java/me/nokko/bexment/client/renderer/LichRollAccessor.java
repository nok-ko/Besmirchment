package me.nokko.bexment.client.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public interface LichRollAccessor {
    @Environment(EnvType.CLIENT) int getLastRollTicks();
}

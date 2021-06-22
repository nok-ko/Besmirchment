package me.nokko.bexment.common.transformation;

import moriyashiine.bewitchment.api.registry.Transformation;
import net.minecraft.entity.player.PlayerEntity;

public class LichTransformation extends Transformation {
    public LichTransformation() {
    }

    @Override
    public void onAdded(PlayerEntity entity) {
        if (entity instanceof LichAccessor) {
            ((LichAccessor) entity).updateCachedSouls();
        }
    }

    @Override
    public void onRemoved(PlayerEntity entity) {
        if (entity instanceof LichAccessor) {
            ((LichAccessor) entity).updateCachedSouls();
            LichLogic.addAttributes(entity, -1);
        }
    }
}

package me.nokko.bexment.common.entity.interfaces;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

// Extend a MobEntity mixin instead?
// This is for Leonard Milking!
public interface InteractableMob {
    ActionResult interactMob(PlayerEntity player, Hand hand);
}

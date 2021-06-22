package me.nokko.bexment.common.ritualfunction;

import me.nokko.bexment.common.packet.LichRevivePacket;
import me.nokko.bexment.common.registry.BSMTransformations;
import moriyashiine.bewitchment.api.BewitchmentAPI;
import moriyashiine.bewitchment.api.interfaces.entity.CurseAccessor;
import moriyashiine.bewitchment.api.interfaces.entity.TransformationAccessor;
import moriyashiine.bewitchment.api.registry.RitualFunction;
import moriyashiine.bewitchment.common.registry.BWCurses;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;

public class ReachLichdomRitualFunction extends RitualFunction {
    public ReachLichdomRitualFunction() {
        super(ParticleTypes.HAPPY_VILLAGER, living -> living.getGroup().equals(BewitchmentAPI.DEMON));
    }


    @Override
    public boolean isValid(ServerWorld world, BlockPos pos, Inventory inventory) {
        PlayerEntity closestPlayer = world.getClosestPlayer((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, 8.0D, false);
        if (BSMTransformations.isLich(closestPlayer, false)){
            closestPlayer.sendMessage(new TranslatableText("message.bexment.no_liches"), true);
            return false;
        }else if (!((CurseAccessor) closestPlayer).hasCurse(BWCurses.APATHY)){
            closestPlayer.sendMessage(new TranslatableText("message.bexment.needs_apathy"), true);
            return false;
        }
        return super.isValid(world, pos, inventory);
    }

    @Override
    public void start(ServerWorld world, BlockPos glyphPos, BlockPos effectivePos, Inventory inventory, boolean catFamiliar) {
        PlayerEntity closestPlayer = world.getClosestPlayer((double)effectivePos.getX() + 0.5D, (double)effectivePos.getY() + 0.5D, (double)effectivePos.getZ() + 0.5D, 8.0D, false);
        if (!BSMTransformations.isLich(closestPlayer, false) && ((CurseAccessor)closestPlayer).hasCurse(BWCurses.APATHY)){
            ((TransformationAccessor) closestPlayer).getTransformation().onRemoved(closestPlayer);
            ((TransformationAccessor) closestPlayer).setAlternateForm(false);
            ((TransformationAccessor) closestPlayer).setTransformation(BSMTransformations.LICH);
            ((TransformationAccessor) closestPlayer).getTransformation().onAdded(closestPlayer);
            LichRevivePacket.send(closestPlayer);
            if (catFamiliar){
                ((CurseAccessor) closestPlayer).removeCurse(BWCurses.APATHY);
                closestPlayer.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 1200, 2, true, true));
                closestPlayer.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 1200, 0, true, true));
            }
        }
        super.start(world, glyphPos, effectivePos, inventory, catFamiliar);
    }
}

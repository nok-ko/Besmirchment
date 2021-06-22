package de.aelpecyem.besmirchment.mixin;


import de.aelpecyem.besmirchment.common.entity.interfaces.InteractableMob;
import moriyashiine.bewitchment.common.entity.interfaces.PledgeAccessor;
import moriyashiine.bewitchment.common.entity.living.LeonardEntity;
import moriyashiine.bewitchment.common.registry.BWObjects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LeonardEntity.class)
public abstract class LeonardEntityMixin implements InteractableMob {

    long lastMilked;

    // Milkable!
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        boolean pledged = ((PledgeAccessor) player).getPledge().equals(((LeonardEntity) (Object) this).getPledgeID());
        ItemStack bowlStack = player.getStackInHand(hand);
        if (bowlStack.getItem() == Items.BOWL && pledged) {
            if (!player.world.isClient) {
                long gameTime = player.world.getTime();
                if (gameTime - lastMilked <= 6000) { // 6000 ticks: 5 minute timer
                    player.sendMessage(Text.of("It is too early to milk me, child."), true);
                    return ActionResult.PASS;
                }
                // exchangeStack(inputStack, player, outputStack, magicBoolean???)
                ItemStack stewStack = ItemUsage.method_30270(
                        bowlStack,
                        player,
                        new ItemStack(BWObjects.GROTESQUE_STEW),
                        false);

                player.setStackInHand(hand, stewStack);
                lastMilked = gameTime;
            }
            return ActionResult.success(((LeonardEntity) (Object) this).world.isClient);
        }
        return ActionResult.PASS;
    }

    // Save the milk timer!
    @Inject(method = "writeCustomDataToTag(Lnet/minecraft/nbt/CompoundTag;)V", at = @At("TAIL"))
    public void writeCustomDataToTag(CompoundTag tag, CallbackInfo ci) {
        tag.putLong("LastMilked", lastMilked);
    }

    @Inject(method = "readCustomDataFromTag(Lnet/minecraft/nbt/CompoundTag;)V", at = @At("TAIL"))
    public void readCustomDataFromTag(CompoundTag tag, CallbackInfo ci) {
        lastMilked = tag.getLong("LastMilked");
    }
}

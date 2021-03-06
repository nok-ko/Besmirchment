package me.nokko.bexment.mixin.client;

import me.nokko.bexment.client.renderer.DyedWerewolfFeatureRenderer;
import me.nokko.bexment.common.Besmirchment;
import me.nokko.bexment.common.entity.interfaces.DyeableEntity;
import me.nokko.bexment.common.item.WitchyDyeItem;
import moriyashiine.bewitchment.client.model.entity.living.WerewolfEntityModel;
import moriyashiine.bewitchment.client.renderer.entity.living.WerewolfEntityRenderer;
import moriyashiine.bewitchment.common.entity.living.WerewolfEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(value = WerewolfEntityRenderer.class, remap = false)
public abstract class WerewolfEntityRendererMixin extends MobEntityRenderer<WerewolfEntity, WerewolfEntityModel<WerewolfEntity>> {
    private static final Identifier UNTINTED_TEXTURE = Besmirchment.id("textures/entity/werewolf/untinted.png");

    public WerewolfEntityRendererMixin(EntityRenderDispatcher entityRenderDispatcher, WerewolfEntityModel entityModel, float f) {
        super(entityRenderDispatcher, entityModel, f);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectConstructor(EntityRenderDispatcher entityRenderDispatcher, CallbackInfo ci){
        this.addFeature(new DyedWerewolfFeatureRenderer((WerewolfEntityRenderer) (Object) this));
    }

    //when colored, use only the untinted parts for the model, the rest is done with the layer
    @Inject(method = "getTexture", at = @At("HEAD"), cancellable = true)
    private void getTexture(WerewolfEntity entity, CallbackInfoReturnable<Identifier> cir){
        if (((DyeableEntity) entity).getColor() >= 0 || ((DyeableEntity) entity).getColor() == WitchyDyeItem.FUNNI_NUMBER){
            cir.setReturnValue(UNTINTED_TEXTURE);
        }
    }
}

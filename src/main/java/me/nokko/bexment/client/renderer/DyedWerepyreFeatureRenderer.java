package me.nokko.bexment.client.renderer;

import me.nokko.bexment.client.model.WerepyreEntityModel;
import me.nokko.bexment.common.Besmirchment;
import me.nokko.bexment.common.entity.interfaces.DyeableEntity;
import me.nokko.bexment.common.entity.WerepyreEntity;
import me.nokko.bexment.common.item.WitchyDyeItem;
import me.nokko.bexment.common.registry.BSMUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

@Environment(EnvType.CLIENT)
public class DyedWerepyreFeatureRenderer extends FeatureRenderer<WerepyreEntity, WerepyreEntityModel<WerepyreEntity>> {
    private static final Identifier TINTED_TEXTURE = Besmirchment.id("textures/entity/werepyre/tinted.png");

    public DyedWerepyreFeatureRenderer(FeatureRendererContext<WerepyreEntity, WerepyreEntityModel<WerepyreEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, WerepyreEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        int color = ((DyeableEntity) entity).getColor();
        if (color > -1) {
            Vector3f rgb = new Vector3f(Vec3d.unpackRgb(color));
            render(this.getContextModel(), this.getContextModel(), TINTED_TEXTURE, matrices, vertexConsumers, light, entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch, tickDelta, rgb.getX(), rgb.getY(), rgb.getZ());
        }else if(color == WitchyDyeItem.FUNNI_NUMBER){
            Vector3f rgb = new Vector3f(Vec3d.unpackRgb(BSMUtil.HSBtoRGB((animationProgress % 100) / 100F, 1,1)));
            render(this.getContextModel(), this.getContextModel(), TINTED_TEXTURE, matrices, vertexConsumers, light, entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch, tickDelta, rgb.getX(), rgb.getY(), rgb.getZ());
        }
    }
}

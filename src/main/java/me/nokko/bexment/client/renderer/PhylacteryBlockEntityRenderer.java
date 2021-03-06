package me.nokko.bexment.client.renderer;

import me.nokko.bexment.client.model.PhylacteryOrbModel;
import me.nokko.bexment.common.Besmirchment;
import me.nokko.bexment.common.block.entity.PhylacteryBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class PhylacteryBlockEntityRenderer extends BlockEntityRenderer<PhylacteryBlockEntity> {
    private static final Identifier PHYLACTERY_TEXTURE = Besmirchment.id("textures/block/phylactery.png");
    private static final PhylacteryOrbModel ORBS = new PhylacteryOrbModel();
    public PhylacteryBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(PhylacteryBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        //matrices.translate(0.5, 0, 0.5);
        ///ORBS.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityCutout(PHYLACTERY_TEXTURE)), light, overlay, 1, 1, 1, 1);
    }
}

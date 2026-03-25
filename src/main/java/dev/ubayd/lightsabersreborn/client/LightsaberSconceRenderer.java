package dev.ubayd.lightsabersreborn.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.ubayd.lightsabersreborn.block.LightsaberBlockEntity;
import dev.ubayd.lightsabersreborn.block.LightsaberSconce;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class LightsaberSconceRenderer implements BlockEntityRenderer<LightsaberBlockEntity> {

    public LightsaberSconceRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(LightsaberBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        ItemStack stack = blockEntity.getStack();
        if (stack.isEmpty()) return;

        poseStack.pushPose();

        Direction facing = blockEntity.getBlockState().getValue(LightsaberSconce.FACING);

        // Start at the absolute center of the block
        poseStack.translate(0.5D, 0.5D, 0.5D);

        // Rotate so the lightsaber turns to face away from the wall
        float rotation = -facing.toYRot();
        poseStack.mulPose(Axis.YP.rotationDegrees(rotation));

        double shiftX = 0.0D;   // Move Left/Right
        double shiftY = 0.1D;  // Move Up/Down (negative is downt)
        double shiftZ = -0.3D;  // Move Forward/Backward

        poseStack.translate(shiftX, shiftY, shiftZ);

        // Draw the item
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, packedLight, packedOverlay, poseStack, bufferSource, blockEntity.getLevel(), 0);

        poseStack.popPose();
    }
}

package mightydanp.industrialtech.client.tileentity.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import mightydanp.industrialtech.common.tileentity.TileEntityMachineFrame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;

/**
 * Created by MightyDanp on 8/6/2020.
 */
public class TileEntityMachineFrameRenderer extends TileEntityRenderer<TileEntityMachineFrame> {
    private float degrees;

    public TileEntityMachineFrameRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
        degrees = 0.0f;
    }

    @Override
    public void render(TileEntityMachineFrame tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        ItemStack circuitSlot = tileEntityIn.circuitInputWrapper.getStackInSlot(tileEntityIn.circuitInputWrapper.getSlots());
        ItemStack conveyorSlot = tileEntityIn.conveyorInputWrapper.getStackInSlot(tileEntityIn.conveyorInputWrapper.getSlots());
        ItemStack emitterSlot = tileEntityIn.emitterInputWrapper.getStackInSlot(tileEntityIn.emitterInputWrapper.getSlots());
        ItemStack fieldGeneratorInputSlot = tileEntityIn.fieldGeneratorInputWrapper.getStackInSlot(tileEntityIn.fieldGeneratorInputWrapper.getSlots());
        ItemStack motorSlot = tileEntityIn.motorInputWrapper.getStackInSlot(tileEntityIn.motorInputWrapper.getSlots());
        ItemStack pistonSlot = tileEntityIn.pistonInputWrapper.getStackInSlot(tileEntityIn.pistonInputWrapper.getSlots());
        ItemStack pumpSlot = tileEntityIn.pumpInputWrapper.getStackInSlot(tileEntityIn.pumpInputWrapper.getSlots());
        ItemStack robotArmSlot = tileEntityIn.robotArmInputWrapper.getStackInSlot(tileEntityIn.robotArmInputWrapper.getSlots());
        ItemStack sensorSlot = tileEntityIn.sensorInputWrapper.getStackInSlot(tileEntityIn.sensorInputWrapper.getSlots());


        if (!circuitSlot.isEmpty()) {
            matrixStackIn.push();
            matrixStackIn.translate(0.5D, 1.5D, 0.5D);
            float currentTime = tileEntityIn.getWorld().getGameTime() + partialTicks;
            matrixStackIn.translate(0D, (Math.sin(Math.PI * currentTime / 16) / 4) + 0.1D, 0D);
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(degrees++ / 2));
            renderItem(circuitSlot, partialTicks, matrixStackIn, bufferIn, combinedLightIn);
            matrixStackIn.pop();
        }
    }

    private void renderItem(ItemStack stack, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn) {
        Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.FIXED, combinedLightIn,
                OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);
    }
}

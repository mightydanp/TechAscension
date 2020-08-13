package mightydanp.industrialtech.client.tileentity.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import mightydanp.industrialtech.client.gui.slot.SlotCircuit;
import mightydanp.industrialtech.common.tileentity.TileEntityMachineFrame;
import muramasa.antimatter.capability.item.ItemStackWrapper;
import muramasa.antimatter.tile.TileEntityMachine;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import java.util.Objects;
import java.util.Set;

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
        if (!tileEntityIn.circuitSlotItemStack.isEmpty()) {
            matrixStackIn.push();
            matrixStackIn.translate(0.5D, 1.5D, 0.5D);
            float currentTime = Objects.requireNonNull(tileEntityIn.getWorld()).getGameTime() + partialTicks;
            matrixStackIn.translate(0D, (Math.sin(Math.PI * currentTime / 16) / 4) + 0.1D, 0D);
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(degrees++ / 2));
            renderItem(tileEntityIn.circuitSlotItemStack, partialTicks, matrixStackIn, bufferIn, combinedLightIn);
            matrixStackIn.pop();
        }
    }

    private void renderItem(ItemStack stack, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn) {
        Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.FIXED, combinedLightIn,
                OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);
    }
}

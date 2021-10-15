package mightydanp.industrialtech.api.client.rendering.tileentities;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mightydanp.industrialtech.api.client.rendering.models.HoleModel;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.api.common.tileentities.HoleTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.model.data.EmptyModelData;

/**
 * Created by MightyDanp on 10/11/2021.
 */
public class HoleTileEntityRenderer extends TileEntityRenderer<HoleTileEntity> {
    private static final ResourceLocation holeTexture = new ResourceLocation(Ref.mod_id, "textures/block/tree/hole/hole.png");
    private static final ResourceLocation resinTexture = new ResourceLocation(Ref.mod_id, "textures/block/tree/hole/resin_hole.png");

    public HoleTileEntityRenderer(TileEntityRendererDispatcher tileEntityRendererDispatcherIn) {
        super(tileEntityRendererDispatcherIn);
    }

    @Override
    public void render(HoleTileEntity holeTileEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int combinedLight, int combinedOverlay) {
        matrixStack.pushPose();
        Model model = new HoleModel(holeTileEntity);

        matrixStack.translate(0.5, 0, 0.5D);

        Direction direction = holeTileEntity.direction;

        switch (direction.getName().toLowerCase()){
            case "east":
                matrixStack.mulPose(Vector3f.YP.rotationDegrees(-90F));
                break;
            case "south":
                matrixStack.mulPose(Vector3f.YP.rotationDegrees(-180F));
                break;
            case "west":
                matrixStack.mulPose(Vector3f.YP.rotationDegrees(-270F));
                break;
        }

        Minecraft.getInstance().getBlockRenderer().renderBlock(Block.byItem(holeTileEntity.getDesiredBlockSlot().getItem()).defaultBlockState(), matrixStack, iRenderTypeBuffer, combinedLight, combinedOverlay, EmptyModelData.INSTANCE);

        IVertexBuilder renderBuffer = iRenderTypeBuffer.getBuffer(model.renderType(getHoleTexture()));
        model.renderToBuffer(matrixStack, renderBuffer, combinedLight, combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);

        if(holeTileEntity.hasResin){
            IVertexBuilder renderBuffer2 = iRenderTypeBuffer.getBuffer(model.renderType(getResinTexture()));
            model.renderToBuffer(matrixStack, renderBuffer2, combinedLight, combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
        }

        matrixStack.popPose();
    }

    public static ResourceLocation getHoleTexture() {
        return holeTexture;
    }

    public static ResourceLocation getResinTexture() {
        return resinTexture;
    }
}

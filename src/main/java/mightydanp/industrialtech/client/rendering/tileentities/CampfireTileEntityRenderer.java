package mightydanp.industrialtech.client.rendering.tileentities;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.client.rendering.models.CampfireModel;
import mightydanp.industrialtech.common.blocks.CampfireBlockOverride;
import mightydanp.industrialtech.common.tileentities.CampfireTileEntityOverride;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.model.data.EmptyModelData;

import javax.annotation.Nonnull;

/**
 * Created by MightyDanp on 5/6/2021.
 */
public class CampfireTileEntityRenderer extends TileEntityRenderer<CampfireTileEntityOverride> {
    private static final ResourceLocation campfireOffTexture = new ResourceLocation(Ref.mod_id, "textures/block/campfire/campfire_log.png");
    private static final ResourceLocation campfireOnTexture = new ResourceLocation(Ref.mod_id, "textures/block/campfire/campfire_log_lit.png");
    private static final ResourceLocation campfireOff2Texture = new ResourceLocation(Ref.mod_id, "textures/block/campfire/campfire_off.png");

    public CampfireTileEntityRenderer(TileEntityRendererDispatcher tileEntityRendererDispatcherIn) {
        super(tileEntityRendererDispatcherIn);
    }

    @Override
    public void render(CampfireTileEntityOverride campfireTileEntityOverride, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int combinedLight, int combinedOverlay) {
        matrixStack.pushPose();
        Model model = new CampfireModel(campfireTileEntityOverride);

        matrixStack.translate(0.5, (double) 0.0F, 0.5);

        Direction direction = campfireTileEntityOverride.getBlockState().getValue(CampfireBlockOverride.FACING);

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

        if(campfireTileEntityOverride.getBlockState().getValue(CampfireBlockOverride.LIT)) {
            matrixStack.pushPose();
            IVertexBuilder renderBuffer = iRenderTypeBuffer.getBuffer(model.renderType(getCampfireOnTextureLocation()));
            model.renderToBuffer(matrixStack, renderBuffer, combinedLight, combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F); // white, fully opaque
            matrixStack.popPose();
        }else{
            if(campfireTileEntityOverride.keepLogsFormed) {
                IVertexBuilder renderBuffer = iRenderTypeBuffer.getBuffer(model.renderType(getCampfireOff2TextureLocation()));
                model.renderToBuffer(matrixStack, renderBuffer, combinedLight, combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
            }else{
                IVertexBuilder renderBuffer = iRenderTypeBuffer.getBuffer(model.renderType(getCampfireOffTextureLocation()));
                model.renderToBuffer(matrixStack, renderBuffer, combinedLight, combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
            }
        }

        matrixStack.popPose();

        if(campfireTileEntityOverride.getBlockState().getValue(CampfireBlockOverride.LIT)) {
            matrixStack.pushPose();
            matrixStack.scale(0.65F, 0.7F, 0.65F);
            matrixStack.translate(0.26225, (double) 0.05F, 0.2625);
            Minecraft.getInstance().getBlockRenderer().renderBlock(Blocks.FIRE.defaultBlockState(), matrixStack, iRenderTypeBuffer, combinedLight, combinedOverlay, EmptyModelData.INSTANCE);
            matrixStack.popPose();
        }

        if(!campfireTileEntityOverride.getCookSlot1().isEmpty()) {
            matrixStack.pushPose();
            matrixStack.translate(0.5D, 0.44921875D, 0.5D);
            Direction direction1 = Direction.from2DDataValue((2 + direction.get2DDataValue()) % 4);
            float f = -direction1.toYRot();
            matrixStack.mulPose(Vector3f.YP.rotationDegrees(f));
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
            matrixStack.translate(-0.3125D, -0.3125D, 0.0D);
            matrixStack.scale(0.375F, 0.375F, 0.375F);
            Minecraft.getInstance().getItemRenderer().renderStatic(campfireTileEntityOverride.getCookSlot1(), ItemCameraTransforms.TransformType.FIXED, combinedLight, combinedOverlay, matrixStack, iRenderTypeBuffer);
            matrixStack.popPose();
        }

        if(!campfireTileEntityOverride.getCookSlot2().isEmpty()) {
            matrixStack.pushPose();
            matrixStack.translate(0.5D, 0.44921875D, 0.5D);
            Direction direction1 = Direction.from2DDataValue((3 + direction.get2DDataValue()) % 4);
            float f = -direction1.toYRot();
            matrixStack.mulPose(Vector3f.YP.rotationDegrees(f));
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
            matrixStack.translate(-0.3125D, -0.3125D, 0.0D);
            matrixStack.scale(0.375F, 0.375F, 0.375F);
            Minecraft.getInstance().getItemRenderer().renderStatic(campfireTileEntityOverride.getCookSlot2(), ItemCameraTransforms.TransformType.FIXED, combinedLight, combinedOverlay, matrixStack, iRenderTypeBuffer);
            matrixStack.popPose();
        }

        if(!campfireTileEntityOverride.getCookSlot3().isEmpty()) {
            matrixStack.pushPose();
            matrixStack.translate(0.5D, 0.44921875D, 0.5D);
            Direction direction1 = Direction.from2DDataValue((1 + direction.get2DDataValue()) % 4);
            float f = -direction1.toYRot();
            matrixStack.mulPose(Vector3f.YP.rotationDegrees(f));
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
            matrixStack.translate(-0.3125D, -0.3125D, 0.0D);
            matrixStack.scale(0.375F, 0.375F, 0.375F);
            Minecraft.getInstance().getItemRenderer().renderStatic(campfireTileEntityOverride.getCookSlot3(), ItemCameraTransforms.TransformType.FIXED, combinedLight, combinedOverlay, matrixStack, iRenderTypeBuffer);
            matrixStack.popPose();
        }

        if(!campfireTileEntityOverride.getCookSlot4().isEmpty()) {
            matrixStack.pushPose();
            matrixStack.translate(0.5D, 0.44921875D, 0.5D);
            Direction direction1 = Direction.from2DDataValue((0 + direction.get2DDataValue()) % 4);
            float f = -direction1.toYRot();
            matrixStack.mulPose(Vector3f.YP.rotationDegrees(f));
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
            matrixStack.translate(-0.3125D, -0.3125D, 0.0D);
            matrixStack.scale(0.375F, 0.375F, 0.375F);
            Minecraft.getInstance().getItemRenderer().renderStatic(campfireTileEntityOverride.getCookSlot4(), ItemCameraTransforms.TransformType.FIXED, combinedLight, combinedOverlay, matrixStack, iRenderTypeBuffer);
            matrixStack.popPose();
        }

    }


    public ResourceLocation getCampfireOffTextureLocation() {
        return campfireOffTexture;
    }

    public ResourceLocation getCampfireOnTextureLocation() {
        return campfireOnTexture;
    }

    public ResourceLocation getCampfireOff2TextureLocation() {
        return campfireOff2Texture;
    }
}
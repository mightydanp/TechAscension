package mightydanp.industrialtech.api.client.rendering.tileentities;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mightydanp.industrialtech.api.client.rendering.models.HoleModel;
import mightydanp.industrialtech.api.client.rendering.models.ResinHoleModel;
import mightydanp.industrialtech.api.common.blocks.HoleBlock;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.api.common.tileentities.HoleTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.client.model.data.EmptyModelData;
import org.apache.commons.lang3.ArrayUtils;

import java.awt.*;
import java.util.Arrays;

/**
 * Created by MightyDanp on 10/11/2021.
 */
public class HoleTileEntityRenderer extends TileEntityRenderer<HoleTileEntity> {
    private static final ResourceLocation holeTexture = new ResourceLocation(Ref.mod_id, "textures/block/tree/hole/hole.png");
    private static final ResourceLocation resinTexture = new ResourceLocation(Ref.mod_id, "textures/block/tree/hole/resin_hole.png");

    public HoleTileEntityRenderer(TileEntityRendererDispatcher tileEntityRendererDispatcherIn) {
        super(tileEntityRendererDispatcherIn);
    }

    public static ResourceLocation getHoleTexture() {
        return holeTexture;
    }

    public static ResourceLocation getResinTexture() {
        return resinTexture;
    }

    @Override
    public void render(HoleTileEntity holeTileEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int combinedLight, int combinedOverlay) {
        matrixStack.pushPose();
        Model holeModel = new HoleModel(holeTileEntity);
        Model resinModel = new ResinHoleModel(holeTileEntity);

        Direction direction = holeTileEntity.getBlockState().getValue(HoleBlock.FACING);
        Boolean hasResin = holeTileEntity.getBlockState().getValue(HoleBlock.RESIN);

        matrixStack.translate(0.5, 0, 0.5);

        switch (direction.getName().toLowerCase()) {
            case "west":
                matrixStack.mulPose(Vector3f.YP.rotationDegrees(-90F));
                break;
            case "north":
                matrixStack.mulPose(Vector3f.YP.rotationDegrees(-180F));
                break;
            case "east"://
                matrixStack.mulPose(Vector3f.YP.rotationDegrees(-270F));
                break;
        }
        Color holeColor = Color.decode(String.valueOf(holeTileEntity.holeColor));

        IVertexBuilder renderBuffer = iRenderTypeBuffer.getBuffer(holeModel.renderType(getHoleTexture()));
        holeModel.renderToBuffer(matrixStack, renderBuffer, combinedLight, combinedOverlay, holeColor.getRed(), holeColor.getGreen(), holeColor.getBlue(), holeColor.getAlpha());

        if (hasResin) {
            Color resinColor = Color.decode(String.valueOf(holeTileEntity.resinColor));

            IVertexBuilder renderBuffer2 = iRenderTypeBuffer.getBuffer(resinModel.renderType(getResinTexture()));
            resinModel.renderToBuffer(matrixStack, renderBuffer2, combinedLight, combinedOverlay, resinColor.getRed(), resinColor.getGreen(), resinColor.getBlue(), resinColor.getAlpha());
        }

        matrixStack.popPose();


        matrixStack.pushPose();
        {
            MatrixStack.Entry currentMatrix = matrixStack.last();

            BlockState state = holeTileEntity.desiredBlockState;
            BlockRendererDispatcher dispatcher = Minecraft.getInstance().getBlockRenderer();
            IBakedModel model = dispatcher.getBlockModel(state);

            IVertexBuilder vertexBuffer = iRenderTypeBuffer.getBuffer(RenderType.solid());
            dispatcher.getModelRenderer().renderModel(currentMatrix, vertexBuffer, null, model,
                    1.0F, 1.0F, 1.0F, combinedLight, combinedOverlay, EmptyModelData.INSTANCE);

            //Minecraft.getInstance().getBlockRenderer().renderBlock(Block.byItem(holeTileEntity.getDesiredBlockSlot().getItem()).defaultBlockState(), matrixStack, iRenderTypeBuffer, combinedLight, combinedOverlay, EmptyModelData.INSTANCE);
        }
        matrixStack.popPose();
    }

    /*
    private int getLight(HoleTileEntity holeTileEntityIn) {

        BlockState blockState = holeTileEntityIn.getBlockState();
        World world = holeTileEntityIn.getLevel();

        int median = 0;

        if(world != null) {
            int[] numArray = new int[]{
                    world.getLightEngine().getRawBrightness(holeTileEntityIn.getBlockPos().above(), 0),
                    world.getLightEngine().getRawBrightness(holeTileEntityIn.getBlockPos().below(), 0),
                    world.getLightEngine().getRawBrightness(holeTileEntityIn.getBlockPos().north(), 0),
                    world.getLightEngine().getRawBrightness(holeTileEntityIn.getBlockPos().south(), 0),
                    world.getLightEngine().getRawBrightness(holeTileEntityIn.getBlockPos().east(), 0),
                    world.getLightEngine().getRawBrightness(holeTileEntityIn.getBlockPos().west(), 0)};

            for(int i = numArray.length - 1;  i >= 0; i--){
                if(numArray[i] == 0){
                    numArray = ArrayUtils.remove(numArray, i);
                }
            }

            Arrays.sort(numArray);

            if (numArray.length % 2 == 0) {
                median = (numArray[numArray.length / 2] + numArray[numArray.length / 2 - 1]) / 2;
            } else {
                median = numArray[numArray.length / 2];
            }
        }

        return median;
    }

     */

    public double interpolate_with_clipping(double x, double x1, double x2, double y1, double y2) {
        if (x1 > x2) {
            double temp = x1;
            x1 = x2;
            x2 = temp;
            temp = y1;
            y1 = y2;
            y2 = temp;
        }

        if (x <= x1) return y1;
        if (x >= x2) return y2;
        double xFraction = (x - x1) / (x2 - x1);
        return y1 + xFraction * (y2 - y1);
    }
}

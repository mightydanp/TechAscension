package mightydanp.industrialtech.api.common.datagen;

import mightydanp.industrialtech.api.common.material.ITMaterial;
import mightydanp.industrialtech.api.common.handler.TreeHandler;
import mightydanp.industrialtech.api.common.material.flag.DefaultMaterialFlag;
import mightydanp.industrialtech.api.common.libs.EnumTreeFlags;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.api.common.handler.StoneLayerHandler;
import mightydanp.industrialtech.api.common.material.flag.IMaterialFlag;
import mightydanp.industrialtech.common.libs.StoneLayerFlagsEnum;
import mightydanp.industrialtech.common.materials.ModMaterials;
import mightydanp.industrialtech.common.stonelayers.ModStoneLayers;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

/**
 * Created by MightyDanp on 10/2/2020.
 */

public class GenBlockStates extends BlockStateProvider {


    public GenBlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Ref.mod_id, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        for(ITMaterial ITMaterial : ModMaterials.ITMaterials) {
            materialHandlerHelper(ITMaterial);
        }

        for(StoneLayerHandler stoneLayer: ModStoneLayers.stoneLayerList){
            stoneLayerHandlerHelper(stoneLayer);
        }
    }

    private void materialHandlerHelper(ITMaterial ITMaterial) {
        for(IMaterialFlag flag : ITMaterial.materialFlags){
            if(flag == DefaultMaterialFlag.ORE){
                for(RegistryObject<Block> blockRegistered : ITMaterial.ore) {
                    Block oreBlock = blockRegistered.get();
                    VariantBlockStateBuilder builder = getVariantBuilder(oreBlock);
                    String modId = oreBlock.getRegistryName().toString().split(":")[0];
                    String oreName = oreBlock.getRegistryName().toString().split(":")[1];
                    String stoneVariant = oreBlock.getRegistryName().toString().split(":")[1].split("_")[0];
                    ModelFile ore = models().withExistingParent("block/ore/" + stoneVariant + "_ore", modId + ":block/ore/state/ore").texture("particle", "minecraft:block/" + stoneVariant).texture("sourceblock", "minecraft:block/" + stoneVariant);
                    simpleBlock(oreBlock , ore);
                }
            }

            if(flag == DefaultMaterialFlag.GEM){
                for(RegistryObject<Block> blockRegistered : ITMaterial.ore) {
                    Block oreBlock = blockRegistered.get();
                    VariantBlockStateBuilder builder = getVariantBuilder(oreBlock);
                    String modId = oreBlock.getRegistryName().toString().split(":")[0];
                    String oreName = oreBlock.getRegistryName().toString().split(":")[1];
                    String stoneVariant = oreBlock.getRegistryName().toString().split(":")[1].split("_")[0];
                    ModelFile ore = models().withExistingParent("block/gem/" + stoneVariant + "_gem", modId + ":block/ore/state/gem").texture("particle", "minecraft:block/" + stoneVariant).texture("sourceblock", "minecraft:block/" + stoneVariant);
                    simpleBlock(oreBlock , ore);
                }
            }

            if(flag == DefaultMaterialFlag.ORE || flag == DefaultMaterialFlag.GEM){
                for(RegistryObject<Block> blockRegistered : ITMaterial.smallOre) {
                    Block oreBlock = blockRegistered.get();
                    VariantBlockStateBuilder builder = getVariantBuilder(oreBlock);
                    String modId = oreBlock.getRegistryName().toString().split(":")[0];
                    String oreName = oreBlock.getRegistryName().toString().split(":")[1];
                    String stoneVariant = oreBlock.getRegistryName().toString().split(":")[1].split("_")[1];
                    ModelFile ore = models().withExistingParent("block/small_ore/" + "small_"+ stoneVariant + "_ore", modId + ":block/ore/state/small_ore").texture("particle", "minecraft:block/" + stoneVariant).texture("sourceblock", "minecraft:block/" + stoneVariant);
                    simpleBlock(oreBlock , ore);
                }

                for(RegistryObject<Block> blockRegistered : ITMaterial.denseOre) {
                    Block oreBlock = blockRegistered.get();
                    VariantBlockStateBuilder builder = getVariantBuilder(oreBlock);
                    String modId = oreBlock.getRegistryName().toString().split(":")[0];
                    String oreName = oreBlock.getRegistryName().toString().split(":")[1];
                    String stoneVariant = oreBlock.getRegistryName().toString().split(":")[1].split("_")[1];
                    ModelFile ore = models().withExistingParent("block/dense_ore/" + "dense_"+ stoneVariant + "_ore", modId + ":block/ore/state/dense_ore").texture("particle", "minecraft:block/" + stoneVariant).texture("sourceblock", "minecraft:block/" + stoneVariant);
                    simpleBlock(oreBlock , new ConfiguredModel(ore));
                }
            }

            if(flag == DefaultMaterialFlag.FLUID || flag == DefaultMaterialFlag.GAS){
                Block block = ITMaterial.fluidBlock.get();

                ModelFile ore = models().withExistingParent("block/fluid/" + ITMaterial.name, "block/lava").texture("particle", block.getRegistryName() + "_still");
                simpleBlock(block , new ConfiguredModel(ore));
            }
        }
    }

    private void stoneLayerHandlerHelper(StoneLayerHandler stoneLayer){
        for(StoneLayerFlagsEnum flag : stoneLayer.flags) {
            if (flag == StoneLayerFlagsEnum.thinSlab) {
                Block thinSlab = stoneLayer.thin_slab_block.get();
                VariantBlockStateBuilder builder = getVariantBuilder(thinSlab);
                String modId = thinSlab.getRegistryName().toString().split(":")[0];
                String oreName = thinSlab.getRegistryName().toString().split(":")[1];
                //String stoneVariant = thinSlab.getRegistryName().toString().split(":")[1].split("_")[1];
                ModelFile ore = models().withExistingParent("block/stone_layer/thin_slab/" + "thin_" + stoneLayer.name + "_slab", modId + ":block/stone_layer/state/thin_slab").texture("particle", "minecraft:block/" + stoneLayer.name).texture("texture", "minecraft:block/" + stoneLayer.name);

                simpleBlock(thinSlab, new ConfiguredModel(ore));
            }
        }
    }

    private void treeHandler(TreeHandler treeHandler) {
        for(EnumTreeFlags flag : treeHandler.treeFlags) {
            if (flag == EnumTreeFlags.LEAVE) {

            }
        }
    }

}
package mightydanp.industrialtech.api.common.datagen;

import mightydanp.industrialtech.api.common.blocks.BlockOre;
import mightydanp.industrialtech.api.common.handler.MaterialHandler;
import mightydanp.industrialtech.api.common.libs.EnumMaterialFlags;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.common.materials.ModMaterials;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ObjectHolder;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by MightyDanp on 10/2/2020.
 */

public class BlockStates extends BlockStateProvider {


    public BlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Ref.mod_id, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        for(MaterialHandler material : ModMaterials.materials) {
            materialHandlerHelper(material);
        }
    }

    private void materialHandlerHelper(MaterialHandler material) {
        for(EnumMaterialFlags flag : material.flags){
            if(flag == EnumMaterialFlags.ORE){
                for(RegistryObject<Block> blockRegistered : material.blockOre) {
                    Block oreBlock = blockRegistered.get();
                    VariantBlockStateBuilder builder = getVariantBuilder(oreBlock);
                    String modId = oreBlock.getRegistryName().toString().split(":")[0];
                    String oreName = oreBlock.getRegistryName().toString().split(":")[1];
                    String stoneVariant = oreBlock.getRegistryName().toString().split(":")[1].split("_")[0];
                    ModelFile ore = models().withExistingParent("block/ore/" + stoneVariant + "_ore", modId + ":block/ore/" + stoneVariant + "_ore");
                    simpleBlock(oreBlock , ore);
                }
            }
            if(flag == EnumMaterialFlags.GEM){
                for(RegistryObject<Block> blockRegistered : material.blockOre) {
                    Block oreBlock = blockRegistered.get();
                    VariantBlockStateBuilder builder = getVariantBuilder(oreBlock);
                    String modId = oreBlock.getRegistryName().toString().split(":")[0];
                    String oreName = oreBlock.getRegistryName().toString().split(":")[1];
                    String stoneVariant = oreBlock.getRegistryName().toString().split(":")[1].split("_")[0];
                    ModelFile ore = models().withExistingParent("block/ore/" + stoneVariant + "_gem", modId + ":block/ore/" + stoneVariant + "_gem");
                    simpleBlock(oreBlock , ore);
                }
            }
        }
    }

    public void simpleBlock(Supplier<? extends Block> blockSupplier) {
        simpleBlock(blockSupplier.get());
    }

    @Override
    public void simpleBlock(Block block, ModelFile model) {
        super.simpleBlock(block, model);
        this.simpleBlockItem(block, model);
    }
}
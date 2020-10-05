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

import java.util.function.Function;

/**
 * Created by MightyDanp on 10/2/2020.
 */

public class BlockStates extends BlockStateProvider {


    public BlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Ref.mod_id, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        for(MaterialHandler material : MaterialHandler.registeredMaterials) {
            materialHandlerHelper(material);
        }
    }

    private void materialHandlerHelper(MaterialHandler material) {
        for(RegistryObject<Block> blockRegistered : material.blockOre){
            Block oreBlock = blockRegistered.get();
            VariantBlockStateBuilder builder = getVariantBuilder(oreBlock);
            if(oreBlock instanceof BlockOre){
                for(Object flag :material.flags){
                    if(flag == EnumMaterialFlags.ORE){
                        String modId= oreBlock.getRegistryName().toString().split(":")[0];
                        String stoneVariant = oreBlock.getRegistryName().toString().split(":")[0].split("_")[0];
                        ModelFile ore = new ModelFile.UncheckedModelFile(new ResourceLocation(modId + ":" + "block/ore/" + stoneVariant + "_ore"));
                        builder.forAllStates(state -> ConfiguredModel.builder().modelFile(ore).build());

                    }
                }
            }
        }
    }
}
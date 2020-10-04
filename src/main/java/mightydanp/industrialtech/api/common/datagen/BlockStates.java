package mightydanp.industrialtech.api.common.datagen;

import mightydanp.industrialtech.api.common.handler.MaterialHandler;
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
        registerOreBlocksAndItems();
    }

    private void registerOreBlocksAndItems(){
        for(MaterialHandler material : MaterialHandler.registeredMaterials) {
            for(RegistryObject<Block> block : material.blockOre) {
                String blockName = block.get().getRegistryName().toString().split(":")[1];
                String modId = block.get().getRegistryName().toString().split(":")[0];
                ResourceLocation txt = new ResourceLocation(Ref.mod_id, "block/" + blockName);
                BlockModelBuilder modelFirstblock = models().withExistingParent("", new ResourceLocation(modId, "block/ore" + blockName.split("_")[0] + "_normal_ore"));
                block(block.get(), state-> {return modelFirstblock;});
            }
        }
    }

    private void block(Block block, Function<BlockState, ModelFile> modelFunc) {
        getVariantBuilder(block)
                .forAllStates(state -> {
                    Direction dir = state.get(BlockStateProperties.FACING);
                    return ConfiguredModel.builder()
                            .modelFile(modelFunc.apply(state))
                            .build();
                });
    }
}
package mightydanp.techapi.common.resources.data.data;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;

public class BlockStateVariantData {
    private final ResourceLocation name;

    private VariantBlockStateBuilder blockState;


    public BlockStateVariantData(ResourceLocation name) {
        this.name = name;
    }

    public JsonObject createJson(){
        return blockState.toJson();
    }

    public BlockStateVariantData createBlockState(VariantBlockStateBuilder blockState) {
        this.blockState = blockState;
        return this;
    }

    public VariantBlockStateBuilder getBlockState() {
        return blockState;
    }
}

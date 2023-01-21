package mightydanp.techapi.common.resources.asset.data;

import com.google.gson.JsonObject;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.IGeneratedBlockstate;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;

import java.lang.reflect.Constructor;

public class BlockStateData {
    private IGeneratedBlockstate blockState;

    public VariantBlockStateBuilder getVariantBuilder(Block b) throws Exception {
        Constructor<VariantBlockStateBuilder> constructor = VariantBlockStateBuilder.class.getDeclaredConstructor(Block.class);
        constructor.setAccessible(true);

        return constructor.newInstance(b);
    }

    public MultiPartBlockStateBuilder getMultipartBuilder(Block b) throws Exception {
        Constructor<MultiPartBlockStateBuilder> constructor = MultiPartBlockStateBuilder.class.getDeclaredConstructor(Block.class);
        constructor.setAccessible(true);

        return constructor.newInstance(b);
    }

    public BlockStateData setBlockState(IGeneratedBlockstate generatedBlockState){
        blockState = generatedBlockState;
        return this;
    }

    public JsonObject createJson(){
        return blockState.toJson();
    }



}

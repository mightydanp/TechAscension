package mightydanp.industrialtech.api.common.datagen;

import com.google.gson.JsonElement;
import net.minecraft.data.BlockModelProvider;
import net.minecraft.data.IFinishedBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Created by MightyDanp on 11/21/2021.
 */
public class GenBlockModel extends BlockModelProvider {
    public GenBlockModel(Consumer<IFinishedBlockState> p_i232514_1_, BiConsumer<ResourceLocation, Supplier<JsonElement>> p_i232514_2_, Consumer<Item> p_i232514_3_) {
        super(p_i232514_1_, p_i232514_2_, p_i232514_3_);
    }
}

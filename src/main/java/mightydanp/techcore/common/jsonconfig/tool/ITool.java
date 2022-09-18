package mightydanp.techcore.common.jsonconfig.tool;

import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;
import java.util.Map;

public interface ITool {
    String getName();

    Map<Integer, List<List<Ingredient>>> getAssembleStepsItems();

}

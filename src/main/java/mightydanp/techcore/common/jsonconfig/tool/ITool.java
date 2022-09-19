package mightydanp.techcore.common.jsonconfig.tool;

import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;
import java.util.Map;

public interface ITool {
    String getName();
    Integer getUseDamage();

    List<String> getEffectiveOn();

    Map<Integer, List<Map<Ingredient, Integer>>> getAssembleStepsItems();

    List<Map<Ingredient, Integer>> getDisassembleItems();

}
